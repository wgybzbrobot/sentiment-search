package zx.soft.sent.api.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.sent.api.dao.KeywordTypeMapper;
import zx.soft.sent.api.dao.TaskKeywordMapper;
import zx.soft.sent.api.domain.KeywordType;
import zx.soft.sent.api.domain.TaskKeyword;

/**
 * 全网任务关键词维护服务
 *
 * @author wanggang
 *
 */
@Service
public class KeywordService {

	@Inject
	private KeywordTypeMapper keywordTypeMapper;

	@Inject
	private TaskKeywordMapper taskKeywordMapper;

	/**
	 * 插入关键词类型
	 */
	public void insertKeywordTypes(List<KeywordType> keywordTypes) {
		for (KeywordType keywordType : keywordTypes) {
			insertKeywordType(keywordType);
		}
	}

	private void insertKeywordType(KeywordType keywordType) {
		keywordTypeMapper.insertKeywordType(keywordType);
	}

	/**
	 * 删除关键词类型
	 */
	public void deleteKeywordType(int first_type, int second_type, int third_type) {
		keywordTypeMapper.deleteKeywordType(first_type, second_type, third_type);
	}

	public void deleteKeywordTypes(int first_type, int second_type) {
		keywordTypeMapper.deleteKeywordTypes(first_type, second_type);
	}

	public void deleteKeywordTypes(int first_type) {
		keywordTypeMapper.deleteKeywordTypes(first_type);
	}

	/**
	 * 查询关键词类型
	 */
	public KeywordType selectKeywordType(int first_type, int second_type, int third_type) {
		return keywordTypeMapper.selectKeywordType(first_type, second_type, third_type);
	}

	public List<KeywordType> selectKeywordTypes(int first_type, int second_type) {
		return keywordTypeMapper.selectKeywordTypes(first_type, second_type);
	}

	public List<KeywordType> selectKeywordTypes(int first_type) {
		return keywordTypeMapper.selectKeywordTypes(first_type);
	}

	/**
	 * 更新关键词类型
	 */
	public void updateKeywordTypes(List<KeywordType> keywordTypes) {
		for (KeywordType keywordType : keywordTypes) {
			updateKeywordType(keywordType);
		}
	}

	private void updateKeywordType(KeywordType keywordType) {
		keywordTypeMapper.updateKeywordType(keywordType);
	}

	/**
	 * 插入关键词
	 */
	public void insertTaskKeywords(List<TaskKeyword> taskKeywords) {
		for (TaskKeyword taskKeyowrd : taskKeywords) {
			insertTaskKeyword(taskKeyowrd);
		}
	}

	private void insertTaskKeyword(TaskKeyword taskKeyword) {
		taskKeywordMapper.insertTaskKeyword(taskKeyword);
	}

	/**
	 * 删除关键词
	 */
	public void deleteTaskKeyword(String keyword_id) {
		taskKeywordMapper.deleteTaskKeyword(keyword_id);
	}

	public void deleteTaskKeywords(int first_type, int second_type, int third_type) {
		taskKeywordMapper.deleteTaskKeywords(first_type, second_type, third_type);
	}

	public void deleteTaskKeywords(int first_type, int second_type) {
		taskKeywordMapper.deleteTaskKeywords(first_type, second_type);
	}

	public void deleteTaskKeywords(int first_type) {
		taskKeywordMapper.deleteTaskKeywords(first_type);
	}

	/**
	 * 查询关键词
	 */
	public List<TaskKeyword> selectTaskKeywords(int first_type, int second_type, int third_type) {
		return taskKeywordMapper.selectTaskKeywords(first_type, second_type, third_type);
	}

	public List<TaskKeyword> selectTaskKeywords(int first_type, int second_type) {
		return taskKeywordMapper.selectTaskKeywords(first_type, second_type);
	}

	public List<TaskKeyword> selectTaskKeywords(int first_type) {
		return taskKeywordMapper.selectTaskKeywords(first_type);
	}

}
