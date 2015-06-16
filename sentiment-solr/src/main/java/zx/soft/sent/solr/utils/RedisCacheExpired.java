package zx.soft.sent.solr.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.log.LogbackUtil;

/**
 * 缓存键值对信息，并提供有效期
 *
 * @author wanggang
 *
 */
public class RedisCacheExpired {

	private static Logger logger = LoggerFactory.getLogger(RedisCacheExpired.class);

	// 设置生命时间，秒
	private int expire;

	private static JedisPool pool;

	public RedisCacheExpired(int expire) {
		this.expire = expire;
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
		pool = new JedisPool(poolConfig, props.getProperty("redis.ft.server"), Integer.parseInt(props
				.getProperty("redis.ft.port")), 30_000, props.getProperty("redis.password"));
	}

	public synchronized static Jedis getJedis() {
		try {
			if (pool != null) {
				return pool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return null;
		}
	}

	/**
	 * 添加数据，members不能为空
	 */
	public synchronized void addRecord(String key, String value) {
		Jedis jedis = getJedis();
		if (jedis == null) {
			return;
		}
		try {
			jedis.set(key, value);
			jedis.expire(key, expire);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
	}

	/**
	 * 获取集合大小
	 */
	public synchronized int getSetSize() {
		int result = 0;
		Jedis jedis = getJedis();
		if (jedis == null) {
			return result;
		}
		try {
			result = jedis.keys("*").size();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取数据
	 */
	public synchronized String getRecords(String key) {
		String result = "";
		Jedis jedis = getJedis();
		if (jedis == null) {
			return result;
		}
		try {
			result = jedis.get(key);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
		return result;
	}

	public void close() {
		// 程序关闭时，需要调用关闭方法
		pool.destroy();
	}

}
