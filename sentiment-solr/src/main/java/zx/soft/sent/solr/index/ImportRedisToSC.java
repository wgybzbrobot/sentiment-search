package zx.soft.sent.solr.index;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.solr.utils.RedisMQ;
import zx.soft.utils.log.LogbackUtil;

/**
 * hefei08运行
 * 
 * @author wanggang
 *
 */
public class ImportRedisToSC {

	private static Logger logger = LoggerFactory.getLogger(ImportRedisToSC.class);

	private final IndexCloudSolr indexCloudSolr;

	private final RedisMQ redisCache;

	public ImportRedisToSC() {
		indexCloudSolr = new IndexCloudSolr();
		redisCache = new RedisMQ();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		ImportRedisToSC importRedisToSC = new ImportRedisToSC();
		importRedisToSC.index();
	}

	public void index() {
		List<String> records;
		List<RecordInfo> recordInfos;
		while (true) {
			// 批量Add并Commit
			logger.info("Starting index ...");
			records = redisCache.getRecords();
			recordInfos = redisCache.mapper(records);
			if (records.size() > 0 && recordInfos.size() > 0) {
				indexCloudSolr.addDocsToSolr(recordInfos);
			}
			logger.info("Finishing index ...");
			// 休息2秒
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			}
		}
	}

	public void close() {
		indexCloudSolr.close();
		redisCache.close();
	}

}
