package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.web.application.SentiSearchApplication;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.jackson.ReplaceConvert;

/**
 * 舆情搜索服务：hefei01和hefei03
 * 搜索示例：
 * http://localhost:8901/sentiment/search?q=美食&fq=platform:7&sort=lasttime:desc&start=10&rows=50&fl=username,nickname&wt=xml&hlfl=content&facetQuery=abc&facetField=nickname
 *
 * 运行目录：/home/zxdfs/run-work/api/search
 * 运行命令： cd sentiment-web
 *         bin/ctl.sh start sentimentSearchServer
 *
 * @author wanggang
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
			component.getDefaultHost().attach("/sentiment", sentiSearchApplication);
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
