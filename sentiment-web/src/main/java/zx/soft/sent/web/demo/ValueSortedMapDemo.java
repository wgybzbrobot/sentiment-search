package zx.soft.sent.web.demo;

import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.utils.json.JsonUtils;

public class ValueSortedMapDemo {

	public static void main(String[] args) {

		List<String> list = new ArrayList<>();
		list.add("ddfg");
		list.add("bbef");
		System.out.println(JsonUtils.toJson(list));
	}

}
