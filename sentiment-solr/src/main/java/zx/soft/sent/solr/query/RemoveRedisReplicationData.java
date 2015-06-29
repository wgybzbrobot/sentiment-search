package zx.soft.sent.solr.query;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.SentimentConstant;
import zx.soft.sent.solr.utils.RedisMQ;

/**
 * 定时删除Redis中的缓存id数据（sent.cache.records）：hefei08
 *
 * 1、删除1周前的微博数据
 * 2、删除1个月前的其他数据
 *
 * 运行目录：/home/zxdfs/run-work/remove/master-redis
 * 运行命令：cd sentiment-solr
 *        ./timer-remove-master-redis.sh &
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
		Timer timer = new Timer();
		timer.schedule(new RemoveTimer(), 0, 86400_000L);
	}

	public static class RemoveTimer extends TimerTask {

		@Override
		public void run() {
			logger.info("Start Removing redis-replication data ...");
			RedisMQ redisMQ = new RedisMQ();
			redisMQ.deleteKey(SentimentConstant.SENT_KEY_INSERTED);
			logger.info("Finish Removing redis-replication data ...");
		}

	}

}
