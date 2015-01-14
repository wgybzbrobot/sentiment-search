package zx.soft.sent.web.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 全网搜索任务请求参数刘表
 * 
 * @author wanggang
 *
 */
public class InternetTask implements Serializable {

	private static final long serialVersionUID = 5887938203759551452L;

	// 任务数
	private int num;
	// 任务列表
	private List<Task> tasks;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	static class Task {

		// 关键词
		private String keywords;
		// 监测开始时间
		private String start;
		// 监测结束时间
		private String end;
		// 站点名称
		private String source_name;

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

		public String getSource_name() {
			return source_name;
		}

		public void setSource_name(String source_name) {
			this.source_name = source_name;
		}

	}

}
