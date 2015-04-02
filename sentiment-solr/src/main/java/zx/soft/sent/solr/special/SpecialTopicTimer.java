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
import zx.soft.sent.solr.domain.FacetDateParams;
import zx.soft.sent.solr.domain.FacetDateResult;
import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.domain.SimpleFacetInfo;
import zx.soft.sent.solr.query.FacetSearch;
import zx.soft.sent.solr.query.SearchingData;
import zx.soft.utils.codec.URLCodecUtils;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.utils.time.TimeUtils;

/**
 * OA专题数据统计——定时分析
 * 
 * @author wanggang
 *
 */
public class SpecialTopicTimer {

	private static Logger logger = LoggerFactory.getLogger(SpecialTopicTimer.class);

	// 定时分析的时间间隔,秒
	private final long timeInterval;

	public SpecialTopicTimer(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		/**
		 * 设置60分钟跑一次
		 */
		SpecialTopicTimer tasker = new SpecialTopicTimer(60 * 60 * 1000);
		try {
			tasker.run();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	/**
	 * 定时执行
	 */
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new SpecialTopicTasker(), 0, timeInterval);
	}

	/**
	 * 定时任务类
	 */
	static class SpecialTopicTasker extends TimerTask {

		public SpecialTopicTasker() {
			super();
		}

		@Override
		public void run() {
			try {
				logger.info("Running updating Tasks at:" + new Date().toString());
				SpecialQuery specialQuery = new SpecialQuery(MybatisConfig.ServerEnum.sentiment);
				SearchingData search = new SearchingData();
				// 在OA专题查询缓存数据表oa_special_query_cache中查询所有活跃的专题identify
				// 在这里认为，如果一个月内没有查询就不更新
				long start = System.currentTimeMillis() - 30 * 86400_000L;
				List<String> identifys = specialQuery.selectSpecialIdentifyByTime(new Date(start));
				// 循环更新每个专题的查询结果
				QueryParams queryParams = null;
				SpecialTopic specialInfo = null;
				FacetDateParams fdp = null;
				QueryResult pieResult = null;
				FacetDateResult trandResult = null;
				for (String identify : identifys) {
					logger.info("Updating identify=" + identify + " at:" + new Date().toString());
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
						if (specialQuery.selectSpecialResult(identify, "pie") == null) {
							specialQuery.insertSpecialResult(identify, "pie",
									JsonUtils.toJsonWithoutPretty(getPieChart(specialInfo, pieResult)));
						} else {
							specialQuery.updateSpecialResult(identify, "pie",
									JsonUtils.toJsonWithoutPretty(getPieChart(specialInfo, pieResult)));
						}
						//					System.out.println(JsonUtils.toJson(getPieChart(specialInfo, pieResult)));
						// 从solr集群中查询趋势图结果
						fdp = new FacetDateParams();
						fdp.setQ(URLCodecUtils.encoder(specialInfo.getKeywords(), "UTF-8")); // URL中的部分字符需要编码转换
						fdp.setFacetDate("timestamp");
						fdp.setFacetDateStart(TimeUtils.transTimeStr(specialInfo.getStart()));
						fdp.setFacetDateEnd(TimeUtils.transTimeStr(specialInfo.getEnd()));
						fdp.setFacetDateGap("%2B1DAY");
						trandResult = FacetSearch.getFacetDates("timestamp", FacetSearch.getFacetDateResult(fdp));
						// 更新趋势图结果到数据库中
						if (specialQuery.selectSpecialResult(identify, "trend") == null) {
							specialQuery.insertSpecialResult(identify, "trend",
									JsonUtils.toJsonWithoutPretty(getTrendChart(specialInfo, trandResult)));
						} else {
							specialQuery.updateSpecialResult(identify, "trend",
									JsonUtils.toJsonWithoutPretty(getTrendChart(specialInfo, trandResult)));
						}
						//					System.out.println(JsonUtils.toJson(getTrendChart(specialInfo, trandResult)));
					}
				}
				search.close();
			} catch (Exception e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			}
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

		private TrendChart getTrendChart(SpecialTopic specialInfo, FacetDateResult result) {
			TrendChart trendChart = new TrendChart();
			trendChart.setSpecialInfo(new SpecialInfo(specialInfo.getIdentify(), specialInfo.getName()));
			trendChart.setCountByDay(result.getDateCounts());
			return trendChart;
		}

		public static String getTimestampFilterQuery(String start, String end) {
			return "timestamp:[" + TimeUtils.transTimeStr(start) + " TO " + TimeUtils.transTimeStr(end) + "]";
		}

	}

}
