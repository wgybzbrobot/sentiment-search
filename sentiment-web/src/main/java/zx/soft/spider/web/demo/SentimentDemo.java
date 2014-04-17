package zx.soft.spider.web.demo;

import java.util.ArrayList;
import java.util.List;

import zx.soft.spider.solr.domain.RecordInfo;
import zx.soft.spider.solr.utils.JsonUtils;
import zx.soft.spider.web.domain.PostData;

public class SentimentDemo {

	public static void main(String[] args) {

		PostData data = new PostData();
		data.setNum(10);
		List<RecordInfo> records = new ArrayList<>();
		RecordInfo r1 = new RecordInfo();
		r1.setId("123");
		r1.setNickname("\"第一财经\"<service@interactive.yicai.com>");
		r1.setPlatform(2);
		r1.setTimestamp(System.currentTimeMillis() / 1000);
		records.add(r1);
		RecordInfo r2 = new RecordInfo();
		r2.setId("456");
		r2.setPlatform(4);
		r2.setTimestamp(System.currentTimeMillis() / 1000 - 86400);
		records.add(r2);
		data.setRecords(records);
		System.out.println(JsonUtils.toJsonWithoutPretty(data));

	}

}
