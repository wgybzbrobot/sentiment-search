package zx.soft.sent.solr.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.negative.sentiment.core.NegativeClassify;
import zx.soft.negative.sentiment.utils.JsonUtils;
import zx.soft.sent.arith.sort.InsertSort;
import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPage;
import zx.soft.sent.solr.firstpage.OAFirstPage;
import zx.soft.sent.utils.checksum.CheckSumUtils;

public class FirstPageDemo {

	public static void main(String[] args) {
		FirstPage firstPage = new FirstPage(MybatisConfig.ServerEnum.sentiment);
		FirstPageDemo demo = new FirstPageDemo(firstPage);
		demo.run();
	}

	private static Logger logger = LoggerFactory.getLogger(FirstPageDemo.class);

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd,HH");

	@SuppressWarnings("unused")
	private final FirstPage firstPage;

	public FirstPageDemo(FirstPage firstPage) {
		this.firstPage = firstPage;
	}

	public void run() {
		logger.info("Starting query OA-FirstPage data...");
		OAFirstPage oafirstPage = new OAFirstPage();
		NegativeClassify negativeClassify = new NegativeClassify();
		/**
		 * 1、统计当前时间各类数据的总量
		 */
		HashMap<String, Long> currentPlatformSum = oafirstPage.getCurrentPlatformSum();
		System.out.println(JsonUtils.toJsonWithoutPretty(currentPlatformSum));
		//		firstPage.insertFirstPage(1, timeStrByHour(), JsonUtils.toJsonWithoutPretty(currentPlatformSum));
		/**
		 * 2、统计当天各类数据的进入量，其中day=0表示当天的数据
		 */
		HashMap<String, Long> todayPlatformInputSum = oafirstPage.getTodayPlatformInputSum(0);
		System.out.println(JsonUtils.toJsonWithoutPretty(todayPlatformInputSum));
		//		firstPage.insertFirstPage(2, timeStrByHour(), JsonUtils.toJsonWithoutPretty(todayPlatformInputSum));
		/**
		 * 4、根据当天的微博数据，分别统计0、3、6、9、12、15、18、21时刻的四大微博数据进入总量；
		 * 即从0点开始，每隔3个小时统计以下，如果当前的小时在这几个时刻内就统计，否则不统计。
		 */
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hour % 3 == 0) {
			HashMap<String, Long> todayWeibosSum = oafirstPage.getTodayWeibosSum(0, hour);
			System.out.println(JsonUtils.toJsonWithoutPretty(todayWeibosSum));
			//			firstPage.insertFirstPage(4, timeStrByHour(), JsonUtils.toJsonWithoutPretty(todayWeibosSum));
		}
		/**
		 * 5、对当天的论坛和微博进入数据进行负面评分，并按照分值推送最大的签100条内容，每小时推送一次。
		 * @param platform:论坛-2,微博-3
		 */
		List<SolrDocument> negativeRecordsForum = oafirstPage.getNegativeRecords(2, 0, 10);
		List<SolrDocument> negativeRecordsWeibo = oafirstPage.getNegativeRecords(3, 0, 10);
		negativeRecordsForum = getTopNNegativeRecords(negativeClassify, negativeRecordsForum, 20);
		negativeRecordsWeibo = getTopNNegativeRecords(negativeClassify, negativeRecordsWeibo, 20);
		System.out.println(JsonUtils.toJson(negativeRecordsForum));
		System.out.println(JsonUtils.toJson(negativeRecordsWeibo));
		//		firstPage.insertFirstPage(52, timeStrByHour(), JsonUtils.toJsonWithoutPretty(negativeRecordsForum));
		//		firstPage.insertFirstPage(53, timeStrByHour(), JsonUtils.toJsonWithoutPretty(negativeRecordsWeibo));
		// 关闭资源
		negativeClassify.cleanup();
		oafirstPage.close();
		logger.info("Finishing query OA-FirstPage data...");
	}

	/**
	 * 将当前的时间戳转换成小时精度，如："2014-9-5,14"
	 */
	public String timeStrByHour() {
		return FORMATTER.format(new Date());
	}

	private List<SolrDocument> getTopNNegativeRecords(NegativeClassify negativeClassify, List<SolrDocument> records,
			int N) {
		List<SolrDocument> result = new ArrayList<>();
		HashSet<String> urls = new HashSet<>();
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
		String[] keyvalue = null;
		for (int i = 0; i < Math.min(table.length, N); i++) {
			keyvalue = table[i].split("=");
			SolrDocument doc = records.get(Integer.parseInt(keyvalue[0]));
			doc.setField("score", keyvalue[1]);
			if (urls.contains(CheckSumUtils.getMD5(doc.getFieldValue("content").toString()))) {
				continue;
			}
			urls.add(CheckSumUtils.getMD5(doc.getFieldValue("content").toString()));
			result.add(doc);
		}

		return result;
	}
}
