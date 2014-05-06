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
import zx.soft.sent.utils.json.JsonUtils;

/**
 * 搜索舆情数据
 * @author wanggang
 *
 */
public class CompanyQuery {

	private static Logger logger = LoggerFactory.getLogger(CompanyQuery.class);

	final CloudSolrServer server;

	public CompanyQuery() {
		Properties props = Config.getProps("solr_params.properties");
		server = new CloudSolrServer(props.getProperty("zookeeper_cloud"));
		server.setDefaultCollection(props.getProperty("collection"));
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {

		CompanyQuery searchData = new CompanyQuery();
		String[] keywords = { "微博新浪腾讯", "工作好辛苦", "医疗制度体系", "中国的教育需要改革", "政府制度助理房地产行业" };
		for (String keyword : keywords) {
			searchData.queryData(keyword);
		}
		searchData.close();
	}

	public void queryData(String keywords) {
		// http://hefei10:8983/solr/sentiment/select?q=XXXX&wt=json&indent=true&facet=true&
		// facet.query=timestamp:[NOW-11MONTH/MONTH TO NOW-10MONTH/MONTH]
		SolrQuery query = new SolrQuery();
		query.setQuery(keywords);
		query.setRows(1);

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
		logger.info("QTime=" + response.getQTime());
		logger.info(JsonUtils.toJson(response));

	}

	public void close() {
		server.shutdown();
	}

}
