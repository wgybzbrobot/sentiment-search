package zx.soft.sent.core.tsdb;

import java.util.Properties;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.CacheFactory;
import zx.soft.redis.client.tsdb.TsdbReporter;
import zx.soft.utils.config.ConfigUtil;

/**
 * 舆情数据统计控制类
 *
 * @author wanggang
 *
 * TODO
 *
 */
public class SentimentReportCore {

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		Cache cache = CacheFactory.getInstance();

		Properties prop = ConfigUtil.getProps("cache-config.properties");

		TsdbReporter reporter = new TsdbReporter(prop.getProperty("tsdb.host"), Integer.parseInt(prop
				.getProperty("tsdb.port")));
		reporter.addReport(new GatherQueueReport(cache));

	}

}
