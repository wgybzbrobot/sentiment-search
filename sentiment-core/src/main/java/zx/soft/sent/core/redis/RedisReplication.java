package zx.soft.sent.core.redis;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.log.LogbackUtil;

/**
 * Redis主从复制客户端
 * 
 * @author wanggang
 *
 */
public class RedisReplication {

	private static Logger logger = LoggerFactory.getLogger(RedisReplication.class);

	private final JedisPool masterPool;

	private final JedisPool slavePool;

	public static final String SENT_KEY_INSERTED = "sent:key:inserted";

	public RedisReplication() {
		Properties props = ConfigUtil.getProps("cache-config.properties");
		masterPool = new JedisPool(new JedisPoolConfig(), props.getProperty("redis.master"), Integer.parseInt(props
				.getProperty("redis.port")), 30_000, props.getProperty("redis.password"));
		slavePool = new JedisPool(new JedisPoolConfig(), props.getProperty("redis.slave"), Integer.parseInt(props
				.getProperty("redis.port")), 30_000, props.getProperty("redis.password"));
	}

	/**
	 * Master：写数据
	 */
	public void sadd(String... members) {
		Jedis jedis = masterPool.getResource();
		try {
			jedis.sadd(SENT_KEY_INSERTED, members);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中 
			masterPool.returnResource(jedis);
		}
	}

	/**
	 * Slave：读数据
	 */
	public boolean sismember(String key, String member) {
		Jedis jedis = slavePool.getResource();
		try {
			return jedis.sismember(key, member);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return Boolean.FALSE;
		} finally {
			slavePool.returnResource(jedis);
		}
	}

	public void close() {
		// 程序关闭时，需要调用关闭方法 
		masterPool.destroy();
		slavePool.destroy();
	}

	public static void main(String[] args) {
		//
	}

}
