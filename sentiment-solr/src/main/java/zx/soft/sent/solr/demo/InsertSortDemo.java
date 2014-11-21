package zx.soft.sent.solr.demo;

import zx.soft.utils.sort.InsertSort;

public class InsertSortDemo {

	public static void main(String[] args) {

		String[] IN_STRS_ARR = { "111=5", "222=1", "333=9", "444=2", "555=6" };

		String[] table = new String[IN_STRS_ARR.length];
		for (int i = 0; i < table.length; i++) {
			table[i] = "0=0";
		}
		for (int i = 0; i < table.length; i++) {
			table = InsertSort.toptable(table, IN_STRS_ARR[i]);
		}
		String[] keys = InsertSort.trans(table);
		for (int i = 0; i < table.length; i++) {
			System.out.println(table[i]);
			System.out.println(keys[i]);
		}

	}

}
