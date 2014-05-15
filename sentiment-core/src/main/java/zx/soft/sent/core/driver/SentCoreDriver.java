package zx.soft.sent.core.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.core.redis.OracleToRedis;

/**
 * 驱动类
 *
 */
public class SentCoreDriver {

	private static Logger logger = LoggerFactory.getLogger(SentCoreDriver.class);

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
		case "oracleToRedis":
			logger.info("将站点数据定时导入Redis中： ");
			OracleToRedis.main(leftArgs);
			break;
		default:
			return;
		}

	}

}
