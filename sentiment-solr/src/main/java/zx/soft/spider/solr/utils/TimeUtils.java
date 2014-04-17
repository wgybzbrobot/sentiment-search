package zx.soft.spider.solr.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	private static final SimpleDateFormat SOLR_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	private static final SimpleDateFormat LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 将时间戳转换成Solr标准的Date格式
	 * 如：2014-04-10T10:07:14Z
	 */
	public static String transToSolrDateStr(long timestamp) {
		return SOLR_FORMAT.format(new Date(timestamp));
	}

	/**
	 * 将Solr标准的Date转换成可读性较好的格式
	 * 如：2014-04-10 10:07:14
	 */
	public static String transToCommonDateStr(String str) {
		return str.replace("T", " ").replace("Z", "");
	}

	/**
	 * 将long型日期（毫秒级别）转换成可读性较好的格式
	 * 如：2014-04-10 10:07:14
	 */
	public static String transToCommonDateStr(long timestamp) {
		return LONG_FORMAT.format(new Date(timestamp));
	}

	/**
	 * 将Solr返回的时间串转换成可读性较好的格式
	 * 如：Sat Oct 19 00:02:12 CST 2013——>2014-04-10 10:07:14
	 */
	@SuppressWarnings("deprecation")
	public static String transStrToCommonDateStr(String str) {
		return LONG_FORMAT.format(new Date(str));
	}

}
