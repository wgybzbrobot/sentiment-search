package zx.soft.sent.spider.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * HTTP信息工具类
 * 
 * @author wanggang
 *
 */
public class HttpInfoUtils {

	public static void main(String[] args) {

		System.out.println(HttpInfoUtils.getLastModifiedTime("www.lietu.com", 80, "/index.jsp"));
		System.out.println(HttpInfoUtils.getLastModifiedTime("http://www.lietu.com"));

	}

	/**
	 * 通过HEAD命令查看lasttime
	 */
	public static String getLastModifiedTime(String host, int port, String filePath) {
		// 建立Socket对象
		try {
			Socket socket = new Socket(host, port);
			try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), false);
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
				// 发送HEAD命令
				pw.print("HEAD " + filePath + " HTTP/1.0\r\n");
				pw.print("Accept:text/plain,text/html,text/*\r\n");
				pw.print("\r\n");
				pw.flush();
				// 返回头信息
				StringBuffer sb = new StringBuffer();
				String str;
				while ((str = br.readLine()) != null) {
					sb.append(str).append("\n");
				}
				return sb.toString();
			} finally {
				socket.close();
			}
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过HttpURLConnection获取修改时间
	 */
	public static String getLastModifiedTime(String urlStr) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("HEAD");
			return url + " 更新时间： " + new Date(http.getLastModified());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (ProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
