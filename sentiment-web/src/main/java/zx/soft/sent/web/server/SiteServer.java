package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.web.application.SiteApplication;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.jackson.ReplaceConvert;

/**
 * 站点组合数据服务
 *
 * @author wanggang
 * 示例：
 *     1、http://192.168.32.11:6900/site
 *     POST: [ "2344,543,23,1355", "667,99,145,987" ]
 *
 */
public class SiteServer {

	private final Component component;
	private final SiteApplication siteApplication;

	private final int PORT;

	public SiteServer() {
		Properties props = ConfigUtil.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		siteApplication = new SiteApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		SiteServer server = new SiteServer();
		server.start();

	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/site", siteApplication);
			ReplaceConvert.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			siteApplication.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
