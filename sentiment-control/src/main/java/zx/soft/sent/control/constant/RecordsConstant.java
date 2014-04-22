package zx.soft.sent.control.constant;

/**
 * 数据记录相关常量
 * @author wanggang
 *
 */
public class RecordsConstant {

	/**
	 * Redis中所有记录md5(key)所在的key
	 */
	// 元搜索类、博客类、邮件类、论坛类、资讯类、QQ群类、回复类、微博类
	public static final String[] REDIS_RECORDS_KEY = { "autm_search", "blog", "email", "forum", "information",
			"qqgroup", "reply", "weibo" };

	/**
	 * MySQL数据统计相关表名
	 */
	public static final String STATISTIC_BY_DAY = "statistic_per_day";

}
