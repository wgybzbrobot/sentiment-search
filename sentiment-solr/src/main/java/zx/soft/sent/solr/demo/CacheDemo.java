package zx.soft.sent.solr.demo;

import java.util.Map;
import java.util.Map.Entry;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.RedisCache;
import zx.soft.sent.dao.common.SentimentConstant;

public class CacheDemo {

	public static void main(String[] args) {

		Cache cache = new RedisCache("192.168.32.19,192.168.32.20", 6379, "zxsoft");
		Map<String, String> sourceIdAndNames = cache.hgetAll(SentimentConstant.SITE_MAP);
		cache.close();
		System.out.println(sourceIdAndNames.size());

		Cache cache1 = new RedisCache("192.168.32.19", 16379, "zxsoft");
		for (Entry<String, String> sourceIdAndName : sourceIdAndNames.entrySet()) {
			System.out.println(sourceIdAndName.getKey() + ":" + sourceIdAndName.getValue());
			cache1.hset(SentimentConstant.SITE_MAP, sourceIdAndName.getKey(), sourceIdAndName.getValue());
		}
		cache1.close();
	}

}
