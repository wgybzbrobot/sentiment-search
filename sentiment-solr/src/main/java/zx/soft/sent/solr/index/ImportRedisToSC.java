package zx.soft.sent.solr.index;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.solr.utils.RedisCacheExpired;
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

	private final RedisCacheExpired redisCacheExpired;

	public ImportRedisToSC() {
		// 三天有效期
		redisCacheExpired = new RedisCacheExpired(3 * 86400);
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
		List<RecordInfo> result;
		String firstTime = null;
		while (true) {
			// 批量Add并Commit
			logger.info("Starting index ...");
			records = redisCache.getRecords();
			recordInfos = redisCache.mapper(records);
			if (records.size() > 0 && recordInfos.size() > 0) {
				result = new ArrayList<>();
				for (RecordInfo tmp : recordInfos) {
					// 如果不是微博信息，则更新首次监测时间
					if (tmp.getPlatform() != 3) {
						// 从Redis中获取首次检测时间
						firstTime = redisCacheExpired.getRecords(tmp.getId());
						if (firstTime != null) {
							tmp.setFirst_time(Long.parseLong(firstTime));
						} else {
							if (tmp.getFirst_time() != 0) {
								redisCacheExpired.addRecord(tmp.getId(), tmp.getFirst_time() + "");
							} else {
								logger.info("id:{} firsttime is null.", tmp.getFirst_time());
							}
						}
					}
					result.add(tmp);
				}
				indexCloudSolr.addDocsToSolr(result);
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
