package zx.soft.sent.solr.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.SentimentConstant;
import zx.soft.sent.solr.utils.RedisMQ;
import zx.soft.utils.time.TimeUtils;

/**
 * 定时删除微博数据：hefei08
 *
 * 注意：每天晚上执行一次，否则第二天更新的重复数据不会被保存到Solr中。
 *
 * 1、删除2周前的微博数据
 * 2、删除2个月前的其他舆情数据
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
		remove(args[0]);
	}

	public static void remove(String type) {
		if ("all".equalsIgnoreCase(type)) {
			removeSentimentData();
			removeRedisReplicationData();
		} else if ("redis".equalsIgnoreCase(type)) {
			removeRedisReplicationData();
		}
		logger.info("Finish Removing expired data ...");
	}

	private static void removeSentimentData() {
		logger.info("Start Removing expired data ...");
		QueryCore queryCore = new QueryCore();
		// 删除15天之前的微博数据
		String end = TimeUtils.transToSolrDateStr(System.currentTimeMillis() - 15 * 86400_000L);
		String query = "platform:3 AND lasttime:[1970-01-01T00:00:00Z TO " + end + "]";
		queryCore.deleteQuery(query);
		// 删除2个月之前的其他舆情数据
		end = TimeUtils.transToSolrDateStr(System.currentTimeMillis() - 60 * 86400_000L);
		query = "lasttime:[1970-01-01T00:00:00Z TO " + end + "]";
		queryCore.deleteQuery(query);
		queryCore.close();
	}

	private static void removeRedisReplicationData() {
		// 情况Redis中的ID去重数据
		logger.info("Start Removing redis-replication data ...");
		RedisMQ redisMQ = new RedisMQ();
		redisMQ.deleteKey(SentimentConstant.SENT_KEY_INSERTED);
		redisMQ.close();
		logger.info("Finish Removing redis-replication data ...");
	}

}
