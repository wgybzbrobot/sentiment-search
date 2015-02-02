package zx.soft.sent.solr.driver;

import zx.soft.sent.solr.firstpage.FirstPageRun;
import zx.soft.sent.solr.firstpage.FirstPageTimer;
import zx.soft.sent.solr.index.ImportRedisToSC;
import zx.soft.sent.solr.index.ImportSentDataToDB;
import zx.soft.sent.solr.index.ImportSentDataToSC;
import zx.soft.sent.solr.index.ImportSinaDataToSC;
import zx.soft.sent.solr.search.OracleToRedis;
import zx.soft.sent.solr.search.RemoveRedisReplicationData;
import zx.soft.sent.solr.search.RemoveSentiData;
import zx.soft.sent.solr.search.RemoveWeiboData;
import zx.soft.sent.solr.special.SpecialTopicRun;
import zx.soft.sent.solr.special.SpecialTopicTimer;
import zx.soft.utils.driver.ProgramDriver;

/**
 * 驱动类
 *
 * @author wanggang
 *
 */
public class SentSolrDriver {

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			pgd.addClass("importSentDataToSC", ImportSentDataToSC.class, "导入舆情数据到SolrCloud");
			pgd.addClass("importSentDataToDB", ImportSentDataToDB.class, "导入舆情数据到DB");
			pgd.addClass("importSinaDataToSC", ImportSinaDataToSC.class, "导入新浪数据到SolrCloud");
			pgd.addClass("oracleToRedis", OracleToRedis.class, "将站点数据定时导入Redis中（默认是每小时）");
			pgd.addClass("specialTopicTimer", SpecialTopicTimer.class, "OA专题数据统计——定时分析");
			pgd.addClass("specialTopicRun", SpecialTopicRun.class, "OA专题数据统计——临时分析");
			pgd.addClass("firstPageTimer", FirstPageTimer.class, "OA首页数据统计——定时分析");
			pgd.addClass("firstPageRun", FirstPageRun.class, "OA首页数据统计——临时分析");
			pgd.addClass("importRedisToSC", ImportRedisToSC.class, "将Redis中的数据所引到SolrCloud");
			pgd.addClass("removeSentiData", RemoveSentiData.class, "定时删除过期舆情数据");
			pgd.addClass("removeWeiboData", RemoveWeiboData.class, "定时删除过期微博数据");
			pgd.addClass("removeRedisReplicationData", RemoveRedisReplicationData.class, "定时清理Redis去重数据");
			pgd.driver(args);
			// Success
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}

		System.exit(exitCode);

	}

}
