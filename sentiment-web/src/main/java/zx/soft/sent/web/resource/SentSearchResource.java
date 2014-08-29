package zx.soft.sent.web.resource;

import java.util.HashMap;

import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.web.application.SentiSearchApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.sent.web.utils.URLCodecUtils;

/**
 * 與请搜索资源类
 * 
 * @author wanggang
 *
 */
public class SentSearchResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SentSearchResource.class);

	private static SentiSearchApplication application;

	//	private static ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector();

	private QueryParams queryParams;

	@Override
	public void doInit() {
		queryParams = new QueryParams();
		application = (SentiSearchApplication) getApplication();
		HashMap<String, String> params = new HashMap<>();
		Form form = getRequest().getResourceRef().getQueryAsForm();
		for (Parameter p : form) {
			if (params.get(p.getName()) == null) {
				params.put(p.getName(), p.getValue());
			} else { // 重复参数以最后一个为准
				params.put(p.getName(), p.getValue());
			}
		}
		// 参数处理
		queryParams.setQ(params.get("q") == null ? "*:*" : params.get("q"));
		queryParams.setFq(params.get("fq") == null ? "" : params.get("fq"));
		queryParams.setSort(params.get("sort") == null ? "" : params.get("sort"));
		queryParams.setStart(params.get("start") == null ? 0 : (Integer.parseInt(params.get("start")) > 1000 ? 1000
				: Integer.parseInt(params.get("start"))));
		queryParams.setRows(params.get("rows") == null ? 10 : (Integer.parseInt(params.get("rows")) > 100 ? 100
				: Integer.parseInt(params.get("rows"))));
		queryParams.setFl(params.get("fl") == null ? "" : params.get("fl"));
		queryParams.setWt(params.get("wt") == null ? "json" : params.get("wt"));
		queryParams.setHlfl(params.get("hlfl") == null ? "" : params.get("hlfl"));
		queryParams.setHlsimple(params.get("hlsimple") == null ? "" : params.get("hlsimple"));
		queryParams.setFacetQuery(params.get("facetQuery") == null ? "" : params.get("facetQuery"));
		queryParams.setFacetField(params.get("facetField") == null ? "" : params.get("facetField"));
		logger.info(queryParams.toString());
	}

	@Get("json")
	public Object getQueryResult() {
		final String queryURL = URLCodecUtils.decoder(getReference().toString(), "utf-8");
		logger.info("Request Url: " + queryURL + ".");
		if (getReference().getRemainingPart() == null) {
			return new ErrorResponse.Builder(20003, "your query params is illegal.").build();
		}
		// 数据库存在的话，返回该条数据，并且另一个线程更新数据
		//		String cacheResult = application.selectCacheQuery(CreateTables.CACHE_QUERY_TABLE,
		//				CheckSumUtils.getMD5(queryURL));
		//		if (cacheResult != null) {
		//			pool.execute(new Runnable() {
		//				@Override
		//				public void run() {
		//					final QueryResult result = application.queryData(queryParams);
		//					application.updateCacheQuery(CreateTables.CACHE_QUERY_TABLE, CheckSumUtils.getMD5(queryURL),
		//							queryURL, JsonUtils.toJsonWithoutPretty(result));
		//				}
		//			});
		//			return cacheResult;
		//		}
		// 数据库不存在的话，查询结果返回，并且另一个线程写数据
		final QueryResult queryResult = application.queryData(queryParams);
		//		pool.execute(new Runnable() {
		//			@Override
		//			public void run() {
		//				application.insertCacheQuery(CreateTables.CACHE_QUERY_TABLE, CheckSumUtils.getMD5(queryURL), queryURL,
		//						JsonUtils.toJsonWithoutPretty(queryResult));
		//			}
		//		});
		return queryResult;
	}

}
