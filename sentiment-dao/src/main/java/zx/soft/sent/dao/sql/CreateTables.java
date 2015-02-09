package zx.soft.sent.dao.sql;

/**
 * 创建舆情相关数据表，根据crc32校验码hash分表。
 *
 * @author wanggang
 *
 */
public class CreateTables {

	public static final int MAX_TABLE_NUM = 128;

	public static final String SENT_TABLE_TABLE = "sentiment_tablenames";

	public static final String CACHE_QUERY_TABLE = "cache_query";

	public static final String SENT_TABLE = "sent_records_";

	public static final String SENT_SITE_TABLE = "sent_site_info";

	// OA专题信息表
	public static final String SPECIAL_INFO_TABLE = "oa_special_info";

	// OA专题缓存数据表
	public static final String SPECIAL_CACHE_TABLE = "oa_special_query_cache";

	// OA首页数据表
	public static final String FIRST_PAGE_TABLE = "oa_firstpage_query_cache";

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		SentDbcp sentDbcp = new SentDbcp();
		// OA首页数据表
		//		sentDbcp.createFirstPageTable(FIRST_PAGE_TABLE);
		// OA专题信息表
		//		sentDbcp.createSpecialInfoTable(SPECIAL_INFO_TABLE);
		// OA专题缓存数据表
		//		sentDbcp.createSpecialQueryCacheTable(SPECIAL_CACHE_TABLE);
		// 创建站点数据表
		//		sentDbcp.createSiteTable(SENT_SITE_TABLE);
		// 创建查询缓存数据表
		//		sentDbcp.createQueryCacheTable(CreateTables.CACHE_QUERY_TABLE);
		// 创建舆情数据表表名表
		//		sentDbcp.createTablenameTable(CreateTables.SENT_TABLE_TABLE);
		// 创建舆情数据表
		for (int i = 0; i < MAX_TABLE_NUM; i++) {
			sentDbcp.createSentimentTable(CreateTables.SENT_TABLE + i);
		}
		sentDbcp.close();
		System.out.println("Finish!");

	}

}
