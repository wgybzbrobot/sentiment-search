package zx.soft.sent.dao.demo;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPage;

public class FirstPageDemo {

	public static void main(String[] args) {

		FirstPage firstPage = new FirstPage(MybatisConfig.ServerEnum.sentiment);
		int type = 4;
		String timestr = "2012-01-07,05";
		String result = "first page result";
		/**
		 * 插入OA首页查询数据
		 */
		firstPage.insertFirstPage(type, timestr, result);
		/**
		 * 查询OA首页查询数据
		 */
		System.out.println(firstPage.selectFirstPage(type, timestr));
		/**
		 * 更新OA首页查询数据
		 */
		firstPage.updateFirstPage(type, timestr, "frist page result update");
		System.out.println(firstPage.selectFirstPage(type, timestr));
		/**
		 * 删除OA首页查询数据
		 */
		firstPage.deleteFirstPage(type, timestr);

	}

}
