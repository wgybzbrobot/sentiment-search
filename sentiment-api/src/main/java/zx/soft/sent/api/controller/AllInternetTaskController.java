package zx.soft.sent.api.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sent.api.service.SimpleService;

/**
 * OA全网任务缓存查询控制类
 *
 * 接口说明：
 *       1、POST:   http://192.168.32.17:2900/sentiment/oa/tasks       返回缓存数据
 *       2、DELETE: http://192.168.32.17:2900/sentiment/oa/tasks/{identify}  不返回数据
 *
 * @author wanggang
 *
 */
@Controller
@RequestMapping("/sentiment/oa/tasks")
public class AllInternetTaskController {

	@Inject
	private SimpleService simpleService;

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String queryGenderByUid(@PathVariable long uid, @PathVariable String name) {
		return simpleService.getName(uid, name);
	}

	/**
	 * 新任务信息插入POST接口
	 */

	/**
	 * 任务缓存结果查询POST接口
	 */

	/**
	 * 任务信息及缓存结果删除接口
	 */

}
