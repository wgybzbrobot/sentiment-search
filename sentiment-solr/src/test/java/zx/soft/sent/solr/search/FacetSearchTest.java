package zx.soft.sent.solr.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import zx.soft.sent.solr.domain.FacetDateParams;
import zx.soft.sent.solr.query.FacetSearch;

public class FacetSearchTest {

	@Test
	public void testGetURL() {
		FacetDateParams fdp = new FacetDateParams();
		fdp.setQ("*:*");
		fdp.setFacetDate("timestamp");
		fdp.setFacetDateStart("NOW-7DAYS");
		fdp.setFacetDateEnd("NOW");
		fdp.setFacetDateGap("%2B1DAY");
		assertEquals("http://192.168.32.11:8983/solr/sentiment/select?wt=json&indent=true&facet=true&rows=0&q=*:*&"
				+ "q.op=AND&facet.date=timestamp&facet.date.start=NOW-7DAYS&facet.date.end=NOW&facet.date.gap=%2B1DAY",
				FacetSearch.getURL(fdp));
	}

}
