package zx.soft.sent.utils.sort;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InsertSortTest {

	private static final String[] IN_STRS_ARR = { "111=5", "222=1", "333=9", "444=2", "555=6" };
	private static final String[] OUT_STRS_ARR = { "333=9", "555=6", "111=5", "444=2", "222=1" };
	private static final String[] OUT_STRS = { "333", "555", "111", "444", "222" };

	private static final int[] IN_INT_ARR = { 5, 1, 9, 2, 6 };
	private static final int[] OUT_INT_ARR = { 9, 6, 5, 2, 1 };

	@Test
	public void testStrArrToptable_测试字符数组() {

		String[] table = new String[IN_STRS_ARR.length];
		for (int i = 0; i < table.length; i++) {
			table[i] = "0=0";
		}
		for (int i = 0; i < table.length; i++) {
			table = InsertSort.toptable(table, IN_STRS_ARR[i]);
		}
		String[] keys = InsertSort.trans(table);
		for (int i = 0; i < table.length; i++) {
			assertEquals(OUT_STRS_ARR[i], table[i]);
			assertEquals(OUT_STRS[i], keys[i]);
		}
	}

	@Test
	public void testIntArrToptable_测试整数数组() {

		int[] table = new int[IN_INT_ARR.length];
		for (int i = 0; i < table.length; i++) {
			table = InsertSort.toptable(table, IN_INT_ARR[i]);
		}
		for (int i = 0; i < table.length; i++) {
			assertEquals(OUT_INT_ARR[i], table[i]);
		}
	}

}
