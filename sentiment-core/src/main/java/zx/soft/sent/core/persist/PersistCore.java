package zx.soft.sent.core.persist;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.CacheFactory;
import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.dao.sentiment.SentimentRecord;
import zx.soft.utils.threads.ApplyThreadPool;

/**
 * 持久化到Mysql
 * 
 * @author wanggang
 *
 */
public class PersistCore {

	private static Logger logger = LoggerFactory.getLogger(PersistCore.class);

	private final Cache cache;

	private final SentimentRecord sentRecord;

	final ThreadPoolExecutor pool;

	public PersistCore() {

		cache = CacheFactory.getInstance();
		sentRecord = new SentimentRecord(MybatisConfig.ServerEnum.sentiment);
		pool = ApplyThreadPool.getThreadPoolExector(64);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
				logger.error("Thread Pool is shudown.");
			}
		}));

	}

	public void persist(RecordInfo record) {

		//		TsdbReporter reporter = new TsdbReporter(Constant.getTsdbHost(), Constant.getTsdbPort());
		//		reporter.addReport(new GatherQueueReport(cache));

		if (!pool.isShutdown()) {
			try {
				pool.execute(new PersistRunnable(cache, sentRecord, record));
			} catch (RuntimeException e) {
				logger.error("Persist error Record:{}", record);
				logger.error("Exception:{}, StackTrace:{}", e.getMessage(), e.getStackTrace());
			}
		}

	}

	public void close() {
		pool.shutdown();
		try {
			pool.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			cache.close();
		}
	}

}
