package zx.soft.sent.solr.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer.RemoteSolrException;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.domain.Record;
import zx.soft.sent.dao.domain.RecordInfo;
import zx.soft.sent.solr.utils.Config;

/**
 * 通过HTTP方式索引数据到单台机器上。
 * @author wgybzb
 *
 */
public class IndexCloudSolr {

	private static Logger logger = LoggerFactory.getLogger(IndexCloudSolr.class);

	public static int FETCH_SIZE = 1_0000;

	private final CloudSolrServer cloudServer;

	public IndexCloudSolr() {
		Properties props = Config.getProps("solr_params.properties");
		FETCH_SIZE = Integer.parseInt(props.getProperty("fetch_size"));
		cloudServer = new CloudSolrServer(props.getProperty("zookeeper_cloud"));
		cloudServer.setDefaultCollection(props.getProperty("collection"));
		cloudServer.setIdField("id");
		cloudServer.setParallelUpdates(true);
		cloudServer.setZkConnectTimeout(Integer.parseInt(props.getProperty("zookeeper_connect_timeout")));
		cloudServer.setZkClientTimeout(Integer.parseInt(props.getProperty("zookeeper_client_timeout")));
	}

	/**
	 * 索引舆情数据
	 */
	public void addSentimentDocsToSolr(List<Record> records) {
		if (records.size() == 0) {
			return;
		}
		Collection<SolrInputDocument> docs = new ArrayList<>();
		for (Record record : records) {
			docs.add(getSentimentDoc(record));
		}
		try {
			cloudServer.add(docs);
			cloudServer.commit();
		} catch (RemoteSolrException | SolrServerException | IOException e) {
			logger.error("SolrServerException: " + e.getMessage());
			//			throw new RuntimeException(e);
		}
	}

	public void addSentimentDocToSolr(RecordInfo record) {
		try {
			cloudServer.add(getSentimentDoc(record));
		} catch (RemoteSolrException | SolrServerException | IOException e) {
			logger.error("SolrServerException: " + e.getMessage());
			//			throw new RuntimeException(e);
		}
	}

	public void commitToSolr() {
		try {
			cloudServer.commit();
		} catch (RemoteSolrException | SolrServerException | IOException e) {
			logger.error("SolrServerException: " + e.getMessage());
			//			throw new RuntimeException(e);
		}
	}

	private SolrInputDocument getSentimentDoc(RecordInfo record) {

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", record.getId());
		doc.addField("platform", record.getPlatform());
		if (record.getMid() != "") {
			doc.addField("mid", record.getMid().trim());
		}
		if (record.getUsername() != "") {
			doc.addField("username", record.getUsername());
		}
		if (record.getNickname() != "") {
			doc.addField("nickname", record.getNickname().trim());
		}
		if (record.getOriginal_uid() != "") {
			doc.addField("original_uid", record.getOriginal_uid());
		}
		if (record.getOriginal_name() != "") {
			doc.addField("original_name", record.getOriginal_name().trim());
		}
		if (record.getOriginal_title() != "") {
			doc.addField("original_title", record.getOriginal_title().trim());
		}
		if (record.getOriginal_url() != "") {
			doc.addField("original_url", record.getOriginal_url().trim());
		}
		if (record.getUrl() != "") {
			doc.addField("url", record.getUrl().trim());
		}
		if (record.getHome_url() != "") {
			doc.addField("home_url", record.getHome_url().trim());
		}
		if (record.getTitle() != "") {
			doc.addField("title", record.getTitle().trim());
		}
		if (record.getType() != "") {
			doc.addField("type", record.getType().trim());
		}
		if (record.getContent() != "") {
			doc.addField("content", record.getContent().trim());
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
			doc.addField("video_url", record.getVideo_url().trim());
		}
		if (record.getPic_url() != "") {
			doc.addField("pic_url", record.getPic_url().trim());
		}
		if (record.getVoice_url() != "") {
			doc.addField("voice_url", record.getVoice_url().trim());
		}
		if (record.getTimestamp() != 0) {
			doc.addField("timestamp", new Date(record.getTimestamp() * 1000));
		}
		if (record.getSource_id() != 0) {
			doc.addField("source_id", record.getSource_id());
		}
		if (record.getLasttime() != 0) {
			doc.addField("lasttime", new Date(record.getLasttime() * 1000));
		}
		if (record.getServer_id() != 0) {
			doc.addField("server_id", record.getServer_id());
		}
		if (record.getIdentify_id() != 0) {
			doc.addField("identify_id", record.getIdentify_id());
		}
		if (record.getIdentify_md5() != "") {
			doc.addField("identify_md5", record.getIdentify_md5().trim());
		}
		if (record.getKeyword() != "") {
			doc.addField("keyword", record.getKeyword().trim());
		}
		if (record.getFirst_time() != 0) {
			doc.addField("first_time", new Date(record.getFirst_time() * 1000));
		}
		if (record.getUpdate_time() != 0) {
			doc.addField("update_time", new Date(record.getUpdate_time() * 1000));
		}
		if (record.getIp() != "") {
			doc.addField("ip", record.getIp().trim());
		}
		if (record.getLocation() != "") {
			doc.addField("location", record.getLocation().trim());
		}
		if (record.getGeo() != "") {
			doc.addField("geo", record.getGeo().trim());
		}
		if (record.getReceive_addr() != "") {
			doc.addField("receive_addr", record.getReceive_addr().trim());
		}
		if (record.getAppend_addr() != "") {
			doc.addField("append_addr", record.getAppend_addr().trim());
		}
		if (record.getSend_addr() != "") {
			doc.addField("send_addr", record.getSend_addr().trim());
		}

		return doc;
	}

