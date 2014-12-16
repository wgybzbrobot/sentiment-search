package zx.soft.sent.solr.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.solr.common.SolrDocument;

import zx.soft.negative.sentiment.core.NegativeClassify;
import zx.soft.utils.checksum.CheckSumUtils;
import zx.soft.utils.sort.InsertSort;

public class HarmfulInfoUtil {

	private static final NegativeClassify negativeClassify = new NegativeClassify();

	/**
	 * 排序计算，得到前N负面信息
	 */
	public static List<SolrDocument> getTopNNegativeRecords(List<SolrDocument> records, int N) {
		List<SolrDocument> result = new ArrayList<>();
		HashSet<String> md5s = new HashSet<>();
		String[] insertTables = new String[records.size()];
		for (int i = 0; i < records.size(); i++) {
			String str = "";
			if (records.get(i).get("title") != null) {
				str += records.get(i).get("title").toString();
			}
			if (records.get(i).get("content") != null) {
				str += records.get(i).get("content").toString();
			}
			insertTables[i] = i + "=" + (int) (negativeClassify.getTextScore(str));
		}
		String[] table = new String[records.size()];
		for (int i = 0; i < table.length; i++) {
			table[i] = "0=0";
		}
		for (int i = 0; i < table.length; i++) {
			table = InsertSort.toptable(table, insertTables[i]);
		}
		String[] keyValue = null;
		for (int i = 0; result.size() < Math.min(table.length, N) && i < table.length; i++) {
			keyValue = table[i].split("=");
			SolrDocument doc = records.get(Integer.parseInt(keyValue[0]));
			if (md5s.contains(CheckSumUtils.getMD5(doc.getFieldValue("content").toString()))) {
				continue;
			}
			md5s.add(CheckSumUtils.getMD5(doc.getFieldValue("content").toString()));
			result.add(doc);
		}

		return result;
	}

}
