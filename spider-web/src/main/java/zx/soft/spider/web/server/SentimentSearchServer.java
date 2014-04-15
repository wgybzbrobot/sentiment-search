package zx.soft.spider.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.spider.solr.utils.Config;
import zx.soft.spider.web.application.SentiSearchApplication;
import zx.soft.spider.web.common.ServerUtils;

/**
 * 舆情搜索Server
 * @author wanggang
 *
 * 搜索示例：http://localhost:2222/sentiment/search?q=美食&fq=platform:7&sort=lasttime:desc&start=10&rows=50&fl=username,nickname&wt=xml&hlfl=content&facetQuery=abc&facetField=nickname
 *
 */
public class SentimentSearchServer {

	private final Component component;
	private final SentiSearchApplication sentiSearchApplication;

	private final int PORT;

	public SentimentSearchServer() {
		Properties props = Config.getProps("web-server.properties");
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
			ServerUtils.configureJacksonConverter();
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
