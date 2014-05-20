package zx.soft.sent.web.sentiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

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

import zx.soft.sent.cache.dao.Cache;
import zx.soft.sent.cache.factory.CacheFactory;
import zx.soft.sent.core.redis.OracleToRedis;
import zx.soft.sent.solr.err.SpiderSearchException;
import zx.soft.sent.utils.config.ConfigUtil;
import zx.soft.sent.utils.json.JsonUtils;
import zx.soft.sent.utils.time.TimeUtils;
import zx.soft.sent.web.application.SiteApplication;
import zx.soft.sent.web.domain.QueryResult;
import zx.soft.sent.web.domain.SimpleFacetInfo;

/**
 * 搜索舆情数据
 * @author wanggang
 *
 */
public class SearchingData {

	private static Logger logger = LoggerFactory.getLogger(SearchingData.class);

	private static final String[] PLATFORMS = { "其他类", "资讯类", "论坛类", "微博类", "博客类", "QQ类", "搜索类", "回复类", "邮件类" };

	final CloudSolrServer server;
	final Cache cache;

	public SearchingData() {
		cache = CacheFactory.getInstance();
		Properties props = ConfigUtil.getProps("solr_params.properties");
		server = new CloudSolrServer(props.getProperty("zookeeper_cloud"));
		server.setDefaultCollection(props.getProperty("collection"));
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {
		SearchingData search = new SearchingData();
		QueryParams queryParams = new QueryParams();
		// q:关键词
		queryParams.setQ("*:*");
		queryParams.setFq("platform:2"); //timestamp:[2014-04-22T00:00:00Z TO 2014-04-23T00:00:00Z]
		queryParams.setSort("timestamp:desc"); // lasttime:desc
		//		queryParams.setStart(0);
		//		queryParams.setRows(10);
		//		queryParams.setWt("json");
		//		queryParams.setFl(""); // nickname,content
		queryParams.setHlfl("title,content");
		queryParams.setHlsimple("red");
		queryParams.setFacetQuery("");
		queryParams.setFacetField("platform");
		QueryResult result = search.queryData(queryParams);
		System.out.println(JsonUtils.toJson(result));
		//		search.deleteQuery();
		search.close();
	}

	public void deleteQuery() {
		try {
			server.deleteByQuery("platform:8");
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询结果数据
	 */
	public QueryResult queryData(QueryParams queryParams) {
		SolrQuery query = getSolrQuery(queryParams);
		QueryResponse queryResponse = null;
		try {
			queryResponse = server.query(query, METHOD.GET);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
		if (queryResponse == null) {
			throw new SpiderSearchException("no response!");
		}

		//		System.out.println(JsonUtils.toJson(queryResponse.getResponse()));

		QueryResult result = new QueryResult();
		result.setHeader(queryResponse.getHeader());
		result.setSort(queryResponse.getSortValues());
		//		result.setHighlighting(queryResponse.getHighlighting());
		result.setGroup(queryResponse.getGroupResponse());
		result.setFacetQuery(queryResponse.getFacetQuery());
		result.setFacetFields(transFacetField(queryResponse.getFacetFields()));
		result.setFacetDates(transFacetField(queryResponse.getFacetDates()));
		result.setFacetRanges(queryResponse.getFacetRanges());
		result.setFacetPivot(queryResponse.getFacetPivot());
		result.setNumFound(queryResponse.getResults().getNumFound());
		result.setResults(queryResponse.getResults());
		// 处理时间timestamp、lasttime、first_time、update_time
		tackleTime(result);
		// 将highlight移到result中，减少数据量，同时方便调用
		if (queryResponse.getHighlighting() != null) {
			for (int i = 0; i < result.getResults().size(); i++) {
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
				if (result.getResults().get(i).getFieldValue("source_id") != null) {
					result.getResults()
							.get(i)
							.setField(
									"source_name",
									cache.hget(OracleToRedis.SITE_MAP,
											result.getResults().get(i).getFieldValue("source_id").toString()));
				}
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
						.setField("timestamp",
								TimeUtils.transStrToCommonDateStr(str.getFieldValueMap().get("timestamp").toString()));
			}
			if (str.getFieldValueMap().get("lasttime") != null) {
				result.getResults()
						.get(i)
						.setField("lasttime",
								TimeUtils.transStrToCommonDateStr(str.getFieldValueMap().get("lasttime").toString()));
			}
			if (str.getFieldValueMap().get("first_time") != null) {
				result.getResults()
						.get(i)
						.setField("first_time",
								TimeUtils.transStrToCommonDateStr(str.getFieldValueMap().get("first_time").toString()));
			}
			if (str.getFieldValueMap().get("update_time") != null) {
				result.getResults()
						.get(i)
						.setField("update_time",
								TimeUtils.transStrToCommonDateStr(str.getFieldValueMap().get("update_time").toString()));
			}
		}
	}

	private List<SimpleFacetInfo> transFacetField(List<FacetField> facets) {
		List<SimpleFacetInfo> result = new ArrayList<>();
		if (facets == null) {
			return null;
		}
		for (FacetField facet : facets) {
			SimpleFacetInfo sfi = new SimpleFacetInfo();
			sfi.setName(facet.getName());
			HashMap<String, Long> t = new LinkedHashMap<>();
			for (Count temp : facet.getValues()) {
				if ("platform".equalsIgnoreCase(facet.getName())) {
					t.put(PLATFORMS[Integer.parseInt(temp.getName())], temp.getCount());
				} else if ("source_id".equalsIgnoreCase(facet.getName())) {
					if ((t.size() < 10) && (temp.getCount() > 0)) {
						t.put(temp.getName() + "," + cache.hget(OracleToRedis.SITE_MAP, temp.getName()),
								temp.getCount());
					} else {
						break;
					}
				} else {
					if ((t.size() < 10) && (temp.getCount() > 0)) {
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
	 * @param fl=username,nickname.content,...
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
		// 设置关键词连接逻辑是AND
		query.set("q.op", "AND");
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
			query.addFacetField(queryParams.getFacetField().split(","));
		}

		return query;
	}

	public String transCacheFq(String fqs) {
		String result = "";
		String sites = fqs.split(":")[1];
		if ((sites.indexOf(",") < 0) && (sites.length() == 32)) {
			sites = cache.hget(SiteApplication.SITE_GROUPS, sites);
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
		if (fqs.contains("-")) {
			result = result.replace("OR", "AND");
		}
		return result;
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		server.shutdown();
		cache.close();
	}

}
