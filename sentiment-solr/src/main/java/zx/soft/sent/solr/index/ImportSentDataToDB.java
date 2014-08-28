package zx.soft.sent.solr.index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.platform.AutmSearch;
import zx.soft.sent.dao.domain.platform.Blog;
import zx.soft.sent.dao.domain.platform.Email;
import zx.soft.sent.dao.domain.platform.Forum;
import zx.soft.sent.dao.domain.platform.Information;
import zx.soft.sent.dao.domain.platform.QQGroup;
import zx.soft.sent.dao.domain.platform.Record;
import zx.soft.sent.dao.domain.platform.Reply;
import zx.soft.sent.dao.domain.platform.Weibo;
import zx.soft.sent.dao.domain.sentiment.RecordInsert;
import zx.soft.sent.dao.oracle.OracleJDBC;
import zx.soft.sent.dao.sentiment.CreateTables;
import zx.soft.sent.dao.sentiment.SentimentRecord;
import zx.soft.sent.solr.utils.ConvertToRecord;
import zx.soft.sent.utils.checksum.CheckSumUtils;
import zx.soft.sent.utils.threads.ApplyThreadPool;
import zx.soft.sent.utils.time.TimeUtils;

/**
 * 索引舆情数据到Solr集群中
 * 
 * @author wanggang
 *
 */
public class ImportSentDataToDB {

	private static Logger logger = LoggerFactory.getLogger(ImportSentDataToDB.class);

	public static int FETCH_SIZE = 1_0000;

	private final OracleJDBC dataOJDBC;

	private final ThreadPoolExecutor pool;

	public static class DBRunnable implements Runnable {

		private static final SentimentRecord sentimentRecord = new SentimentRecord(MybatisConfig.ServerEnum.sentiment);

		private final RecordInsert recordInsert;

		public DBRunnable(RecordInsert recordInsert) {
			this.recordInsert = recordInsert;
		}

		@Override
		public void run() {

			try {
				sentimentRecord.insertRecord(recordInsert);
			} catch (Exception e) {
				logger.error("Exception: " + e);
			}

		}

	}

	// SJCJ_WBL：微博，SJCJ_BKL:博客，SJCJ_LTL：论坛，SJCJ_QQQ：QQ群
	// SJCJ_YSSL：元搜索，SJCJ_ZXL：资讯，YHXX_HFXX：回复信息, SJCJ_YJL：邮件（暂无数据）
	//  "SJCJ_WBL", "SJCJ_BKL", "SJCJ_LTL", "SJCJ_QQQ", "SJCJ_YSSL", "SJCJ_ZXL", "YHXX_HFXX", "SJCJ_YJL"
	public static final String[] TYPES = { "SJCJ_BKL", "SJCJ_LTL", "SJCJ_YSSL", "SJCJ_ZXL", "YHXX_HFXX", "SJCJ_WBL" };

