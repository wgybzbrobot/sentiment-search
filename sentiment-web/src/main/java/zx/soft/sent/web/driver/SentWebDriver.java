package zx.soft.sent.web.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.sentiment.SearchingData;
import zx.soft.sent.web.server.SentimentIndexServer;
import zx.soft.sent.web.server.SentimentSearchServer;

/**
 * 驱动类
 *
 */
public class SentWebDriver {

	private static Logger logger = LoggerFactory.getLogger(SentWebDriver.class);

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			System.err.println("Usage: Driver <class-name>");
			System.exit(-1);
		}
		String[] leftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, leftArgs, 0, leftArgs.length);

		switch (args[0]) {
		case "sentimentIndexServer":
			logger.info("索引接口： ");
			SentimentIndexServer.main(leftArgs);
			break;
		case "sentimentSearchServer":
			logger.info("搜索接口：");
			SentimentSearchServer.main(leftArgs);
			break;
		case "searchingData":
			logger.info("搜索测试: ");
			SearchingData.main(leftArgs);
		default:
			return;
		}

	}

}