	private SolrInputDocument getSentimentDoc(Record record) {

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", record.getId());
		doc.addField("platform", record.getPlatform());
		if (record.getMid() != "") {
			doc.addField("mid", record.getMid().trim());
		}
		if (record.getUsername() != "") {
			doc.addField("username", record.getUsername());
		}
		if (record.getNickname() != "") {
			doc.addField("nickname", record.getNickname().trim());
		}
		if (record.getOriginal_uid() != "") {
			doc.addField("original_uid", record.getOriginal_uid());
		}
		if (record.getOriginal_name() != "") {
			doc.addField("original_name", record.getOriginal_name().trim());
		}
		if (record.getOriginal_title() != "") {
			doc.addField("original_title", record.getOriginal_title().trim());
		}
		if (record.getOriginal_url() != "") {
			doc.addField("original_url", record.getOriginal_url().trim());
		}
		if (record.getUrl() != "") {
			doc.addField("url", record.getUrl().trim());
		}
		if (record.getHome_url() != "") {
			doc.addField("home_url", record.getHome_url().trim());
		}
		if (record.getTitle() != "") {
			doc.addField("title", record.getTitle().trim());
		}
		if (record.getType() != "") {
			doc.addField("type", record.getType().trim());
		}
		if (record.getContent() != "") {
			doc.addField("content", record.getContent().trim());
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
			doc.addField("video_url", record.getVideo_url().trim());
		}
		if (record.getPic_url() != "") {
			doc.addField("pic_url", record.getPic_url().trim());
		}
		if (record.getVoice_url() != "") {
			doc.addField("voice_url", record.getVoice_url().trim());
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
			doc.addField("identify_md5", record.getIdentify_md5().trim());
		}
		if (record.getKeyword() != "") {
			doc.addField("keyword", record.getKeyword().trim());
		}
		if (record.getFirst_time() != null) {
			doc.addField("first_time", record.getFirst_time());
		}
		if (record.getUpdate_time() != null) {
			doc.addField("update_time", record.getUpdate_time());
		}
		if (record.getIp() != "") {
			doc.addField("ip", record.getIp().trim());
		}
		if (record.getLocation() != "") {
			doc.addField("location", record.getLocation().trim());
		}
		if (record.getGeo() != "") {
			doc.addField("geo", record.getGeo().trim());
		}
		if (record.getReceive_addr() != "") {
			doc.addField("receive_addr", record.getReceive_addr().trim());
		}
		if (record.getAppend_addr() != "") {
			doc.addField("append_addr", record.getAppend_addr().trim());
		}
		if (record.getSend_addr() != "") {
			doc.addField("send_addr", record.getSend_addr().trim());
		}

		return doc;
	}

	public void close() {
		cloudServer.shutdown();
	}

}
