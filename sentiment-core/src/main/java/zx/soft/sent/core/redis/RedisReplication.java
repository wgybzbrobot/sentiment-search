package zx.soft.sent.core.redis;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zx.soft.redis.client.cache.Cache;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.log.LogbackUtil;

/**
 * Redis主从复制客户端
 * 
 * @author wanggang
 *
 */
public class RedisReplication implements Cache {

	private static Logger logger = LoggerFactory.getLogger(RedisReplication.class);

	private final JedisPool masterPool;

	private final JedisPool slavePool;

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
	@Override
	public void sadd(String key, String... members) {
		Jedis jedis = masterPool.getResource();
		try {
			jedis.sadd(key, members);
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
	@Override
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

	@Override
	public Long del(String... keys) {
		return null;
	}

	@Override
	public boolean exists(String key) {
		return false;
	}

	@Override
	public void eval(String script, String[] keys, String... members) {
		//
	}

	@Override
	public Long scard(String key) {
		return null;
	}

	@Override
	public Set<String> smembers(String key) {
		return null;
	}

	@Override
	public String spop(String key) {
		return null;
	}

	@Override
	public String srandmember(String key) {
		return null;
	}

	@Override
	public Set<String> srandmember(String key, int count) {
		return null;
	}

	@Override
	public Long srem(String key, String... members) {
		return null;
	}

	@Override
	public Long hset(String key, String field, String value) {
		return null;
	}

	@Override
	public String hget(String key, String field) {
		return null;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return null;
	}

	@Override
	public void close() {
		// 程序关闭时，需要调用关闭方法 
		masterPool.destroy();
		slavePool.destroy();
	}

}
