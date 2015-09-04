package zx.soft.sent.solr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import zx.soft.sent.dao.common.SentimentConstant;
import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;

public class JedisClient {

	private static Logger logger = LoggerFactory.getLogger(JedisClient.class);

	private static final ObjectMapper OBJECT_MAPPER = JsonUtils.getObjectMapper();

	private final String IP;
	private final int PORT;
	private final String PASSWORD;

	public JedisClient() {
		Properties props = ConfigUtil.getProps("cache-config.properties");
		this.IP = props.getProperty("redis.mq.server");
		this.PORT = Integer.parseInt(props.getProperty("redis.mq.port"));
		this.PASSWORD = props.getProperty("redis.password");
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
			jedis.sadd(SentimentConstant.SENTIMENT_CACHE_KEY, members);
		} catch (Exception e) {
			logger.error("Exception:{},Records'size={}.", LogbackUtil.expection2Str(e), members.length);
		} finally {
			jedis.close();
		}
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
		} finally {
			jedis.close();
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

	private Jedis getJedis() {
		try {
			Jedis jedis = new Jedis(IP, PORT);
			jedis.auth(PASSWORD);
			return jedis;
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return null;
		}
	}

}
