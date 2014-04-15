package zx.soft.spider.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
