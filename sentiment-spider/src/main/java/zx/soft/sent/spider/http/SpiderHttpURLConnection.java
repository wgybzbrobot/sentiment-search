package zx.soft.sent.spider.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * getContentType等方法的错误指出在于只识别头信息中小写的字符串，需要修改成大小写不敏感。
 * @author wanggang
 *
 */
public class SpiderHttpURLConnection extends HttpURLConnection {

	protected SpiderHttpURLConnection(URL u) {
		super(u);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getHeaderField(String fieldKey) {
		Map<String, List<String>> header = getHeaderFields();
		Iterator<String> iter = header.keySet().iterator();
		String key = null;
		while (iter.hasNext()) {
			key = iter.next();
			if (key == null) {
				if (fieldKey == null) {
					return (String) ((List<?>) (header.get(null))).get(0);
				}
			} else {
				if (key.equalsIgnoreCase(fieldKey)) {
					return (String) ((List<?>) (header.get(key))).get(0);
				}
			}
		}
		return null;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean usingProxy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connect() throws IOException {
		// TODO Auto-generated method stub

	}

}
