package zx.soft.sent.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sent.api.dao.TaskMapper;
import zx.soft.sent.api.dao.TaskQueryMapper;
import zx.soft.sent.api.domain.Task;
import zx.soft.sent.api.domain.TaskStatisticResult;

/**
 * OA全网任务相关服务类
 *
 * @author wanggang
 *
 */
@Service
public class TaskService {

	@Inject
	private TaskMapper taskMapper;

	@Inject
	private TaskQueryMapper taskQueryMapper;

	/**
	 * 插入任务
	 */
	public void insertTasks(List<Task> tasks) {
		for (Task task : tasks) {
			insertTask(task);
		}
	}

	private void insertTask(Task task) {
		taskMapper.insertTask(task);
	}

	/**
	 * 删除任务
	 */
	public void deleteTasks(String identifys) {
		for (String identify : identifys.split(",")) {
			deleteTask(identify);
		}
	}

	private void deleteTask(String identify) {
		taskMapper.deleteTask(identify);
	}

	/**
	 * 查询任务
	 */
	public List<Task> selectTasks(String identifys) {
		List<Task> tasks = new ArrayList<>();
		Task tmp = null;
		for (String identify : identifys.split(",")) {
			tmp = selectTask(identify);
			if (tmp != null) {
				tasks.add(tmp);
			}
		}
		return tasks;
	}

	private Task selectTask(String identify) {
		return taskMapper.selectTask(identify);
	}

	/**
	 * 修改任务
	 */
	public void updateTasks(List<Task> tasks) {
		for (Task task : tasks) {
			updateTask(task);
		}
	}

	private void updateTask(Task task) {
		taskMapper.updateTask(task);
	}

	/**
	 * 根据多个identify查询缓存结果
	 */
	public HashMap<String, TaskStatisticResult> getQueryResults(List<String> identifys) {
		HashMap<String, TaskStatisticResult> result = new HashMap<>();
		for (String identify : identifys) {
			result.put(identify, getQueryResult(identify));
		}
		return result;
	}

	/**
	 * 根据单个identify查询缓存结果
	 */
	private TaskStatisticResult getQueryResult(String identify) {
		TaskStatisticResult result = taskQueryMapper.selectInternetTask(identify);
		// 更新查询时间
		taskQueryMapper.updateInternetTaskQuerytime(identify);
		return result;
	}

	/**
	 * 删除单个任务缓存结果
	 */
	public void deleteQueryResult(String identify) {
		taskQueryMapper.deleteInternetTask(identify);
	}

}
