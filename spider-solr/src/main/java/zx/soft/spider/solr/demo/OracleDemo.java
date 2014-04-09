package zx.soft.spider.solr.demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OracleDemo {

	/**
	 * 主函数
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd T HH:mm:ss", Locale.ENGLISH);
		Date parse = dateFormat.parse("Wed Oct 23 16:58:17 +0800 2013");
		System.out.println(parse);

	}

}
