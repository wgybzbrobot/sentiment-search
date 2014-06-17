package zx.soft.sent.dao.sentiment;

import java.util.List;

import zx.soft.sent.dao.domain.InsertCacheQuery;
import zx.soft.sent.dao.domain.RecordInsert;
import zx.soft.sent.dao.domain.RecordSelect;
import zx.soft.sent.dao.domain.SelectParamsById;
import zx.soft.sent.dao.domain.SelectParamsByTime;
import zx.soft.sent.dao.domain.SentTablename;

/**
 * 舆情数据接口
 * 
 * @author wanggang
 *
 */
public interface SentimentRecordDao {

	/**
	 * 获取最大Id
	 */
	public int getMaxId(SentTablename sentTablename);

	/**
	 * 插入数据表名
	 */
	public void insertTablename(SentTablename sentTablename);

	/**
	 * 插入Record数据
	 */
	public void insertRecord(RecordInsert recordInsert);

	/**
	 * 获取Record数据，根据md5的id
	 */
	public RecordSelect selectRecordById(SelectParamsById selectParamsById);

	/**
	 * 获取Records数据，根据lasttime
	 */
	public List<RecordSelect> selectRecordsByLasttime(SelectParamsByTime selectParamsByTime);

	/**
	 * 删除Record数据，根据md5的id
	 */
	public void deleteRecordById(SelectParamsById selectParamsById);

	/**
	 * 插入查询缓存数据
	 */
	public void insertCacheQuery(InsertCacheQuery insertCacheQuery);

	/**
	 * 更新查询缓存数据
	 */
	public void updateCacheQuery(InsertCacheQuery insertCacheQuery);

	/**
	 * 查询查询缓存数据
	 */
	public String selectCacheQuery(InsertCacheQuery insertCacheQuery);

	/**
	 * 删除查询缓存数据
	 */
	public void deleteCacheQuery(InsertCacheQuery insertCacheQuery);

}
