package zx.soft.sent.solr.query;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.SentimentConstant;
import zx.soft.sent.solr.utils.RedisMQ;
import zx.soft.utils.time.TimeUtils;

/**
 * 定时删除微博数据：hefei08
 *
 * 1、删除1周前的微博数据
 * 2、删除1个月前的其他舆情数据
 * 3、清空Redis中的缓存id数据（sent.cache.records）
 *
 * 运行目录：/home/zxdfs/run-work/remove
 * 运行命令：cd sentiment-solr
 *        ./timer-remove.sh &
 *
 * @author wanggang
 *
 */
public class RemoveExpiredData {

	private static Logger logger = LoggerFactory.getLogger(RemoveExpiredData.class);

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		remove();
	}

	public static void runTimer() {
		Timer timer = new Timer();
		timer.schedule(new RemoveTimer(), 0, 86400_000L * 7);
	}

	public static class RemoveTimer extends TimerTask {

		@Override
		public void run() {
			remove();
		}

	}

	public static void remove() {
		logger.info("Start Removing expired data ...");
		QueryCore queryCore = new QueryCore();
		// 删除七天之前的微博数据
		String end = TimeUtils.transToSolrDateStr(System.currentTimeMillis() - 7 * 86400_000L);
		String query = "platform:3 AND lasttime:[1970-01-01T00:00:00Z TO " + end + "]";
		queryCore.deleteQuery(query);
		// 删除一个月之前的其他舆情数据
		end = TimeUtils.transToSolrDateStr(System.currentTimeMillis() - 30 * 86400_000L);
		query = "lasttime:[1970-01-01T00:00:00Z TO " + end + "]";
		queryCore.deleteQuery(query);
		queryCore.close();
		logger.info("Finish Removing expired data ...");
		// 情况Redis中的ID去重数据
		logger.info("Start Removing redis-replication data ...");
		RedisMQ redisMQ = new RedisMQ();
		redisMQ.deleteKey(SentimentConstant.SENTIMENT_CACHE_KEY);
		redisMQ.close();
		logger.info("Finish Removing redis-replication data ...");
	}

}
