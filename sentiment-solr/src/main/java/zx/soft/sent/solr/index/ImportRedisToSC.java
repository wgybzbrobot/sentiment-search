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
 * 将Redis消息队列中的数据所引到SolrCloud：hefei09,hefei10运行
 *
 * 运行目录：/home/zxdfs/run-work/index
 * 运行命令： cd sentiment-solr
 *         bin/ctl.sh start importRedisToSC
 *
 * @author wanggang
 *
 */
public class ImportRedisToSC {

	private static Logger logger = LoggerFactory.getLogger(ImportRedisToSC.class);

	private final IndexCloudSolr indexCloudSolr;

	private final RedisMQ redisCache;
	//	private final JedisClient redisCache;

	private final RedisCacheExpired redisCacheExpired;

	public ImportRedisToSC() {
		// 7天有效期
		redisCacheExpired = new RedisCacheExpired(7 * 86400);
		indexCloudSolr = new IndexCloudSolr();
		redisCache = new RedisMQ();
		//		redisCache = new JedisClient();
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
								logger.info("id:{} firsttime is null.", tmp.getId());
							}
						}
					}
					result.add(tmp);
				}
				try {
					indexCloudSolr.addDocsToSolr(result);
				} catch (final Exception e) {
					logger.error("Exception:{}", LogbackUtil.expection2Str(e));
					// 索引提交失败，还需要将元数据写回Redis
					redisCache.addRecord(records.toArray(new String[0]));
				}
			} else {
				try {
					Thread.sleep(1_000);
				} catch (InterruptedException e) {
					// TODO
				}
			}
			logger.info("Finishing index ...");
		}
	}

	public void close() {
		indexCloudSolr.close();
		//		redisCache.close();
	}

}
