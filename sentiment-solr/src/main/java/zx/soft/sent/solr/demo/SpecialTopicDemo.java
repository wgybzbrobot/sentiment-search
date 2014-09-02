package zx.soft.sent.solr.demo;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.search.SearchingData;

public class SpecialTopicDemo {

	public static void main(String[] args) {

		SearchingData search = new SearchingData();
		QueryParams queryParams = new QueryParams();
		queryParams.setQ("*:*");
		queryParams.setRows(0);
		queryParams.getFacetDate().put("facet.date", "timestamp");
		queryParams.getFacetDate().put("facet.date.start", "2014-04-10T19:18:00Z");
		queryParams.getFacetDate().put("facet.date.end", "2014-04-14T19:18:00Z");
		queryParams.getFacetDate().put("facet.date.gap", "%2B1DAY");
		queryParams.setFq("timestamp:[2014-04-10T19:18:00Z TO 2014-04-14T19:18:00Z]");
		QueryResult result = search.queryData(queryParams, false);
		//		TrendChart trandChart = new TrendChart();
		//		trandChart.setSpecialInfo(new SpecialInfo("shshfkfl", "合肥测试"));
		//		List<SimpleFacetInfo> facetFields = result.getFacetFields();
		//		for (SimpleFacetInfo facetField : facetFields) {
		//			if ("platform".equalsIgnoreCase(facetField.getName())) {
		//				trandChart.setPlatformCount(facetField.getValues());
		//			}
		//		}

		//		System.out.println(JsonUtils.toJson(result));
		search.close();
	}
}
