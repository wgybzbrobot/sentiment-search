package zx.soft.sent.solr.driver;

import zx.soft.sent.solr.allinternet.TaskUpdate;
import zx.soft.sent.solr.firstpage.FirstPageHarmfulRun;
import zx.soft.sent.solr.firstpage.FirstPageRun;
import zx.soft.sent.solr.index.ImportRedisToSC;
import zx.soft.sent.solr.query.OracleToRedis;
import zx.soft.sent.solr.query.RemoveExpiredData;
import zx.soft.sent.solr.special.SpecialTopicRun;
import zx.soft.sent.solr.utils.RedisMQTest;
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
			// 在hefei09机器上运行
			pgd.addClass("oracleToRedis", OracleToRedis.class, "将站点数据定时导入Redis中（默认是每小时）");
			// 在hefei07机器上运行
			pgd.addClass("specialTopicRun", SpecialTopicRun.class, "OA专题数据统计——临时分析");
			// 在hefei07机器上运行
			pgd.addClass("firstPageRun", FirstPageRun.class, "OA首页数据统计——临时分析");
			// 在hefei07机器上运行
			pgd.addClass("firstPageHarmfulRun", FirstPageHarmfulRun.class, "OA首页负面信息数据定时统计");
			// 在hefei10机器上运行多个进程
			pgd.addClass("importRedisToSC", ImportRedisToSC.class, "将Redis中的数据所引到SolrCloud");
			// 在hefei08机器上运行
			pgd.addClass("removeExpiredData", RemoveExpiredData.class, "定时每周删除过期舆情数据和Redis缓存数据");
			// 在hefei10机器上运行
			pgd.addClass("taskUpdate", TaskUpdate.class, "全网任务信息查询结果存储缓存信息");
			// 测试
			pgd.addClass("redisMQTest", RedisMQTest.class, "测试Redis");
			pgd.driver(args);
			// Success
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}

		System.exit(exitCode);

	}

}
