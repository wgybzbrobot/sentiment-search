package zx.soft.spider.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.VirtualHost;

import zx.soft.spider.solr.utils.Config;
import zx.soft.spider.web.application.SentiIndexApplication;
import zx.soft.spider.web.application.SentiSearchApplication;
import zx.soft.spider.web.common.ServerUtils;

/**
 * 舆情搜索Server
 * @author wanggang
 *
 */
public class SentimentServer {

	private final Component component;
	private final SentiIndexApplication sentiIndexApplication;
	private final SentiSearchApplication sentiSearchApplication;

	private final int PORT;

	public SentimentServer() {
		Properties props = Config.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		sentiIndexApplication = new SentiIndexApplication();
		sentiSearchApplication = new SentiSearchApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		SentimentServer server = new SentimentServer();
		server.start();
	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			VirtualHost indexHost = new VirtualHost();
			indexHost.attach("/sentiment/index", sentiIndexApplication);
			VirtualHost searchHost = new VirtualHost();
			searchHost.attach("/sentiment/search", sentiSearchApplication);
			component.getHosts().add(indexHost);
			component.getHosts().add(searchHost);
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
			sentiSearchApplication.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
