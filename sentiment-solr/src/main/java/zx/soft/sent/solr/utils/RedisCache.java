package zx.soft.sent.solr.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;

public class RedisCache {

	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private final JedisPool pool;

	private static final String CACHE_SENTIMENT_KEY = "cache-records";

	private static final ObjectMapper OBJECT_MAPPER = JsonUtils.getObjectMapper();

	public RedisCache() {
		Properties props = ConfigUtil.getProps("cache-sentiment.properties");
		pool = new JedisPool(new JedisPoolConfig(), props.getProperty("redis.servers"), Integer.parseInt(props
				.getProperty("redis.port")), 30_000, props.getProperty("redis.password"));
	}

	/**
	 * 添加数据
	 */
	public void addRecord(String... members) {
		Jedis jedis = pool.getResource();
		try {
			jedis.watch(CACHE_SENTIMENT_KEY);
			Transaction tx = jedis.multi();
			tx.sadd(CACHE_SENTIMENT_KEY, members);
			tx.exec();
			jedis.unwatch();
			jedis.disconnect();
			// pipeline适用于批处理，管道比事务效率高
			// 不使用dsicard会出现打开文件数太多，使用的话DISCARD without MULTI。
			//			Pipeline p = jedis.pipelined();
			//			p.sadd(CACHE_SENTIMENT_KEY, members);
			//			p.sync();// 关闭pipeline
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中 
			pool.returnResource(jedis);
		}
	}

	/**
	 * 获取集合大小
	 */
	public long getSetSize() {
		long result = 0L;
		Jedis jedis = pool.getResource();
		try {
			// 在事务和管道中不支持同步查询
			result = jedis.scard(CACHE_SENTIMENT_KEY).longValue();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			pool.returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取数据
	 */
	public List<String> getRecords() {
		List<String> records = new ArrayList<>();
		Jedis jedis = pool.getResource();
		try {
			String value = jedis.spop(CACHE_SENTIMENT_KEY);
			while (value != null) {
				records.add(value);
				value = jedis.spop(CACHE_SENTIMENT_KEY);
			}
			logger.info("Records'size = {}", records.size());
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
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
			} catch (IOException e) {
				logger.error("Record:{}", record);
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			}
		}
		return recordInfos;
	}

	public void close() {
		// 程序关闭时，需要调用关闭方法 
		pool.destroy();
	}

}
