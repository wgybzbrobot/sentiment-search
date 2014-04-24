package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.solr.utils.Config;
import zx.soft.sent.web.application.SentiIndexApplication;
import zx.soft.sent.web.jackson.ReplaceConvert;

/**
 * 舆情搜索Server
 * @author wanggang
 *
 * 索引示例：
 * http://localhost:8900/sentiment/index   POST
 * http://hdp321:8900/sentiment/index   POST
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
			ReplaceConvert.configureJacksonConverter();
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
