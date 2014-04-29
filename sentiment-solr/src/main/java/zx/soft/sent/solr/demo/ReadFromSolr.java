package zx.soft.sent.solr.demo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class ReadFromSolr {

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws SolrServerException, IOException {

		CloudSolrServer server = new CloudSolrServer("ppcc2:2181,ppcc3:2181");
		SolrQuery query = new SolrQuery();
		query.setQuery("name:ipod");
		query.setHighlight(true).setHighlightSnippets(1); //set other params as needed
		query.setParam("hl.fl", "name,text");

		QueryResponse rsp = server.query(query);
		Iterator<SolrDocument> iter = rsp.getResults().iterator();

		while (iter.hasNext()) {
			SolrDocument resultDoc = iter.next();
			Object title = resultDoc.getFieldValue("name");
			Object id = resultDoc.getFieldValue("id");
			System.out.println("id=" + id + ",title=" + title);
			if (rsp.getHighlighting().get(id) != null) {
				List<String> highlightSnippets = rsp.getHighlighting().get(id).get("name");
				System.out.println(highlightSnippets.toString());

			}
		}

		server.shutdown();
	}

	/**
	 * 高亮查询
	 */
	public void highlightQuery() throws SolrServerException {

		SolrServer server = new HttpSolrServer("http://ppcc1:8983/solr/sina_weibos");

		SolrQuery query = new SolrQuery();
		query.setQuery("name:ipod");
		query.setHighlight(true).setHighlightSnippets(1); //set other params as needed
		query.setParam("hl.fl", "name,text");

		QueryResponse rsp = server.query(query);
		Iterator<SolrDocument> iter = rsp.getResults().iterator();

		while (iter.hasNext()) {
			SolrDocument resultDoc = iter.next();
			Object title = resultDoc.getFieldValue("name");
			Object id = resultDoc.getFieldValue("id");
			System.out.println("id=" + id + ",title=" + title);
			if (rsp.getHighlighting().get(id) != null) {
				List<String> highlightSnippets = rsp.getHighlighting().get(id).get("name");
				System.out.println(highlightSnippets.toString());

			}
		}

		server.shutdown();
	}

	/**
	 * 高级查询
	 */
	public void advancedQuery() throws SolrServerException {

		SolrServer server = new HttpSolrServer("http://ppcc1:8983/solr/sina_weibos");

		SolrQuery query = new SolrQuery().setQuery("ipod").setFacet(true).setFacetMinCount(1).setFacetLimit(8)
				.addFacetField("category").addFacetField("inStock");

		QueryResponse rsp = server.query(query);
		SolrDocumentList docs = rsp.getResults();

		System.out.println(docs.size());

		server.shutdown();
	}

	/**
	 * 基本查询
	 */
	public void baseQuery() throws SolrServerException {

		SolrServer server = new HttpSolrServer("http://ppcc1:8983/solr/sina_weibos");

		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.addSort(new SortClause("price", SolrQuery.ORDER.asc));
		query.setRows(50);

		QueryResponse rsp = server.query(query);
		SolrDocumentList docs = rsp.getResults();
		// 将结果放入Beans中
		//		List<Item> beans = rsp.getBeans(Item.class);
		System.out.println(docs.size());

		server.shutdown();
	}

}
