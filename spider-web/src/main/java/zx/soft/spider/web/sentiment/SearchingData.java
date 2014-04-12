package zx.soft.spider.web.sentiment;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.spider.solr.err.SpiderSearchException;
import zx.soft.spider.solr.search.QueryResult;
import zx.soft.spider.solr.utils.Config;

/**
 * 搜索舆情数据
 * @author wanggang
 *
 */
public class SearchingData {

	private static Logger logger = LoggerFactory.getLogger(SearchingData.class);

	final CloudSolrServer server;

	public SearchingData() {
		Properties props = Config.getProps("solr_params.properties");
		server = new CloudSolrServer(props.getProperty("zookeeper_cloud"));
		server.setDefaultCollection(props.getProperty("collection"));
	}

	/**
	 * 查询结果数据
	 */
	public QueryResult queryData(QueryParams queryParams) {

		SolrQuery query = getSolrQuery(queryParams);
		QueryResponse response = null;
		try {
			response = server.query(query, METHOD.GET);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
		if (response == null) {
			throw new SpiderSearchException("no response!");
		}
		// 数据结果处理
		QueryResult QueryResult = new QueryResult();
		/*
		 * .........................
		 */
		Iterator<SolrDocument> iterator = response.getResults().iterator();
		logger.info("numFound=" + response.getResults().getNumFound());
		logger.info("QTime=" + response.getQTime());
		while (iterator.hasNext()) {
			SolrDocument doc = iterator.next();
			System.out.println(doc.getFieldValue("nickname"));
			Object id = doc.getFieldValue("id");
			if (response.getHighlighting().get(id) != null) {
				List<String> snippets = response.getHighlighting().get(id).get("content");
				if (snippets != null) {
					System.out.println(snippets.toString());
				}
			}
		}

		return QueryResult;
	}

	/**
	 * 组合查询类
	 * @param q:abc或者[1 TO 100]
	 * @param fq:f1:abc,f2:cde,...
	 * @param fl:username,nickname.content,...
	 * @param hlfl:title,content,...
	 * @param sort:platform:desc,source_id:asc,...
	 * @return
	 */
	private SolrQuery getSolrQuery(QueryParams queryParams) {

		SolrQuery query = new SolrQuery();
		if (queryParams.getQ() != "") {
			query.setQuery(queryParams.getQ());
		}
		if (queryParams.getFq() != null) {
			for (String fq : queryParams.getFq().split(",")) {
				query.addFilterQuery(fq);
			}
		}
		if (queryParams.getSort() != "") {
			for (String sort : queryParams.getSort().split(",")) {
				query.addSort(sort.split(":")[0], "desc".equalsIgnoreCase(sort.split(":")[1]) ? ORDER.desc : ORDER.asc);
			}
		}
		if (queryParams.getStart() != -1) {
			query.setStart(queryParams.getStart());
		}
		if (queryParams.getRows() != -1) {
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
		if (queryParams.getFacetQuery() != "") {
			query.addFacetQuery(queryParams.getFacetQuery());
		}
		if (queryParams.getFacetField() != "") {
			query.addFacetField(queryParams.getFacetField());
		}

		return query;
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		server.shutdown();
	}

}
