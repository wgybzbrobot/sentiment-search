package zx.soft.sent.web.demo;

import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.utils.json.JsonUtils;
import zx.soft.sent.web.domain.PostData;

public class PostDataDemo {

	public static void main(String[] args) {

		RecordInfo recordInfo1 = new RecordInfo();
		recordInfo1.setId("128");
		recordInfo1.setPlatform(7);
		recordInfo1.setMid("123abcdef");
		recordInfo1.setUsername("987654321");
		recordInfo1.setNickname("wgybzb");
		recordInfo1.setOriginal_id("5648333");
		recordInfo1.setOriginal_uid("256339988");
		recordInfo1.setOriginal_name("owgybzb");
		recordInfo1.setOriginal_title("原创标题");
		recordInfo1.setOriginal_url("http://www.baidu.com");
		recordInfo1.setUrl("http://www.pp.cc");
		recordInfo1.setHome_url("http://www.google.com");
		recordInfo1.setTitle("标题");
		recordInfo1.setType("美容美食");
		recordInfo1.setIsharmful(Boolean.TRUE);
		recordInfo1.setContent("这是一条测试数据");
		recordInfo1.setComment_count(100);
		recordInfo1.setRead_count(200);
		recordInfo1.setFavorite_count(300);
		recordInfo1.setAttitude_count(400);
		recordInfo1.setRepost_count(500);
		recordInfo1.setVideo_url("http://www.video.vom");
		recordInfo1.setPic_url("http://www.pic.com");
		recordInfo1.setVoice_url("http://www.voice.com");
		recordInfo1.setTimestamp(129856473L);
		recordInfo1.setSource_id(153);
		recordInfo1.setLasttime(985647213L);
		recordInfo1.setServer_id(5);
		recordInfo1.setIdentify_id(125633L);
		recordInfo1.setIdentify_md5("identifymd5value");
		recordInfo1.setKeyword("美食娱乐");
		recordInfo1.setFirst_time(1566423587L);
		recordInfo1.setUpdate_time(1426879123L);
		recordInfo1.setIp("192.168.1.100");
		recordInfo1.setLocation("安徽省合肥市");
		recordInfo1.setGeo("经度:120.2366554,纬度:50.122599");
		recordInfo1.setReceive_addr("wanggang@zxils.com");
		recordInfo1.setAppend_addr("wanggang@pp.cc");
		recordInfo1.setSend_addr("wgybzb@sina.cn");
		recordInfo1.setSource_name("新浪微博");
		recordInfo1.setSource_type(5);
		recordInfo1.setCountry_code(1);
		recordInfo1.setLocation_code(2130123);
		recordInfo1.setProvince_code(34);
		recordInfo1.setCity_code(12);

		RecordInfo recordInfo2 = new RecordInfo();
		recordInfo2.setId("256");
		recordInfo2.setPlatform(7);
		recordInfo2.setMid("456jdjdkff");
		recordInfo2.setUsername("2564788");
		recordInfo2.setNickname("wgybzb1");
		recordInfo2.setOriginal_id("5648333");
		recordInfo2.setOriginal_uid("256339988");
		recordInfo2.setOriginal_name("owgybzb1");
		recordInfo2.setOriginal_title("原创标题");
		recordInfo2.setOriginal_url("http://www.baidu.com");
		recordInfo2.setUrl("http://www.pp.cc");
		recordInfo2.setHome_url("http://www.google.com");
		recordInfo2.setTitle("标题");
		recordInfo2.setType("美容美食");
		recordInfo2.setContent("这是一条测试数据");
		recordInfo2.setComment_count(100);
		recordInfo2.setRead_count(200);
		recordInfo2.setFavorite_count(300);
		recordInfo2.setAttitude_count(400);
		recordInfo2.setRepost_count(500);
		recordInfo2.setVideo_url("http://www.video.vom");
		recordInfo2.setPic_url("http://www.pic.com");
		recordInfo2.setVoice_url("http://www.voice.com");
		recordInfo2.setTimestamp(129856473L);
		recordInfo2.setSource_id(153);
		recordInfo2.setLasttime(985647213L);
		recordInfo2.setServer_id(5);
		recordInfo2.setIdentify_id(125633L);
		recordInfo2.setIdentify_md5("identifymd5value");
		recordInfo2.setKeyword("美食娱乐");
		recordInfo2.setFirst_time(1566423587L);
		recordInfo2.setUpdate_time(1426879123L);
		recordInfo2.setIp("192.168.1.100");
		recordInfo2.setLocation("安徽省合肥市");
		recordInfo2.setGeo("经度:120.2366554,纬度:50.122599");
		recordInfo2.setReceive_addr("wanggang@zxils.com");
		recordInfo2.setAppend_addr("wanggang@pp.cc");
		recordInfo2.setSend_addr("wgybzb@sina.cn");
		recordInfo2.setSource_name("新浪微博");
		recordInfo2.setSource_type(5);
		recordInfo2.setCountry_code(1);
		recordInfo2.setLocation_code(2130123);
		recordInfo2.setProvince_code(34);
		recordInfo2.setCity_code(12);

		List<RecordInfo> records = new ArrayList<>();
		records.add(recordInfo1);
		records.add(recordInfo2);
		PostData postData = new PostData();
		postData.setNum(2);
		postData.setRecords(records);

		System.out.println(JsonUtils.toJsonWithoutPretty(postData));
	}

}
