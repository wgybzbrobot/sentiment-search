package zx.soft.sent.control.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.cache.dao.Cache;
import zx.soft.sent.cache.factory.CacheFactory;

/**
 * 舆情数据Redis控制类
 * @author wanggang
 *
 */
public class SentimentRedisMain {

	private static Logger logger = LoggerFactory.getLogger(SentimentRedisMain.class);

	/**
	 * 如果members不在key2和key3所关联的set中，则保存到key1所关联的set中
	 */
	public static final String saddIfNotExistOthers_script = "local count = 0\n" //
			+ "for i, uid in ipairs(ARGV) do\n" //
			+ "    if redis.call('sismember', KEYS[2], uid) == 0 and redis.call('sismember', KEYS[3], uid) == 0 then\n" //
			+ "        redis.call('sadd', KEYS[1], uid)\n" //
			+ "        count = count + 1\n" //
			+ "    end\n" //
			+ "end\n" //
			+ "return count";

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		// 测试10亿个md5的key，所需要的内存、读取时间等
		Cache cache = CacheFactory.getInstance();
		logger.info("Getting cache instance successful.");
		cache.sadd("foo", "bar");

	}

}
