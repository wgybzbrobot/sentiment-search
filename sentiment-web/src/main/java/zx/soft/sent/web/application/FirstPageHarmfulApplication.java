package zx.soft.sent.web.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.codehaus.jackson.JsonNode;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPageHarmful;
import zx.soft.sent.web.resource.FirstPageHarmfulResource;
import zx.soft.utils.chars.JavaPattern;
import zx.soft.utils.json.JsonNodeUtils;

public class FirstPageHarmfulApplication extends Application {

	private final FirstPageHarmful firstPage;

	public FirstPageHarmfulApplication() {
		firstPage = new FirstPageHarmful(MybatisConfig.ServerEnum.sentiment);
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// POST专题信息
		router.attach("/{type}/{datestr}", FirstPageHarmfulResource.class);
		return router;
	}

	/**
	 * 查询OA首页查询数据，type=1,2,3,4,5,6,7,8,9,10
	 */
	public List<SolrDocument> selectFirstPageType(int type, String timestr) {
		List<SolrDocument> result = new ArrayList<>();
		String resultStr = firstPage.selectFirstPage(type, timestr);
		if (resultStr == null) {
			return null;
		}
		JsonNode node = JsonNodeUtils.getJsonNode(resultStr);
		String key = "", value = "";
		Iterator<String> keys = null;
		SolrDocument doc = null;
		for (int i = 0; i < node.size(); i++) {
			doc = new SolrDocument();
			keys = node.get(i).getFieldNames();
			while (keys.hasNext()) {
				key = keys.next();
				value = node.get(i).get(key).toString().replaceAll("\"", "");
				if (JavaPattern.isAllNum(value)) {
					doc.setField(key, Long.parseLong(value));
				} else {
					doc.setField(key, value);
				}
			}
			result.add(doc);
		}
		return result;
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}

}
