package zx.soft.sent.web.resource;

import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.domain.special.SpecialTopic;
import zx.soft.sent.solr.special.PieChart;
import zx.soft.sent.solr.special.SpecialInfo;
import zx.soft.sent.solr.special.TrendChart;
import zx.soft.sent.utils.json.JsonNodeUtils;
import zx.soft.sent.web.application.SpecialApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.sent.web.utils.URLCodecUtils;

/**
 * 专题资源类
 * 
 * @author wanggang
 *
 */
public class SpecialResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SpecialResource.class);

	private SpecialApplication application;

	private String identify = "";
	private String type = "";

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (SpecialApplication) getApplication();
		identify = (String) this.getRequest().getAttributes().get("identify");
		type = (String) this.getRequest().getAttributes().get("type");
	}

	@Post("json")
	public Object addSpecialTopics(final SpecialTopics specialTopics) {
		application.insertSpecialInfos(specialTopics);
		return new ErrorResponse.Builder(0, "ok").build();
	}

	@Get("json")
	public Object getSpecialResult() {
		if (identify == null || identify.length() == 0 || type == null || type.length() == 0) {
			logger.error("Params `identify` or `type` is null.");
			return null;
		}
		String queryResult = application.selectSpecialResult(identify, type);
		if (queryResult.contains("platformCount")) {
			return strToPieChart(queryResult);
		} else if (queryResult.contains("countByDay")) {
			return strToTrendChart(queryResult);
		} else {
			return new ErrorResponse.Builder(-1, "params error!").build();
		}
	}

	@Delete
	public Object rmSpecialTopic() {
		if (identify == null || identify.length() == 0) {
			logger.error("Param `identify` is null.");
			return null;
		}
		application.deleteSpecialInfo(identify);
		return new ErrorResponse.Builder(0, "ok").build();
	}

	/**
	 * 解决Jackson无法识别java.util.Collection问题。
	 *
	 */
	public static class SpecialTopics extends ArrayList<SpecialTopic> {

		private static final long serialVersionUID = 1144770130427640340L;

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
			pieChart.getPlatformCount().put(i + "", Long.parseLong(platformCount.get(i + "").toString()));
		}
		return pieChart;
	}

}
