package zx.soft.sent.api.dao;

import java.util.List;

import zx.soft.sent.api.domain.TaskKeyword;

/**
 * 全网后台自动任务：关键词接口
 *
 * @author wanggang
 *
 */
public interface TaskKeywordMapper {

	/**
	 * 插入关键词
	 */
	public void insertTaskKeyword(TaskKeyword taskKeyword);

	/**
	 * 删除关键词
	 */
	public void deleteTaskKeyword(String keyword_id);

	public void deleteTaskKeywords(int first_type, int second_type, int third_type);

	public void deleteTaskKeywords(int first_type, int second_type);

	public void deleteTaskKeywords(int first_type);

	/**
	 * 查询关键词
	 */
	public List<TaskKeyword> selectTaskKeywords(int first_type, int second_type, int third_type);

	public List<TaskKeyword> selectTaskKeywords(int first_type, int second_type);

	public List<TaskKeyword> selectTaskKeywords(int first_type);

}
