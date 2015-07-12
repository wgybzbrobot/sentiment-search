package zx.soft.sent.api.domain;

import java.io.Serializable;

/**
 * OA全网搜索任务缓存结果数据
 *
 * @author wanggang
 *
 */
public class TaskStatisticResult implements Serializable {

	private static final long serialVersionUID = 4224722992054268127L;

	private int local_count; // 本地库搜索结果总数：关键词，时间段：2000.1.1-创建时间
	private int autm_count; // 元搜索搜索结果总数：关键词，时间段：创建时间-结束时间/当前时间，source_ids
	private int all_count; // 总和：local_count+autm_count

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
