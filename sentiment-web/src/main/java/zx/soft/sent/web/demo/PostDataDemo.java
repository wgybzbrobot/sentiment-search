package zx.soft.sent.web.demo;

import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.dao.domain.RecordInfo;
import zx.soft.sent.utils.json.JsonUtils;
import zx.soft.sent.web.domain.PostData;

public class PostDataDemo {

	public static void main(String[] args) {

		PostData postData = new PostData();
		postData.setNum(2);
		List<RecordInfo> records = new ArrayList<>();
		RecordInfo recordInfo1 = new RecordInfo();
		recordInfo1.setId("shdbbdhfo12343jjf");
		recordInfo1.setPlatform(4);
		recordInfo1.setUsername("1010432115");
		recordInfo1.setNickname("永不止步");
		RecordInfo recordInfo2 = new RecordInfo();
		recordInfo2.setId("2256jjfft896699");
		recordInfo2.setPlatform(4);
		recordInfo2.setUsername("wgybzb");
		recordInfo2.setNickname("行云流水");
		records.add(recordInfo1);
		records.add(recordInfo2);
		postData.setRecords(records);

		System.out.println(JsonUtils.toJson(postData));

	}

}
