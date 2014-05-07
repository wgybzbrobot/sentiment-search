package zx.soft.sent.web.demo;

import java.util.LinkedHashMap;
import java.util.Map;

import zx.soft.sent.utils.json.JsonUtils;

public class ValueSortedMapDemo {

	public static void main(String[] args) {

		Map<String, Integer> map = new LinkedHashMap<>();
		map.put("a", 234);
		map.put("b", 123);
		map.put("c", 453);
		System.out.println(JsonUtils.toJson(map));
	}

}
