package zx.soft.sent.special;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.special.SpecialQuery;

/**
 * OA专题数据统计——定时分析
 * 
 * @author wanggang
 *
 */
public class SpecialTopicTimer {

	private static Logger logger = LoggerFactory.getLogger(SpecialTopicTimer.class);

	// MySQL操作类
	private final SpecialQuery specialQuery;

	// 定时分析的时间间隔,秒
	private final long timeInterval;

	public SpecialTopicTimer(long timeInterval) {
		specialQuery = new SpecialQuery(MybatisConfig.ServerEnum.sentiment);
		this.timeInterval = timeInterval;
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * 定时执行
	 */
	public void run() {
		//		
	}

	/**
	 * 定时任务类
	 */
	static class SpecialTopicTasker extends TimerTask {

		private final SpecialQuery specialQuery;

		public SpecialTopicTasker(SpecialQuery specialQuery) {
			super();
			this.specialQuery = specialQuery;
		}

		@Override
		public void run() {
			// 在OA专题查询缓存数据表oa_special_query_cache中查询所有活跃的专题identify
			// 在这里认为，如果

		}

	}

}
