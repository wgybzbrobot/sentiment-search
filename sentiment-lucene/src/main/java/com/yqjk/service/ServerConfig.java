/**
 * 作者：罗培胤
 * 日期： 2013-10-23
 */
package com.yqjk.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

/**
 * 服务配置信息
 * 
 * @author Luopy
 * 
 */
public class ServerConfig {
	// 配置文件
	private static final String FILE_NAME = "LuceneIndex.xml";
	// 配置本地索引库目录
	private static final String INDEX_PATHLOCAL = "index_pathlocal";
	// 配置同步索引库目录
	private static final String INDEX_PATHOUT = "index_pathout";
	// 配置提交索引周期(秒)
	private static final String INDEX_COMMITSECOND = "index_commitsecond";
	// 最大搜索天数
	private static final String MAXSEARCH_DAYS = "maxsearch_days";

	private Properties props;

	/**
	 * 构造配置
	 * 
	 * @return ServerConfig单例
	 */
	public static ServerConfig getInstance() {
		return new ServerConfig();
	}

	/**
	 * 构造函数
	 */
	private ServerConfig() {
		props = new Properties();

		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream(FILE_NAME);
		if (input != null) {
			try {
				props.loadFromXML(input);
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取本地索引目录
	 * 
	 * @return 本地索引目录
	 */
	public String getLocalIndexPath() {
		return props.getProperty(INDEX_PATHLOCAL);
	}

	/**
	 * 配置提交索引周期(秒)
	 * 
	 * @return
	 */
	public int getIndexCommitSecond() {
		return Integer.parseInt(props.getProperty(INDEX_COMMITSECOND));
	}

	/**
	 * 获取同步索引目录
	 * 
	 * @return 所有同步索引目录列表
	 */
	public List<String> getOutIndexPath() {
		List<String> outIndexPathList = new ArrayList<String>();
		String outIndexPathCfg = props.getProperty(INDEX_PATHOUT);
		if (outIndexPathCfg != null) {
			// 使用;分割多个同步索引目录
			String[] outIndexPaths = outIndexPathCfg.split(";");
			if (outIndexPaths != null) {
				for (String outIndexPath : outIndexPaths) {
					if (outIndexPath != null && !"".equals(outIndexPath.trim())) {
						outIndexPathList.add(outIndexPath.trim());
					}
				}
			}
		}
		return outIndexPathList;
	}

	/**
	 * 最大搜索天数
	 * 
	 * @return 配置的向前最大搜索天数
	 */
	public int getMaxSearchDays() {
		return Integer.parseInt(props.getProperty(MAXSEARCH_DAYS));
	}
}
