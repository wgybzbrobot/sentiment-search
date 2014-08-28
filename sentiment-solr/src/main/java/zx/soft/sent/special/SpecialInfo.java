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
	private String createTime; // 创建时间:"2014/3/24 13:12"
	private int identify; // 标识

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getIdentify() {
		return identify;
	}

	public void setIdentify(int identify) {
		this.identify = identify;
	}

}
