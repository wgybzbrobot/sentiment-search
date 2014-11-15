package zx.soft.sent.solr.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import zx.soft.sent.dao.domain.platform.RecordInfo;

public class IndexCloudSolrTest {

	private static RecordInfo recordInfo;

	@Before
	public void prepare() {
		recordInfo = new RecordInfo();
		recordInfo.setId("sentiment");
		recordInfo.setPlatform(10);
		recordInfo.setMid("123456789987654321");
		recordInfo.setUsername("zxsoft");
		recordInfo.setNickname("中新舆情");
		recordInfo.setOriginal_id("original_sentiment");
		recordInfo.setOriginal_uid("original_zxsoft");
		recordInfo.setOriginal_name("original_中新软件");
		recordInfo.setOriginal_title("original_标题");
		recordInfo.setOriginal_url("http://www.orignal_url.com");
		recordInfo.setUrl("http://www.url.com");
		recordInfo.setHome_url("http://www.home_url.com");
		recordInfo.setTitle("标题");
		recordInfo.setType("所属类型");
		recordInfo.setIsharmful(Boolean.TRUE);
		recordInfo.setContent("测试内容");
		recordInfo.setComment_count(10);
		recordInfo.setRead_count(20);
		recordInfo.setFavorite_count(30);
		recordInfo.setAttitude_count(40);
		recordInfo.setRepost_count(50);
		recordInfo.setVideo_url("http://www.video_url.com");
		recordInfo.setPic_url("htpp://www.pic_url.com");
		recordInfo.setVoice_url("http://www.voice_url.com");
		recordInfo.setTimestamp(1416038921);
		recordInfo.setSource_id(70);
		recordInfo.setLasttime(1416038921 + 86400);
		recordInfo.setServer_id(90);
		recordInfo.setIdentify_id(100);
		recordInfo.setIdentify_md5("abcdefg123456789");
		recordInfo.setKeyword("关键词");
		recordInfo.setFirst_time(1416038921 + 86400 * 2);
		recordInfo.setUpdate_time(1416038921 + 86400 * 3);
		recordInfo.setIp("192.168.32.45");
		recordInfo.setLocation("安徽省合肥市");
		recordInfo.setGeo("经纬度信息");
		recordInfo.setReceive_addr("receive@gmail.com");
		recordInfo.setAppend_addr("append@gmail.com");
		recordInfo.setSend_addr("send@gmail.com");
		recordInfo.setSource_name("新浪微博");
		recordInfo.setSource_type(121);
		recordInfo.setCountry_code(122);
		recordInfo.setLocation_code(123);
		recordInfo.setProvince_code(124);
		recordInfo.setCity_code(125);
	}

	@Test
	public void testGetSentimentDoc_测试id为空的情况() {
		assertNotNull(IndexCloudSolr.getSentimentDoc(recordInfo));
		recordInfo.setId(null);
		assertNull(IndexCloudSolr.getSentimentDoc(recordInfo));
		recordInfo.setId("");
		assertNull(IndexCloudSolr.getSentimentDoc(recordInfo));
	}

