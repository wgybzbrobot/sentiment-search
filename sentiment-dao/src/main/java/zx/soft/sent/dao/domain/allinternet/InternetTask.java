package zx.soft.sent.dao.domain.allinternet;

import java.io.Serializable;

/**
 * OA全网搜索任务数据模型
 *
 * @author wanggang
 *
 */
public class InternetTask implements Serializable {

	private static final long serialVersionUID = -3367601380930928682L;

	private String identify; // MD5值
	private String keywords; // 关键词组
	private String start_time; // 2015-02-02T01:34:40Z
	private String end_time; // 2015-02-02T01:34:40Z
	private String source_id; // 多个逗号分割，如：234,2344,355

	public InternetTask() {
		//
	}

	public InternetTask(String identify, String keywords, String start_time, String end_time, String source_id) {
		this.identify = identify;
		this.keywords = keywords;
		this.start_time = start_time;
		this.end_time = end_time;
		this.source_id = source_id;
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

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getSource_id() {
		return source_id;
	}

	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}

}
