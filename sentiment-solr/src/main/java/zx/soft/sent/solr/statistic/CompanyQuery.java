package zx.soft.sent.solr.statistic;

import java.util.Properties;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.err.SpiderSearchException;
import zx.soft.sent.solr.utils.Config;

/**
 * 搜索舆情数据
 * @author wanggang
 *
 */
public class CompanyQuery {

	private static Logger logger = LoggerFactory.getLogger(CompanyQuery.class);

	private static final String GOOD_NEWS = "盈利、景气、受益、回暖、增长、突破、优化、转型、扩张、利好、优势、核心技术、政策倾斜、需求旺盛、前景广阔";

	private static final String BAD_NEWS = "亏损、淡季、低迷、风险、损失、差距、下跌、低谷、过剩、回落、萧条、疲软、复苏乏力、增速放缓、业绩下滑";

	final CloudSolrServer server;

	public CompanyQuery() {
		Properties props = Config.getProps("solr_params.properties");
		server = new CloudSolrServer(props.getProperty("zookeeper_cloud"));
		server.setDefaultCollection(props.getProperty("collection"));
	}

	public long queryData(String keywords, int type) {
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		if (type == 1) { // 好消息
			query.addFilterQuery("content:" + keywords + " AND content:" + GOOD_NEWS);
		} else if (type == 2) { // 坏消息
			query.addFilterQuery("content:" + keywords + " AND content:" + BAD_NEWS);
		} else { // 总数
			query.addFilterQuery("content:" + keywords);
		}
		query.setRows(0);
		//		query.set("q.op", "AND");

		// 按照年份统计
		//		query.setFacet(true);
		//		String[] fq = { "{!key=\"2013\"}timestamp:[NOW/YEAR-1YEAR TO NOW/YEAR]",
		//				"{!key=\"2012\"}timestamp:[NOW/YEAR-2YEAR TO NOW/YEAR-1YEAR]",
		//				"{!key=\"2011\"}timestamp:[NOW/YEAR-3YEAR TO NOW/YEAR-2YEAR]",
		//				"{!key=\"2010\"}timestamp:[NOW/YEAR-4YEAR TO NOW/YEAR-3YEAR]",
		//				"{!key=\"2009\"}timestamp:[NOW/YEAR-5YEAR TO NOW/YEAR-4YEAR]" };
		//		query.addFilterQuery(fq);

		// 按照季度统计
		// facet=true
		// facet.query=timestamp:[NOW-30MONTH%20TO%20NOW-20MONTH]
		// facet.query=timestamp:[NOW-20MONTH%20TO%20NOW]

		QueryResponse response = null;
		try {
			response = server.query(query, METHOD.GET);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
		if (response == null) {
			throw new SpiderSearchException("no response!");
		}
		logger.info("numFound=" + response.getResults().getNumFound());
		//		logger.info("QTime=" + response.getQTime());
		//		logger.info(JsonUtils.toJson(response));

		return response.getResults().getNumFound();
	}

	public void close() {
		server.shutdown();
	}

}
