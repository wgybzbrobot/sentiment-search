package zx.soft.spider.cache.demo;

import zx.soft.spider.cache.redis.impl.RedisCache;

public class RedisDemo {

	public static void main(String[] args) {

		RedisCache redisCache = new RedisCache("localhost", "zxsoft");
		String key = "record_key_md5";
		String[] members = { "v1", "v2", "v3", "v4", "v5", "v3" };
		redisCache.sadd(key, members);
		System.out.println(redisCache.scard(key));
		System.out.println(redisCache.sismember(key, "v3"));
		System.out.println(redisCache.sismember(key, "v6"));
		redisCache.sadd(key, "v5", "v7");
		System.out.println(redisCache.scard(key));
		System.out.println(redisCache.smembers(key));

	}

}
