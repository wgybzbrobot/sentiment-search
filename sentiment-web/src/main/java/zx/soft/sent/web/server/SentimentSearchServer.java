package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.utils.config.ConfigUtil;
import zx.soft.sent.web.application.SentiSearchApplication;
import zx.soft.sent.web.jackson.ReplaceConvert;

/**
 * 舆情搜索Server
 * @author wanggang
 *
 * 搜索示例：
 * http://localhost:8901/sentiment/search?q=美食&fq=platform:7&sort=lasttime:desc&start=10&rows=50&fl=username,nickname&wt=xml&hlfl=content&facetQuery=abc&facetField=nickname
 *
 */
public class SentimentSearchServer {

	private final Component component;
	private final SentiSearchApplication sentiSearchApplication;

	private final int PORT;

	public SentimentSearchServer() {
		Properties props = ConfigUtil.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		sentiSearchApplication = new SentiSearchApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		SentimentSearchServer server = new SentimentSearchServer();
		server.start();
	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/sentiment/search", sentiSearchApplication);
			ReplaceConvert.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			sentiSearchApplication.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
