package zx.soft.sent.dao.domain.special;

/**
 * 插入的专题参数数据
 * 
 * @author wanggang
 *
 */

public class InsertSpecialInfo {

	private String identify; // 专题唯一标识
	private String name; // 专题名称
	private String keywords;
	private String start;
	private String end;
	private int hometype;

	public InsertSpecialInfo() {
		//		
	}

	public InsertSpecialInfo(String identify, String name, String keywords, String start, String end, int hometype) {
		this.name = name;
		this.identify = identify;
		this.keywords = keywords;
		this.start = start;
		this.end = end;
		this.hometype = hometype;
	}

	@Override
	public String toString() {
		return "InsertSpecialInfo:[identify=" + identify + ",name=" + name + ",keywords=" + keywords + ",start="
				+ start + ",end=" + end + ",hometype=" + hometype + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getHometype() {
		return hometype;
	}

	public void setHometype(int hometype) {
		this.hometype = hometype;
	}

}
