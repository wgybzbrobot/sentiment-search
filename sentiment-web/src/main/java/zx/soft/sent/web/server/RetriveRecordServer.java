package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.solr.utils.Config;
import zx.soft.sent.web.application.RetriveRecordApplication;
import zx.soft.sent.web.jackson.ReplaceConvert;

/**
 *  从Mysql中根据id获取记录数据
 * @author wanggang
 *  示例：
 *       1、根据多个id查询记录：http://localhost:7900/sentiment/ids/{ids}
 *       2、根据lasttime时间段查询记录：
 *       3、根据多个id、关键词、高亮标签查询数据：http://localhost:7900/sentiment/ids/{ids}?keyword=XXXX&hlsimple=red
 *
 */
public class RetriveRecordServer {

	private final Component component;
	private final RetriveRecordApplication retriveRecordApplication;

	private final int PORT;

	public RetriveRecordServer() {
		Properties props = Config.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		retriveRecordApplication = new RetriveRecordApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		RetriveRecordServer server = new RetriveRecordServer();
		server.start();

	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/sentiment", retriveRecordApplication);
			ReplaceConvert.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			retriveRecordApplication.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
