package zx.soft.sent.spring.utils;

import zx.soft.sent.dao.sql.CreateTables;
import zx.soft.utils.checksum.CheckSumUtils;

public class Demo {

	public static void main(String[] args) {

		System.out
				.println(CreateTables.SENT_TABLE + CheckSumUtils.getCRC32("sentiment79") % CreateTables.MAX_TABLE_NUM);
	}

}
