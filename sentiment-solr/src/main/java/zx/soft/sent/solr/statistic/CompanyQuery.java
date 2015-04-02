package zx.soft.sent.solr.statistic;

import java.util.Properties;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.ecxception.SpiderSearchException;
import zx.soft.utils.config.ConfigUtil;

/**
 * 搜索舆情数据
 * 
 * @author wanggang
 *
 */
public class CompanyQuery {

	private static Logger logger = LoggerFactory.getLogger(CompanyQuery.class);

	final CloudSolrServer server;

	public CompanyQuery() {
		Properties props = ConfigUtil.getProps("solr_params.properties");
		server = new CloudSolrServer(props.getProperty("zookeeper_cloud"));
		server.setDefaultCollection(props.getProperty("collection"));
	}

	public QueryResponse queryData(SolrQuery query) {
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

		return response;
	}

	public void close() {
		server.shutdown();
	}

}
