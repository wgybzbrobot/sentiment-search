package zx.soft.sent.solr.firstpage;

import java.util.Date;

import zx.soft.utils.time.TimeUtils;

public class Demo {

	public static void main(String[] args) {
		// 当天第hour-3,hour时间段
		int hour = 3;
		long currentTime = System.currentTimeMillis() - 6 * 3600_000L;
		long startTime = currentTime - currentTime % 86400_000L - 8 * 3600_000L;
		if (new Date(currentTime).getHours() < 8) {
			startTime += 1 * 86400_000L;
		}
		long endTime = startTime + hour * 3600_000; // 该天的第hour时刻，时间间隔为三小时
		startTime = endTime - 3 * 3600_000; // 该天的第hour-3时刻
		System.out.println("lasttime:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(endTime) + "]");
	}

}
