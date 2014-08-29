package zx.soft.sent.special;

/**
 * 专题数据统计——专题信息
 * 
 * @author wanggang
 *
 */
public class SpecialInfo {

	private int specialId; // 专题ID
	private String specialName; // 专题名称

	public SpecialInfo() {
		//		
	}

	public SpecialInfo(int specialId, String specialName) {
		this.specialId = specialId;
		this.specialName = specialName;
	}

	public int getSpecialId() {
		return specialId;
	}

	public void setSpecialId(int specialId) {
		this.specialId = specialId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

}
