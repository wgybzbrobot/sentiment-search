package zx.soft.spider.solr.index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.spider.solr.domain.AutmSearch;
import zx.soft.spider.solr.domain.Blog;
import zx.soft.spider.solr.domain.Email;
import zx.soft.spider.solr.domain.Forum;
import zx.soft.spider.solr.domain.Information;
import zx.soft.spider.solr.domain.QQGroup;
import zx.soft.spider.solr.domain.Record;
import zx.soft.spider.solr.domain.Reply;
import zx.soft.spider.solr.domain.Weibo;
import zx.soft.spider.solr.oracle.DataOJDBC;
import zx.soft.spider.solr.utils.ConvertToRecord;

public class ImportDataMain {

	private static Logger logger = LoggerFactory.getLogger(ImportDataMain.class);

	private static IndexCloudSolr indexCloudSolr;

	// SJCJ_WBL：微薄，SJCJ_BKL:博客，SJCJ_LTL：论坛，SJCJ_QQQ：QQ群
	// SJCJ_YSSL：元搜索，SJCJ_ZXL：资讯，YHXX_HFXX：回复信息, SJCJ_YJL：邮件（暂无数据）
	//  "SJCJ_WBL", "SJCJ_BKL", "SJCJ_LTL", "SJCJ_QQQ", "SJCJ_YSSL", "SJCJ_ZXL", "YHXX_HFXX", "SJCJ_YJL"
	public static final String[] TYPES = { "SJCJ_BKL", "SJCJ_LTL", "SJCJ_YSSL", "SJCJ_ZXL", "YHXX_HFXX", "SJCJ_WBL" };

	public ImportDataMain() {
		/**
		 * 添加到CloudSolr
		 */
		logger.info("Start importing data to CloudSolr ...");
		indexCloudSolr = new IndexCloudSolr();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		ImportDataMain importData = new ImportDataMain();
		for (int i = 0; i < TYPES.length; i++) {
			logger.info("Importing '" + TYPES[i] + "' data to CloudSolr.");
			logger.info("data size=" + importData.getRecordsCount(TYPES[i]));
			importData.indexData(TYPES[i]);
		}

		importData.close();
	}

	public void close() {
		indexCloudSolr.close();
		logger.info("Importing data to CloudSolr finish!");
	}

	/**
	 * 索引数据
	 */
	public void indexData(String table_name) {
		/**
		 * 获取待索引数据
		 */
		logger.info("Start retriving '" + table_name + "' data ...");

		// 获取记录总数
		int count = getRecordsCount(table_name);
		logger.info("'" + table_name + "' Records' count=" + count);

		DataOJDBC dataOJDBC = new DataOJDBC();
		ResultSet rs = null;
		Record record = null;
		for (int i = 0; i <= count / IndexCloudSolr.FETCH_SIZE; i++) {
			List<Record> records = new ArrayList<>();
			rs = dataOJDBC.query("SELECT * FROM (SELECT rownum AS no1,t.* FROM " + table_name + " t WHERE rownum < "
					+ IndexCloudSolr.FETCH_SIZE * (i + 1) + ") WHERE no1>= " + IndexCloudSolr.FETCH_SIZE * i);
			// 添加文档索引
			logger.info("Indexing '" + table_name + "' weibo data at: " + IndexCloudSolr.FETCH_SIZE * i);
			try {
				while (rs.next()) {
					record = transData(table_name, rs);
					if (record != null) {
						records.add(record);
					}
					if (records.size() % IndexCloudSolr.COMIIT_SIZE == 0) {
						indexCloudSolr.addDocsToSolr(records);
						records = new ArrayList<>();
					}
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			indexCloudSolr.addDocsToSolr(records);
			indexCloudSolr.commitToSolr();
		}
		dataOJDBC.close();

		logger.info("Retriving '" + table_name + "' data finish!");
	}

	/**
	 * 获取数据表记录数
	 */
	public int getRecordsCount(String table_name) {

		DataOJDBC dataOJDBC = new DataOJDBC();
		ResultSet rscount = dataOJDBC.query("SELECT COUNT(*) as ct FROM " + table_name);
		int count = 0;
		try {
			if (rscount.next()) {
				count = rscount.getInt("ct");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			dataOJDBC.close();
		}

		return count;
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
					.setID(rs.getLong("ID")).setSJRDZ(rs.getString("SJRDZ") == null ? "" : rs.getString("SJRDZ"))
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
			return new Reply.Builder(rs.getLong("HFID"), rs.getString("URL") == null ? "" : rs.getString("URL"))
					.setHFFID(rs.getLong("HFFID")).setYHZH(rs.getString("YHZH") == null ? "" : rs.getString("YHZH"))
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
			return new QQGroup.Builder(rs.getLong("QH"), rs.getLong("FBYH")).setID(rs.getString("ID"))
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
			return new Weibo.Builder(rs.getString("WBDZ"),
					rs.getString("FBYH") == null ? "" : rs.getString("FBYH"))
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

}
