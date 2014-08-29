package zx.soft.sent.web.demo;

import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.dao.domain.special.SpecialTopic;
import zx.soft.sent.utils.json.JsonUtils;

public class SpecialTopicDemo {

	public static void main(String[] args) {

		List<SpecialTopic> specialTopics = new ArrayList<>();
		SpecialTopic st1 = new SpecialTopic();
		st1.setIdentify("absbhdfhfjfi");
		st1.setName("专题1");
		st1.setKeywords("（合肥 and 警察） not 打人");
		st1.setStart("2014-08-25 00:00:00");
		st1.setEnd("2014-08-25 23:59:59");
		st1.setHometype(0);
		SpecialTopic st2 = new SpecialTopic();
		st2.setIdentify("djdjfkfklglg");
		st2.setName("专题2");
		st2.setKeywords("（安徽 and 城管） not 暴力执法");
		st2.setStart("2013-08-25 00:00:00");
		st2.setEnd("2013-08-25 23:59:59");
		st2.setHometype(2);
		specialTopics.add(st1);
		specialTopics.add(st2);
		System.out.println(JsonUtils.toJsonWithoutPretty(specialTopics));
	}

}
