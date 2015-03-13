package zx.soft.sent.api.apply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zx.soft.sent.dao.allinternet.AllInternet;
import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.allinternet.TaskResult;
import zx.soft.utils.json.JsonUtils;

public class OATasksAccess {

	private AllInternet allInternet;

	public OATasksAccess() {
		this.allInternet = new AllInternet(MybatisConfig.ServerEnum.sentiment);
	}

	public static void main(String[] args) {
		List<String> identifys = new ArrayList<>();
		identifys.add("4E4E7BF57E3B57B9718549646D364964");
		identifys.add("CE9402701D796B22E15D433EB17FA4FE");
		identifys.add("76AC38F439C31812D71251EE024B316A");
		identifys.add("76AC38F439C31812D71251EE024B316B");
		OATasksAccess access = new OATasksAccess();
		HashMap<String, TaskResult> result = access.getQueryResults(identifys);
		System.out.println(JsonUtils.toJson(result));
	}

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
		TaskResult result = allInternet.selectInternetTask(identify);
		// 更新查询时间
		allInternet.updateInternetTaskQuerytime(identify);
		return result;
	}

	/**
	 * 删除单个任务缓存结果
	 */
	public void deleteQueryResult(String identify) {
		allInternet.deleteInternetTask(identify);
	}

}
