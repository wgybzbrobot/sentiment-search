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

import zx.soft.sent.api.domain.Task;
import zx.soft.sent.api.service.TaskService;

/**
 * 全网任务控制类
 *
 * 接口说明：
 *       1、POST:   http://localhost:8888/tasks              List<Task>
 *       2、DELETE: http://localhost:8888/tasks/{identifys}
 *       3、GET：   http://localhost:8888/tasks/{identifys}
 *       4、PUT：   http://localhost:8888/tasks              List<Task>
 *
 * @author wanggang
 *
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Inject
	private TaskService taskService;

	/**
	 * 多个任务添加
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addTasks(@RequestBody List<Task> tasks) {
		taskService.insertTasks(tasks);
	}

	/**
	 * 多个任务删除
	 */
	@RequestMapping(value = "/{identifys}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void removeTasks(@PathVariable String identifys) {
		taskService.deleteTasks(identifys);
	}

	/**
	 * 多个任务查询
	 */
	@RequestMapping(value = "/{identifys}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Task> retriveTasks(@PathVariable String identifys) {
		return taskService.selectTasks(identifys);
	}

	/**
	 * 多个任务更新
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void updateTasks(@RequestBody List<Task> tasks) {
		taskService.updateTasks(tasks);
	}

}
