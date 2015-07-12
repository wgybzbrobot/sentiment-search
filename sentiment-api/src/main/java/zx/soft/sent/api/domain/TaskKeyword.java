package zx.soft.sent.api.domain;

import java.io.Serializable;

/**
 * 全网搜索任务自动分发：关键词
 *
 * @author wanggang
 *
 */
public class TaskKeyword implements Serializable {

	private static final long serialVersionUID = -9088381949700807201L;

	// 关键词MD5
	private String keyword_id;
	// 一级分类类型
	private int first_type;
	// 二级分类类型
	private int second_type;
	// 三级分类类型
	private int third_type;
	// 关键词名称
	private String keyword_name;

	public TaskKeyword() {
		super();
	}

	public TaskKeyword(Builder builder) {
		this.keyword_id = builder.keyword_id;
		this.first_type = builder.first_type;
		this.second_type = builder.second_type;
		this.third_type = builder.third_type;
		this.keyword_name = builder.keyword_name;
	}

	public static class Builder {

		private String keyword_id;
		private int first_type;
		private int second_type;
		private int third_type;
		private String keyword_name;

		public Builder(String keyword_id, int first_type, int second_type, int third_type) {
			this.keyword_id = keyword_id;
			this.first_type = first_type;
			this.second_type = second_type;
			this.third_type = third_type;
		}

		public Builder setThird_type(int third_type) {
			this.third_type = third_type;
			return this;
		}

		public TaskKeyword build() {
			return new TaskKeyword(this);
		}

	}

	@Override
	public String toString() {
		return "TaskKeyword [keyword_id=" + keyword_id + ", first_type=" + first_type + ", second_type=" + second_type
				+ ", third_type=" + third_type + ", keyword_name=" + keyword_name + "]";
	}

	public String getKeyword_id() {
		return keyword_id;
	}

	public void setKeyword_id(String keyword_id) {
		this.keyword_id = keyword_id;
	}

	public int getFirst_type() {
		return first_type;
	}

	public void setFirst_type(int first_type) {
		this.first_type = first_type;
	}

	public int getSecond_type() {
		return second_type;
	}

	public void setSecond_type(int second_type) {
		this.second_type = second_type;
	}

	public int getThird_type() {
		return third_type;
	}

	public void setThird_type(int third_type) {
		this.third_type = third_type;
	}

	public String getKeyword_name() {
		return keyword_name;
	}

	public void setKeyword_name(String keyword_name) {
		this.keyword_name = keyword_name;
	}

}
