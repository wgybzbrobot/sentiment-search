package zx.soft.sent.api.apply;

import java.util.ArrayList;
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
		identifys.add("8FAF811490276F53F9134DEA99D131EA");
		identifys.add("671CC22D8FB5669A848B3E9CBE006D1F");
		identifys.add("C714698D31BADD2921403693093E095A");
		identifys.add("1B5E770CA5944C1F4804AB8FA1F815D3");
		identifys.add("D68152D6CA0061E114205B76C52510FD");
		System.out.println(JsonUtils.toJsonWithoutPretty(identifys));
		OATasksAccess access = new OATasksAccess();
		List<TaskResult> result = access.getQueryResults(identifys);
		System.out.println(JsonUtils.toJson(result));
		access.deleteQueryResult("8FAF811490276F53F9134DEA99D131EA");
	}

	/**
	 * 根据多个identify查询缓存结果
	 */
	public List<TaskResult> getQueryResults(List<String> identifys) {
		List<TaskResult> result = new ArrayList<>();
		TaskResult r = null;
		for (String identify : identifys) {
			r = getQueryResult(identify);
			if (r != null) {
				result.add(r);
			}
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
