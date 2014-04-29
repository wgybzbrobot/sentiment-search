package zx.soft.sent.core.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		case "persistCore":
			logger.info("持久化数据到Mysql： ");
			break;
		default:
			return;
		}

	}

}
