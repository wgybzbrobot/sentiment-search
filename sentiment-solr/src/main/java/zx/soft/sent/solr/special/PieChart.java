package zx.soft.sent.solr.special;

import java.util.HashMap;

import zx.soft.sent.utils.json.JsonUtils;

/**
 * 與请专题统计数据——饼状图
 * 
 * @author wanggang
 *
 */
public class PieChart {

	/*
	 * 专题信息
	 */
	private SpecialInfo specialInfo;

	/*
	 * 各类数据统计:
	 * 0-其他,1-资讯类,2-论坛类,3-微博类,4-博客类,5-QQ群类,
	 * 6-元搜索类,7-回复信息（其他），8-邮件类,9-图片识别类
	 */
	private HashMap<String, Long> platformCount = new HashMap<>();

	public SpecialInfo getSpecialInfo() {
		return specialInfo;
	}

	public void setSpecialInfo(SpecialInfo specialInfo) {
		this.specialInfo = specialInfo;
	}

	public HashMap<String, Long> getPlatformCount() {
		return platformCount;
	}

	public void setPlatformCount(HashMap<String, Long> platformCount) {
		this.platformCount = platformCount;
	}

	public static void main(String[] args) {
		PieChart pieChart = new PieChart();
		pieChart.setSpecialInfo(new SpecialInfo("405", "冰桶挑战"));
		pieChart.getPlatformCount().put("0", 3200L);
		pieChart.getPlatformCount().put("1", 12265L);
		pieChart.getPlatformCount().put("2", 568L);
		pieChart.getPlatformCount().put("3", 3866L);
		pieChart.getPlatformCount().put("4", 1235L);
		pieChart.getPlatformCount().put("5", 86L);
		pieChart.getPlatformCount().put("6", 793L);
		pieChart.getPlatformCount().put("7", 17889L);
		pieChart.getPlatformCount().put("8", 3556L);
		pieChart.getPlatformCount().put("9", 30L);
		System.out.println(JsonUtils.toJsonWithoutPretty(pieChart));
	}

}
