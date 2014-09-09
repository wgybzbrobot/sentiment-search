package zx.soft.sent.solr.demo;

import zx.soft.sent.solr.domain.FacetDateParams;
import zx.soft.sent.solr.domain.FacetDateResult;
import zx.soft.sent.solr.search.FacetSearch;
import zx.soft.sent.utils.json.JsonUtils;

public class SpecialTopicDemo {

	public static void main(String[] args) {

		FacetDateParams fdp = new FacetDateParams();
		fdp.setQ("*:*");
		fdp.setFacetDate("timestamp");
		fdp.setFacetDateStart("NOW-7DAYS");
		fdp.setFacetDateEnd("NOW");
		fdp.setFacetDateGap("%2B1DAY");
		String result = FacetSearch.getFacetDateResult(fdp);
		FacetDateResult facetDateResult = FacetSearch.getFacetDates("timestamp", result);
		System.out.println(JsonUtils.toJson(facetDateResult));
	}

}
