package zx.soft.sent.solr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zx.soft.sent.dao.common.SentimentConstant;
import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;

public class RedisMQ {

	private static Logger logger = LoggerFactory.getLogger(RedisMQ.class);

	private static JedisPool pool;

	private static final ObjectMapper OBJECT_MAPPER = JsonUtils.getObjectMapper();

	public RedisMQ() {
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
		pool = new JedisPool(poolConfig, props.getProperty("redis.mq.server"), Integer.parseInt(props
				.getProperty("redis.mq.port")), 30_000, props.getProperty("redis.password"));
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
	public synchronized void addRecord(String... members) {
		Jedis jedis = getJedis();
		if (jedis == null) {
			return;
		}
		try {
			//			jedis.watch(SentimentConstant.SENTIMENT_CACHE_KEY);
			//			Transaction tx = jedis.multi();
			//			tx.sadd(SentimentConstant.SENTIMENT_CACHE_KEY, members);
			jedis.sadd(SentimentConstant.SENTIMENT_CACHE_KEY, members);
			//			tx.exec();
			//			jedis.unwatch();
			// pipeline适用于批处理，管道比事务效率高
			// 不使用dsicard会出现打开文件数太多，使用的话DISCARD without MULTI。
			//			Pipeline p = jedis.pipelined();
			//			p.sadd(CACHE_SENTIMENT_KEY, members);
			//			p.sync();// 关闭pipeline
		} catch (Exception e) {
			logger.error("Exception:{},Records'size={}.", LogbackUtil.expection2Str(e), members.length);
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
	public synchronized long getSetSize() {
		long result = 0L;
		Jedis jedis = getJedis();
		if (jedis == null) {
			return result;
		}
		try {
			// 在事务和管道中不支持同步查询
			result = jedis.scard(SentimentConstant.SENTIMENT_CACHE_KEY).longValue();
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
	public synchronized List<String> getRecords() {
		List<String> records = new ArrayList<>();
		Jedis jedis = getJedis();
		if (jedis == null) {
			return records;
		}
		try {
			String value;
			for (int i = 0; i < 10000; i++) {
				value = jedis.spop(SentimentConstant.SENTIMENT_CACHE_KEY);
				if (value != null) {
					records.add(value);
				} else {
					break;
				}
			}
			logger.info("Records'size = {}", records.size());
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
		return records;
	}

	/**
	 * 将数据从String映射到Object
	 */
	public List<RecordInfo> mapper(List<String> records) {
		List<RecordInfo> recordInfos = new ArrayList<>();
		for (String record : records) {
			try {
				recordInfos.add(OBJECT_MAPPER.readValue(record, RecordInfo.class));
			} catch (Exception e) {
				logger.error("Record:{}", record);
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			}
		}
		return recordInfos;
	}

	public synchronized void sadd(String key, String... members) {
		Jedis jedis = getJedis();
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
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
	}

	public synchronized boolean sismember(String key, String member) {
		Jedis jedis = getJedis();
		if (jedis == null) {
			return Boolean.FALSE;
		}
		try {
			return jedis.sismember(key, member);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
			return Boolean.FALSE;
		} finally {
			if (jedis != null && jedis.isConnected())
				pool.returnResource(jedis);
		}
	}

	public void deleteKey(String key) {
		Jedis jedis = getJedis();
		if (jedis != null) {
			try {
				jedis.del(key);
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
		}
	}

	public void close() {
		// 程序关闭时，需要调用关闭方法
		pool.destroy();
	}

}
