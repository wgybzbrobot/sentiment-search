package zx.soft.sent.solr.query;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.time.TimeUtils;

/**
 * 定时删除
 *
 * 1、删除1周前的微博数据
 * 2、删除1个月前的其他数据
 *
 * @author wanggang
 *
 */
public class RemoveSentiData {

	private static Logger logger = LoggerFactory.getLogger(RemoveSentiData.class);

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new RemoveTimer(), 0, 86400_000L);
	}

	public static class RemoveTimer extends TimerTask {

		@Override
		public void run() {
			logger.info("Start Removing expired data ...");
			QueryCore queryCore = new QueryCore();
			// 删除一个月之前的数据
			String end = TimeUtils.transToSolrDateStr(System.currentTimeMillis() - 30 * 86400_000L);
			String query = "lasttime:[1970-01-01T00:00:00Z TO " + end + "]";
			queryCore.deleteQuery(query);
			queryCore.close();
			logger.info("Finish Removing expired data ...");
		}

	}

}
