package zx.soft.sent.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * URL编解码器工具类
 * 
 * @author wanggang
 *
 */
public class URLCodecUtils {

	public static String decoder(String url, String codec) {
		try {
			String result = URLDecoder.decode(url, codec);
			return result;
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

}
