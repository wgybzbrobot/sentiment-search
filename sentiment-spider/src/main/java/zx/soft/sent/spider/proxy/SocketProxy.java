package zx.soft.sent.spider.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.spider.utils.DataReader;

/**
 * Socket代理设置
 * @author wanggang
 *
 */
public class SocketProxy {

	private static Logger logger = LoggerFactory.getLogger(SocketProxy.class);

	private static List<String> proxyList;

	private static final Random RANDOM = new Random();

	static {
		proxyList = DataReader.getProxyIPs("proxy/proxy-ips.txt");
		logger.info("Loading proxy ips successful!");
	}

	public static Proxy getProxy() {
		String ip = proxyList.get(RANDOM.nextInt(proxyList.size()));
		String[] ipAndPort = ip.split(":");
		if (ipAndPort.length == 2) {
			SocketAddress socketAddress = new InetSocketAddress(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
			return new Proxy(Proxy.Type.HTTP, socketAddress);
		}
		return null;
	}

}
