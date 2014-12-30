package zx.soft.sent.web.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.special.SpecialQuery;
import zx.soft.sent.solr.special.PieChart;
import zx.soft.sent.solr.special.SpecialInfo;
import zx.soft.sent.solr.special.TrendChart;
import zx.soft.sent.web.resource.SpecialSpeedResource;
import zx.soft.utils.json.JsonNodeUtils;

/**
 * 专题模块应用类
 * 
 * @author wanggang
 *
 */
public class SpecialSpeedApplication extends Application {

	private final SpecialQuery specialQuery;

	public SpecialSpeedApplication() {
		specialQuery = new SpecialQuery(MybatisConfig.ServerEnum.sentiment);
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// GET专题信息
		router.attach("/{identifys}", SpecialSpeedResource.class);
		return router;
	}

	/**
	 * 获取专题查询结果，根据identify+type，查询结束后更新时间
	 */
	public List<SpecialResult> selectSpecialResult(String identifys) {
		List<SpecialResult> result = new ArrayList<>();
		for (String identify : identifys.split(",")) {
			SpecialResult specialResult = new SpecialResult();
			specialResult.setPieChart(strToPieChart(specialQuery.selectSpecialResult(identify, "pie")));
			specialResult.setTrendChart(strToTrendChart(specialQuery.selectSpecialResult(identify, "trend")));
			result.add(specialResult);
			specialQuery.updateSpecialResultLasttime(identify);
		}
		return result;
	}

	public class SpecialResult {

		private PieChart pieChart;
		private TrendChart trendChart;

		public PieChart getPieChart() {
			return pieChart;
		}

		public void setPieChart(PieChart pieChart) {
			this.pieChart = pieChart;
		}

		public TrendChart getTrendChart() {
			return trendChart;
		}

		public void setTrendChart(TrendChart trendChart) {
			this.trendChart = trendChart;
		}

	}

	private TrendChart strToTrendChart(String queryResult) {
		TrendChart trendChart = new TrendChart();
		JsonNode specialInfo = JsonNodeUtils.getJsonNode(queryResult, "specialInfo");
		JsonNode countByDay = JsonNodeUtils.getJsonNode(queryResult, "countByDay");
		trendChart.setSpecialInfo(new SpecialInfo(specialInfo.get("identify").toString().replaceAll("\"", ""),
				specialInfo.get("specialName").toString().replaceAll("\"", "")));
		Iterator<String> names = countByDay.getFieldNames();
		String key;
		while (names.hasNext()) {
			key = names.next();
			trendChart.getCountByDay().put(key, Long.parseLong(countByDay.get(key).toString()));
		}
		return trendChart;
	}

	private PieChart strToPieChart(String queryResult) {
		PieChart pieChart = new PieChart();
		JsonNode specialInfo = JsonNodeUtils.getJsonNode(queryResult, "specialInfo");
		JsonNode platformCount = JsonNodeUtils.getJsonNode(queryResult, "platformCount");
		pieChart.setSpecialInfo(new SpecialInfo(specialInfo.get("identify").toString().replaceAll("\"", ""),
				specialInfo.get("specialName").toString().replaceAll("\"", "")));
		for (int i = 0; i < 10; i++) {
			if (platformCount.get(i + "") == null) {
				pieChart.getPlatformCount().put(i + "", 0L);
				continue;
			}
			pieChart.getPlatformCount().put(i + "", Long.parseLong(platformCount.get(i + "").toString()));
		}
		return pieChart;
	}

}
