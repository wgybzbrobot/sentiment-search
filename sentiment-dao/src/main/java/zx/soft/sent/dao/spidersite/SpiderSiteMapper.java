package zx.soft.sent.dao.spidersite;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zx.soft.sent.dao.domain.platform.SiteInfo;

/**
 * 站点信息Mapper
 *
 * @author wanggang
 *
 */
public interface SpiderSiteMapper {

	/**
	 * 插入站点信息
	 */
	@Insert("INSERT INTO `sent_site_info` (`url`,`zone`,`description`,`source_name`,`platform`,`status`,"
			+ "`spider_id`,`spider_type`,`source_id`,`source_type`,`source_code`,`contact`,`admin`,`root`,"
			+ "`params`,`uid`,`timestamp`,`identify`,`lasttime`) VALUES (#{url},#{zone},#{description},"
			+ "#{source_name},#{platform},#{status},#{spider_id},#{spider_type},#{source_id},#{source_type},"
			+ "#{source_code},#{contact},#{admin},#{root},#{params},#{uid},#{timestamp},#{identify},NOW())")
	public void insertSiteInfo(SiteInfo siteInfo);

	/**
	 * 根据站点Id返回站点名称
	 */
	@Select("SELECT `source_name` FROM `sent_site_info` WHERE `source_id` = #{source_id}")
	@ConstructorArgs(value = { @Arg(column = "source_name", javaType = String.class) })
	public String getSourceName(int source_id);

	/**
	 * 根据站点名称返回站点Id
	 */
	@Select("SELECT `source_id` FROM `sent_site_info` WHERE `source_name` = #{source_name}")
	@ConstructorArgs(value = { @Arg(column = "source_id", javaType = int.class) })
	public int getSourceId(String source_name);

	/**
	 * 根据站点Id删除站点信息
	 */
	@Delete("DELETE FROM `sent_site_info` WHERE `source_id` = #{source_id}")
	public void deleteSiteById(int source_id);

}