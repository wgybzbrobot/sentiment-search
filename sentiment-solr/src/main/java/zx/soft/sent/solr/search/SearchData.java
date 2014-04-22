package zx.soft.sent.solr.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.err.SpiderSearchException;
import zx.soft.sent.solr.utils.Config;

/**
 * 搜索舆情数据
 * @author wanggang
 *
 */
public class SearchData {

	private static Logger logger = LoggerFactory.getLogger(SearchData.class);

	final CloudSolrServer server;

	public SearchData() {
		Properties props = Config.getProps("solr_params.properties");
		server = new CloudSolrServer(props.getProperty("zookeeper_cloud"));
		server.setDefaultCollection(props.getProperty("collection"));
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {

		SearchData searchData = new SearchData();
		String[] keywords = { "微博新浪腾讯", "工作好辛苦", "医疗制度体系", "中国的教育需要改革", "政府制度助理房地产行业" };
		for (String keyword : keywords) {
			searchData.queryData(keyword);
		}
		searchData.close();
	}

	public void queryData(String keywords) {
		SolrQuery query = new SolrQuery();
		query.setQuery(keywords);
		query.setFields("nickname,original_url,content");
		query.setHighlight(true).setHighlightSnippets(1);
		//		query.addFilterQuery("platform:7");
		query.setParam("hl.fl", "title,content");

		QueryResponse response = null;
		try {
			response = server.query(query, METHOD.GET);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
		if (response == null) {
			throw new SpiderSearchException("no response!");
		}
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
	}

	public void close() {
		server.shutdown();
	}

}
