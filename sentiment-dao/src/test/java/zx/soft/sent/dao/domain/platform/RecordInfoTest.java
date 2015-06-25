package zx.soft.sent.dao.domain.platform;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RecordInfoTest {

	private static RecordInfo recordInfo;

	@BeforeClass
	public static void prepare() {
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
		recordInfo.setTimestamp(60);
		recordInfo.setSource_id(70);
		recordInfo.setLasttime(80);
		recordInfo.setServer_id(90);
		recordInfo.setIdentify_id(100);
		recordInfo.setIdentify_md5("abcdefg123456789");
		recordInfo.setKeyword("关键词");
		recordInfo.setFirst_time(110);
		recordInfo.setUpdate_time(120);
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
	public void testRecordInfo() {
		assertEquals("sentiment", recordInfo.getId());
		assertEquals(10, recordInfo.getPlatform());
		assertEquals("123456789987654321", recordInfo.getMid());
		assertEquals("zxsoft", recordInfo.getUsername());
		assertEquals("中新舆情", recordInfo.getNickname());
		assertEquals("original_sentiment", recordInfo.getOriginal_id());
		assertEquals("original_zxsoft", recordInfo.getOriginal_uid());
		assertEquals("original_中新软件", recordInfo.getOriginal_name());
		assertEquals("original_标题", recordInfo.getOriginal_title());
		assertEquals("http://www.orignal_url.com", recordInfo.getOriginal_url());
		assertEquals("http://www.url.com", recordInfo.getUrl());
		assertEquals("http://www.home_url.com", recordInfo.getHome_url());
		assertEquals("标题", recordInfo.getTitle());
		assertEquals("所属类型", recordInfo.getType());
		assertEquals(Boolean.TRUE, recordInfo.isIsharmful());
		assertEquals("测试内容", recordInfo.getContent());
		assertEquals(10, recordInfo.getComment_count());
		assertEquals(20, recordInfo.getRead_count());
		assertEquals(30, recordInfo.getFavorite_count());
		assertEquals(40, recordInfo.getAttitude_count());
		assertEquals(50, recordInfo.getRepost_count());
		assertEquals("http://www.video_url.com", recordInfo.getVideo_url());
		assertEquals("htpp://www.pic_url.com", recordInfo.getPic_url());
		assertEquals("http://www.voice_url.com", recordInfo.getVoice_url());
		assertEquals(60, recordInfo.getTimestamp());
		assertEquals(70, recordInfo.getSource_id());
		assertEquals(80, recordInfo.getLasttime());
		assertEquals(90, recordInfo.getServer_id());
		assertEquals(100, recordInfo.getIdentify_id());
		assertEquals("abcdefg123456789", recordInfo.getIdentify_md5());
		assertEquals("关键词", recordInfo.getKeyword());
		assertEquals(110, recordInfo.getFirst_time());
		assertEquals(120, recordInfo.getUpdate_time());
		assertEquals("192.168.32.45", recordInfo.getIp());
		assertEquals("安徽省合肥市", recordInfo.getLocation());
		assertEquals("经纬度信息", recordInfo.getGeo());
		assertEquals("receive@gmail.com", recordInfo.getReceive_addr());
		assertEquals("append@gmail.com", recordInfo.getAppend_addr());
		assertEquals("send@gmail.com", recordInfo.getSend_addr());
		assertEquals("新浪微博", recordInfo.getSource_name());
		assertEquals(121, recordInfo.getSource_type());
		assertEquals(122, recordInfo.getCountry_code());
		assertEquals(123, recordInfo.getLocation_code());
		assertEquals(124, recordInfo.getProvince_code());
		assertEquals(125, recordInfo.getCity_code());
	}

	@AfterClass
	public static void cleanup() {
		recordInfo = null;
	}

}
