package zx.soft.sent.api.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sent.api.apply.OATasksAccess;
import zx.soft.sent.dao.domain.allinternet.TaskResult;

/**
 * OA全网任务缓存查询服务类
 *
 * @author wanggang
 *
 */
@Service
public class OATasksService {

	@Inject
	private OATasksAccess oATaskAccess;

	/**
	 * 根据多个identify查询缓存结果
	 */
	public List<TaskResult> getQueryResults(List<String> identifys) {
		return oATaskAccess.getQueryResults(identifys);
	}

	/**
	 * 删除单个任务缓存结果
	 */
	public void deleteQueryResult(String identify) {
		oATaskAccess.deleteQueryResult(identify);
	}

}
