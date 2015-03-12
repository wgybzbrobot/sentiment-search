package zx.soft.sent.api.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sent.api.service.OATasksService;
import zx.soft.sent.dao.domain.allinternet.TaskResult;

/**
 * OA全网任务缓存查询控制类
 *
 * 接口说明：
 *       1、POST:   http://192.168.32.17:2900/sentiment/oa/tasks            返回缓存数据
 *         ["671CC22D8FB5669A848B3E9CBE006D1F","1B5E770CA5944C1F4804AB8FA1F815D3","D68152D6CA0061E114205B76C52510FD"]
 *       2、DELETE: http://192.168.32.17:2900/sentiment/oa/tasks/{identify}  不返回数据
 *
 * @author wanggang
 *
 */
@Controller
@RequestMapping("/sentiment/oa")
public class OATasksController {

	@Inject
	private OATasksService oATasksService;

	/**
	 * 根据多个identify查询缓存结果
	 */
	@RequestMapping(value = "/tasks", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TaskResult> queryGenderByUid(@RequestBody List<String> identifys) {
		return oATasksService.getQueryResults(identifys);
	}

	/**
	 * 删除单个任务缓存结果
	 */
	@RequestMapping(value = "/tasks/{identify}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String identify) {
		oATasksService.deleteQueryResult(identify);
	}

}
