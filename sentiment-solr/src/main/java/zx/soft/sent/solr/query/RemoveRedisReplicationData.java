package zx.soft.sent.solr.query;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.core.redis.RedisReplication;

/**
 * 定时删除
 *
 * 1、删除1周前的微博数据
 * 2、删除1个月前的其他数据
 *
 * @author wanggang
 *
 */
public class RemoveRedisReplicationData {

	private static Logger logger = LoggerFactory.getLogger(RemoveRedisReplicationData.class);

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		logger.info("Start Removing redis-replication data ...");
		RedisReplication pool = new RedisReplication();
		pool.flushallMaster();
		pool.close();
		logger.info("Finish Removing redis-replication data ...");
		//		Timer timer = new Timer();
		//		timer.schedule(new RemoveTimer(), 0, 86400_000L);
	}

	public static class RemoveTimer extends TimerTask {

		@Override
		public void run() {
			logger.info("Start Removing redis-replication data ...");
			RedisReplication pool = new RedisReplication();
			pool.flushallMaster();
			pool.close();
			logger.info("Finish Removing redis-replication data ...");
		}

	}

}
