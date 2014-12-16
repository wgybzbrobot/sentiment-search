package zx.soft.sent.web.driver;

import zx.soft.sent.web.sentiment.SearchingData;
import zx.soft.sent.web.server.FirstPageServer;
import zx.soft.sent.web.server.HarmfulInfoServer;
import zx.soft.sent.web.server.PullRecordServer;
import zx.soft.sent.web.server.SentimentIndexServer;
import zx.soft.sent.web.server.SentimentSearchServer;
import zx.soft.sent.web.server.SiteServer;
import zx.soft.sent.web.server.SpecialServer;
import zx.soft.utils.driver.ProgramDriver;

/**
 * 驱动类
 * 
 * @author wanggang
 *
 */
public class SentWebDriver {

	/**
	 * 主函数，需要修改，启动接口后，又关闭了
	 */
	public static void main(String[] args) {

		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			pgd.addClass("sentimentIndexServer", SentimentIndexServer.class, "索引接口");
			pgd.addClass("sentimentSearchServer", SentimentSearchServer.class, "搜索接口");
			pgd.addClass("searchingData", SearchingData.class, "搜索测试");
			pgd.addClass("pullRecordServer", PullRecordServer.class, "查库接口");
			pgd.addClass("siteServer", SiteServer.class, "写入站点数据组合到Redis中");
			pgd.addClass("specialServer", SpecialServer.class, "OA专题查询缓存服务，专题信息写入和删除");
			pgd.addClass("firstPageServer", FirstPageServer.class, "OA首页信息缓存服务");
			pgd.addClass("harmfulInfoServer", HarmfulInfoServer.class, "OA有害信息查询");
			pgd.driver(args);
			// Success
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}

		System.exit(exitCode);

	}

}
