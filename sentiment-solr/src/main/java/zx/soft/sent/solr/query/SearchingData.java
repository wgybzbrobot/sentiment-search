package zx.soft.sent.solr.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.RedisCache;
import zx.soft.redis.client.common.Config;
import zx.soft.sent.dao.common.SentimentConstant;
import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.domain.SimpleFacetInfo;
import zx.soft.sent.solr.ecxception.SpiderSearchException;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.utils.time.TimeUtils;

/**
 * 搜索舆情数据
 *
 * @author wanggang
 *
 */
@Deprecated
public class SearchingData {

	private static Logger logger = LoggerFactory.getLogger(SearchingData.class);

	private static final String[] PLATFORMS = { "其他类", "资讯类", "论坛类", "微博类", "博客类", "QQ类", "搜索类", "回复类", "邮件类", "图片类",
			"微信类" };

	private enum Shards {
		shard1, shard2, shard3, shard4, shard5, shard6
	};

	final CloudSolrServer cloudServer;
	final Cache cache;

	public SearchingData() {
		cache = new RedisCache(Config.get("redis.rp.slave"), Integer.parseInt(Config.get("redis.rp.port")),
				Config.get("redis.password"));
		Properties props = ConfigUtil.getProps("solr_params.properties");
		cloudServer = new CloudSolrServer(props.getProperty("zookeeper_cloud"));
		cloudServer.setDefaultCollection(props.getProperty("collection"));
		cloudServer.setZkConnectTimeout(Integer.parseInt(props.getProperty("zookeeper_connect_timeout")));
		cloudServer.setZkClientTimeout(Integer.parseInt(props.getProperty("zookeeper_client_timeout")));
		cloudServer.connect();
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {
		SearchingData search = new SearchingData();
		//		QueryParams queryParams = new QueryParams();
		// q:关键词
		//		queryParams.setQ("香港占中");
		//		queryParams.setFq("timestamp:[2014-11-27T00:00:00Z TO 2014-11-27T23:59:59Z]"); //timestamp:[2014-04-22T00:00:00Z TO 2014-04-23T00:00:00Z]
		//		queryParams.setSort("timestamp:desc"); // lasttime:desc
		//		queryParams.setStart(0);
		//		queryParams.setRows(10);
		//		queryParams.setWt("json");
		//		queryParams.setFl(""); // nickname,content
		//		queryParams.setHlfl("title,content");
		//		queryParams.setHlsimple("red");
		//		queryParams.setFacetQuery("");
		//		queryParams.setFacetField("nickname");
		//		QueryResult result = search.queryData(queryParams, true);
		//		System.out.println(JsonUtils.toJson(result));
		search.deleteQuery("timestamp:[2000-11-27T00:00:00Z TO 2014-09-30T23:59:59Z]");
		search.close();
	}

	public void deleteQuery(String q) {
		try {
			cloudServer.deleteByQuery(q);
			cloudServer.commit();
		} catch (SolrServerException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			e.printStackTrace();
		}
	}

	/**
	 * added by donglei
	 * 仅对用于大量数据的facet应用
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * QTime: 7353
	 * QTime: 7376
	 * QTime: 7383
	 * QTime: 7610
	 * QTime: 7637
	 * QTime: 7661
	 * 多线程请求耗时：7883
	 */
	public List<QueryResult> facetResult(final QueryParams queryParams, final boolean isPlatformTrans) {
		List<QueryResult> queryResults = new ArrayList<QueryResult>();
		final SolrQuery query = getSolrQuery(queryParams);
		long startTime = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(6);
		List<Future<QueryResult>> results = new ArrayList<Future<QueryResult>>();
		for (final Shards shard : Shards.values()) {
			Future<QueryResult> result = executor.submit(new Callable<QueryResult>() {
				@Override
				public QueryResult call() throws Exception {
					query.set("shards", shard.name());
					QueryResponse queryResponse = null;
					try {
						queryResponse = cloudServer.query(query, METHOD.POST);
					} catch (SolrServerException e) {
						logger.error("Exception:{}", LogbackUtil.expection2Str(e));
						throw new RuntimeException(e);
					}
					if (queryResponse == null) {
						logger.error("no response!");
						throw new SpiderSearchException("no response!");
					}
					QueryResult result = new QueryResult();
					logger.info("QTime: " + queryResponse.getQTime());
					result.setFacetFields(transFacetField(queryResponse.getFacetFields(), queryParams, isPlatformTrans));
					result.setFacetDates(transFacetField(queryResponse.getFacetDates(), queryParams, isPlatformTrans));
					return result;
				}
			});

			results.add(result);
		}
		for (Future<QueryResult> result : results) {
			try {
				queryResults.add(result.get(20, TimeUnit.SECONDS));
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				throw new RuntimeException(e);
			}
		}
		logger.info("多线程请求耗时：" + (System.currentTimeMillis() - startTime));
		executor.shutdown();
		return queryResults;
	}

	/**
	 * 根据多条件查询结果数据
	 */
	public QueryResult queryData(QueryParams queryParams, boolean isPlatformTrans) {
		SolrQuery query = getSolrQuery(queryParams);
		QueryResponse queryResponse = null;
		try {
			queryResponse = cloudServer.query(query, METHOD.POST);
			// GET方式的时候所有查询条件都是拼装到url上边的，url过长当然没有响应，必然中断talking了
			//			queryResponse = server.query(query, METHOD.GET);
		} catch (SolrServerException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
		if (queryResponse == null) {
			logger.error("no response!");
			throw new SpiderSearchException("no response!");
		}

		//		System.out.println(queryResponse.getFacetFields());
		//		System.out.println(JsonUtils.toJson(queryResponse.getResults()));

		QueryResult result = new QueryResult();
		result.setQTime(queryResponse.getQTime());
		// header数据太长，暂时不需要
		//				result.setHeader(queryResponse.getHeader());
		result.setSort(queryResponse.getSortValues());
		//		result.setHighlighting(queryResponse.getHighlighting());
		result.setGroup(queryResponse.getGroupResponse());
		result.setFacetQuery(queryResponse.getFacetQuery());
		//		System.out.println(queryResponse.getFacetFields());
		result.setFacetFields(transFacetField(queryResponse.getFacetFields(), queryParams, isPlatformTrans));
		result.setFacetDates(transFacetField(queryResponse.getFacetDates(), queryParams, isPlatformTrans));
		result.setFacetRanges(queryResponse.getFacetRanges());
		result.setFacetPivot(queryResponse.getFacetPivot());
		result.setNumFound(queryResponse.getResults().getNumFound());
		result.setResults(queryResponse.getResults());
		// 处理时间timestamp、lasttime、first_time、update_time
		// 同时，查询出来的结果比原先的晚8小时，所以需要将得到的时间减少8小时
		tackleTime(result);
		// 将highlight移到result中，减少数据量，同时方便调用
		for (int i = 0; i < result.getResults().size(); i++) {
			if (queryResponse.getHighlighting() != null) {
				for (String hl : queryParams.getHlfl().split(",")) {
					if (queryResponse.getHighlighting().get(result.getResults().get(i).getFieldValue("id")).get(hl) != null) {
						result.getResults()
								.get(i)
								.setField(
										hl,
										queryResponse.getHighlighting()
												.get(result.getResults().get(i).getFieldValue("id")).get(hl).get(0));
					}
				}
			}
			if (result.getResults().get(i).getFieldValue("source_id") != null) {
				result.getResults()
						.get(i)
						.setField(
								"source_name",
								cache.hget(SentimentConstant.SITE_MAP,
										result.getResults().get(i).getFieldValue("source_id").toString()));
			}
		}

		logger.info("numFound=" + queryResponse.getResults().getNumFound());
		logger.info("QTime=" + queryResponse.getQTime());

		return result;
	}

	private void tackleTime(QueryResult result) {
		SolrDocument str = null;
		for (int i = 0; i < result.getResults().size(); i++) {
			str = result.getResults().get(i);
			if (str.getFieldValueMap().get("timestamp") != null) {
				result.getResults()
						.get(i)
						.setField(
								"timestamp",
								TimeUtils
										.transStrToCommonDateStr(str.getFieldValueMap().get("timestamp").toString(), 8));
			}
			if (str.getFieldValueMap().get("lasttime") != null) {
				result.getResults()
						.get(i)
						.setField("lasttime",
								TimeUtils.transStrToCommonDateStr(str.getFieldValueMap().get("lasttime").toString(), 8));
			}
			if (str.getFieldValueMap().get("first_time") != null) {
				result.getResults()
						.get(i)
						.setField(
								"first_time",
								TimeUtils.transStrToCommonDateStr(str.getFieldValueMap().get("first_time").toString(),
										8));
			}
			if (str.getFieldValueMap().get("update_time") != null) {
				result.getResults()
						.get(i)
						.setField(
								"update_time",
								TimeUtils.transStrToCommonDateStr(str.getFieldValueMap().get("update_time").toString(),
										8));
			}
		}
	}

	private List<SimpleFacetInfo> transFacetField(List<FacetField> facets, QueryParams queryParams,
			boolean isPlatformTrans) {
		List<SimpleFacetInfo> result = new ArrayList<>();
		if (facets == null) {
			return null;
		}
		String fqPlatform = "";
		for (String str : queryParams.getFq().split(";")) {
			if (str.contains("platform")) {
				fqPlatform = str;
			}
		}
		for (FacetField facet : facets) {
			SimpleFacetInfo sfi = new SimpleFacetInfo();
			sfi.setName(facet.getName());
			HashMap<String, Long> t = new LinkedHashMap<>();
			for (Count temp : facet.getValues()) {
				if ("platform".equalsIgnoreCase(facet.getName())) {
					if (fqPlatform.contains("platform")) {
						if (fqPlatform.contains(temp.getName())) {
							if (isPlatformTrans) {
								t.put(PLATFORMS[Integer.parseInt(temp.getName())], temp.getCount());
							} else {
								t.put(temp.getName(), temp.getCount());
							}
						}
					} else {
						if (isPlatformTrans) {
							// 目前我们的平台类型共有11个，如果超过11则不处理
							if (Integer.parseInt(temp.getName()) < 11) {
								t.put(PLATFORMS[Integer.parseInt(temp.getName())], temp.getCount());
							}
						} else {
							t.put(temp.getName(), temp.getCount());
						}
					}
				} else if ("source_id".equalsIgnoreCase(facet.getName())) {
					if ((t.size() < 11) && (temp.getCount() > 0)) {
						t.put(temp.getName() + "," + cache.hget(SentimentConstant.SITE_MAP, temp.getName()),
								temp.getCount());
					} else {
						break;
					}
				} else {
					if ((t.size() < 11) && (temp.getCount() > 0)) {
						t.put(temp.getName(), temp.getCount());
					} else {
						break;
					}
				}
			}
			sfi.setValues(t);
			result.add(sfi);
		}
		return result;
	}

	/**
	 * 组合查询类
	 * @param q=abc或者[1 TO 100]
	 * @param fq=+f1:abc,dec;-f2:cde,fff;...
	 * @param fl=username,nickname,content,...
	 * @param hlfl=title,content,...
	 * @param sort=platform:desc,source_id:asc,...
	 * @param facetQuery={!key="day1"}timestamp:[NOW/MONTH-12MONTH TO NOW/MONTH-6MONTH],{!key="day2"}timestamp:[NOW/MONTH-18MONTH TO NOW/MONTH-12MONTH],...
	 * @param facetField=nickname,platform,source_id,... 默认platform全返回，其他域只返回前10
	 * @return
	 */
	private SolrQuery getSolrQuery(QueryParams queryParams) {

		SolrQuery query = new SolrQuery();
		if (queryParams.getQ() != "") {
			query.setQuery(queryParams.getQ());
		}
		// 忽略版本信息，否则会对分类统计产生影响
		String[] vinfo = null;
		query.add("version", vinfo);
		// 分片失效忽略
		query.set("shards.tolerant", true);
		// 设置关键词连接逻辑，默认是AND
		query.set("q.op", queryParams.getQop());
		if (queryParams.getFq() != "") {
			for (String fq : queryParams.getFq().split(";")) {
				if (fq.contains("source_id")) {
					if (transCacheFq(fq) != "") {
						query.addFilterQuery(transCacheFq(fq));
					} else {
						logger.error("fq=" + fq + " is null.");
					}
				} else {
					query.addFilterQuery(transFq(fq));
				}
			}
		}
		if (queryParams.getSort() != "") {
			for (String sort : queryParams.getSort().split(",")) {
				query.addSort(sort.split(":")[0], "desc".equalsIgnoreCase(sort.split(":")[1]) ? ORDER.desc : ORDER.asc);
			}
		}
		if (queryParams.getStart() != 0) {
			query.setStart(queryParams.getStart());
		}
		if (queryParams.getRows() != 10) {
			query.setRows(queryParams.getRows());
		}
		if (queryParams.getFl() != "") {
			query.setFields(queryParams.getFl());
		}
		if (queryParams.getWt() != "") {
			query.set("wt", queryParams.getWt());
		}
		if (queryParams.getHlfl() != "") {
			query.setHighlight(true).setHighlightSnippets(1);
			query.addHighlightField(queryParams.getHlfl());
		}
		if (queryParams.getHlsimple() != "") {
			query.setHighlightSimplePre("<" + queryParams.getHlsimple() + ">");
			query.setHighlightSimplePost("</" + queryParams.getHlsimple() + ">");
		}
		if (queryParams.getFacetQuery() != "") {
			for (String fq : queryParams.getFacetQuery().split(",")) {
				query.addFacetQuery(fq);
			}
		}
		if (queryParams.getFacetField() != "") {
			//			query.setFacet(true);
			//			query.addFacetField(queryParams.getFacetField().split(","));
			for (String field : queryParams.getFacetField().split(",")) {
				query.addFacetField(field);
				query.set("f." + field + ".facet.method", "fcs");
				query.set("f." + field + ".facet.limit", 15);
			}
		}

		// 按日期分类查询
		if (queryParams.getFacetDate().size() == 4) {
			for (Entry<String, String> facetDate : queryParams.getFacetDate().entrySet()) {
				query.set(facetDate.getKey(), facetDate.getValue());
			}
		}

		return query;
	}

	private String transCacheFq(String fqs) {
		String result = "";
		String sites = fqs.split(":")[1];
		if ((sites.indexOf(",") < 0) && (sites.length() == 32)) {
			sites = cache.hget(SentimentConstant.SITE_GROUPS, sites);
			if (sites == null) {
				return "";
			}
		}
		int count = 0;
		for (String site : sites.split(",")) {
			if (count++ > 250) {
				break;
			}
			result = result + fqs.split(":")[0] + ":" + site + " OR ";
		}
		result = result.substring(0, result.length() - 4);
		if (fqs.contains("-")) {
			result = result.replace("OR", "AND");
		}
		return result;
	}

	public static String transFq(String fqs) {
		int index = fqs.indexOf(":");
		String result = "";
		for (String str : fqs.substring(index + 1).split(",")) {
			result = result + fqs.substring(0, index) + ":" + str + " OR ";
		}
		result = result.substring(0, result.length() - 4);
		if (fqs.charAt(0) == '-' || fqs.contains(";-")) {
			result = result.replace("OR", "AND");
		}
		return result;
	}

	/**
	 * 获取资源站点和名称列表
	 */
	public Map<String, String> getSourceIdAndNames() {
		return cache.hgetAll(SentimentConstant.SITE_MAP);
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		cloudServer.shutdown();
		cache.close();
	}

}
