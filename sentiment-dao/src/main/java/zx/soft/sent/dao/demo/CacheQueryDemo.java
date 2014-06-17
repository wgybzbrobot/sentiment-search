package zx.soft.sent.dao.demo;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.sentiment.SentimentRecord;

public class CacheQueryDemo {

	public static void main(String[] args) {

		SentimentRecord sr = new SentimentRecord(MybatisConfig.ServerEnum.sentiment);
		/**
		 * 插入查询缓存数据
		 */
		sr.insertCacheQuery("cache_query", "123546987ddfeff", "http://www.baidu.com", "result");
		System.out.println(sr.selectCacheQuery("cache_query", "123546987ddfeff"));

		/**
		 * 更新查询缓存数据
		 */
		sr.updateCacheQuery("cache_query", "123546987ddfeff", "http://www.baidu.com", "update_result");

		/**
		 * 查询查询缓存数据
		 */
		System.out.println(sr.selectCacheQuery("cache_query", "123546987ddfeff"));

		/**
		 * 删除查询缓存数据
		 */
		sr.deleteCacheQuery("cache_query", "123546987ddfeff");
	}

}
