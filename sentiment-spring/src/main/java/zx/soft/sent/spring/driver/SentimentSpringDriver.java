package zx.soft.sent.spring.driver;

import zx.soft.sent.spring.server.IndexApiServer;
import zx.soft.utils.driver.ProgramDriver;

/**
 * 驱动类
 *
 * @author wanggang
 *
 */
public class SentimentSpringDriver {

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			// 运行在hefei01~hefei06,hefei08~hefei10机器上
			// 测试环境：hefei11:192.168.31.11 目录：/home/solr/run-work/api/index  端口：8900
			pgd.addClass("indexApiServer", IndexApiServer.class, "舆情数据索引接口");
			pgd.driver(args);
			// Success
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}

		System.exit(exitCode);

	}

}
