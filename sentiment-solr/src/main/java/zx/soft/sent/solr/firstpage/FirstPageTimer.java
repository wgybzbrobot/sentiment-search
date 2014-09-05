package zx.soft.sent.solr.firstpage;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPage;
import zx.soft.sent.utils.json.JsonUtils;

/**
 * OA首页信息——定时分析
 * 
 * @author wanggang
 *
 */
public class FirstPageTimer {

	private static Logger logger = LoggerFactory.getLogger(FirstPageTimer.class);

	// MySQL操作类
	private final FirstPage firstPage;

	// 定时分析的时间间隔,秒
	private final long timeInterval;

	// 发布人username
	private final String username;

	public FirstPageTimer(long timeInterval, String username) {
		firstPage = new FirstPage(MybatisConfig.ServerEnum.sentiment);
		this.timeInterval = timeInterval;
		this.username = username;
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		/**
		 * 设置1小时跑一次
		 */
		FirstPageTimer tasker = new FirstPageTimer(60 * 60 * 1000, "123456");
		tasker.run();
	}

	/**
	 * 定时执行
	 */
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new FirstPageTasker(firstPage, username), 0, timeInterval);
		timer.cancel();
	}

	/**
	 * 定时任务类
	 */
	static class FirstPageTasker extends TimerTask {

		private final FirstPage firstPage;
		private final String username;

		public FirstPageTasker(FirstPage firstPage, String username) {
			super();
			this.firstPage = firstPage;
			this.username = username;
		}

		@Override
		public void run() {
			logger.info("Starting query OA-FirstPage data...");
			OAFirstPage oafirstPage = new OAFirstPage();
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
			 * 3、根据发布人username获取他最新的N条信息
			 */
			List<SolrDocument> topNRecordsByUsername = oafirstPage.getTopNRecordsByUsername(20, username);
			firstPage.insertFirstPage(3, timeStrByHour(), JsonUtils.toJsonWithoutPretty(topNRecordsByUsername));
			/**
			 * 4、根据当天的微博数据，分别统计0、3、6、9、12、15、18、21时刻的四大微博数据进入总量；
			 * 即从0点开始，每隔3个小时统计以下，如果当前的小时在这几个时刻内就统计，否则不统计。
			 */
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if (hour % 3 == 0) {
				HashMap<String, Long> todayWeibosSum = oafirstPage.getTodayWeibosSum(0, hour);
				firstPage.insertFirstPage(4, timeStrByHour(), JsonUtils.toJsonWithoutPretty(todayWeibosSum));
			}

			/**
			 * 5、对当天的论坛和微博进入数据进行负面评分，并按照分值推送最大的签20条内容，每小时推送一次。
			 * @param platform:论坛-2,微博-3
			 */
			List<SolrDocument> negativeRecordsForum = oafirstPage.getNegativeRecords(2, 0, 20);
			List<SolrDocument> negativeRecordsWeibo = oafirstPage.getNegativeRecords(3, 0, 20);
			firstPage.insertFirstPage(52, timeStrByHour(), JsonUtils.toJsonWithoutPretty(negativeRecordsForum));
			firstPage.insertFirstPage(53, timeStrByHour(), JsonUtils.toJsonWithoutPretty(negativeRecordsWeibo));
			// 关闭资源
			oafirstPage.close();
			logger.info("Finishing query OA-FirstPage data...");
		}

		/**
		 * 将当前的时间戳转换成小时精度，如："2014-9-5,14"
		 */
		private String timeStrByHour() {
			@SuppressWarnings("deprecation")
			String[] dateStr = Calendar.getInstance().getTime().toLocaleString().split("\\s");
			return dateStr[0] + "," + dateStr[1].split(":")[0];
		}

	}

}
