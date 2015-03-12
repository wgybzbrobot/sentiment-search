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
				"123,456,789", 100, 200);
		InternetTask task2 = new InternetTask("jiioiyyf", "测试数据2", "2015-01-01T01:34:40Z", "2015-01-02T01:34:40Z",
				"548,6694,6685,5588", 200, 300);
		/**
		 * 插入任务的缓存信息
		 */
		allInternet.insertInternetTask(task1);
		allInternet.insertInternetTask(task2);
		/**
		 * 查询任务的缓存信息
		 */
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTask("absdjdff")));
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTask("jiioiyyf")));
		/**
		 * 判断任务是否存在
		 */
		System.out.println(allInternet.isInternetTaskExisted("absdjdff"));
		System.out.println(allInternet.isInternetTaskExisted("absdjdfff"));
		/**
		 * 更新任务的缓存信息
		 */
		InternetTask task3 = new InternetTask("absdjdff", "测试数据1", "2015-02-01T01:34:40Z", "2015-02-02T01:34:40Z",
				"123,456,789", 110, 210);
		allInternet.updateInternetTask(task3);
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTask("absdjdff")));
		/**
		 * 更新任务的最新查询时间
		 */
		allInternet.updateInternetTaskQuerytime("absdjdff");
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTask("absdjdff")));
		/**
		 * 查询固定时间内的所有任务identify
		 */
		System.out.println(JsonUtils.toJson(allInternet.selectInternetTaskIdentifys(new Date(
				System.currentTimeMillis() - 10 * 60_000L))));
		/**
		 * 删除任务的缓存信息
		 */
		allInternet.deleteInternetTask("absdjdff");
		allInternet.deleteInternetTask("jiioiyyf");
	}

}
