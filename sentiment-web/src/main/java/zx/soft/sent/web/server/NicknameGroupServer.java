package zx.soft.sent.web.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.sent.web.application.NicknameGroupApplication;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.jackson.ReplaceConvert;

/**
 * 舆情搜索Server，针对nickname：hefei02
 * 搜索示例：http://192.168.32.12:8901/sentiment/search?q=合肥&fq=timestamp:[2014-12-23T00:00:00Z TO 2014-12-29T23:59:59Z]
 * http://192.168.31.11:8902/sentiment/search?q="人"&fq=timestamp:[2015-05-06T00:00:00Z TO 2015-06-04T23:59:59Z];
 *
 * 运行目录：/home/zxdfs/run-work/api/nicknamegroup
 * 运行命令： cd sentiment-web
 *         bin/ctl.sh start nicknameGroupServer
 *
 * @author wanggang
 *
 */
public class NicknameGroupServer {

	private final Component component;
	private final NicknameGroupApplication nicknameGroupApplication;

	private final int PORT;

	public NicknameGroupServer() {
		Properties props = ConfigUtil.getProps("web-server.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		nicknameGroupApplication = new NicknameGroupApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		NicknameGroupServer server = new NicknameGroupServer();
		server.start();
	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/sentiment", nicknameGroupApplication);
			ReplaceConvert.configureJacksonConverter();
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
			nicknameGroupApplication.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
