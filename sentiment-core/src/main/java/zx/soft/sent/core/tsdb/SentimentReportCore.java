package zx.soft.sent.core.tsdb;

import java.util.Properties;

import zx.soft.sent.cache.dao.Cache;
import zx.soft.sent.cache.factory.CacheFactory;
import zx.soft.sent.cache.tsdb.TsdbReporter;
import zx.soft.sent.utils.config.ConfigUtil;

/**
 * 舆情数据统计控制类
 * @author wanggang
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
