package zx.soft.sent.solr.special;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.special.SpecialTopic;
import zx.soft.sent.dao.special.SpecialQuery;
import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.domain.SimpleFacetInfo;
import zx.soft.sent.solr.search.SearchingData;
import zx.soft.sent.solr.utils.TimeConvert;
import zx.soft.sent.utils.json.JsonUtils;

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

		/**
		 * 设置30分钟跑一次
		 */
		SpecialTopicTimer tasker = new SpecialTopicTimer(30 * 60 * 1000);
		tasker.run();
	}

	/**
	 * 定时执行
	 */
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new SpecialTopicTasker(specialQuery), 0, timeInterval);
		timer.cancel();
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

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			logger.info("Running updating Tasks at:" + new Date().toLocaleString());
			SearchingData search = new SearchingData();
			// 在OA专题查询缓存数据表oa_special_query_cache中查询所有活跃的专题identify
			// 在这里认为，如果一个月内没有查询就不更新
			long start = System.currentTimeMillis() / 1000 - 30 * 86400;
			List<String> identifys = specialQuery.selectSpecialIdentifyByTime(start);
			// 循环更新每个专题的查询结果
			QueryParams queryParams = null;
			SpecialTopic specialInfo = null;
			QueryResult pieResult = null;
			QueryResult trandResult = null;
			for (String identify : identifys) {
				logger.info("Updating identify=" + identify + " at:" + new Date().toLocaleString());
				// 查询专题信息
				specialInfo = specialQuery.selectSpecialInfo(identify);
				if (specialInfo != null) {
					// 从solr集群中查询饼状图结果
					queryParams = new QueryParams();
					queryParams.setQ(specialInfo.getKeywords());
					queryParams.setFq(getTimestampFilterQuery(specialInfo.getStart(), specialInfo.getEnd())
							+ ";country_code:" + specialInfo.getHometype());
					queryParams.setFacetField("platform");
					pieResult = search.queryData(queryParams, false);
					// 更新饼状图结果到数据库中
					specialQuery.updateSpecialResult(identify, "",
							JsonUtils.toJsonWithoutPretty(getPieChart(specialInfo, pieResult)));
					// 从solr集群中查询趋势图结果
					// 在某个时间段内按天统计
					// facet=true&facet.date=timestamp
					// facet.date.start=2014-04-10T19:18:00Z
					// facet.date.end=2014-04-14T19:18:00Z
					// facet.date.gap=%2B1DAY
					queryParams = new QueryParams();
					queryParams.setQ(specialInfo.getKeywords() + "&facet=true&facet.date=timestamp"
							+ "&facet.date.start=" + TimeConvert.transTimeStr(specialInfo.getStart())
							+ "&facet.date.end="
							+ TimeConvert.transTimeStr(specialInfo.getEnd() + "facet.date.gap=%2B1DAY"));
					queryParams.setFq(getTimestampFilterQuery(specialInfo.getStart(), specialInfo.getEnd())
							+ ";country_code:" + specialInfo.getHometype());
					trandResult = search.queryData(queryParams, false);
					// 更新趋势图结果到数据库中
					specialQuery.updateSpecialResult(identify, "", JsonUtils.toJsonWithoutPretty(trandResult));
				}
			}
			search.close();
		}

		private PieChart getPieChart(SpecialTopic specialInfo, QueryResult result) {
			PieChart pieChart = new PieChart();
			pieChart.setSpecialInfo(new SpecialInfo(specialInfo.getIdentify(), specialInfo.getName()));
			List<SimpleFacetInfo> facetFields = result.getFacetFields();
			for (SimpleFacetInfo facetField : facetFields) {
				if ("platform".equalsIgnoreCase(facetField.getName())) {
					pieChart.setPlatformCount(facetField.getValues());
				}
			}
			return pieChart;
		}

		public static String getTimestampFilterQuery(String start, String end) {
			return "timestamp:[" + TimeConvert.transTimeStr(start) + " TO " + TimeConvert.transTimeStr(end) + "]";
		}

	}

}
