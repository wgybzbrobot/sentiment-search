package zx.soft.sent.api.domain;

import java.io.Serializable;

/**
 * 全网搜索任务自动分发：关键词-类型
 *
 * @author wanggang
 *
 */
public class KeywordType implements Serializable {

	private static final long serialVersionUID = 5823492889404182032L;

	// 一级分类类型
	private int first_type;
	// 一级分类名称
	private String first_type_name;
	// 二级分类类型
	private int second_type;
	// 二级分类名称
	private String second_type_name;
	// 三级分类类型
	private int third_type;
	// 三级分类名称
	private String third_type_name;

	public KeywordType() {
		super();
	}

	public KeywordType(Builder builder) {
		this.first_type = builder.first_type;
		this.first_type_name = builder.first_type_name;
		this.second_type = builder.second_type;
		this.second_type_name = builder.second_type_name;
		this.third_type = builder.third_type;
		this.third_type_name = builder.third_type_name;
	}

	public static class Builder {

		private int first_type;
		private String first_type_name;
		private int second_type;
		private String second_type_name;
		private int third_type;
		private String third_type_name;

		public Builder(int first_type, String first_type_name) {
			this.first_type = first_type;
			this.first_type_name = first_type_name;
		}

		public Builder setSecond_type(int second_type) {
			this.second_type = second_type;
			return this;
		}

		public Builder setSecond_type_name(String second_type_name) {
			this.second_type_name = second_type_name;
			return this;
		}

		public Builder setThird_type(int third_type) {
			this.third_type = third_type;
			return this;
		}

		public Builder setThird_type_name(String third_type_name) {
			this.third_type_name = third_type_name;
			return this;
		}

		public KeywordType build() {
			return new KeywordType(this);
		}

	}

	@Override
	public String toString() {
		return "KeywordType [first_type=" + first_type + ", first_type_name=" + first_type_name + ", second_type="
				+ second_type + ", second_type_name=" + second_type_name + ", third_type=" + third_type
				+ ", third_type_name=" + third_type_name + "]";
	}

	public int getFirst_type() {
		return first_type;
	}

	public void setFirst_type(int first_type) {
		this.first_type = first_type;
	}

	public String getFirst_type_name() {
		return first_type_name;
	}

	public void setFirst_type_name(String first_type_name) {
		this.first_type_name = first_type_name;
	}

	public int getSecond_type() {
		return second_type;
	}

	public void setSecond_type(int second_type) {
		this.second_type = second_type;
	}

	public String getSecond_type_name() {
		return second_type_name;
	}

	public void setSecond_type_name(String second_type_name) {
		this.second_type_name = second_type_name;
	}

	public int getThird_type() {
		return third_type;
	}

	public void setThird_type(int third_type) {
		this.third_type = third_type;
	}

	public String getThird_type_name() {
		return third_type_name;
	}

	public void setThird_type_name(String third_type_name) {
		this.third_type_name = third_type_name;
	}

}
