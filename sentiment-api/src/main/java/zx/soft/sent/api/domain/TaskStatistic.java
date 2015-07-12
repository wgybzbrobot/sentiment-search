package zx.soft.sent.api.domain;

import java.io.Serializable;

/**
 * OA全网搜索任务统计数据模型
 *
 * @author wanggang
 *
 */
public class TaskStatistic implements Serializable {

	private static final long serialVersionUID = -3367601380930928682L;

	private String identify; // MD5值, id,gjc,cjsj MD5大写
	private String keywords; // 关键词组
	private String start_time; // 2015-02-02T01:34:40Z
	private String end_time; // 2015-02-02T01:34:40Z
	private String source_ids; // 多个逗号分割，如：234,2344,355
	private int local_count; // 本地库搜索结果总数：关键词，时间段：2000.1.1-创建时间
	private int autm_count; // 元搜索搜索结果总数：关键词，时间段：创建时间-结束时间/当前时间，source_ids
	private int all_count; // 总和：local_count+autm_count

	public TaskStatistic() {
		//
	}

	public TaskStatistic(String identify, String keywords, String start_time, String end_time, String source_ids) {
		super();
		this.identify = identify;
		this.keywords = keywords;
		this.start_time = start_time;
		this.end_time = end_time;
		this.source_ids = source_ids;
	}

	public TaskStatistic(String identify, String keywords, String start_time, String end_time, String source_ids,
			int local_count, int autm_count) {
		super();
		this.identify = identify;
		this.keywords = keywords;
		this.start_time = start_time;
		this.end_time = end_time;
		this.source_ids = source_ids;
		this.local_count = local_count;
		this.autm_count = autm_count;
		this.all_count = local_count + autm_count;
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

	public String getSource_ids() {
		return source_ids;
	}

	public void setSource_ids(String source_ids) {
		this.source_ids = source_ids;
	}

	public int getLocal_count() {
		return local_count;
	}

	public void setLocal_count(int local_count) {
		this.local_count = local_count;
	}

	public int getAutm_count() {
		return autm_count;
	}

	public void setAutm_count(int autm_count) {
		this.autm_count = autm_count;
	}

	public int getAll_count() {
		return all_count;
	}

	public void setAll_count(int all_count) {
		this.all_count = all_count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
