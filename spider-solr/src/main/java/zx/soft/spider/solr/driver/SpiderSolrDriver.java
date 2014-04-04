package zx.soft.spider.solr.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.spider.solr.index.ImportDataMain;

/**
 * 驱动类
 *
 */
public class SpiderSolrDriver {

	private static Logger logger = LoggerFactory.getLogger(SpiderSolrDriver.class);

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
		case "importData":
			logger.info("索引爬虫数据：： ");
			ImportDataMain.main(leftArgs);
			break;
		default:
			return;
		}

	}

}
