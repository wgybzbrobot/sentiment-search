package zx.soft.spider.solr.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class BaseSolrDemo {

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws SolrServerException, IOException {

		CloudSolrServer server = new CloudSolrServer("test1:2181,test3:2181");
		server.setDefaultCollection("sentiment");
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "637jf9dde49", 1.0f);
		doc1.addField("nickname", "中新软件公司", 1.0f);
		doc1.addField("comment_count", 10);
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "hjde23339490", 1.0f);
		doc2.addField("nickname", "数据处理", 1.0f);
		doc2.addField("repost_count", 20);
		Collection<SolrInputDocument> docs = new ArrayList<>();
		docs.add(doc1);
		docs.add(doc2);

		server.add(docs);
		//		server.deleteByQuery("*:*");
		server.commit();
		server.shutdown();

	}

	/**
	 * 添加索引到CloudSolr中
	 */
	public static void addDocsToCloudSolr() throws SolrServerException, IOException {

		CloudSolrServer server = new CloudSolrServer("ppcc2:2181,ppcc3:2181");
		server.setDefaultCollection("sina_users");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "12345");
		doc.addField("name", "A lovely summer holiday test");
		doc.addField("description", "test test test");
		doc.addField("friends", "12233,1455366");
		server.add(doc);
		server.commit();

		server.shutdown();
	}

	/**
	 * 添加POJOs到Solr中
	 */
	public void addDocsByJavaBeans() throws IOException, SolrServerException {

		SolrServer server = new HttpSolrServer("http://ppcc1:8983/solr/sentiment");

		Item item1 = new Item();
		item1.id = "one";
		item1.name = "wanggang";
		item1.categories = new String[] { "aaa", "bbb", "ccc" };
		//		server.addBean(item1);
		Item item2 = new Item();
		item2.id = "two";
		item2.name = " two wanggang";
		item2.categories = new String[] { "aa", "bb", "cc" };
		List<Item> beans = new ArrayList<>();
		beans.add(item1);
		beans.add(item2);

		server.addBeans(beans);
		server.commit();

		server.shutdown();
	}

	/**
	 * 通过单个Http请求添加所有文档
	 */
	@SuppressWarnings("unchecked")
	public static void addAllDocsBySingleHttpRequest() throws SolrServerException, IOException {

		SolrServer server = new HttpSolrServer("http://ppcc1:8983/solr/sentiment");

		Iterator<SolrInputDocument> iter = new Iterator<SolrInputDocument>() {

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public SolrInputDocument next() {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", "id100", 1.0f);
				doc.addField("name", "wgybzb robot ", 1.0f);
				doc.addField("price", 10);
				return doc;
			}

			@Override
			public void remove() {
				//
			}
		};
		server.add((Collection<SolrInputDocument>) iter);

		server.commit();

		server.shutdown();
	}

	/**
	 * 添加文档
	 */
	public void addDocs() throws SolrServerException, IOException {

		SolrServer server = new HttpSolrServer("http://ppcc1:8983/solr/sina_weibos");

		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id1", 1.0f);
		doc1.addField("name", "wgybzb robot", 1.0f);
		doc1.addField("price", 10);
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "id2", 1.0f);
		doc2.addField("name", "wgybzb wanggang", 1.0f);
		doc2.addField("price", 20);
		Collection<SolrInputDocument> docs = new ArrayList<>();
		docs.add(doc1);
		docs.add(doc2);

		server.add(docs);
		server.commit();

		server.shutdown();
	}

	/**
	 * 快速添加文档
	 */
	public void addDocsRapid() throws SolrServerException, IOException {

		SolrServer server = new HttpSolrServer("http://ppcc1:8983/solr/sina_weibos");

		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id1", 1.0f);
		doc1.addField("name", "wgybzb robot", 1.0f);
		doc1.addField("price", 10);
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "id2", 1.0f);
		doc2.addField("name", "wgybzb wanggang", 1.0f);
		doc2.addField("price", 20);
		Collection<SolrInputDocument> docs = new ArrayList<>();
		docs.add(doc1);
		docs.add(doc2);

		UpdateRequest req = new UpdateRequest();
		req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
		req.add(docs);
		UpdateResponse rsp = req.process(server);
		System.out.println(rsp.getResponse().toString());

		server.shutdown();
	}

	/**
	 * 查询删除
	 */
	public void deleteByQuery() throws SolrServerException, IOException {
		SolrServer server = new HttpSolrServer("http://ppcc1:8983/solr/sina_weibos");
		server.deleteByQuery("name:wgybzb");
		server.commit();
		server.shutdown();
	}

}
