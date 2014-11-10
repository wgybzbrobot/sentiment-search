package zx.soft.sent.utils.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符集处理测试类
 * 
 * @author wanggang
 *
 */
public class CharsDemo {

	private static Logger logger = LoggerFactory.getLogger(CharsDemo.class);

	public static void main(String[] args) throws UnsupportedEncodingException {

		String str = "测试转码";
		byte[] utf_16be = str.getBytes("utf-16be");
		String utf8str = CharsDemo.visualUtf8Str(utf_16be);
		System.out.println(utf8str);

		//		char c = 164;
		//		System.out.println(c);

	}

	public static String visualUtf8Str(byte[] bt) {
		String result = "";
		int count = 0;
		for (int i = 0; i < bt.length; i++) {
			int hex = bt[i] & 0xff;
			if (count++ % 2 == 0) {
				//				result += "&#x" + Integer.toHexString(hex);
				result += "\\u" + Integer.toHexString(hex);
			} else {
				result += Integer.toHexString(hex);
			}
			logger.info(Integer.toHexString(hex) + " ");
		}
		return result;
	}

	/**
	 * Get XML String of utf-8
	 *
	 * @return XML-Formed string
	 */
	public static String getUTF8XMLString(String xml) {
		// A StringBuffer Object
		StringBuffer sb = new StringBuffer();
		sb.append(xml);
		String xmString = "";
		String xmlUTF8 = "";
		try {
			xmString = new String(sb.toString().getBytes("UTF-8"));
			xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
			logger.info("utf-8 编码：" + xmlUTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return xmlUTF8;
	}

}
