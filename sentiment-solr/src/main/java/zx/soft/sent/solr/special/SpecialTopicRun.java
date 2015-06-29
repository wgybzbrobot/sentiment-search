package zx.soft.sent.solr.special;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import zx.soft.sent.solr.query.QueryCore;
import zx.soft.utils.codec.URLCodecUtils;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.utils.time.TimeUtils;

/**
 * OA专题统计定时分析：hefei07
 *
 * 运行目录：/home/zxdfs/run-work/timer/oa-special
 * 运行命令：./special_timer.sh &
 *
 * @author wanggang
 *
 */
public class SpecialTopicRun {

	private static Logger logger = LoggerFactory.getLogger(SpecialTopicRun.class);

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		SpecialTopicRun specialTopicRun = new SpecialTopicRun();
		specialTopicRun.run();
	}

	public void run() {
		try {
			logger.info("Running updating Tasks at:" + new Date().toString());
			SpecialQuery specialQuery = new SpecialQuery(MybatisConfig.ServerEnum.sentiment);
			QueryCore queryCore = new QueryCore();
			// 在OA专题查询缓存数据表oa_special_query_cache中查询所有活跃的专题identify
			// 理论上，如果专题的end时间小于当天时间，那么就视为过期专题，不更新；只更新end时间大于等于今天的专题数据。
			// 不过在这里，认为该专题在半个月之内未被查询的化，就认为是过期的，不更新；因为我们爬虫的数据暂时不还不够及时和准确。
			long current = System.currentTimeMillis();
			List<String> identifys = specialQuery.selectSpecialIdentifyByTime(new Date(current - 15 * 86400_000L));
			// 循环更新每个专题的查询结果
			QueryParams queryParams = null;
			SpecialTopic specialInfo = null;
			FacetDateParams fdp = null;
			QueryResult pieResult = null;
			FacetDateResult trandResult = null;
			String start, end;
			for (String identify : identifys) {
				try {
					logger.info("Updating identify=" + identify + " at:" + new Date().toString());
					// 查询专题信息
					specialInfo = specialQuery.selectSpecialInfo(identify);
					if (specialInfo != null) {
						// 下面主要是时间处理
						start = specialInfo.getStart();
						end = specialInfo.getEnd();
						if (format.parse(specialInfo.getEnd()).getTime() < current) {
							// 如果end时间小于当前时间，则统计时间间隔修正为：start=end-6,end
							start = transDate(format.parse(specialInfo.getEnd()).getTime() - 6 * 86400_000, "start");
						} else {
							// 如果end时间不小于当前时间，则统计时间间隔修正为：end=current
							end = transDate(current, "end");
							if (format.parse(specialInfo.getStart()).getTime() < current - 6 * 86400_000) {
								// 如果start时间小于当前日期之前6天内，那么start=current-6
								start = transDate(current - 6 * 86400_000, "start");
							} else {
								// 如果start时间不小于当前日期之前6天内，那么start=start
							}
						}
						// 从solr集群中查询饼状图结果
						queryParams = new QueryParams();
						queryParams.setQ(specialInfo.getKeywords());
						if (specialInfo.getHometype() == 2) {
							// 2代表查询包括境内（1表示）和境外（0表示）的所有数据
							queryParams.setFq(getTimestampFilterQuery(start, end));
						} else {
							queryParams.setFq(getTimestampFilterQuery(start, end) + ";country_code:"
									+ specialInfo.getHometype());
						}
						queryParams.setFacetField("platform");
						pieResult = queryCore.queryData(queryParams, false);
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
						fdp.setFacetDateStart(TimeUtils.transTimeStr(start));
						fdp.setFacetDateEnd(TimeUtils.transTimeStr(end));
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
				} catch (Exception e) {
					logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				}
			}
			queryCore.close();
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

	public static String transDate(long timems, String type) {
		long time = timems - timems % 86400_000;
		if ("start".equalsIgnoreCase(type)) {
			time = time - 8 * 3600_000;
		} else {
			time = time + 15 * 3600_000 + 59 * 60_000;
		}
		return TimeUtils.transToCommonDateStr(time);
	}

	public static String getTimestampFilterQuery(String start, String end) {
		return "timestamp:[" + TimeUtils.transTimeStr(start) + " TO " + TimeUtils.transTimeStr(end) + "]";
	}

}
