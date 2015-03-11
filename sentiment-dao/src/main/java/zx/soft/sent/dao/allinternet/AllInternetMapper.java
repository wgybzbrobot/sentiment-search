package zx.soft.sent.dao.allinternet;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zx.soft.sent.dao.domain.allinternet.InternetTask;

/**
 * OA全网搜索任务接口
 *
 * @author wanggang
 *
 */
public interface AllInternetMapper {

	/**
	 * 任务信息插入
	 */
	@Insert("INSERT INTO `oa_all_internet_task_info` (`identify`,`keywords`,`start_time`,`end_time`,`source_id`,"
			+ "`lasttime`) VALUES (#{identify},#{keywords},#{start_time},#{end_time},#{source_id},NOW())")
	public void insertInternetTask(InternetTask internetTask);

	/**
	 * 任务信息查询：根据identify
	 */
	@Select("SELECT `identify`,`keywords`,`start_time`,`end_time`,`source_id` FROM `oa_all_internet_task_info`"
			+ " WHERE `identify` = #{identify}")
	public InternetTask selectInternetTask(String identify);

	/**
	 * 任务信息查询：查询未完成任务，并按照最后更新时间降序
	 * @param startTime: 起始查询时间
	 */
	@Select("SELECT `identify`,`keywords`,`start_time`,`end_time`,`source_id` FROM `oa_all_internet_task_info`"
			+ " WHERE `is_over` = 0 AND `lasttime` > #{startTime} ORDER BY `lasttime` DESC")
	public List<InternetTask> selectInternetTasks(Date startTime);

	/**
	 * 任务信息中isover字段更新
	 */
	@Update("UPDATE `oa_all_internet_task_info` SET `is_over` = #{1},`lasttime` = NOW() WHERE `identify` = #{0}")
	public void updateInternetTaskIsOver(String identify, int isOver);

	/**
	 * 任务信息最近更新时间字段更新
	 */
	@Update("Update `oa_all_internet_task_info` SET `lasttime` = NOW() WHERE `identify` = #{identify}")
	public void updateInternetTaskLasttime(String identify);

	/**
	 * 任务信息删除
	 */
	@Delete("DELETE FROM `oa_all_internet_task_info` WHERE `identify` = #{identify}")
	public void deleteInternetTask(String identify);

	/**
	 * 任务查询结果插入
	 */
	@Insert("INSERT INTO `oa_all_internet_task_query` (`identify`,`query_result`,`lasttime`) VALUES (#{0},#{1},NOW())")
	public void insertInternetTaskQuery(String identify, String queryResult);

	/**
	 * 任务查询结果查询
	 */
	@Select("SELECT `query_result` FROM `oa_all_internet_task_query` WHERE `identify` = #{identify}")
	public String selectInternetTaskQuery(String identify);

	/**
	 * 任务查询结果更新
	 */
	@Update("UPDATE `oa_all_internet_task_query` SET `query_result` = #{1},`lasttime` = NOW() WHERE `identify` = #{0}")
	public void updateInternetTaskQuery(String identify, String queryResult);

	/**
	 * 任务查询结果删除
	 */
	@Delete("DELETE FROM `oa_all_internet_task_query` WHERE `identify` = #{identify}")
	public void deleteInternetTaskQuery(String identify);

}
