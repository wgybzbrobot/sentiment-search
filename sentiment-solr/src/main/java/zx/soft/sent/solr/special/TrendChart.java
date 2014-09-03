package zx.soft.sent.solr.special;

import java.util.HashMap;

import zx.soft.sent.utils.json.JsonUtils;

/**
 * 专题数据统计——趋势图
 * 
 * @author wanggang
 *
 */
public class TrendChart {

	/*
	 * 专题信息
	 */
	private SpecialInfo specialInfo;

	/*
	 * 每天对应的数据量
	 */
	private HashMap<String, Long> countByDay = new HashMap<>();

	public SpecialInfo getSpecialInfo() {
		return specialInfo;
	}

	public void setSpecialInfo(SpecialInfo specialInfo) {
		this.specialInfo = specialInfo;
	}

	public HashMap<String, Long> getCountByDay() {
		return countByDay;
	}

	public void setCountByDay(HashMap<String, Long> countByDay) {
		this.countByDay = countByDay;
	}

	public static void main(String[] args) {
		TrendChart trandChart = new TrendChart();
		trandChart.setSpecialInfo(new SpecialInfo("addfeff", "合肥测试"));
		trandChart.getCountByDay().put("2014-04-10", 1292728L);
		trandChart.getCountByDay().put("2014-04-11", 1512505L);
		trandChart.getCountByDay().put("2014-04-12", 80221L);
		trandChart.getCountByDay().put("2014-04-13", 1430429L);
		System.out.println(JsonUtils.toJson(trandChart));
	}
}
