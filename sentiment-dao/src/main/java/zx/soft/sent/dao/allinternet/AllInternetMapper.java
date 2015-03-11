package zx.soft.sent.dao.allinternet;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zx.soft.sent.dao.domain.allinternet.InternetTask;
import zx.soft.sent.dao.domain.allinternet.TaskResult;

/**
 * OA全网搜索任务接口
 *
 * @author wanggang
 *
 */
public interface AllInternetMapper {

	/**
	 * 插入任务的缓存信息
	 */
	@Insert("INSERT INTO `oa_all_internet_task_query` (`identify`,`keywords`,`start_time`,`end_time`,`source_ids`,"
			+ "`local_count`,`autm_count`,`all_count`,`lasttime`) VALUES (#{identify},#{keywords},#{start_time},"
			+ "#{end_time},#{source_ids},#{local_count},#{autm_count},#{all_count},NOW())")
	public void insertInternetTask(InternetTask internetTask);

	/**
	 * 查询任务的缓存信息
	 */
	@Select("SELECT `local_count`,`autm_count`,`all_count` FROM `oa_all_internet_task_query` WHERE `identify` = #{identify}")
	public TaskResult selectInternetTask(String identify);

	/**
	 * 更新任务的缓存信息
	 */
	@Update("UPDATE `oa_all_internet_task_query` SET `end_time` = #{end_time},`source_ids` = #{source_ids},"
			+ "`local_count` = #{local_count},`autm_count` = #{autm_count},`all_count` = #{all_count} WHERE `identify` = #{identify}")
	public void updateInternetTask(InternetTask internetTask);

	/**
	 * 更新任务的最新查询时间lasttime
	 */
	@Update("UPDATE `oa_all_internet_task_query` SET `lasttime` = NOW() WHERE `identify` = #{identify}")
	public void updateInternetTaskLasttime(String identify);

	/**
	 * 查询固定时间内的所有任务identify
	 */
	@Select("SELECT `identify` FROM `oa_all_internet_task_query` WHERE `lasttime` >= #{startTime}")
	public List<String> selectInternetTaskIdentifys(Date startTime);

	/**
	 * 删除任务的缓存信息
	 */
	@Delete("DELETE FROM `oa_all_internet_task_query` WHERE `identify` = #{identify}")
	public void deleteInternetTask(String identify);

}
