package zx.soft.sent.api.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zx.soft.sent.api.domain.TaskStatistic;
import zx.soft.sent.api.domain.TaskStatisticResult;

/**
 * OA全网搜索任务缓存查询结果接口
 *
 * @author wanggang
 *
 */
public interface TaskQueryMapper {

	/**
	 * 插入任务的缓存信息
	 */
	@Insert("INSERT INTO `oa_all_internet_task_query` (`identify`,`keywords`,`start_time`,`end_time`,`source_ids`,"
			+ "`local_count`,`autm_count`,`all_count`,`query_time`,`lasttime`) VALUES (#{identify},#{keywords},#{start_time},"
			+ "#{end_time},#{source_ids},#{local_count},#{autm_count},#{all_count},NOW(),NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void insertInternetTask(TaskStatistic internetTask);

	/**
	 * 查询任务的缓存信息
	 */
	@Select("SELECT `local_count`,`autm_count`,`all_count` FROM `oa_all_internet_task_query` WHERE `identify` = #{identify}")
	public TaskStatisticResult selectInternetTask(String identify);

	/**
	 * 查询任务的identify
	 */
	@Select("SELECT `identify` FROM `oa_all_internet_task_query` WHERE `identify` = #{identify}")
	public String selectInternetTaskIdentify(String identify);

	/**
	 * 更新任务的缓存信息
	 */
	@Update("UPDATE `oa_all_internet_task_query` SET `end_time` = #{end_time},`source_ids` = #{source_ids},"
			+ "`local_count` = #{local_count},`autm_count` = #{autm_count},`all_count` = #{all_count},"
			+ "`lasttime` = NOW() WHERE `identify` = #{identify}")
	public void updateInternetTask(TaskStatistic internetTask);

	/**
	 * 更新任务的最新查询时间
	 */
	@Update("UPDATE `oa_all_internet_task_query` SET `query_time` = NOW() WHERE `identify` = #{identify}")
	public void updateInternetTaskQuerytime(String identify);

	/**
	 * 查询固定时间内的所有任务identify
	 */
	@Select("SELECT `identify` FROM `oa_all_internet_task_query` WHERE `query_time` >= #{startTime}")
	public List<String> selectInternetTaskIdentifys(Date startTime);

	/**
	 * 删除任务的缓存信息
	 */
	@Delete("DELETE FROM `oa_all_internet_task_query` WHERE `identify` = #{identify}")
	public void deleteInternetTask(String identify);

}
