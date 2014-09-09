package zx.soft.sent.solr.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.domain.FacetDateParams;
import zx.soft.sent.utils.http.HttpUtils;

/**
 * 分类搜索，目前solrj中对时间分类搜索不支持，搜索不出结果，
 * 所以采用GET直接请求Solr接口的形式获取按时间分类统计的数据。
 * 
 * @author wanggang
 *
 */
public class FacetSearch {

	private static Logger logger = LoggerFactory.getLogger(FacetSearch.class);

	private static final String BASE_URL = "http://192.168.32.11:8983/solr/sentiment/select?wt=json&indent=true&facet=true&rows=0";

	private static final String Q = "q";
	private static final String FACET_DATE = "facet.date";
	private static final String FACET_DATE_START = "facet.date.start";
	private static final String FACET_DATE_END = "facet.date.end";
	private static final String FACET_DATE_GAP = "facet.date.gap";

	private static final String CHARSET = "utf-8";

	public static String getURL(FacetDateParams fdp) {
		String url = BASE_URL + "&" + Q + "=" + fdp.getQ() + "&" + FACET_DATE + "=" + fdp.getFacetDate() + "&"
				+ FACET_DATE_START + "=" + fdp.getFacetDateStart() + "&" + FACET_DATE_END + "=" + fdp.getFacetDateEnd()
				+ "&" + FACET_DATE_GAP + "=" + fdp.getFacetDateGap();
		logger.info("Request's URL=" + url);
		return url;
	}

	public static String getFacetDateResult(FacetDateParams fdp) {
		String result = HttpUtils.doGet(getURL(fdp), CHARSET);
		return result;
	}

	public static void main(String[] args) {
		FacetDateParams fdp = new FacetDateParams();
		fdp.setQ("*:*");
		fdp.setFacetDate("timestamp");
		fdp.setFacetDateStart("NOW-7DAYS");
		fdp.setFacetDateEnd("NOW");
		fdp.setFacetDateGap("%2B1DAY");
		String result = FacetSearch.getFacetDateResult(fdp);
		System.out.println(result);
	}

}
