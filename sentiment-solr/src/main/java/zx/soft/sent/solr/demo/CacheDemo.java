package zx.soft.sent.solr.demo;

import java.util.Map;
import java.util.Map.Entry;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.CacheFactory;
import zx.soft.sent.solr.query.OracleToRedis;

public class CacheDemo {

	public static void main(String[] args) {

		Cache cache = CacheFactory.getInstance();
		Map<String, String> sourceIdAndNames = cache.hgetAll(OracleToRedis.SITE_MAP);
		for (Entry<String, String> sourceIdAndName : sourceIdAndNames.entrySet()) {
			System.out.println(sourceIdAndName.getKey() + ":" + sourceIdAndName.getValue());
		}
		cache.close();
	}

}
