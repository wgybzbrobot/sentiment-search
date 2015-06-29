package zx.soft.sent.web.application;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.domain.SimpleFacetInfo;
import zx.soft.sent.solr.query.QueryCore;
import zx.soft.sent.web.resource.NicknameGroupResource;
import zx.soft.utils.sort.InsertSort;

/**
 * 舆情搜索应用类
 *
 * @author wanggang
 *
 */
public class NicknameGroupApplication extends Application {

	private final QueryCore queryCore;

	public NicknameGroupApplication() {
		queryCore = new QueryCore();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/search", NicknameGroupResource.class);
		return router;
	}

	public HashMap<String, Integer> queryData(QueryParams queryParams) {
		long startTime = System.currentTimeMillis();
		List<QueryResult> queryResults = queryCore.facetResult(queryParams, true);
		//		System.err.println(System.currentTimeMillis() - startTime);
		//		startTime = System.currentTimeMillis();
		for (String field : queryParams.getFacetField().split(",")) {
			Map<String, Long> facet = new HashMap<String, Long>();
			for (QueryResult queryResult : queryResults) {
				for (SimpleFacetInfo info : queryResult.getFacetFields()) {
					if (info.getName().equals(field)) {
						for (Entry<String, Long> entrys : info.getValues().entrySet()) {
							String key = entrys.getKey();
							if (facet.containsKey(key)) {
								facet.put(key, facet.get(key) + entrys.getValue());
							} else {
								facet.put(key, entrys.getValue());
							}
						}
					}
				}
			}
			String[] table = new String[10];
			for (int i = 0; i < table.length; i++) {
				table[i] = "0=0";
			}
			for (Entry<String, Long> tmp : facet.entrySet()) {
				table = InsertSort.toptable(table, tmp.getKey() + "=" + tmp.getValue());
			}
			HashMap<String, Integer> result = new LinkedHashMap<>();
			String[] t = null;
			for (int i = 0; i < table.length; i++) {
				if ("0=0".equalsIgnoreCase(table[i])) {
					break;
				}
				t = table[i].split("=");
				result.put(t[0], Integer.parseInt(t[1]));
			}
			System.err.println(System.currentTimeMillis() - startTime);
			return result;
		}
		return null;
	}

	public void close() {
		queryCore.close();
	}

}
