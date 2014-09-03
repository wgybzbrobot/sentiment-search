package zx.soft.sent.solr.firstpage;

import java.util.HashMap;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.domain.SimpleFacetInfo;
import zx.soft.sent.solr.search.SearchingData;
import zx.soft.sent.utils.json.JsonUtils;
import zx.soft.sent.utils.time.TimeUtils;

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
		List<SolrDocument> lastInfos = firstPage.getTopNRecordsByUsername(20, "452962");
		System.out.println(JsonUtils.toJson(lastInfos));
		firstPage.close();

	}

	/**
	 * 统计当前时间各类数据的总量
	 */
	public HashMap<String, Long> getCurrentPlatformSum() {
		logger.info("Getting current platform's sum...");
		HashMap<String, Long> result = null;
		QueryParams queryParams = new QueryParams();
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

	/**
	 * 统计当天各类数据的进入量，其中day=0表示当天的数据
	 */
	public HashMap<String, Long> getTodayPlatformInputSum(int day) {
		logger.info("Getting today platform's sum...");
		HashMap<String, Long> result = null;
		// 注意：86400_000L必能换成86400_000，否则会超出int型的范围，从而导致计算错误，应当为long型。
		long currentTime = System.currentTimeMillis() - day * 86400_000L;
		long startTime = currentTime - currentTime % 86400_000L - 8 * 3600_000L;
		QueryParams queryParams = new QueryParams();
		queryParams.setRows(0);
		queryParams.setFacetField("platform");
		queryParams.setFq("update_time:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(currentTime) + "]");
		QueryResult queryResult = search.queryData(queryParams, false);
		for (SimpleFacetInfo facetField : queryResult.getFacetFields()) {
			if ("platform".equalsIgnoreCase(facetField.getName())) {
				result = facetField.getValues();
			}
		}
		return result;
	}

	/**
	 * 根据发布人username获取他最新的N条信息
	 * 测试username:452962
	 */
	public List<SolrDocument> getTopNRecordsByUsername(int N, String username) {
		List<SolrDocument> result = null;
		QueryParams queryParams = new QueryParams();
		queryParams.setRows(N);
		queryParams.setFq("username:" + username);
		queryParams.setSort("timestamp:desc");
		QueryResult queryResult = search.queryData(queryParams, false);
		result = queryResult.getResults();
		return result;
	}

	/**
	 * 
	 */

	public void close() {
		search.close();
	}

}
