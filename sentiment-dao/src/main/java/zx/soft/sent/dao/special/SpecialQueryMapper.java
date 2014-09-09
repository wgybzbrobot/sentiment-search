package zx.soft.sent.dao.special;

import java.util.List;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zx.soft.sent.dao.domain.special.SpecialResult;
import zx.soft.sent.dao.domain.special.SpecialTopic;

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
	@Insert("INSERT INTO `oa_special_info` (`identify`,`name`,`keywords`,`start`,`end`,`hometype`,`lasttime`) "
			+ "VALUES (#{identify},#{name},#{keywords},#{start},#{end},#{hometype},UNIX_TIMESTAMP())")
	public void insertSpecialInfo(SpecialTopic specialTopic);

	/**
	 * 查询专题信息
	 */
	public SpecialTopic selectSpecialInfo(String identify);

	/**
	 * 删除专题信息
	 */
	@Delete("DELETE FROM `oa_special_info` WHERE `identify` = #{identify}")
	public void deleteSpecialInfo(String identify);

	/**
	 * 插入专题查询结果
	 */
	@Insert("INSERT INTO `oa_special_query_cache` (`identify`,`type`,`result`,`lasttime`) "
			+ "VALUES (#{identify},#{type},#{result},UNIX_TIMESTAMP())")
	public void insertSpecialResult(SpecialResult specialResult);

	/**
	 * 查询专题identify，按时间查询
	 */
	@Select("SELECT `identify` FROM `oa_special_info` WHERE `lasttime` > #{lasttime}")
	@ConstructorArgs(value = { @Arg(column = "identify", javaType = String.class) })
	public List<String> selectSpecialIdentifyByTime(long lasttime);

	/**
	 * 查询专题查询结果
	 */
	@Select("SELECT `result` FROM `oa_special_query_cache` WHERE `identify` = #{identify} AND `type` = #{type}")
	@ConstructorArgs(value = { @Arg(column = "result", javaType = String.class) })
	public String selectSpecialResult(@Param("identify") String p1, @Param("type") String p2);

	/**
	 * 更新专题查询结果的时间，在每次查询后更新时间
	 */
	@Update("UPDATE `oa_special_info` SET `lasttime` = UNIX_TIMESTAMP() WHERE `identify` = #{identify}")
	public void updateSpecialResultLasttime(String identify);

	/**
	 * 更新专题查询结果
	 */
	@Update("UPDATE `oa_special_query_cache` SET `result` = #{result},`lasttime` = UNIX_TIMESTAMP() "
			+ "WHERE `identify` = #{identify}")
	public void updateSpecialResult(SpecialResult insertSpecialResult);

	/**
	 * 删除专题查询结果
	 */
	@Delete("DELETE FROM `oa_special_query_cache` WHERE `identify` = #{identify}")
	public void deleteSpecialResult(String identify);

}
