package zx.soft.sent.web.application;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.solr.common.SolrDocument;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.search.SearchingData;
import zx.soft.sent.web.resource.NicknameGroupResource;
import zx.soft.utils.sort.InsertSort;

/**
 * 與请搜索应用类
 * 
 * @author wanggang
 *
 */
public class NicknameGroupApplication extends Application {

	private final SearchingData searchingData;

	public NicknameGroupApplication() {
		searchingData = new SearchingData();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/search", NicknameGroupResource.class);
		return router;
	}

	public LinkedHashMap<String, Integer> queryData(QueryParams queryParams) {
		queryParams.setRows(10_000);
		queryParams.setFl("nickname");
		QueryResult queryResult = searchingData.queryData(queryParams, true);
		// Hash表
		HashMap<String, Integer> map = new HashMap<>();
		for (SolrDocument tmp : queryResult.getResults()) {
			if (tmp.getFieldValue("nickname") == null) {
				continue;
			}
			if (map.get(tmp.getFieldValue("nickname").toString()) == null) {
				map.put(tmp.getFieldValue("nickname").toString(), 1);
			} else {
				map.put(tmp.getFieldValue("nickname").toString(), 1 + map.get(tmp.getFieldValue("nickname")));
			}
		}
		String[] table = new String[50];
		for (int i = 0; i < table.length; i++) {
			table[i] = "0=0";
		}
		for (Entry<String, Integer> tmp : map.entrySet()) {
			table = InsertSort.toptable(table, tmp.getKey() + "=" + tmp.getValue());
		}
		LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
		String[] t = null;
		for (int i = 0; i < 15; i++) {
			t = table[i].split("=");
			result.put(t[0], Integer.parseInt(t[1]));
		}
		return result;
	}

	public void close() {
		searchingData.close();
	}

}
