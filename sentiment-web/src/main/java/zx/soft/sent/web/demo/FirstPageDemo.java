package zx.soft.sent.web.demo;

import java.util.HashMap;
import java.util.List;

import org.apache.solr.common.SolrDocument;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPage;
import zx.soft.sent.solr.firstpage.OAFirstPage;
import zx.soft.sent.utils.json.JsonUtils;

public class FirstPageDemo {

	public static void main(String[] args) {

		System.out.println("Starting query OA-FirstPage data...");
		FirstPage firstPage = new FirstPage(MybatisConfig.ServerEnum.sentiment);
		OAFirstPage oafirstPage = new OAFirstPage();
		String timeStr = "2014-09-05,14";
		/**
		 * 1、统计当前时间各类数据的总量
		 */
		HashMap<String, Long> currentPlatformSum = oafirstPage.getCurrentPlatformSum();
		firstPage.insertFirstPage(1, timeStr, JsonUtils.toJsonWithoutPretty(currentPlatformSum));
		/**
		 * 2、统计当天各类数据的进入量，其中day=0表示当天的数据
		 */
		HashMap<String, Long> todayPlatformInputSum = oafirstPage.getTodayPlatformInputSum(147);
		firstPage.insertFirstPage(2, timeStr, JsonUtils.toJsonWithoutPretty(todayPlatformInputSum));
		/**
		 * 3、根据发布人username获取他最新的N条信息
		 */
		List<SolrDocument> topNRecordsByUsername = oafirstPage.getTopNRecordsByUsername(20, "452962");
		firstPage.insertFirstPage(3, timeStr, JsonUtils.toJsonWithoutPretty(topNRecordsByUsername));
		/**
		 * 4、根据当天的微博数据，分别统计0、3、6、9、12、15、18、21时刻的四大微博数据进入总量；
		 * 即从0点开始，每隔3个小时统计以下，如果当前的小时在这几个时刻内就统计，否则不统计。
		 */
		HashMap<String, Long> todayWeibosSum = oafirstPage.getTodayWeibosSum(147, 9);
		firstPage.insertFirstPage(4, timeStr, JsonUtils.toJsonWithoutPretty(todayWeibosSum));

		/**
		 * 5、对当天的论坛和微博进入数据进行负面评分，并按照分值推送最大的签20条内容，每小时推送一次。
		 * @param platform:论坛-2,微博-3
		 */
		List<SolrDocument> negativeRecordsForum = oafirstPage.getNegativeRecords(2, 147, 10);
		List<SolrDocument> negativeRecordsWeibo = oafirstPage.getNegativeRecords(3, 147, 10);
		firstPage.insertFirstPage(52, timeStr, JsonUtils.toJsonWithoutPretty(negativeRecordsForum));
		firstPage.insertFirstPage(53, timeStr, JsonUtils.toJsonWithoutPretty(negativeRecordsWeibo));
		// 关闭资源
		oafirstPage.close();
		System.out.println("Finishing query OA-FirstPage data...");
	}

}
