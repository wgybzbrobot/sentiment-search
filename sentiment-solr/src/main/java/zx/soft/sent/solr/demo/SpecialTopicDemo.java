package zx.soft.sent.solr.demo;

import java.util.Date;
import java.util.List;

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
import zx.soft.sent.solr.search.FacetSearch;
import zx.soft.sent.solr.search.SearchingData;
import zx.soft.sent.solr.special.PieChart;
import zx.soft.sent.solr.special.SpecialInfo;
import zx.soft.sent.solr.special.TrendChart;
import zx.soft.sent.utils.json.JsonUtils;
import zx.soft.sent.utils.time.TimeUtils;

public class SpecialTopicDemo {

	public static void main(String[] args) {

		SpecialQuery specialQuery = new SpecialQuery(MybatisConfig.ServerEnum.sentiment);
		SpecialTopicDemo demo = new SpecialTopicDemo(specialQuery);
		demo.run();
	}

	private static Logger logger = LoggerFactory.getLogger(SpecialTopicDemo.class);

	private final SpecialQuery specialQuery;

	public SpecialTopicDemo(SpecialQuery specialQuery) {
		this.specialQuery = specialQuery;
	}

	@SuppressWarnings("deprecation")
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
		FacetDateParams fdp = null;
		QueryResult pieResult = null;
		FacetDateResult trandResult = null;
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
				if (specialQuery.selectSpecialResult(identify, "pie") == null) {
					specialQuery.insertSpecialResult(identify, "pie",
							JsonUtils.toJsonWithoutPretty(getPieChart(specialInfo, pieResult)));
				} else {
					System.out.println(JsonUtils.toJson(getPieChart(specialInfo, pieResult)));
					specialQuery.updateSpecialResult(identify, "pie",
							JsonUtils.toJsonWithoutPretty(getPieChart(specialInfo, pieResult)));
				}
				// 从solr集群中查询趋势图结果
				fdp = new FacetDateParams();
				fdp.setQ(specialInfo.getKeywords());
				fdp.setFacetDate("timestamp");
				fdp.setFacetDateStart(TimeUtils.transTimeStr(specialInfo.getStart()));
				fdp.setFacetDateEnd(TimeUtils.transTimeStr(specialInfo.getEnd()));
				fdp.setFacetDateGap("%2B1DAY");
				trandResult = FacetSearch.getFacetDates("timestamp", FacetSearch.getFacetDateResult(fdp));
				//				 更新趋势图结果到数据库中
				if (specialQuery.selectSpecialResult(identify, "trend") == null) {
					specialQuery.insertSpecialResult(identify, "trend",
							JsonUtils.toJsonWithoutPretty(getTrendChart(specialInfo, trandResult)));
				} else {
					System.out.println(JsonUtils.toJson(getTrendChart(specialInfo, trandResult)));
					specialQuery.updateSpecialResult(identify, "trend",
							JsonUtils.toJsonWithoutPretty(getTrendChart(specialInfo, trandResult)));
				}
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
