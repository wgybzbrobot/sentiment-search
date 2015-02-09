package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.web.application.SpecialSpeedApplication;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.jackson.ReplaceConvert;

/**
 * OA专题查询缓存服务：hefei03
 * 示例：GET: http://192.168.32.13:5900/special/{identifys}
 *       408,409,410,412,413,414,415,416,417,419,427,450
 *
 * 运行目录：/home/zxdfs/run-work/api/special
 * 运行命令：cd sentiment-web
 *        bin/ctl.sh start specialSpeedServer
 *
 * @author wanggang
 *
 */
public class SpecialSpeedServer {

	private final Component component;
	private final SpecialSpeedApplication specialApplication;

	private final int PORT;

	public SpecialSpeedServer() {
		Properties props = ConfigUtil.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		specialApplication = new SpecialSpeedApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		SpecialSpeedServer specialServer = new SpecialSpeedServer();
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
