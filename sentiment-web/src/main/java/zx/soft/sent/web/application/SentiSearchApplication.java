package zx.soft.sent.web.application;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.sentiment.SentimentRecord;
import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.search.SearchingData;
import zx.soft.sent.web.resource.SentSearchResource;

/**
 * 舆情搜索应用类
 *
 * @author wanggang
 *
 */
public class SentiSearchApplication extends Application {

	private final SearchingData searchingData;
	private final SentimentRecord sentimentRecord;

	public SentiSearchApplication() {
		searchingData = new SearchingData();
		sentimentRecord = new SentimentRecord(MybatisConfig.ServerEnum.sentiment);
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		getContext().getParameters().add("maxThreads", "512");
		getContext().getParameters().add("minThreads", "100");
		getContext().getParameters().add("lowThreads", "200");
		getContext().getParameters().add("maxConnectionsPerHost", "128");
		getContext().getParameters().add("initialConnections", "255");
		getContext().getParameters().add("maxTotalConnections", "1024");
		getContext().getParameters().add("maxIoIdleTimeMs", "100");
		router.attach("/search", SentSearchResource.class);
		//		router.attach("/get", SentGetResource.class);
		return router;
	}

	public QueryResult queryData(QueryParams queryParams) {
		return searchingData.queryData(queryParams, Boolean.TRUE);
	}

	/**
	 * 插入查询缓存数据
	 */
	public void insertCacheQuery(String tablename, String query_id, String query_url, String query_result) {
		sentimentRecord.insertCacheQuery(tablename, query_id, query_url, query_result);
	}

	/**
	 * 更新查询缓存数据
	 */
	public void updateCacheQuery(String tablename, String query_id, String query_url, String query_result) {
		sentimentRecord.updateCacheQuery(tablename, query_id, query_url, query_result);
	}

	/**
	 * 查询查询缓存数据
	 */
	public String selectCacheQuery(String tablename, String query_id) {
		return sentimentRecord.selectCacheQuery(tablename, query_id);
	}

	/**
	 * 删除查询缓存数据
	 */
	public void deleteCacheQuery(String tablename, String query_id) {
		sentimentRecord.deleteCacheQuery(tablename, query_id);
	}

	public void close() {
		searchingData.close();
	}

}
