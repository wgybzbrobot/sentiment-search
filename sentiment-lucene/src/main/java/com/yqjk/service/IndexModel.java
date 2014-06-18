/**
 * 作者：罗培胤
 * 日期： 2013-10-28
 */
package com.yqjk.service;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建索引对象实体
 * 
 * @author Luopy
 * 
 */
public class IndexModel extends IndexModelExt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2766274206307532333L;
	/**
	 * URL地址 /GUID (必须)
	 */
	public String url = "";
	/**
	 * 标题
	 */
	public String title = "";
	/**
	 * 内容
	 */
	public String coutent = "";
	/**
	 * 发布人
	 */
	public String auther = "";
	/**
	 * 发布时间 (必须)
	 */
	public Date releasetime = null;

	/**
	 * 站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他-回复数据) (必须)
	 */
	public String zdbs = "";
	/**
	 * 站点名称 (必须)
	 */
	public String zdmc = "";

	/**
	 * 来源ID(来自ID) FLLB_CJLB 的ID (必须)
	 */
	public String lzid = "";

	/**
	 * QQ群号
	 */
	public String qqgroupno = "";

	/**
	 * 境内：1，境外：2
	 */
	public String jingnei = "";

	/**
	 * 区域代码
	 */
	public String quyucode = "";

}
