package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.web.application.FirstPageHarmfulApplication;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.jackson.ReplaceConvert;

/**
 * OA首页负面信息查询缓存服务：hefei07
 * 示例：http://localhost:5902/firstpage/{type}/{datestr}
 *
 * type是平台类型，1～10
 *
 * 运行目录：/home/zxdfs/run-work/api/oa-firstpage-harmful
 * 运行命令：cd sentiment-web
 *        bin/ctl.sh start firstPageHarmfulServer
 *
 * @author wanggang
 *
 */
public class FirstPageHarmfulServer {

	private final Component component;
	private final FirstPageHarmfulApplication firstPageApplication;

	private final int PORT;

	public FirstPageHarmfulServer() {
		Properties props = ConfigUtil.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		firstPageApplication = new FirstPageHarmfulApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		FirstPageHarmfulServer specialServer = new FirstPageHarmfulServer();
		specialServer.start();
	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/firstpage", firstPageApplication);
			ReplaceConvert.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			firstPageApplication.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
