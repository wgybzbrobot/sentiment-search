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
 * 简单控制类示例
 *
 *  接口说明：
 *       1、GET: http://localhost:2900/simple/{uid}/{name}
 *
 * @author wanggang
 *
 */
@Controller
@RequestMapping("/simple/{uid}")
public class SimpleController {

	@Inject
	private SimpleService simpleService;

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String queryGenderByUid(@PathVariable long uid, @PathVariable String name) {
		return simpleService.getName(uid, name);
	}

}
