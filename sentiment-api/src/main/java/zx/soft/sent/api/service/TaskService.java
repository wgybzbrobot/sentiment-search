package zx.soft.sent.api.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sent.api.dao.TaskMapper;
import zx.soft.sent.api.domain.TaskResult;

/**
 * OA全网任务缓存查询服务类
 *
 * @author wanggang
 *
 */
@Service
public class TaskService {

	@Inject
	private TaskMapper taskMapper;

	/**
	 * 根据多个identify查询缓存结果
	 */
	public HashMap<String, TaskResult> getQueryResults(List<String> identifys) {
		HashMap<String, TaskResult> result = new HashMap<>();
		for (String identify : identifys) {
			result.put(identify, getQueryResult(identify));
		}
		return result;
	}

	/**
	 * 根据单个identify查询缓存结果
	 */
	private TaskResult getQueryResult(String identify) {
		TaskResult result = taskMapper.selectInternetTask(identify);
		// 更新查询时间
		taskMapper.updateInternetTaskQuerytime(identify);
		return result;
	}

	/**
	 * 删除单个任务缓存结果
	 */
	public void deleteQueryResult(String identify) {
		taskMapper.deleteInternetTask(identify);
	}

}
