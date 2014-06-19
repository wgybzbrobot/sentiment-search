package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.utils.config.ConfigUtil;
import zx.soft.sent.web.application.PushRecordApplication;
import zx.soft.sent.web.jackson.ReplaceConvert;

public class PushRecordServer {

	private final Component component;
	private final PushRecordApplication pushRecordApplication;

	private final int PORT;

	public PushRecordServer() {
		Properties props = ConfigUtil.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		pushRecordApplication = new PushRecordApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		PullRecordServer server = new PullRecordServer();
		server.start();

	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/sentiment", pushRecordApplication);
			ReplaceConvert.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			pushRecordApplication.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
