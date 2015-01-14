package zx.soft.sent.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.utils.json.JsonUtils;

/**
 * POST的索引数据
 * 
 * @author wanggang
 *
 */
public class PostData implements Serializable {

	private static final long serialVersionUID = 3183580989697121542L;

	private int num;
	private List<RecordInfo> records;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<RecordInfo> getRecords() {
		return records;
	}

	public void setRecords(List<RecordInfo> records) {
		this.records = records;
	}

	public static void main(String[] args) {
		//  2014-12-28 16:33:47    1419755627695
		RecordInfo recordInfo = new RecordInfo();
		recordInfo.setId("sentiment");
		recordInfo.setPlatform(10);
		recordInfo.setMid("123456789987654321");
		recordInfo.setUsername("zxsoft");
		recordInfo.setNickname("中新舆情");
		recordInfo.setOriginal_id("original_sentiment");
		recordInfo.setOriginal_uid("original_zxsoft");
		recordInfo.setOriginal_name("original_中新软件");
		recordInfo.setOriginal_title("original_标题");
		recordInfo.setOriginal_url("http://www.orignal_url.com");
		recordInfo.setUrl("http://www.url.com");
		recordInfo.setHome_url("http://www.home_url.com");
		recordInfo.setTitle("标题");
		recordInfo.setType("所属类型");
		recordInfo.setIsharmful(Boolean.TRUE);
		recordInfo.setContent("测试内容");
		recordInfo.setComment_count(10);
		recordInfo.setRead_count(20);
		recordInfo.setFavorite_count(30);
		recordInfo.setAttitude_count(40);
		recordInfo.setRepost_count(50);
		recordInfo.setVideo_url("http://www.video_url.com");
		recordInfo.setPic_url("htpp://www.pic_url.com");
		recordInfo.setVoice_url("http://www.voice_url.com");
		recordInfo.setTimestamp(1419755627695L);
		recordInfo.setSource_id(70);
		recordInfo.setLasttime(1419755627695L + 86400_000L);
		recordInfo.setServer_id(90);
		recordInfo.setIdentify_id(100);
		recordInfo.setIdentify_md5("abcdefg123456789");
		recordInfo.setKeyword("关键词");
		recordInfo.setFirst_time(1419755627695L + 86400_000L * 2);
		recordInfo.setUpdate_time(1419755627695L + 86400_000L * 3);
		recordInfo.setIp("192.168.32.45");
		recordInfo.setLocation("安徽省合肥市");
		recordInfo.setGeo("经纬度信息");
		recordInfo.setReceive_addr("receive@gmail.com");
		recordInfo.setAppend_addr("append@gmail.com");
		recordInfo.setSend_addr("send@gmail.com");
		recordInfo.setSource_name("新浪微博");
		recordInfo.setSource_type(121);
		recordInfo.setCountry_code(122);
		recordInfo.setLocation_code(123);
		recordInfo.setProvince_code(124);
		recordInfo.setCity_code(125);
		List<RecordInfo> records = new ArrayList<>();
		records.add(recordInfo);
		PostData data = new PostData();
		data.setNum(1);
		data.setRecords(records);
		System.out.println(JsonUtils.toJsonWithoutPretty(data));
	}

}
