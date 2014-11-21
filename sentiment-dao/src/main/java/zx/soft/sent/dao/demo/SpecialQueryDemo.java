package zx.soft.sent.dao.demo;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.special.SpecialQuery;
import zx.soft.utils.checksum.CheckSumUtils;
import zx.soft.utils.json.JsonUtils;

/**
 * 专题信息CURD
 * 
 * @author wanggang
 *
 */
public class SpecialQueryDemo {

	public static void main(String[] args) {

		SpecialQuery specialQuery = new SpecialQuery(MybatisConfig.ServerEnum.sentiment);
		String keywords = "(合肥 OR 学校) AND 开学";
		String name = "专题名称";
		String start = "2014-08-27 00:00:00";
		String end = "2014-08-27 23:59:59";
		int hometype = 1;
		String identify = CheckSumUtils.getMD5(keywords + start + end + hometype);
		String identify2 = CheckSumUtils.getMD5(end + keywords + start + hometype);
		/**
		 * 插入专题信息
		 */
		specialQuery.insertSpecialInfo(identify, name, keywords, start, end, hometype);
		/**
		 * 查询专题信息
		 */
		System.out.println(JsonUtils.toJson(specialQuery.selectSpecialInfo(identify)));
		/**
		 * 删除专题信息
		 */
		specialQuery.deleteSpecialInfo(identify);
		/**
		 * 插入专题查询结果
		 */
		specialQuery.insertSpecialResult(identify, "pie", "solr-query-result-insert1");
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		specialQuery.insertSpecialResult(identify2, "trend", "solr-query-result-insert2");
		/**
		 * 查询专题identify，按时间查询
		 */
		System.out.println(JsonUtils.toJson(specialQuery.selectSpecialIdentifyByTime(1409291844L)));

		//
		/**
		 * 查询专题查询结果
		 */
		System.out.println(specialQuery.selectSpecialResult(identify, "pie"));
		System.out.println(specialQuery.selectSpecialResult(identify2, "trend"));
		/**
		 * 更新专题查询结果的时间，在每次查询后更新时间
		 */
		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		specialQuery.updateSpecialResultLasttime(identify);
		System.out.println(specialQuery.selectSpecialResult(identify, "pie"));
		/**
		 * 更新专题查询结果
		 */
		specialQuery.updateSpecialResult(identify, "pie", "solr-query-result-update");
		System.out.println(specialQuery.selectSpecialResult(identify, "pie"));
		/**
		 * 删除专题查询结果
		 */
		specialQuery.deleteSpecialResult(identify);
		specialQuery.deleteSpecialResult(identify2);
	}

}
