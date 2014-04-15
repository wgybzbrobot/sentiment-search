package zx.soft.spider.solr.demo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import zx.soft.spider.solr.domain.Record;
import zx.soft.spider.solr.utils.JsonUtils;

public class OracleDemo {

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws ParseException, SQLException {

		List<Record> records = new ArrayList<>();
		Record r1 = new Record.Builder("12442", 1).build();
		Record r2 = new Record.Builder("543212", 3).build();
		records.add(r1);
		records.add(r2);
		System.out.println(JsonUtils.toJsonWithoutPretty(records));

	}

}
