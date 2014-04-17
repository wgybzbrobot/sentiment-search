package zx.soft.spider.web.common;

/**
 * 字符处理工具类
 * @author wanggang
 *
 */
public class StringPattern {

	public static boolean isAllNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

}
