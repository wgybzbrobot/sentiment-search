package zx.soft.sent.api.domain;

import java.io.Serializable;

/**
 * 任务信息
 *
 * @author wanggang
 *
 */
public class Task implements Serializable {

	private static final long serialVersionUID = -1459309687204968370L;

	// 任务唯一标识
	private String identify;
	// 任务的关键词
	private String keywords;
	// 任务的站点名称
	private String source_ids;
	// 任务的开始时间
	private String start_time;
	// 任务的结束时间
	private String end_time;

	public Task() {
		super();
	}

	public Task(Builder builder) {
		this.identify = builder.identify;
		this.keywords = builder.keywords;
		this.source_ids = builder.source_ids;
		this.start_time = builder.start_time;
		this.end_time = builder.end_time;
	}

	@Override
	public String toString() {
		return "Task [identify=" + identify + ", keywords=" + keywords + ", source_ids=" + source_ids + ", start_time="
				+ start_time + ", end_time=" + end_time + "]";
	}

	public static class Builder {

		private String identify;
		private String keywords;
		private String source_ids;
		private String start_time;
		private String end_time;

		public Builder(String identify, String keywords, String source_ids) {
			super();
			this.identify = identify;
			this.keywords = keywords;
			this.source_ids = source_ids;
		}

		public Builder setStart_time(String start_time) {
			this.start_time = start_time;
			return this;
		}

		public Builder setEnd_time(String end_time) {
			this.end_time = end_time;
			return this;
		}

		public Task build() {
			return new Task(this);
		}

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

}
