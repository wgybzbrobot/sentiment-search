package zx.soft.sent.web.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.codehaus.jackson.JsonNode;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPage;
import zx.soft.sent.utils.json.JsonNodeUtils;
import zx.soft.sent.utils.json.JsonUtils;
import zx.soft.sent.web.utils.JavaPattern;

public class Demo {

	public static void main(String[] args) {
		FirstPage firstPage = new FirstPage(MybatisConfig.ServerEnum.sentiment);
		List<SolrDocument> result = new ArrayList<>();
		String resultStr = firstPage.selectFirstPage(53, "2014-09-10,11");
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
		System.out.println(JsonUtils.toJson(result));
	}

}
