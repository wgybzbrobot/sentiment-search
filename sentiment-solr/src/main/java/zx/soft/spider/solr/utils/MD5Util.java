package zx.soft.spider.solr.utils;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5工具类
 * @author wanggang
 *
 */
public class MD5Util {

	private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String str2MD5(String inStr) {

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			logger.error("'" + inStr + "' is error str.");
			throw new RuntimeException(e);
		}
		if (md5 == null) {
			logger.error("md5=null at '" + inStr + "'.");
			return null;
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

}