	@Test
	public void testGetSentimentDoc_测试转换字段是否正确() {
		String result = "SolrInputDocument(fields: [id=sentiment, platform=10, mid=123456789987654321, username=zxsoft,"
				+ " nickname=中新舆情, original_id=original_sentiment, original_uid=original_zxsoft, original_name=original_中新软件,"
				+ " original_title=original_标题, original_url=http://www.orignal_url.com, url=http://www.url.com,"
				+ " home_url=http://www.home_url.com, title=标题, type=所属类型, isharmful=true, content=测试内容, comment_count=10,"
				+ " read_count=20, favorite_count=30, attitude_count=40, repost_count=50, video_url=http://www.video_url.com,"
				+ " pic_url=htpp://www.pic_url.com, voice_url=http://www.voice_url.com, timestamp=Sat Nov 15 16:08:41 CST 2014,"
				+ " source_id=70, lasttime=Sun Nov 16 16:08:41 CST 2014, server_id=90, identify_id=100, identify_md5=abcdefg123456789,"
				+ " keyword=关键词, first_time=Mon Nov 17 16:08:41 CST 2014, update_time=Tue Nov 18 16:08:41 CST 2014, ip=192.168.32.45,"
				+ " location=安徽省合肥市, geo=经纬度信息, receive_addr=receive@gmail.com, append_addr=append@gmail.com,"
				+ " send_addr=send@gmail.com, source_name=新浪微博, source_type=121, country_code=122, location_code=123,"
				+ " province_code=124, city_code=125])";
		SolrInputDocument doc = IndexCloudSolr.getSentimentDoc(recordInfo);
		assertEquals(result, doc.toString());
		assertEquals("sentiment", doc.getFieldValue("id"));
		assertEquals(10, doc.getFieldValue("platform"));
		assertEquals("123456789987654321", doc.getFieldValue("mid"));
		assertEquals("zxsoft", doc.getFieldValue("username"));
		assertEquals("中新舆情", doc.getFieldValue("nickname"));
		assertEquals("original_sentiment", doc.getFieldValue("original_id"));
		assertEquals("original_zxsoft", doc.getFieldValue("original_uid"));
		assertEquals("original_中新软件", doc.getFieldValue("original_name"));
		assertEquals("original_标题", doc.getFieldValue("original_title"));
		assertEquals("http://www.orignal_url.com", doc.getFieldValue("original_url"));
		assertEquals("http://www.url.com", doc.getFieldValue("url"));
		assertEquals("http://www.home_url.com", doc.getFieldValue("home_url"));
		assertEquals("标题", doc.getFieldValue("title"));
		assertEquals("所属类型", doc.getFieldValue("type"));
		assertEquals(Boolean.TRUE, doc.getFieldValue("isharmful"));
		assertEquals("测试内容", doc.getFieldValue("content"));
		assertEquals(10, doc.getFieldValue("comment_count"));
		assertEquals(20, doc.getFieldValue("read_count"));
		assertEquals(30, doc.getFieldValue("favorite_count"));
		assertEquals(40, doc.getFieldValue("attitude_count"));
		assertEquals(50, doc.getFieldValue("repost_count"));
		assertEquals("http://www.video_url.com", doc.getFieldValue("video_url"));
		assertEquals("htpp://www.pic_url.com", doc.getFieldValue("pic_url"));
		assertEquals("http://www.voice_url.com", doc.getFieldValue("voice_url"));
		assertEquals("Sat Nov 15 16:08:41 CST 2014", doc.getFieldValue("timestamp").toString());
		assertEquals(70, doc.getFieldValue("source_id"));
		assertEquals("Sun Nov 16 16:08:41 CST 2014", doc.getFieldValue("lasttime").toString());
		assertEquals(90, doc.getFieldValue("server_id"));
		assertEquals(100L, doc.getFieldValue("identify_id"));
		assertEquals("abcdefg123456789", doc.getFieldValue("identify_md5"));
		assertEquals("关键词", doc.getFieldValue("keyword"));
		assertEquals("Mon Nov 17 16:08:41 CST 2014", doc.getFieldValue("first_time").toString());
		assertEquals("Tue Nov 18 16:08:41 CST 2014", doc.getFieldValue("update_time").toString());
		assertEquals("192.168.32.45", doc.getFieldValue("ip"));
		assertEquals("安徽省合肥市", doc.getFieldValue("location"));
		assertEquals("经纬度信息", doc.getFieldValue("geo"));
		assertEquals("receive@gmail.com", doc.getFieldValue("receive_addr"));
		assertEquals("append@gmail.com", doc.getFieldValue("append_addr"));
		assertEquals("send@gmail.com", doc.getFieldValue("send_addr"));
		assertEquals("新浪微博", doc.getFieldValue("source_name"));
		assertEquals(121, doc.getFieldValue("source_type"));
		assertEquals(122, doc.getFieldValue("country_code"));
		assertEquals(123, doc.getFieldValue("location_code"));
		assertEquals(124, doc.getFieldValue("province_code"));
		assertEquals(125, doc.getFieldValue("city_code"));
	}

}
