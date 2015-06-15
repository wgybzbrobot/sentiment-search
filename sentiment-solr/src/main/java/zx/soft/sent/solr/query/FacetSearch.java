package zx.soft.sent.solr.query;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.domain.FacetDateParams;
import zx.soft.sent.solr.domain.FacetDateResult;
import zx.soft.utils.http.HttpUtils;
import zx.soft.utils.json.JsonNodeUtils;

/**
 * 分类搜索，目前solrj中对时间分类搜索不支持，搜索不出结果，
 * 所以采用GET直接请求Solr接口的形式获取按时间分类统计的数据。
 *
 * @author wanggang
 *
 */
@Deprecated
public class FacetSearch {

	private static Logger logger = LoggerFactory.getLogger(FacetSearch.class);

	private static final String BASE_URL = "http://192.168.32.11:8983/solr/sentiment/select?wt=json&indent=true&facet=true&rows=0";

	private static final String Q = "q";
	private static final String Q_OP = "q.op"; // 系统默认是OR关系，但是舆情项目是AND关系
	private static final String FACET_DATE = "facet.date";
	private static final String FACET_DATE_START = "facet.date.start";
	private static final String FACET_DATE_END = "facet.date.end";
	private static final String FACET_DATE_GAP = "facet.date.gap";

	public static String getURL(FacetDateParams fdp) {
		String url = BASE_URL + "&" + Q + "=" + fdp.getQ() + "&" + Q_OP + "=AND" + "&" + FACET_DATE + "="
				+ fdp.getFacetDate() + "&" + FACET_DATE_START + "=" + fdp.getFacetDateStart() + "&" + FACET_DATE_END
				+ "=" + fdp.getFacetDateEnd() + "&" + FACET_DATE_GAP + "=" + fdp.getFacetDateGap();
		logger.info("Request's URL=" + url);
		return url;
	}

	public static String getFacetDateResult(FacetDateParams fdp) {
		String result = HttpUtils.doGet(getURL(fdp));
		return result;
	}

	public static FacetDateResult getFacetDates(String fieldName, String str) {
		FacetDateResult facetDateResult = new FacetDateResult();
		JsonNode result = JsonNodeUtils.getJsonNode(str, "facet_counts");
		result = JsonNodeUtils.getJsonNode(result, "facet_dates");
		result = JsonNodeUtils.getJsonNode(result, fieldName);
		Iterator<String> keys = result.getFieldNames();
		String key = "";
		while (keys.hasNext()) {
			key = keys.next();
			if ("start".equalsIgnoreCase(key)) {
				facetDateResult.setStart(result.get(key).toString().replaceAll("\"", ""));
			} else if ("end".equalsIgnoreCase(key)) {
				facetDateResult.setEnd(result.get(key).toString().replaceAll("\"", ""));
			} else if ("gap".equalsIgnoreCase(key)) {
				facetDateResult.setGap(result.get(key).toString().replaceAll("\"", ""));
			} else {
				facetDateResult.getDateCounts().put(key.split("T")[0],
						Long.parseLong(result.get(key).toString().replaceAll("\"", "")));
			}

		}
		return facetDateResult;
	}

	public static void main(String[] args) {
		FacetDateParams fdp = new FacetDateParams();
		fdp.setQ("*:*");
		fdp.setFacetDate("timestamp");
		fdp.setFacetDateStart("NOW-7DAYS");
		fdp.setFacetDateEnd("NOW%2B1DAY");
		fdp.setFacetDateGap("%2B1DAY");
		String result = FacetSearch.getFacetDateResult(fdp);
		System.out.println(result);
	}

}
