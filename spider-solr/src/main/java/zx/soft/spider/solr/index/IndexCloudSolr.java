package zx.soft.spider.solr.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer.RemoteSolrException;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.spider.solr.domain.Record;

/**
 * 通过HTTP方式索引数据到单台机器上。
 * @author wgybzb
 *
 */
public class IndexCloudSolr {

	private static Logger logger = LoggerFactory.getLogger(IndexCloudSolr.class);

	public static final int FETCH_SIZE = 1_0000;

	// 这里使用集群中的一个结点即可
	//	private static final String ZOOKEEPER_CLOUD = "hdp321:2181,hdp322:2181,hdp323:2181";
	private static final String ZOOKEEPER_CLOUD = "hdp322:2181";
	private static final String COLLECTION_NAME = "sentiment";

	private final CloudSolrServer cloudServer;

	public IndexCloudSolr() {
		cloudServer = new CloudSolrServer(ZOOKEEPER_CLOUD);
		cloudServer.setDefaultCollection(COLLECTION_NAME);
		cloudServer.setIdField("id");
		cloudServer.setParallelUpdates(true);
		cloudServer.setZkConnectTimeout(5_000);
		cloudServer.setZkClientTimeout(5_000);
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {

		IndexCloudSolr indexSingleNode = new IndexCloudSolr();

		List<Record> records = new ArrayList<>();
		records.add(new Record.Builder("1234", 1).setUsername(987654321).setNickname("wgybzb").build());

		indexSingleNode.addDocsToSolr(records);

		indexSingleNode.close();
	}

	/**
	 * 索引数据
	 */
	public void addDocsToSolr(List<Record> records) {
		if (records.size() == 0) {
			return;
		}
		Collection<SolrInputDocument> docs = new ArrayList<>();
		for (Record record : records) {
			docs.add(getDoc(record));
		}
		try {
			cloudServer.add(docs);
			cloudServer.commit();
		} catch (RemoteSolrException | SolrServerException | IOException e) {
			logger.error("SolrServerException: " + e.getMessage());
			//			throw new RuntimeException(e);
		}
	}

	private SolrInputDocument getDoc(Record record) {

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", record.getId());
		doc.addField("platform", record.getPlatform());
		if (record.getMid() != "") {
			doc.addField("mid", record.getMid());
		}
		if (record.getUsername() != 0) {
			doc.addField("username", record.getUsername());
		}
		if (record.getNickname() != "") {
			doc.addField("nickname", record.getNickname());
		}
		if (record.getOriginal_uid() != 0) {
			doc.addField("original_uid", record.getOriginal_uid());
		}
		if (record.getOriginal_name() != "") {
			doc.addField("original_name", record.getOriginal_name());
		}
		if (record.getOriginal_title() != "") {
			doc.addField("original_title", record.getOriginal_title());
		}
		if (record.getOriginal_url() != "") {
			doc.addField("original_url", record.getOriginal_url());
		}
		if (record.getUrl() != "") {
			doc.addField("url", record.getUrl());
		}
		if (record.getHome_url() != "") {
			doc.addField("home_url", record.getHome_url());
		}
		if (record.getTitle() != "") {
			doc.addField("title", record.getTitle());
		}
		if (record.getType() != "") {
			doc.addField("type", record.getType());
		}
		if (record.getContent() != "") {
			doc.addField("content", record.getContent());
		}
		if (record.getComment_count() != 0) {
			doc.addField("comment_count", record.getComment_count());
		}
		if (record.getRead_count() != 0) {
			doc.addField("read_count", record.getRead_count());
		}
		if (record.getFavorite_count() != 0) {
			doc.addField("favorite_count", record.getFavorite_count());
		}
		if (record.getAttitude_count() != 0) {
			doc.addField("attitude_count", record.getAttitude_count());
		}
		if (record.getRepost_count() != 0) {
			doc.addField("repost_count", record.getRepost_count());
		}
		if (record.getVideo_url() != "") {
			doc.addField("video_url", record.getVideo_url());
		}
		if (record.getPic_url() != "") {
			doc.addField("pic_url", record.getPic_url());
		}
		if (record.getVoice_url() != "") {
			doc.addField("voice_url", record.getVoice_url());
		}
		if (record.getTimestamp() != null) {
			doc.addField("timestamp", record.getTimestamp());
		}
		if (record.getSource_id() != 0) {
			doc.addField("source_id", record.getSource_id());
		}
		if (record.getLasttime() != null) {
			doc.addField("lasttime", record.getLasttime());
		}
		if (record.getServer_id() != 0) {
			doc.addField("server_id", record.getServer_id());
		}
		if (record.getIdentify_id() != 0) {
			doc.addField("identify_id", record.getIdentify_id());
		}
		if (record.getIdentify_md5() != "") {
			doc.addField("identify_md5", record.getIdentify_id());
		}
		if (record.getKeyword() != "") {
			doc.addField("keyword", record.getKeyword());
		}
		if (record.getFirst_time() != null) {
			doc.addField("first_time", record.getFirst_time());
		}
		if (record.getUpdate_time() != null) {
			doc.addField("update_time", record.getUpdate_time());
		}
		if (record.getIp() != "") {
			doc.addField("ip", record.getIp());
		}
		if (record.getLocation() != "") {
			doc.addField("location", record.getLocation());
		}
		if (record.getGeo() != "") {
			doc.addField("geo", record.getGeo());
		}
		if (record.getReceive_addr() != "") {
			doc.addField("receive_addr", record.getReceive_addr());
		}
		if (record.getAppend_addr() != "") {
			doc.addField("append_addr", record.getAppend_addr());
		}
		if (record.getSend_addr() != "") {
			doc.addField("send_addr", record.getSend_addr());
		}

		return doc;
	}

	public void close() {
		cloudServer.shutdown();
	}

}
