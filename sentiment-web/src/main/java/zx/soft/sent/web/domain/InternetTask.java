package zx.soft.sent.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zx.soft.utils.json.JsonUtils;

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

	public static void main(String[] args) {
		InternetTask internetTask = new InternetTask();
		List<Task> tasks = new ArrayList<>();
		Task task1 = new Task();
		task1.setKeywords("穿越火线");
		task1.setStart("2014-12-25T15:09:01Z");
		task1.setEnd("2014-12-26T15:09:01Z");
		task1.setSource_name("腾讯微博");
		tasks.add(task1);
		Task task2 = new Task();
		task2.setKeywords("圣诞节");
		task2.setStart("2014-12-25T15:09:01Z");
		task2.setEnd("2014-12-26T15:09:01Z");
		task2.setSource_name("新浪微博");
		tasks.add(task2);
		internetTask.setNum(2);
		internetTask.setTasks(tasks);
		System.out.println(JsonUtils.toJsonWithoutPretty(internetTask));
	}

}
