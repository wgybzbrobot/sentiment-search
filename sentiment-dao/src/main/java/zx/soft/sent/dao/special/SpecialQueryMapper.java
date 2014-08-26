package zx.soft.sent.dao.special;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zx.soft.sent.dao.domain.special.InsertSpecialInfo;
import zx.soft.sent.dao.domain.special.InsertSpecialResult;

/**
 * OA专题模块Mapper
 * 
 * @author wanggang
 *
 */
public interface SpecialQueryMapper {

	/**
	 * 插入专题信息
	 */
	@Insert("INSERT INTO `oa_special_info` (`identify`,`keywords`,`start`,`end`,`hometype`,`lasttime`) "
			+ "VALUES (#{identify},#{keywords},#{start},#{end},#{hometype},UNIX_TIMESTAMP())")
	public void insertSpecialInfo(InsertSpecialInfo insertSpecialInfo);

	/**
	 * 删除专题信息
	 */
	@Delete("DELETE FROM `oa_special_info` WHERE `identify` = #{identify}")
	public void deleteSpecialInfo(String identify);

	/**
	 * 插入专题查询结果
	 */
	@Insert("INSERT INTO `oa_special_query_cache` (`identify`,`result`,`lasttime`) "
			+ "VALUES (#{identify},#{result},UNIX_TIMESTAMP())")
	public void insertSpecialResult(InsertSpecialResult insertSpecialResult);

	/**
	 * 查询专题查询结果
	 */
	@Select("SELECT `result` FROM `oa_special_query_cache` WHERE `identify` = #{identify}")
	@ConstructorArgs(value = { @Arg(column = "result", javaType = String.class) })
	public String selectSpecialResult(String identify);

	/**
	 * 更新专题查询结果
	 */
	@Update("UPDATE `oa_special_query_cache` SET `result` = #{result},`lasttime` = UNIX_TIMESTAMP() "
			+ "WHERE `identify` = #{identify}")
	public void updateSpecialResult(InsertSpecialResult insertSpecialResult);

	/**
	 * 删除专题查询结果
	 */
	@Delete("DELETE FROM `oa_special_query_cache` WHERE `identify` = #{identify}")
	public void deleteSpecialResult(String identify);

}
