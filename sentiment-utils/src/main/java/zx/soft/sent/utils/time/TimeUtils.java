package zx.soft.sent.utils.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

	private static final SimpleDateFormat SOLR_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	private static final SimpleDateFormat LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		System.out.println(TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014"));
	}

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
	 * 如：SThu Apr 10 11:40:56 CST 2014——>2014-04-10 10:07:14
	 */
	public static String transStrToCommonDateStr(String str) {
		try {
			return LONG_FORMAT.format(dateFormat.parse(str));
		} catch (ParseException e) {
			return "";
			//			throw new RuntimeException();
		}
	}

}
