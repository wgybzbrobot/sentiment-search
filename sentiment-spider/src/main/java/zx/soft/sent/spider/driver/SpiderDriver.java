package zx.soft.sent.spider.driver;

import zx.soft.sent.spider.sina.SinaPublicWeibosSpider;
import zx.soft.utils.driver.ProgramDriver;

/**
 * 驱动类
 *
 * @author wanggang
 *
 */
public class SpiderDriver {

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			// 运行在hefei05机器上
			pgd.addClass("sinaPublicWeibosSpider", SinaPublicWeibosSpider.class, "新浪公共微博抓取");
			pgd.driver(args);
			// Success
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}

		System.exit(exitCode);

	}

}
