package zx.soft.sent.solr.index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.Record;
import zx.soft.sent.dao.domain.WeiboOldInfo;
import zx.soft.sent.dao.sina.WeiboSina;
import zx.soft.sent.utils.checksum.CheckSumUtils;

/**
 * @author wanggang
 * 索引新浪微博数据到Solr集群中
 *
 */
public class ImportSinaDataToSC {

	private static Logger logger = LoggerFactory.getLogger(ImportSentDataToSC.class);

	private static IndexCloudSolr indexCloudSolr;

	private static WeiboSina weiboSina;

	public ImportSinaDataToSC() {
		weiboSina = new WeiboSina(MybatisConfig.ServerEnum.sentiment);
		/**
		 * 添加到CloudSolr
		 */
		logger.info("Start importing sina data to CloudSolr ...");
		indexCloudSolr = new IndexCloudSolr();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		ImportSinaDataToSC importSinaData = new ImportSinaDataToSC();
		importSinaData.indexAllTables();
		importSinaData.close();
	}

	/**
	 * 索引所有数据表数据
	 */
	public void indexAllTables() {
		List<String> tablenames = weiboSina.getAllTablenames();
		logger.info("tablenames' count=" + tablenames.size());
		int count = 0; // 续传
		for (String tablename : tablenames) {
			if (++count < 83) {
				continue; // 续传
			}
			logger.info("Indexing at tablename=" + tablename);
			indexOneTable(tablename);
		}
	}

	/**
	 * 索引一张数据表数据
	 */
	private void indexOneTable(String tablename) {
		int maxId = weiboSina.getMaxId(tablename);
		List<WeiboOldInfo> weibos = null;
		for (int i = 0; i <= maxId/IndexCloudSolr.FETCH_SIZE; i++) {
			logger.info("Indexing '" + tablename + "' at count=" + i * IndexCloudSolr.FETCH_SIZE);
			List<Record> records = new ArrayList<>();
			weibos = weiboSina.getBatchWeibos(tablename, IndexCloudSolr.FETCH_SIZE * i, IndexCloudSolr.FETCH_SIZE
					* (i + 1));
			for (WeiboOldInfo weibo : weibos) {
				records.add(transWeibo(weibo));
			}
			indexCloudSolr.addSentimentDocsToSolr(records);
		}

	}

	private Record transWeibo(WeiboOldInfo weibo) {
		return new Record.Builder(CheckSumUtils.getMD5(weibo.getWid().toString()), 0).setUsername(weibo.getUsername())
				.setRepost_count(weibo.getRepostsCount()).setComment_count(weibo.getCommentsCount())
				.setContent(weibo.getText()).setTimestamp(new Date(weibo.getCreateat() * 1000))
				.setOriginal_uid(weibo.getOusername()).setMid(weibo.getOwid().toString()).setGeo(weibo.getGeo())
				.setPic_url(weibo.getOriginalpic()).setType(weibo.getSource()).build();
	}

	public void close() {
		indexCloudSolr.close();
		logger.info("Importing sina data to CloudSolr finish!");
	}

}
