package zx.soft.sent.api.controller;

import java.util.HashMap;
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

import zx.soft.sent.api.domain.TaskStatisticResult;
import zx.soft.sent.api.service.TaskService;

/**
 * OA全网任务缓存查询控制类
 *
 * 接口说明：
 *       1、POST:   http://192.168.32.17:2900/sentiment/oa/tasks            返回缓存数据
 *         ["CE9402701D796B22E15D433EB17FA4FE","4E4E7BF57E3B57B9718549646D364964","76AC38F439C31812D71251EE024B316A"]
 *       2、DELETE: http://192.168.32.17:2900/sentiment/oa/tasks/{identify}  不返回数据
 *
 * @author wanggang
 *
 */
@Controller
@RequestMapping("/sentiment/oa")
public class TaskQueryController {

	@Inject
	private TaskService taskService;

	/**
	 * 根据多个identify查询缓存结果
	 */
	@RequestMapping(value = "/tasks", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody HashMap<String, TaskStatisticResult> queryGenderByUid(@RequestBody List<String> identifys) {
		return taskService.getQueryResults(identifys);
	}

	/**
	 * 删除单个任务缓存结果
	 */
	@RequestMapping(value = "/tasks/{identify}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String identify) {
		taskService.deleteQueryResult(identify);
	}

}
