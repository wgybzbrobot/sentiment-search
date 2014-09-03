package zx.soft.sent.solr.firstpage;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.domain.SimpleFacetInfo;
import zx.soft.sent.solr.search.SearchingData;

/**
 * OA首页信息类
 * 
 * @author wanggang
 *
 */
public class OAFirstPage {

	private static Logger logger = LoggerFactory.getLogger(OAFirstPage.class);

	private final SearchingData search;

	public OAFirstPage() {
		this.search = new SearchingData();
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {

		OAFirstPage firstPage = new OAFirstPage();
		HashMap<String, Long> currentPlatformSum = firstPage.getCurrentPlatformSum();
		System.out.println(currentPlatformSum);
		firstPage.close();

	}

	/**
	 * 统计当前时间各类数据的总量
	 */
	public HashMap<String, Long> getCurrentPlatformSum() {
		HashMap<String, Long> result = null;
		QueryParams queryParams = new QueryParams();
		queryParams.setQ("*:*");
		queryParams.setRows(0);
		queryParams.setFacetField("platform");
		QueryResult queryResult = search.queryData(queryParams, false);
		for (SimpleFacetInfo facetField : queryResult.getFacetFields()) {
			if ("platform".equalsIgnoreCase(facetField.getName())) {
				result = facetField.getValues();
			}
		}
		return result;
	}

	public void close() {
		search.close();
	}

}
