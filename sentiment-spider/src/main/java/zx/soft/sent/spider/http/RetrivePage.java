package zx.soft.sent.spider.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

/**
 * 下载网页，并解析。
 * 该类中提供了三种资源提取方式，但不能提供全面灵活性和套接字连接池等开发爬虫需要的功能，因而建议使用HttpClient较好。
 * 
 * @author wanggang
 *
 */
public class RetrivePage {

	private static Logger logger = LoggerFactory.getLogger(RetrivePage.class);

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {
		//		String html = RetrivePage.downloadPageByScanner("http://www.baidu.com");
		String html = RetrivePage.downloadPageBySocket("www.baidu.com", 80, "/");
		System.out.println(html);
	}

	/**
	 * 使用Reader方式读取数据流
	 */
	public static String downloadPageByReader(String path) {
		// 根据传入的路径构造URL
		URL url = null;
		try {
			url = new URL(path);
			// 设置代理
			//			url.openConnection(SocketProxy.getProxy());
		} catch (MalformedURLException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		} catch (@SuppressWarnings("hiding") IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
		// 创建网络流
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));) {
			String str;
			StringBuffer sb = new StringBuffer();
			// 读取网页内容
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			return sb.toString();
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用Scanner方式去读数据流
	 */
	public static String downloadPageByScanner(String path) {
		// 根据传入的路径构造URL
		URL url = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
		// 指定编码格式UTF-8的Scanner
		try (Scanner scanner = new Scanner(new InputStreamReader(url.openStream(), "utf-8"));) {
			// 可以使用正则表达式分段读取网页
			scanner.useDelimiter("\\z");
			StringBuffer sb = new StringBuffer();
			// 读取网页内容
			while (scanner.hasNext()) {
				sb.append(scanner.next());
			}
			return sb.toString();
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用Socket方式下载网页
	 * @param host: 主机名
	 * @param port：端口号
	 * @param filePath：网页路径
	 */
	public static String downloadPageBySocket(String host, int port, String filePath) {
		// 申明Socket
		Socket socket = null;
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}

		try (PrintWriter pw = new PrintWriter(socket.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
			// 发送GET命令
			pw.print("GET " + filePath + " HTTP/1.0\r\n");
			// 检查是否修改
			//			pw.print("If-Modified-Since:Tue May 07 17:24:37 CST 2013\r\n");
			pw.print("Accept:text/plain,text/html,text/*\r\n");
			pw.print("\r\n");
			pw.flush();
			// 输出网页内容
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			return sb.toString();
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				throw new RuntimeException(e);
			}
		}
	}

}
