package zx.soft.sent.special;

/**
 * 专题数据统计——专题信息
 * 
 * @author wanggang
 *
 */
public class SpecialInfo {

	private String identify; // 专题ID
	private String specialName; // 专题名称

	public SpecialInfo() {
		//		
	}

	public SpecialInfo(String identify, String specialName) {
		this.identify = identify;
		this.specialName = specialName;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

}
