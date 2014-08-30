package zx.soft.sent.solr.utils;

public class TimeConvert {

	/**
	 * 将"2014-08-25 00:00:00"转换为"2014-08-25T00:00:00Z"
	 * @param str
	 * @return
	 */
	public static String transTimeStr(String str) {
		String[] strs = str.split("\\s");
		if (strs.length != 2) {
			throw new RuntimeException("TimeStr=" + str + " converted error!");
		} else {
			return strs[0] + "T" + strs[1] + "Z";
		}
	}

}
