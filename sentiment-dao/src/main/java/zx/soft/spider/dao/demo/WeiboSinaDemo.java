package zx.soft.spider.dao.demo;

import zx.soft.spider.dao.common.MybatisConfig;
import zx.soft.spider.dao.sina.WeiboSina;

public class WeiboSinaDemo {

	public static void main(String[] args) {

		WeiboSina weiboSina = new WeiboSina(MybatisConfig.ServerEnum.local);
		System.out.println(weiboSina.getMaxId("sina_user_weibos_1386980126"));
		System.out.println(weiboSina.getAllTablenames().size());
		System.out.println(weiboSina.getBatchWeibos("sina_user_weibos_1386980126", 0, 10).size());

	}

}
