package zx.soft.sent.web.domain;

/**
 * 专题模块的POST数据字段
 * 
 * @author wanggang
 *
 */
public class SpecialTopic {

	private String identify;
	private String name;
	private String keywords; // （合肥 and 警察） not 打人
	private String start; // '2014-08-25 00:00:00'
	private String end; // '2014-08-25 23:59:59'
	private int hometype; // 0代表全部 1代表境内 2代表境外

	public SpecialTopic() {
		//
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
