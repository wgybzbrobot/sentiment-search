package zx.soft.sent.dao.demo;

import java.util.Date;

import zx.soft.sent.dao.allinternet.AllInternet;
import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.allinternet.InternetTask;
import zx.soft.utils.json.JsonUtils;

public class AllInternetDemo {

	public static void main(String[] args) {

		AllInternet allInternet = new AllInternet(MybatisConfig.ServerEnum.sentiment);
		// 测试数据
		InternetTask task1 = new InternetTask("absdjdff", "测试数据1", "2015-02-01T01:34:40Z", "2015-02-02T01:34:40Z",
				"123,456,789");
		InternetTask task2 = new InternetTask("jiioiyyf", "测试数据2", "2015-01-01T01:34:40Z", "2015-01-02T01:34:40Z",
				"548,6694,6685,5588");
		InternetTask task3 = new InternetTask("swffeeef", "测试数据3", "2015-04-01T01:34:40Z", "2015-04-02T01:34:40Z",
				"6685,5588");
		/**
		 * 任务信息插入
		 */
		allInternet.insertInternetTask(task1);
		allInternet.insertInternetTask(task2);
		allInternet.insertInternetTask(task3);
		/**
		 * 任务信息查询：根据identify
		 */
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTask("absdjdff")));
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTask("jiioiyyf")));
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTask("swffeeef")));
		/**
		 * 任务信息查询：查询未完成任务，并按照最后更新时间降序
		 */
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTasks(new Date(
				System.currentTimeMillis() - 30 * 86400_000L))));
		/**
		 * 任务信息中isover字段更新
		 */
		allInternet.updateInternetTaskIsOver("absdjdff", 1);
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTasks(new Date(
				System.currentTimeMillis() - 100 * 86400_000L))));
		/**
		 * 任务信息最近更新时间字段更新
		 */
		allInternet.updateInternetTaskLasttime("jiioiyyf");
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTasks(new Date(
				System.currentTimeMillis() - 100 * 86400_000L))));
		/**
		 * 任务信息删除
		 */
		allInternet.deleteInternetTask("absdjdff");
		allInternet.deleteInternetTask("jiioiyyf");
		allInternet.deleteInternetTask("swffeeef");
		/**
		 * 任务查询结果插入
		 */
		allInternet.insertInternetTaskQuery("absdjdff", "测试数据1的查询结果");
		/**
		 * 任务查询结果查询
		 */
		System.out.println(allInternet.selectInternetTaskQuery("absdjdff"));
		/**
		 * 任务查询结果更新
		 */
		allInternet.updateInternetTaskQuery("absdjdff", "测试数据1的查询结果更新");
		System.out.println(allInternet.selectInternetTaskQuery("absdjdff"));
		/**
		 * 任务查询结果删除
		 */
		allInternet.deleteInternetTaskQuery("absdjdff");
	}

}
