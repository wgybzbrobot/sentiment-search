package zx.soft.sent.solr.search;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	}

	public static class RemoveTimer extends TimerTask {

		@Override
		public void run() {
			SearchingData search = new SearchingData();
			search.deleteQuery("timestamp:[2000-11-27T00:00:00Z TO 2014-09-30T23:59:59Z]");
			search.close();
		}

	}

}
