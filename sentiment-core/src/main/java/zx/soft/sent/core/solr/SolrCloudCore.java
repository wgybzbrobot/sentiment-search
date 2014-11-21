package zx.soft.sent.core.solr;

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

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.utils.config.ConfigUtil;

/**
 * 索引到SolrCloud
 * 
 * @author wanggang
 *
 */
public class SolrCloudCore {

	private static Logger logger = LoggerFactory.getLogger(SolrCloudCore.class);

	public static int FETCH_SIZE = 1_0000;

	private final CloudSolrServer cloudServer;

	public SolrCloudCore() {
		Properties props = ConfigUtil.getProps("solr_params.properties");
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
	public void addSentimentDocsToSolr(List<RecordInfo> records) {
		if (records.size() == 0) {
			return;
		}
		Collection<SolrInputDocument> docs = new ArrayList<>();
		for (RecordInfo record : records) {
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

	/**
	 *  注意：有字段添加或者修改时，这里也需要修改
	 *  后面可以将RecordInfo修改成动态Model类。
	 */
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
		if (record.getOriginal_id() != "") {
			doc.addField("original_id", record.getOriginal_id().trim());
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
			doc.addField("title", record.getTitle().trim(), 10f); // 增加权值10
		}
		if (record.getType() != "") {
			doc.addField("type", record.getType().trim());
		}
		if (record.getContent() != "") {
			doc.addField("content", record.getContent().trim(), 5f); // 增加权值5
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
		if (record.getSource_name() != "") {
			doc.addField("source_name", record.getSource_name().trim());
		}
		if (record.getSource_type() != 0) {
			doc.addField("source_type", record.getSource_type());
		}
		if (record.getCountry_code() != 0) {
			doc.addField("country_code", record.getCountry_code());
		}
		if (record.getLocation_code() != 0) {
			doc.addField("location_code", record.getLocation_code());
		}
		if (record.getProvince_code() != 0) {
			doc.addField("province_code", record.getProvince_code());
		}
		if (record.getCity_code() != 0) {
			doc.addField("city_code", record.getCity_code());
		}

		return doc;
	}

	public void close() {
		cloudServer.shutdown();
	}

}
