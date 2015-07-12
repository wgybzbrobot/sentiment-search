package zx.soft.sent.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zx.soft.sent.api.domain.KeywordType;

/**
 * 全网后台自动任务：关键词类型接口
 *
 * @author wanggang
 *
 */
public interface KeywordTypeMapper {

	/**
	 * 插入关键词类型
	 */
	@Insert("INSERT INTO `task_keywords_type` (`first_type`,`first_type_name`,`second_type`,`second_type_name`,"
			+ "`third_type`,`third_type_name`,`lasttime`) VALUE (#{first_type},#{first_type_name},#{second_type},"
			+ "#{second_type_name},#{third_type},#{third_type_name},NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "ktid")
	public void insertKeywordType(KeywordType keywordType);

	/**
	 * 删除关键词类型
	 */
	@Delete("DELETE FROM `task_keywords_type` WHERE `first_type` = #{0} AND `second_type` = #{1} AND `third_type`=#{2}")
	public void deleteKeywordType(int first_type, int second_type, int third_type);

	@Delete("DELETE FROM `task_keywords_type` WHERE `first_type` = #{0} AND `second_type` = #{1}")
	public void deleteKeywordTypes(int first_type, int second_type);

	@Delete("DELETE FROM `task_keywords_type` WHERE `first_type` = #{0}")
	public void deleteKeywordTypes(int first_type);

	/**
	 * 查询关键词类型
	 */
	@Select("SELECT FROM `task_keywords_type` WHERE `first_type` = #{0} AND `second_type` = #{1} AND `third_type`=#{2}")
	public KeywordType selectKeywordType(int first_type, int second_type, int third_type);

	@Select("SELECT FROM `task_keywords_type` WHERE `first_type` = #{0} AND `second_type` = #{1}")
	public List<KeywordType> selectKeywordTypes(int first_type, int second_type);

	@Select("SELECT FROM `task_keywords_type` WHERE `first_type` = #{0}")
	public List<KeywordType> selectKeywordTypes(int first_type);

	/**
	 * 更新关键词类型
	 */
	@Update("UPDATE `task_keywords_type` SET `first_type_name` = #{first_type_name},`second_type_name` = "
			+ "#{second_type_name},`third_type_name` = #{third_type_name},`lasttime` = NOW() WHERE "
			+ "`first_type` = #{first_type} AND `second_type` = #{second_type} AND `third_type`=#{third_type}")
	public void updateKeywordType(KeywordType keywordType);

}
