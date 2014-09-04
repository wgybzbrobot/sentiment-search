package zx.soft.sent.solr.firstpage;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.domain.SimpleFacetInfo;
import zx.soft.sent.solr.search.SearchingData;
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
		//		HashMap<String, Long> todayWeibos = firstPage.getTodayWeibosSum(147, 9);
		//		System.out.println(todayWeibos);
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
		// lasttime代表入solr时间，update_time更新时间
		queryParams.setFq("lasttime:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
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
	 * 测试:N=20,username:452962
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
	 * 根据当天的微博数据，分别统计0、3、6、9、12、15、18、21时刻的四大微博数据进入总量；
	 * 即从0点开始，每隔3个小时统计以下。
	 */
	public HashMap<String, Long> getTodayWeibosSum(int day, int hour) {
		logger.info("Getting today weibos' sum...");
		HashMap<String, Long> result = initWeibosResult();
		long currentTime = System.currentTimeMillis() - day * 86400_000L;
		long startTime = currentTime - currentTime % 86400_000L - 8 * 3600_000L + hour * 3600_000;//该天的第hour时刻
		long endTime = startTime + 3 * 3600_000; // 该天的第hour+3时刻，时间间隔为三小时
		QueryParams queryParams = new QueryParams();
		queryParams.setRows(0);
		queryParams.setFacetField("source_id");
		//		System.out.println("update_time:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
		//				+ TimeUtils.transToSolrDateStr(endTime) + "];platform:3");
		queryParams.setFq("lasttime:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(endTime) + "];platform:3");
		QueryResult queryResult = search.queryData(queryParams, false);
		HashMap<String, Long> ff = null;
		for (SimpleFacetInfo facetField : queryResult.getFacetFields()) {
			if ("source_id".equalsIgnoreCase(facetField.getName())) {
				ff = facetField.getValues();
			}
		}
		if (ff != null) {
			for (Entry<String, Long> temp : ff.entrySet()) {
				if (temp.getValue() > 0) {
					if (result.get(temp.getKey().split(",")[1]) != null) {
						result.put(temp.getKey().split(",")[1],
								temp.getValue() + result.get(temp.getKey().split(",")[1]));
					}
				}
			}
		}

		return result;
	}

	/**
	 * 对当天的论坛和微博进入数据进行负面评分，并按照分值推送最大的签20条内容，每小时推送一次。
	 */

	/**
	 * 初始化四大微博统计结果
	 */
	private HashMap<String, Long> initWeibosResult() {
		HashMap<String, Long> result = new HashMap<>();
		result.put("新浪微博", 0L);
		result.put("腾讯微博", 0L);
		result.put("搜狐微博", 0L);
		result.put("网易微博", 0L);
		return result;
	}

	public void close() {
		search.close();
	}

}
