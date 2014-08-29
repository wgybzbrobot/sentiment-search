package zx.soft.sent.solr.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.index.ImportSentDataToDB;
import zx.soft.sent.solr.index.ImportSentDataToSC;
import zx.soft.sent.solr.index.ImportSinaDataToSC;
import zx.soft.sent.solr.search.OracleToRedis;
import zx.soft.sent.solr.statistic.CompanyMain;

/**
 * 驱动类
 * 
 * @author wanggang
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
		case "importSentDataToSC":
			logger.info("导入舆情数据到SolrCloud： ");
			ImportSentDataToSC.main(leftArgs);
			break;
		case "importSentDataToDB":
			logger.info("导入舆情数据到DB： ");
			ImportSentDataToDB.main(leftArgs);
			break;
		case "importSinaDataToSC":
			logger.info("导入新浪数据到SolrCloud：");
			ImportSinaDataToSC.main(leftArgs);
			break;
		case "companyMain":
			logger.info("微博数据统计：");
			CompanyMain.main(leftArgs);
			break;
		case "oracleToRedis":
			logger.info("将站点数据定时导入Redis中： ");
			OracleToRedis.main(leftArgs);
			break;
		default:
			return;
		}

	}

}
