package zx.soft.sent.spider.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * DNS缓存处理
 * 
 * @author wanggang
 *
 */
public class CacheDNS {

	public static void main(String[] args) throws UnknownHostException {

		InetAddress[] ips = InetAddress.getAllByName("www.baidu.com");
		for (InetAddress ip : ips) {
			System.out.println(ip);
		}

	}

}
