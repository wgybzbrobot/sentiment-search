package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.solr.utils.Config;
import zx.soft.sent.web.application.SentiSearchApplication;
import zx.soft.sent.web.common.ServerUtils;

/**
 * 舆情搜索Server
 * @author wanggang
 *
 * 搜索示例：
 * http://localhost:8901/sentiment/search?q=美食&fq=platform:7&sort=lasttime:desc&start=10&rows=50&fl=username,nickname&wt=xml&hlfl=content&facetQuery=abc&facetField=nickname
 * http://hdp321:8901/sentiment/search?q=%E5%95%86%E4%B8%9A&fq=content:%E7%BE%8E%E9%A3%9F&facetField=platform&start=10&rows=2&hlfl=content&sort=lasttime:desc
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
