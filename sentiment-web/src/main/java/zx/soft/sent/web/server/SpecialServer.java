package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.web.application.SpecialApplication;
import zx.soft.sent.web.jackson.ReplaceConvert;
import zx.soft.utils.config.ConfigUtil;

/**
 * OA专题查询缓存服务
 * 
 * 示例：
 *      1、POST: http://localhost:5900/special/add
 *      [{"identify":"absbhdfhfjfi","name":"专题1","keywords":"（合肥 and 警察） not 打人","start":"2014-08-25 00:00:00","end":"2014-08-25 23:59:59","hometype":0},{"identify":"djdjfkfklglg","name":"专题2","keywords":"（安徽 and 城管） not 暴力执法","start":"2013-08-25 00:00:00","end":"2013-08-25 23:59:59","hometype":2}]
 *      2、GET: http://localhost:5900/special/{identify}/{type}
 *      3、DELETE: http://localhost:5900/special/{identify}/remove
 * 
 * @author wanggang
 *
 */
public class SpecialServer {

	private final Component component;
	private final SpecialApplication specialApplication;

	private final int PORT;

	public SpecialServer() {
		Properties props = ConfigUtil.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		specialApplication = new SpecialApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		SpecialServer specialServer = new SpecialServer();
		specialServer.start();
	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/special", specialApplication);
			ReplaceConvert.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			specialApplication.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
