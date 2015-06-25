package zx.soft.sent.solr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.threads.ApplyThreadPool;

public class RedisMQTest {

	public static void main(String[] args) {
		// 单线程
		//		runSimpleThread();
		// 多线程
		runMultiThreads();
	}

	public static void runSimpleThread() {
		RedisMQ redisMQ = new RedisMQ();
		System.err.println("初始化完成......");

		for (int i = 0; i < 10; i++) {
			System.out.println("Add at : " + i);
			writeData(redisMQ, i * 10, (i + 1) * 10);
		}
	}

	public static void runMultiThreads() {
		final ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector(8);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
			}
		}));

		final RedisMQ redisMQ = new RedisMQ();
		System.err.println("初始化完成......");

		for (int i = 1000; i < 2000; i++) {
			System.out.println("Add at : " + i);
			pool.execute(new WriteRunnable(redisMQ, i * 100, (i + 1) * 100));
		}

		try {
			Thread.sleep(120_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdown();
		redisMQ.close();
	}

	public static class WriteRunnable implements Runnable {

		private RedisMQ cache;
		private int start;
		private int end;

		public WriteRunnable(RedisMQ cache, int start, int end) {
			super();
			this.cache = cache;
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			List<String> records = getRecords(start, end);
			//			System.err.println("Adding at:" + (start / 100) + ",size=" + records.size());
			cache.addRecord(records.toArray(new String[records.size()]));
		}

	}

	public static void writeData(RedisMQ cache, int start, int end) {
		List<String> records = getRecords(start, end);
		cache.addRecord(records.toArray(new String[records.size()]));
	}

	public static List<String> getRecords(int start, int end) {
		List<String> result = new ArrayList<>();
		for (int i = start; i < end; i++) {
			result.add(getRecords(i));
		}
		return result;
	}

	private static String getRecords(int i) {
		RecordInfo recordInfo = new RecordInfo();
		System.err.println("sentiment" + i);
		recordInfo.setId("sentiment" + i);
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
		recordInfo.setTimestamp(1419755627695L);
		recordInfo.setSource_id(i);
		recordInfo.setLasttime(1419755627695L + 86400_000L);
		recordInfo.setServer_id(90);
		recordInfo.setIdentify_id(100);
		recordInfo.setIdentify_md5("abcdefg123456789");
		recordInfo.setKeyword("关键词");
		recordInfo.setFirst_time(1419755627695L + 86400_000L * 2);
		recordInfo.setUpdate_time(1419755627695L + 86400_000L * 3);
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
		return JsonUtils.toJsonWithoutPretty(recordInfo);
	}

}
