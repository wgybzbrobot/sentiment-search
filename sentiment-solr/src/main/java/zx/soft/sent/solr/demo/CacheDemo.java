package zx.soft.sent.solr.demo;

import java.util.Map;
import java.util.Map.Entry;

import zx.soft.sent.cache.dao.Cache;
import zx.soft.sent.cache.factory.CacheFactory;
import zx.soft.sent.solr.search.OracleToRedis;

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
