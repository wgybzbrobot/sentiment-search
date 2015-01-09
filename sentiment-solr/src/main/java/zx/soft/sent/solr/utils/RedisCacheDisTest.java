package zx.soft.sent.solr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.threads.ApplyThreadPool;

public class RedisCacheDisTest {

	private static final Random RANDOM = new Random();

	public static void main(String[] args) {

		final ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector(8);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
			}
		}));

		final RedisCache redisCache = new RedisCache();
		System.err.println("初始化完成......");

		for (int i = 0; i < 1000000; i++) {
			System.out.println("Add at : " + i);
			pool.execute(new Thread(new Runnable() {
				@Override
				public void run() {
					List<String> records = getRecords10();
					redisCache.addRecord(records.toArray(new String[records.size()]));
				}
			}));
			if (i % 100 == 0) {
				System.err.println(redisCache.getSetSize());
				redisCache.getRecords();
				//				List<String> records = redisCache.getRecords();
				//				System.out.println(JsonUtils.toJson(redisCache.mapper(records)));
			}
		}

		try {
			Thread.sleep(3_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdown();

	}

	public static List<String> getRecords10() {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			for (String str : getRecords()) {
				result.add(str);
			}
		}
		return result;
	}

	private static List<String> getRecords() {
		int i = RANDOM.nextInt(10000);
		RecordInfo recordInfo1 = new RecordInfo();
		recordInfo1.setId("sentiment" + i);
		recordInfo1.setPlatform(10);
		recordInfo1.setMid("123456789987654321");
		recordInfo1.setUsername("zxsoft");
		recordInfo1.setNickname("中新舆情");
		recordInfo1.setOriginal_id("original_sentiment");
		recordInfo1.setOriginal_uid("original_zxsoft");
		recordInfo1.setOriginal_name("original_中新软件");
		recordInfo1.setOriginal_title("original_标题");
		recordInfo1.setOriginal_url("http://www.orignal_url.com");
		recordInfo1.setUrl("http://www.url.com");
		recordInfo1.setHome_url("http://www.home_url.com");
		recordInfo1.setTitle("标题");
		recordInfo1.setType("所属类型");
		recordInfo1.setIsharmful(Boolean.TRUE);
		recordInfo1.setContent("测试内容");
		recordInfo1.setComment_count(10);
		recordInfo1.setRead_count(20);
		recordInfo1.setFavorite_count(30);
		recordInfo1.setAttitude_count(40);
		recordInfo1.setRepost_count(50);
		recordInfo1.setVideo_url("http://www.video_url.com");
		recordInfo1.setPic_url("htpp://www.pic_url.com");
		recordInfo1.setVoice_url("http://www.voice_url.com");
		recordInfo1.setTimestamp(1419755627695L);
		recordInfo1.setSource_id(70);
		recordInfo1.setLasttime(1419755627695L + 86400_000L);
		recordInfo1.setServer_id(90);
		recordInfo1.setIdentify_id(100);
		recordInfo1.setIdentify_md5("abcdefg123456789");
		recordInfo1.setKeyword("关键词");
		recordInfo1.setFirst_time(1419755627695L + 86400_000L * 2);
		recordInfo1.setUpdate_time(1419755627695L + 86400_000L * 3);
		recordInfo1.setIp("192.168.32.45");
		recordInfo1.setLocation("安徽省合肥市");
		recordInfo1.setGeo("经纬度信息");
		recordInfo1.setReceive_addr("receive@gmail.com");
		recordInfo1.setAppend_addr("append@gmail.com");
		recordInfo1.setSend_addr("send@gmail.com");
		recordInfo1.setSource_name("新浪微博");
		recordInfo1.setSource_type(121);
		recordInfo1.setCountry_code(122);
		recordInfo1.setLocation_code(123);
		recordInfo1.setProvince_code(124);
		recordInfo1.setCity_code(125);
		RecordInfo recordInfo2 = new RecordInfo();
		recordInfo2.setId("sentiment" + (i + 1));
		recordInfo2.setPlatform(10);
		recordInfo2.setMid("123456789987654321");
		recordInfo2.setUsername("zxsoft");
		recordInfo2.setNickname("中新舆情");
		recordInfo2.setOriginal_id("original_sentiment");
		recordInfo2.setOriginal_uid("original_zxsoft");
		recordInfo2.setOriginal_name("original_中新软件");
		recordInfo2.setOriginal_title("original_标题");
		recordInfo2.setOriginal_url("http://www.orignal_url.com");
		recordInfo2.setUrl("http://www.url.com");
		recordInfo2.setHome_url("http://www.home_url.com");
		recordInfo2.setTitle("标题");
		recordInfo2.setType("所属类型");
		recordInfo2.setIsharmful(Boolean.TRUE);
		recordInfo2.setContent("测试内容");
		recordInfo2.setComment_count(10);
		recordInfo2.setRead_count(20);
		recordInfo2.setFavorite_count(30);
		recordInfo2.setAttitude_count(40);
		recordInfo2.setRepost_count(50);
		recordInfo2.setVideo_url("http://www.video_url.com");
		recordInfo2.setPic_url("htpp://www.pic_url.com");
		recordInfo2.setVoice_url("http://www.voice_url.com");
		recordInfo2.setTimestamp(1419755627695L);
		recordInfo2.setSource_id(70);
		recordInfo2.setLasttime(1419755627695L + 86400_000L);
		recordInfo2.setServer_id(90);
		recordInfo2.setIdentify_id(100);
		recordInfo2.setIdentify_md5("abcdefg123456789");
		recordInfo2.setKeyword("关键词");
		recordInfo2.setFirst_time(1419755627695L + 86400_000L * 2);
		recordInfo2.setUpdate_time(1419755627695L + 86400_000L * 3);
		recordInfo2.setIp("192.168.32.45");
		recordInfo2.setLocation("安徽省合肥市");
		recordInfo2.setGeo("经纬度信息");
		recordInfo2.setReceive_addr("receive@gmail.com");
		recordInfo2.setAppend_addr("append@gmail.com");
		recordInfo2.setSend_addr("send@gmail.com");
		recordInfo2.setSource_name("新浪微博");
		recordInfo2.setSource_type(121);
		recordInfo2.setCountry_code(122);
		recordInfo2.setLocation_code(123);
		recordInfo2.setProvince_code(124);
		recordInfo2.setCity_code(125);
		List<String> recordInfos = new ArrayList<>();
		recordInfos.add(JsonUtils.toJsonWithoutPretty(recordInfo1));
		recordInfos.add(JsonUtils.toJsonWithoutPretty(recordInfo2));
		return recordInfos;
	}

}
