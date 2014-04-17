package zx.soft.spider.dao.sina;

import java.util.List;

import zx.soft.spider.dao.domain.WeiboOldInfo;
import zx.soft.spider.dao.domain.WeiboSinaParams;

/**
 * 新浪微薄数据接口
 * @author wanggang
 *
 */
public interface WeiboSinaDao {

	/**
	 * 获取最大Id
	 */
	public int getMaxId(WeiboSinaParams weiboSinaParams);

	/**
	 * 获取所有数据表名
	 */
	public List<String> getAllTablenames();

	/*
	 * 旧数据表
	 */

	/**
	 * 获取新浪微博数据，批量
	 */
	public List<WeiboOldInfo> getBatchWeibos(WeiboSinaParams weiboSinaParams);

	/**
	 * 插入新浪微博数据
	 */

	/**
	 * 删除新浪微博数据
	 */

	/*
	 * 新数据表
	 */

}
