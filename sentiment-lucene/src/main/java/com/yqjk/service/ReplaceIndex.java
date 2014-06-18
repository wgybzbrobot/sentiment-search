package com.yqjk.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * 去重索引
 * 
 * @author Luopy
 * 
 */
public class ReplaceIndex {
	/**
	 * 单例搜索类
	 */
	private static ReplaceIndex singleRepaceIndex = null;

	/**
	 * 历史搜索
	 */
	public IndexSearcher historySearcher = null;

	/**
	 * IndexReader历史对象数组
	 */
	public ArrayList<DirectoryReader> ListHis = new ArrayList<DirectoryReader>();

	/**
	 * 当天
	 */
	public String Today = "";

	/**
	 * 
	 */
	public Boolean isChanged = false;

	public Object objlock = new Object();

	/**
	 * 更新索引库
	 */
	public Thread trc = null;

	public static ReplaceIndex GetInstance() {

		if (getSingleReplaceIndex() == null) {
			syncInit();
		}

		return getSingleReplaceIndex();
	}

	private static synchronized void syncInit() {
		if (getSingleReplaceIndex() == null) {
			// System.out.println("RepaceIndex");
			setSingleReplaceIndex(new ReplaceIndex());
		}

	}

	private ReplaceIndex() {

		try {
			ListHis.clear();
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			Today = sdf.format(dt);// 当天
			for (int i = 0; i < ServerConfig.getInstance().getMaxSearchDays(); i++) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -i);
				dt = calendar.getTime();
				String Today = sdf.format(dt);
				String path = ServerConfig.getInstance().getLocalIndexPath()
						+ Today + "/";
				File file = new File(path);

				if (i == 0) {// 当天用近实时搜索
					// 当天去重给数据库处理
					// DirectoryReader rd = DirectoryReader.open(CreateIndex
					// .GetInstance().getIwriter(), true);

				} else if (file.exists() && file.listFiles().length > 1) {
					// 创建IndexSearcher 对象
					Directory directory = FSDirectory.open(new File(
							ServerConfig.getInstance().getLocalIndexPath()
									+ Today + "/"));
					DirectoryReader rd = DirectoryReader.open(directory);
					ListHis.add(rd);
				} else {// 不存在文件不添加查询索引

				}
			}

			DirectoryReader[] mrdhis = (DirectoryReader[]) ListHis
					.toArray(new DirectoryReader[0]);
			MultiReader mrhis = new MultiReader(mrdhis);
			historySearcher = new IndexSearcher(mrhis);

			trc = new Thread(new ReplaceIndexChange(this));
			trc.start();

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
	}

	/**
	 * 
	 * @return
	 */
	public IndexSearcher GetHistorySearcher() {
		if (historySearcher != null) {
			return historySearcher;
		} else {
			try {
				if (getSingleReplaceIndex().ListHis.size() == 0) {
					return null;
				}
				DirectoryReader[] mrdhis = (DirectoryReader[]) getSingleReplaceIndex().ListHis
						.toArray(new DirectoryReader[0]);
				MultiReader mrhis = new MultiReader(mrdhis);
				historySearcher = new IndexSearcher(mrhis);

			} catch (Exception e) {
				Logger logger = LogManager.getLogger("CreateIndex");
				logger.error(e.getMessage(), e);
				logger.exit();
			}
		}
		return historySearcher;
	}

	public static ReplaceIndex getSingleReplaceIndex() {
		return singleRepaceIndex;
	}

	public static void setSingleReplaceIndex(ReplaceIndex singleReplaceIndex) {
		ReplaceIndex.singleRepaceIndex = singleReplaceIndex;
	}
}
