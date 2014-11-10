package zx.soft.sent.utils.sort;

/**
 * Title: 动态数组排序：对一组不断加进来的数据进行插入并排序，这类数据形如：username=50
 * @author wanggang
 * @version 1.1
 * @since 2013-05-27
 */
public class InsertSort {

	/**
	 * 键值插入
	 */
	public static String[] toptable(String[] table, String str) {

		String[] result = new String[table.length]; //从大到小排序
		int key = Integer.parseInt(str.substring(str.indexOf("=") + 1));
		int lastvalue = Integer.parseInt(table[table.length - 1].substring(table[table.length - 1].indexOf("=") + 1));
		int firstvalue = Integer.parseInt(table[0].substring(table[0].indexOf("=") + 1));
		int index = 0;
		if (key >= firstvalue) {
			result[0] = str;
			for (int j = 1; j < table.length; j++) {
				result[j] = table[j - 1];
			}
		} else if (key < lastvalue) {
			result = table;
		} else {
			index = InsertSort.indexSearch(table, key);
			for (int i = 0; i < index; i++) {
				result[i] = table[i];
			}
			result[index] = str;
			if (index < table.length - 1) {
				for (int j = index + 1; j < table.length; j++) {
					result[j] = table[j - 1];
				}
			}
		}

		return result;
	}

	/**
	 * 键值查找，二分法查找
	 */
	private static int indexSearch(String[] table, int key) {

		//table从大到小排序,key插在index位置
		int index = 0;
		int low = 0;
		int high = table.length;
		int media = 0;
		while (low <= high) {
			media = (low + high) / 2;
			int value = Integer.parseInt(table[media].substring(table[media].indexOf("=") + 1));
			if (key > value) {
				high = media;
				index = high;
				if (high - low == 1)
					break;
			} else if (key < value) {
				low = media;
				index = low + 1;
				if (high - low == 1)
					break;
			} else {
				index = media;
				break;
			}
		}

		return index;
	}

	/**
	 * 对于整数数组，插入排序操作
	 */
	public static int[] toptable(int[] table, int key) {

		int[] result = new int[table.length]; //从大到小排序
		int lastvalue = table[table.length - 1];
		int firstvalue = table[0];
		int index = 0;
		if (key >= firstvalue) {
			result[0] = key;
			for (int j = 1; j < table.length; j++) {
				result[j] = table[j - 1];
			}
		} else if (key < lastvalue) {
			result = table;
		} else {
			index = InsertSort.indexSearch(table, key);
			for (int i = 0; i < index; i++) {
				result[i] = table[i];
			}
			result[index] = key;
			if (index < table.length - 1) {
				for (int j = index + 1; j < table.length; j++) {
					result[j] = table[j - 1];
				}
			}
		}

		return result;
	}

	/**
	 * 键值查找
	 */
	private static int indexSearch(int[] table, int key) {

		//table从大到小排序,key插在index位置
		int index = 0;
		int low = 0;
		int high = table.length;
		int media = 0;
		while (low <= high) {
			media = (low + high) / 2;
			int value = table[media];
			if (key > value) {
				high = media;
				index = high;
				if (high - low == 1)
					break;
			} else if (key < value) {
				low = media;
				index = low + 1;
				if (high - low == 1)
					break;
			} else {
				index = media;
				break;
			}
		}

		return index;
	}

	/**
	 * 转换数据
	 */
	public static String[] trans(String[] table) {

		String[] result = new String[table.length];
		for (int i = 0; i < table.length; i++) {
			if (table[i].length() > 3) {
				result[i] = table[i].substring(0, table[i].indexOf("="));
			} else {
				result[i] = null;
			}
		}

		return result;
	}

}
