package zx.soft.sent.solr.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.index.ImportSentimentData;
import zx.soft.sent.solr.index.ImportSinaData;

/**
 * 驱动类
 *
 */
public class SentSolrDriver {

	private static Logger logger = LoggerFactory.getLogger(SentSolrDriver.class);

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
		case "importSentimentData":
			logger.info("索引舆情数据： ");
			ImportSentimentData.main(leftArgs);
			break;
		case "importSinaData":
			logger.info("索引新浪微博数据：");
			ImportSinaData.main(leftArgs);
			break;
		default:
			return;
		}

	}

}
