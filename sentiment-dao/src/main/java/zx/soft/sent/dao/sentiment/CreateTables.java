package zx.soft.sent.dao.sentiment;

/**
 * 创建舆情相关数据表，根据crc32校验码hash分表。
 * @author wanggang
 *
 */
public class CreateTables {

	public static final int MAX_TABLE_NUM = 128;

	public static final String SENT_TABLE_TABLE = "sentiment_tablenames";

	public static final String SENT_TABLE = "sent_records_";

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		SentJDBC sentJDBC = new SentJDBC();
		// 创建舆情数据表表名表
		sentJDBC.createTablenameTable(CreateTables.SENT_TABLE_TABLE);
		// 创建舆情数据表
		for (int i = 0; i < CreateTables.MAX_TABLE_NUM; i++) {
			System.out.println(i);
			sentJDBC.createSentimentTable(CreateTables.SENT_TABLE + i);
		}

	}

}