	public ImportSentDataToDB() {
		logger.info("Start importing data to DB ...");
		dataOJDBC = new OracleJDBC();
		pool = ApplyThreadPool.getThreadPoolExector();

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
			}
		}));
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		ImportSentDataToDB importData = new ImportSentDataToDB();
		for (int i = 0; i < TYPES.length; i++) {
			logger.info("Importing '" + TYPES[i] + "' data to DB.");
			//			logger.info("data size=" + importData.getRecordsCount(TYPES[i]));
			importData.importData(TYPES[i]);
		}

		importData.close();
	}

	/**
	 * 导入数据，按时间段查询
	 */
	public void importData(String table_name) {
		/**
		 * 获取待索引数据
		 */
		logger.info("Start retriving '" + table_name + "' data ...");

		// 获取记录总数
		int count = getRecordsCount(table_name);
		logger.info("'" + table_name + "' Records' count=" + count);

		// 获取最大最小时间
		long max_time = getMaxLasttime(table_name);
		long min_time = getMinLasttime(table_name);

		// 计算时间间隔等参数
		int fetch_count = count / FETCH_SIZE;
		if (fetch_count == 0) {
			logger.info("AllCount is less than FETCH_SIZE!");
			return;
		}
		long lastime_span = (max_time - min_time) / fetch_count;
		logger.info("fetch_count = " + fetch_count);
		logger.info("lastime_span = " + lastime_span);

		long low_time, high_time;
		String sql;
		Record record = null;
		for (int i = 0; i <= fetch_count; i++) {
			// 添加文档索引
			logger.info("Indexing '" + table_name + "' data at: " + i + "/" + fetch_count);
			// sql
			low_time = min_time + i * lastime_span;
			high_time = min_time + (i + 1) * lastime_span;
			sql = "SELECT * FROM " + table_name + " WHERE JCSJ BETWEEN to_date('"
					+ TimeUtils.transToCommonDateStr(low_time) + "','yyyy-mm-dd hh24:mi:ss') " + "AND to_date('"
					+ TimeUtils.transToCommonDateStr(high_time) + "','yyyy-mm-dd hh24:mi:ss')";
			int c = 0;
			try (ResultSet rs = dataOJDBC.query(sql);) {
				while (rs.next()) {
					c++;
					record = transData(table_name, rs);
					if (record != null) {
						pool.execute(new DBRunnable(transRecord(record)));
					}
				}
			} catch (SQLException e) {
				logger.error("indexData SQLException: " + e.getMessage());
				throw new RuntimeException(e);
			}
			logger.info("records' size=" + c);
		}

		logger.info("Retriving '" + table_name + "' data finish!");
	}

	private RecordInsert transRecord(Record record) {

		return new RecordInsert.Builder(CreateTables.SENT_TABLE + CheckSumUtils.getCRC32(record.getId())
				% CreateTables.MAX_TABLE_NUM, record.getId(), record.getPlatform()).setMid(record.getMid())
				.setUsername(record.getUsername()).setNickname(record.getNickname())
				.setOriginal_id(record.getOriginal_id()).setOriginal_uid(record.getOriginal_uid())
				.setOriginal_name(record.getOriginal_name()).setOriginal_title(record.getOriginal_title())
				.setOriginal_url(record.getOriginal_url()).setUrl(record.getUrl()).setHome_url(record.getHome_url())
				.setTitle(record.getTitle()).setType(record.getType()).setContent(record.getContent())
				.setComment_count(record.getComment_count()).setRead_count(record.getRead_count())
				.setFavorite_count(record.getFavorite_count()).setAttitude_count(record.getAttitude_count())
				.setRepost_count(record.getRepost_count()).setVideo_url(record.getVideo_url())
				.setPic_url(record.getPic_url()).setVoice_url(record.getVoice_url())
				.setTimestamp(record.getTimestamp() == null ? 0 : record.getTimestamp().getTime() / 1000)
				.setSource_id(record.getSource_id())
				.setLasttime(record.getLasttime() == null ? 0 : record.getLasttime().getTime() / 1000)
				.setServer_id(record.getServer_id()).setIdentify_id(record.getIdentify_id())
				.setIdentify_md5(record.getIdentify_md5()).setKeyword(record.getKeyword())
				.setFirst_time(record.getFirst_time() == null ? 0 : record.getFirst_time().getTime() / 1000)
				.setUpdate_time(record.getUpdate_time() == null ? 0 : record.getUpdate_time().getTime() / 1000)
				.setIp(record.getIp()).setLocation(record.getLocation()).setGeo(record.getGeo())
				.setReceive_addr(record.getReceive_addr()).setAppend_addr(record.getAppend_addr())
				.setSend_addr(record.getSend_addr()).setSource_name(record.getSource_name())
				.setSource_type(record.getSource_type()).setCountry_code(record.getCountry_code())
				.setLocation_code(record.getLocation_code()).setProvince_code(record.getProvince_code())
				.setCity_code(record.getCity_code()).build();
	}

	/**
	 * 获取最小记录时间
	 */
	private long getMinLasttime(String table_name) {
		try (ResultSet rs = dataOJDBC.query("SELECT MIN(JCSJ) AS min_time FROM " + table_name);) {
			if (rs.next()) {
				return rs.getTimestamp("min_time").getTime();
			} else {
				return 0;
			}
		} catch (SQLException e) {
			logger.error("getMinLasttime SQLException: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取最大记录时间
	 */
	private long getMaxLasttime(String table_name) {
		try (ResultSet rs = dataOJDBC.query("SELECT MAX(JCSJ) AS max_time FROM " + table_name);) {
			if (rs.next()) {
				return rs.getTimestamp("max_time").getTime();
			} else {
				return 0;
			}
		} catch (SQLException e) {
			logger.error("getMaxLasttime SQLException: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取数据表记录数
	 */
	private int getRecordsCount(String table_name) {
		try (ResultSet rscount = dataOJDBC.query("SELECT COUNT(*) as ct FROM " + table_name);) {
			if (rscount.next()) {
				return rscount.getInt("ct");
			} else {
				return 0;
			}
		} catch (SQLException e) {
			logger.error("getRecordsCount SQLException: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据表名转化数据
	 */
	private Record transData(String table_name, ResultSet rs) {
		switch (table_name) {
		case "SJCJ_WBL":
			return ConvertToRecord.weiboToRecord(getWeiboData(rs));
		case "SJCJ_BKL":
			return ConvertToRecord.blogToRecord(getBlogData(rs));
		case "SJCJ_LTL":
			return ConvertToRecord.forumToRecord(getForumData(rs));
		case "SJCJ_QQQ":
			return ConvertToRecord.qQGroupToRecord(getQQGroupData(rs));
		case "SJCJ_YSSL":
			return ConvertToRecord.autmSearchToRecord(getAutmSearchData(rs));
		case "SJCJ_ZXL":
			return ConvertToRecord.informationToRecord(getInformationData(rs));
		case "YHXX_HFXX":
			return ConvertToRecord.replyToRecord(getReplyData(rs));
		case "SJCJ_YJL":
			return ConvertToRecord.emailToRecord(getEmailData(rs));
		default:
			return null;
		}
	}

	/**
	 * 返回邮件数据
	 */
	private Email getEmailData(ResultSet rs) {
		try {
			if (rs.getString("QJID") == null) {
				logger.error("Email data error at QJID=null");
				return null;
			}
			return new Email.Builder(rs.getString("QJID"), rs.getString("FJRDZ") == null ? "" : rs.getString("FJRDZ"))
					.setID(rs.getString("ID") == null ? "" : rs.getString("ID"))
					.setSJRDZ(rs.getString("SJRDZ") == null ? "" : rs.getString("SJRDZ"))
					.setCSRDZ(rs.getString("CSRDZ") == null ? "" : rs.getString("CSRDZ"))
					.setYJBT(rs.getString("YJBT") == null ? "" : rs.getString("YJBT"))
					.setYJLR(rs.getString("YJLR") == null ? "" : rs.getString("YJLR"))
					.setLX(rs.getString("LX") == null ? "" : rs.getString("LX"))
					.setFSSJ(new Date(rs.getTimestamp("FSSJ").getTime()))
					.setFXSJ(new Date(rs.getTimestamp("FXSJ").getTime()))
					.setGXSJ(new Date(rs.getTimestamp("GXSJ").getTime()))
					.setJCSJ(new Date(rs.getTimestamp("JCSJ").getTime())).setLY(rs.getInt("LY")).setLZ(rs.getInt("LZ"))
					.setBZ(rs.getInt("BZ")).build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回回复信息数据
	 */
	private Reply getReplyData(ResultSet rs) {
		try {
			if (rs.getString("MD5") == null) {
				logger.error("Reply data error at MD5=null");
				return null;
			}
			return new Reply.Builder(rs.getString("HFID") == null ? "" : rs.getString("HFID"),
					rs.getString("URL") == null ? "" : rs.getString("URL"))
					.setHFFID(rs.getString("HFFID") == null ? "" : rs.getString("HFFID"))
					.setYHZH(rs.getString("YHZH") == null ? "" : rs.getString("YHZH"))
					.setHFRZY(rs.getString("HFRZY") == null ? "" : rs.getString("HFRZY"))
					.setHFLR(rs.getString("HFLR") == null ? "" : rs.getString("HFLR"))
					.setSPURL(rs.getString("SPURL") == null ? "" : rs.getString("SPURL"))
					.setTPURL(rs.getString("TPURL") == null ? "" : rs.getString("TPURL"))
					.setYPURL(rs.getString("YPURL") == null ? "" : rs.getString("YPURL"))
					.setHFZT(rs.getString("HFZT") == null ? "" : rs.getString("HFZT"))
					.setGSD(rs.getString("GSD") == null ? "" : rs.getString("GSD"))
					.setMD5(rs.getString("MD5") == null ? "" : rs.getString("MD5"))
					.setDQURL(rs.getString("DQURL") == null ? "" : rs.getString("DQURL"))
					.setIP(rs.getString("IP") == null ? "" : rs.getString("IP"))
					.setIPDZ(rs.getString("IPDZ") == null ? "" : rs.getString("IPDZ"))
					.setSCSJ(new Date(rs.getTimestamp("SCSJ").getTime()))
					.setJCSJ(new Date(rs.getTimestamp("JCSJ").getTime()))
					.setGXSJ(new Date(rs.getTimestamp("GXSJ").getTime())).setLY(rs.getInt("LY")).setLZ(rs.getInt("LZ"))
					.setBZ(rs.getInt("BZ")).build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回资讯数据
	 */
	private Information getInformationData(ResultSet rs) {
		try {
			if (rs.getString("ZXDZ") == null) {
				logger.error("Information data error at ZXDZ=null");
				return null;
			}
			return new Information.Builder(rs.getString("ZXDZ"), rs.getString("FBYH") == null ? ""
					: rs.getString("FBYH")).setZXDZ(rs.getString("ZXDZ") == null ? "" : rs.getString("ZXDZ"))
					.setZXBT(rs.getString("ZXBT") == null ? "" : rs.getString("ZXBT"))
					.setZXLR(rs.getString("ZXLR") == null ? "" : rs.getString("ZXLR"))
					.setWZLY(rs.getString("WZLY") == null ? "" : rs.getString("WZLY"))
					.setSPURL(rs.getString("SPURL") == null ? "" : rs.getString("SPURL"))
					.setTPURL(rs.getString("TPURL") == null ? "" : rs.getString("TPURL"))
					.setYPURL(rs.getString("YPURL") == null ? "" : rs.getString("YPURL"))
					.setIP(rs.getString("IP") == null ? "" : rs.getString("IP"))
					.setIPDZ(rs.getString("IPDZ") == null ? "" : rs.getString("IPDZ"))
					.setFBSJ(new Date(rs.getTimestamp("FBSJ").getTime()))
					.setJCSJ(new Date(rs.getTimestamp("JCSJ").getTime()))
					.setGXSJ(new Date(rs.getTimestamp("GXSJ").getTime())).setLY(rs.getInt("LY")).setLZ(rs.getInt("LZ"))
					.setBZ(rs.getInt("BZ")).setPLS(rs.getInt("PLS")).setZBS(rs.getInt("ZBS")).setYDS(rs.getInt("YDS"))
					.build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回元搜索数据
	 */
	private AutmSearch getAutmSearchData(ResultSet rs) {
		try {
			if (rs.getString("SSDZ") == null) {
				logger.error("AutmSearch data error at SSDZ=null");
				return null;
			}
			return new AutmSearch.Builder(rs.getString("SSDZ"), rs.getString("FBYH") == null ? ""
					: rs.getString("FBYH")).setSSBT(rs.getString("SSBT") == null ? "" : rs.getString("SSBT"))
					.setSSNR(rs.getString("SSNR") == null ? "" : rs.getString("SSNR"))
					.setSPURL(rs.getString("SPURL") == null ? "" : rs.getString("SPURL"))
					.setTPURL(rs.getString("TPURL") == null ? "" : rs.getString("TPURL"))
					.setYPURL(rs.getString("YPURL") == null ? "" : rs.getString("YPURL"))
					.setGJC(rs.getString("GJC") == null ? "" : rs.getString("GJC"))
					.setIP(rs.getString("IP") == null ? "" : rs.getString("IP"))
					.setIPDZ(rs.getString("IPDZ") == null ? "" : rs.getString("IPDZ")).setLY(rs.getInt("LY"))
					.setLZ(rs.getInt("LZ")).setBZ(rs.getInt("BZ")).setFBSJ(new Date(rs.getTimestamp("FBSJ").getTime()))
					.setJCSJ(new Date(rs.getTimestamp("JCSJ").getTime()))
					.setGXSJ(new Date(rs.getTimestamp("GXSJ").getTime())).build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回QQ群数据
	 */
	private QQGroup getQQGroupData(ResultSet rs) {
		try {
			if (rs.getString("ID") == null) {
				logger.error("QQGroup data error at ID=null");
				return null;
			}
			return new QQGroup.Builder(rs.getString("QH") == null ? "" : rs.getString("QH"),
					rs.getString("FBYH") == null ? "" : rs.getString("FBYH")).setID(rs.getString("ID"))
					.setMC(rs.getString("MC") == null ? "" : rs.getString("MC"))
					.setGG(rs.getString("GG") == null ? "" : rs.getString("GG"))
					.setYHNC(rs.getString("YHNC") == null ? "" : rs.getString("YHNC"))
					.setFBLR(rs.getString("FBLR") == null ? "" : rs.getString("FBLR"))
					.setFBSJ(new Date(rs.getTimestamp("FBSJ").getTime())).setLY(rs.getInt("LY")).setLZ(rs.getInt("LZ"))
					.setBZ(rs.getInt("BZ")).setJCSJ(new Date(rs.getTimestamp("JCSJ").getTime())).build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回论坛数据
	 */
	private Forum getForumData(ResultSet rs) {
		try {
			if (rs.getString("WYDZ") == null) {
				logger.error("Forum data error at WYDZ=null");
				return null;
			}
			return new Forum.Builder(rs.getString("WYDZ"), rs.getString("FBYH") == null ? "" : rs.getString("FBYH"))
					.setFXSJ(new Date(rs.getTimestamp("FXSJ").getTime()))
					.setGXSJ(new Date(rs.getTimestamp("GXSJ").getTime())).setLY(rs.getInt("LY"))
					.setJCSJ(new Date(rs.getTimestamp("JCSJ").getTime())).setLZ(rs.getInt("LZ")).setBZ(rs.getInt("BZ"))
					.setIP(rs.getString("IP") == null ? "" : rs.getString("IP"))
					.setIPDZ(rs.getString("IPDZ") == null ? "" : rs.getString("IPDZ"))
					.setBKMC(rs.getString("BKMC") == null ? "" : rs.getString("BKMC"))
					.setWYBT(rs.getString("WYBT") == null ? "" : rs.getString("WYBT"))
					.setWYLR(rs.getString("WYLR") == null ? "" : rs.getString("WYLR"))
					.setSPURL(rs.getString("SPURL") == null ? "" : rs.getString("SPURL"))
					.setTPURL(rs.getString("TPURL") == null ? "" : rs.getString("TPURL"))
					.setYPURL(rs.getString("YPURL") == null ? "" : rs.getString("YPURL"))
					.setFBSJ(new Date(rs.getTimestamp("FBSJ").getTime()))
					.setGJC(rs.getString("GJC") == null ? "" : rs.getString("GJC")).setLLL(rs.getInt("LLL"))
					.setGTL(rs.getInt("GTL")).build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回博客数据
	 */
	private Blog getBlogData(ResultSet rs) {
		try {
			if (rs.getString("BKDZ") == null) {
				logger.error("Blog data error at BKDZ=null");
				return null;
			}
			return new Blog.Builder(rs.getString("BKID") == null ? "" : rs.getString("BKID"), rs.getString("BKDZ"))
					.setBKBT(rs.getString("BKBT") == null ? "" : rs.getString("BKBT"))
					.setBKLR(rs.getString("BKLR") == null ? "" : rs.getString("BKLR"))
					.setYHM(rs.getString("YHM") == null ? "" : rs.getString("YHM"))
					.setFBYH(rs.getString("FBYH") == null ? "" : rs.getString("FBYH")).setPLS(rs.getInt("PLS"))
					.setYDS(rs.getInt("YDS")).setSCS(rs.getInt("SCS")).setXHS(rs.getInt("XHS"))
					.setZZS(rs.getInt("ZZS")).setSPURL(rs.getString("ZZS") == null ? "" : rs.getString("ZZS"))
					.setTPURL(rs.getString("TPURL") == null ? "" : rs.getString("TPURL"))
					.setYPURL(rs.getString("YPURL") == null ? "" : rs.getString("YPURL"))
					.setFBSJ(new Date(rs.getTimestamp("FBSJ").getTime())).setLY(rs.getInt("LY"))
					.setJCSJ(new Date(rs.getTimestamp("JCSJ").getTime())).setLZ(rs.getInt("LZ")).setBZ(rs.getInt("BZ"))
					.setIP(rs.getString("IP") == null ? "" : rs.getString("IP"))
					.setGJC(rs.getString("GJC") == null ? "" : rs.getString("GJC"))
					.setGXSJ(new Date(rs.getTimestamp("GXSJ").getTime()))
					.setIPDZ(rs.getString("IPDZ") == null ? "" : rs.getString("IPDZ")).build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回微薄数据
	 */
	private Weibo getWeiboData(ResultSet rs) {
		try {
			if (rs.getString("WBDZ") == null) {
				logger.error("Weibo data error at WBDZ=null");
				return null;
			}
			return new Weibo.Builder(rs.getString("WBDZ"), rs.getString("FBYH") == null ? "" : rs.getString("FBYH"))
					.setWBLR(rs.getString("WBLR") == null ? "" : Jsoup.parse(rs.getString("WBLR"), "UTF-8").text())
					.setSPURL(rs.getString("SPURL") == null ? "" : rs.getString("SPURL"))
					.setTPURL(rs.getString("TPURL") == null ? "" : rs.getString("TPURL"))
					.setYPURL(rs.getString("YPURL") == null ? "" : rs.getString("YPURL"))
					.setFBSJ(new Date(rs.getTimestamp("FBSJ").getTime())).setPLS(rs.getInt("PLS"))
					.setZBS(rs.getInt("ZBS")).setYDS(rs.getInt("YDS"))
					.setGJC(rs.getString("GJC") == null ? "" : rs.getString("GJC")).setLY(rs.getInt("LY"))
					.setJCSJ(new Date(rs.getTimestamp("JCSJ").getTime())).setLZ(rs.getInt("LZ")).setBZ(rs.getInt("BZ"))
					.setIP(rs.getString("IP") == null ? "" : rs.getString("IP"))
					.setGXSJ(new Date(rs.getTimestamp("GXSJ").getTime()))
					.setIPDZ(rs.getString("IPDZ") == null ? "" : rs.getString("IPDZ")).build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		dataOJDBC.close();
		pool.shutdown();
		try {
			pool.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		logger.info("Importing data to CloudSolr finish!");
	}

}
