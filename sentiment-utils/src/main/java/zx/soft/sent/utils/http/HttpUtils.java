package zx.soft.sent.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {

	private static Logger logger = Logger.getLogger(HttpUtils.class);

	/**
	 * 新工具
	 */
	public static String doGet(String url) {
		// 创建一个客户端，类似于打开一个浏览器
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建一个GET方法，类似于在浏览器地址栏中输入一个地址
		HttpGet httpGet = new HttpGet(url);
		// 类似于在浏览器中输入回车，获得网页内容
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			logger.error("IOException in HttpUtils: ", e);
		}
		// 查看返回内容，类似于在浏览器中查看网页源码
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			// 读入内容流，并以字符串形式返回，这里指定网页编码是UTF-8
			try {
				result = EntityUtils.toString(entity);
				// 网页的Meta标签中指定了编码
				EntityUtils.consume(entity); // 关闭内容流
			} catch (ParseException | IOException e) {
				//				throw new RuntimeException(e);
			}
		}
		try {
			httpClient.close();
		} catch (IOException e) {
			//			
		}

		return result;
	}

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 *
	 * @param url: 请求的URL地址
	 * @param queryString: 请求的查询参数,可以为null
	 * @param charset: 字符集
	 * @param pretty: 是否美化
	 * @return: 返回请求响应的HTML
	 */
	public static String doGet(String url, String queryString, String charset, boolean pretty) {

		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
			if (StringUtils.isNotBlank(queryString)) {
				method.setQueryString(URIUtil.encodeQuery(queryString));
			}
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
						charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				reader.close();
			}
		} catch (URIException e) {
			logger.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
		} catch (IOException e) {
			logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
			//			client.getHttpConnectionManager().closeIdleConnections(1000);
		}
		return response.toString();
	}

	/**
	 * 重载函数
	 */
	public static String doGet(String url, String charset) {
		return doGet(url, "", charset, Boolean.TRUE);
	}

	/**
	 * 测试函数
	 * @param args
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void main(String[] args) throws HttpException, IOException {

		//		System.out.println(HttpUtils.doGet("http://114.112.65.13:8111/sina/users/2161358634/basic", "utf-8"));
		//		System.out.println(HttpUtils.doGet(" http://60.169.74.26:8111/sina/users/2039654561/fromapi", "utf-8"));
		System.out.println(HttpUtils.doGet("http://114.112.65.13:8111/sina/users/2161358634/basic"));

	}

}
