package com.yqjk.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ReplaceIndexChange implements Runnable {
	public ReplaceIndex single = null;

	public ReplaceIndexChange(ReplaceIndex index) {
		single = index;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 60 * 10);
			} catch (Exception e) {

			}

			// 延迟10分钟创建历史索引
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // 设置当前日期
			// c.add(Calendar.MINUTE, 10); //
			// 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
			Date dt = c.getTime(); // 结果
			//
			String temp_str = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			temp_str = sdf.format(dt);

			if (!single.Today.equals(temp_str) && !single.isChanged) {
				// 更改索引库
				synchronized (single.objlock) {
					ChangeSearchBase();
					single.Today = temp_str;
				}

			}

		}

	}

	private void ChangeSearchBase() {

		try {
			single.ListHis.clear();
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

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
					single.ListHis.add(rd);
				} else {// 不存在文件不添加查询索引

				}
			}

			DirectoryReader[] mrdhis = (DirectoryReader[]) single.ListHis
					.toArray(new DirectoryReader[0]);
			MultiReader mrhis = new MultiReader(mrdhis);
			single.historySearcher = new IndexSearcher(mrhis);

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
	}

}
