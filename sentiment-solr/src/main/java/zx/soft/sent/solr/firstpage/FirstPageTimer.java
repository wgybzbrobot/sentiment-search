package zx.soft.sent.solr.firstpage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.negative.sentiment.core.NegativeClassify;
import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPage;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.sort.InsertSort;

/**
 * OA首页信息——定时分析
 * 
 * @author wanggang
 *
 */
public class FirstPageTimer {

	private static Logger logger = LoggerFactory.getLogger(FirstPageTimer.class);

	// 定时分析的时间间隔,秒
	private final long timeInterval;

	public FirstPageTimer(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		/**
		 * 设置1小时跑一次
		 */
		FirstPageTimer tasker = new FirstPageTimer(2 * 60 * 1000);
		tasker.run();
	}

	/**
	 * 定时执行
	 */
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new FirstPageTasker(), 0, timeInterval);
	}

	/**
	 * 定时任务类
	 */
	static class FirstPageTasker extends TimerTask {

		private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd,HH");

		public FirstPageTasker() {
			super();
		}

		@Override
		public void run() {
			logger.info("Starting query OA-FirstPage data...");
			FirstPage firstPage = new FirstPage(MybatisConfig.ServerEnum.sentiment);
			OAFirstPage oafirstPage = new OAFirstPage();
			NegativeClassify negativeClassify = new NegativeClassify();
			/**
			 * 1、统计当前时间各类数据的总量
			 */
			HashMap<String, Long> currentPlatformSum = oafirstPage.getCurrentPlatformSum();
			firstPage.insertFirstPage(1, timeStrByHour(), JsonUtils.toJsonWithoutPretty(currentPlatformSum));
			/**
			 * 2、统计当天各类数据的进入量，其中day=0表示当天的数据
			 */
			HashMap<String, Long> todayPlatformInputSum = oafirstPage.getTodayPlatformInputSum(0);
			firstPage.insertFirstPage(2, timeStrByHour(), JsonUtils.toJsonWithoutPretty(todayPlatformInputSum));
			/**
			 * 4、根据当天的微博数据，分别统计0、3、6、9、12、15、18、21时刻的四大微博数据进入总量；
			 * 即从0点开始，每隔3个小时统计以下，如果当前的小时在这几个时刻内就统计，否则不统计。
			 */
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if (hour % 3 == 0) {
				HashMap<String, Long> todayWeibosSum = oafirstPage.getTodayWeibosSum(0, hour);
				firstPage.insertFirstPage(4, timeStrByHour(), JsonUtils.toJsonWithoutPretty(todayWeibosSum));
				//				System.out.println(JsonUtils.toJson(todayWeibosSum));
			}
			/**
			 * 5、对当天的论坛和微博进入数据进行负面评分，并按照分值推送最大的签20条内容，每小时推送一次。
			 * @param platform:论坛-2,微博-3
			 */
			List<SolrDocument> negativeRecordsForum = oafirstPage.getNegativeRecords(2, 0, 10);
			List<SolrDocument> negativeRecordsWeibo = oafirstPage.getNegativeRecords(3, 0, 10);
			negativeRecordsForum = getTopNNegativeRecords(negativeClassify, negativeRecordsForum, 20);
			negativeRecordsWeibo = getTopNNegativeRecords(negativeClassify, negativeRecordsWeibo, 20);
			firstPage.insertFirstPage(52, timeStrByHour(), JsonUtils.toJsonWithoutPretty(negativeRecordsForum));
			firstPage.insertFirstPage(53, timeStrByHour(), JsonUtils.toJsonWithoutPretty(negativeRecordsWeibo));
			//			System.out.println(JsonUtils.toJson(currentPlatformSum));
			//			System.out.println(JsonUtils.toJson(todayPlatformInputSum));
			//			System.out.println(JsonUtils.toJson(negativeRecordsForum));
			//			System.out.println(JsonUtils.toJson(negativeRecordsWeibo));
			// 关闭资源
			negativeClassify.cleanup();
			oafirstPage.close();
			logger.info("Finishing query OA-FirstPage data...");
		}

		/**
		 * 将当前的时间戳转换成小时精度，如："2014-09-05,14"
		 */
		private String timeStrByHour() {
			return FORMATTER.format(new Date());
		}

		/**
		 * 排序计算，得到前20负面信息
		 * @param records
		 * @param N
		 * @return
		 */
		private List<SolrDocument> getTopNNegativeRecords(NegativeClassify negativeClassify,
				List<SolrDocument> records, int N) {
			List<SolrDocument> result = new ArrayList<>();
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
				result.add(doc);
			}

			return result;
		}

	}

}
