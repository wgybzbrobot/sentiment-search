package zx.soft.spider.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.spider.solr.utils.Config;
import zx.soft.spider.web.application.SentiIndexApplication;
import zx.soft.spider.web.common.ServerUtils;

/**
 * 舆情搜索Server
 * @author wanggang
 *
 * 索引示例：http://localhost:2222/sentiment/index   POST
 */
public class SentimentIndexServer {

	private final Component component;
	private final SentiIndexApplication sentiIndexApplication;

	private final int PORT;

	public SentimentIndexServer() {
		Properties props = Config.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		sentiIndexApplication = new SentiIndexApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		SentimentIndexServer server = new SentimentIndexServer();
		server.start();
	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/sentiment/index", sentiIndexApplication);
			ServerUtils.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			sentiIndexApplication.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
