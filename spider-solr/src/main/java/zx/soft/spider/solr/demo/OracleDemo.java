package zx.soft.spider.solr.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import zx.soft.spider.solr.oracle.DataOJDBC;
import zx.soft.spider.solr.utils.TimeUtils;

public class OracleDemo {

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws ParseException, SQLException {

		DataOJDBC dataOJDBC = new DataOJDBC();
		//		ResultSet rs = dataOJDBC.query("SELECT MAX(JCSJ) AS max_time FROM SJCJ_WBL");
		long low_time = System.currentTimeMillis() - 86400000 * 10L;
		long high_time = System.currentTimeMillis();
		String sql = "SELECT * FROM SJCJ_WBL WHERE JCSJ BETWEEN to_date('" + TimeUtils.transToCommonDateStr(low_time)
				+ "','yyyy-mm-dd hh24:mi:ss') " + "AND to_date('" + TimeUtils.transToCommonDateStr(high_time)
				+ "','yyyy-mm-dd hh24:mi:ss')";
		System.out.println(sql);
		ResultSet rs = dataOJDBC.query(sql);
		while (rs.next()) {
			System.out.println(rs.getString("JCSJ"));
		}
		System.out.println(rs.getFetchSize());
		rs.close();
		dataOJDBC.close();

	}

}
