package zx.soft.sent.special;

import java.util.HashMap;

/**
 * 专题数据统计——趋势图
 * 
 * @author wanggang
 *
 */
public class TrandChart {

	/*
	 * 专题信息
	 */
	private SpecialInfo specialInfo;

	/*
	 * 每天对应的数据量
	 */
	private HashMap<String, Integer> countByDay = new HashMap<>();

	public SpecialInfo getSpecialInfo() {
		return specialInfo;
	}

	public void setSpecialInfo(SpecialInfo specialInfo) {
		this.specialInfo = specialInfo;
	}

	public HashMap<String, Integer> getCountByDay() {
		return countByDay;
	}

	public void setCountByDay(HashMap<String, Integer> countByDay) {
		this.countByDay = countByDay;
	}

}
