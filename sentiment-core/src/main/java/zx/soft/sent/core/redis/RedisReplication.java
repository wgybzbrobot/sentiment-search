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
@Deprecated
public class RedisReplication implements Cache {

	private static Logger logger = LoggerFactory.getLogger(RedisReplication.class);

	private static JedisPool masterPool;

	private static JedisPool slavePool;

	public RedisReplication() {
		init();
	}

	private void init() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(256);
		poolConfig.setMinIdle(64);
		poolConfig.setMaxWaitMillis(10_000);
		poolConfig.setMaxTotal(1024);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTimeBetweenEvictionRunsMillis(30000);
		Properties props = ConfigUtil.getProps("cache-config.properties");
		masterPool = new JedisPool(poolConfig, props.getProperty("redis.rp.master"), Integer.parseInt(props
				.getProperty("redis.rp.port")), 30_000, props.getProperty("redis.password"));
		slavePool = new JedisPool(poolConfig, props.getProperty("redis.rp.slave"), Integer.parseInt(props
				.getProperty("redis.rp.port")), 30_000, props.getProperty("redis.password"));
	}

	/**
	 * Master：写数据
	 */
	@Override
	public synchronized void sadd(String key, String... members) {
		Jedis jedis = getMasterJedis();
		// 下面可能导致丢失少量数据，后期需要修改
		if (jedis == null) {
			return;
		}
		try {
			//			jedis.getClient().setTimeoutInfinite();
			jedis.sadd(key, members);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				masterPool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中
			if (jedis != null && jedis.isConnected())
				masterPool.returnResource(jedis);
		}
	}

	public synchronized static Jedis getMasterJedis() {
		try {
			if (masterPool != null) {
				return masterPool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return null;
		}
	}

	/**
	 * Slave：读数据
	 */
	@Override
	public synchronized boolean sismember(String key, String member) {
		Jedis jedis = getSlaveJedis();
		if (jedis == null) {
			return Boolean.FALSE;
		}
		try {
			return jedis.sismember(key, member);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				slavePool.returnBrokenResource(jedis);
				jedis = null;
			}
			return Boolean.FALSE;
		} finally {
			if (jedis != null && jedis.isConnected())
				slavePool.returnResource(jedis);
		}
	}

	public synchronized static Jedis getSlaveJedis() {
		try {
			if (slavePool != null) {
				return slavePool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return null;
		}
	}

	/**
	 * 情况Master中的数据
	 */
	public void flushallMaster() {
		Jedis jedis = getMasterJedis();
		if (jedis == null) {
			return;
		}
		try {
			jedis.flushAll();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				masterPool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (jedis != null && jedis.isConnected())
				masterPool.returnResource(jedis);
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
