package zx.soft.sent.solr.search;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.time.TimeUtils;

/**
 * 定时删除微博数据：hefei08
 *
 * 运行目录：/home/zxdfs/run-work/remove/weibos
 * 运行命令：cd sentiment-solr
 *        ./timer-remove-weibos.sh &
 *
 * @author wanggang
 *
 */
public class RemoveWeiboData {

	private static Logger logger = LoggerFactory.getLogger(RemoveWeiboData.class);

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		logger.info("Start Removing expired data ...");
		SearchingData search = new SearchingData();
		// 删除七天之前的数据
		String end = TimeUtils.transToSolrDateStr(System.currentTimeMillis() - 7 * 86400_000L);
		String query = "platform:3 AND timestamp:[1970-01-01T00:00:00Z TO " + end + "]";
		search.deleteQuery(query);
		search.close();
		logger.info("Finish Removing expired data ...");
		//		Timer timer = new Timer();
		//		timer.schedule(new RemoveTimer(), 0, 86400_000L);
	}

	public static class RemoveTimer extends TimerTask {

		@Override
		public void run() {
			logger.info("Start Removing expired data ...");
			SearchingData search = new SearchingData();
			// 删除七天之前的数据
			String end = TimeUtils.transToSolrDateStr(System.currentTimeMillis() - 7 * 86400_000L);
			String query = "platform:3 AND timestamp:[1970-01-01T00:00:00Z TO " + end + "]";
			search.deleteQuery(query);
			search.close();
			logger.info("Finish Removing expired data ...");
		}

	}

}
