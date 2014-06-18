/**
 * 作者：罗培胤
 * 日期： 2013-11-30
 */
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

/**
 * 改变索引库，
 * 
 * @author Luopy @
 */
public class ChangeSearchIndex implements Runnable {

	public SearchIndex single = null;

	public ChangeSearchIndex(SearchIndex index) {
		single = index;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 60 * 10);
			} catch (Exception e) {
				// TODO: handle exception
			}

			Date dt = new Date();
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
		// TODO Auto-generated method stub
		try {

			single.isChanged = true;
			single.List.clear();
			single.ListHis.clear();
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			for (int i = 0; i < ServerConfig.getInstance().getMaxSearchDays(); i++) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -i);
				dt = calendar.getTime();
				String Today = sdf.format(dt);
				String path = ServerConfig.getInstance().getLocalIndexPath()
						+ Today + "\\";
				File file = new File(path);

				if (i == 0) {// 当天用近实时搜索
					// if (CreateIndex.getIwriter() == null) {
					// CreateIndex.GetInstance();
					// }
					DirectoryReader rd = DirectoryReader.open(CreateIndex
							.GetInstance().getIwriter(), true);
					single.List.add(rd);
				} else if (file.exists() && file.listFiles().length > 1) {
					// 创建IndexSearcher 对象

					Directory directory = FSDirectory.open(new File(
							ServerConfig.getInstance().getLocalIndexPath()
									+ Today + "/"));
					// DirectoryReader dr = DirectoryReader.open(directory);
					DirectoryReader rd = DirectoryReader.open(directory);
					// searcharray[i] = new IndexSearcher(dr);
					// System.out.println(path);
					// MultiReader md = new MultiReader(rd);
					// IndexSearcher ssr = new IndexSearcher(md);
					// MultiSearcher fc=new
					single.List.add(rd);
					single.ListHis.add(rd);
				} else {// 不存在文件不添加查询索引

				}

				//
			}
			
			
			DirectoryReader[] mrdhis = (DirectoryReader[]) single.ListHis
					.toArray(new DirectoryReader[0]);
			MultiReader mrhis = new MultiReader(mrdhis);
			single.historySearcher = new IndexSearcher(mrhis);


			// if (CreateIndex.getIwriter() == null) {
			// CreateIndex.GetInstance();
			// }
			// IndexReader rdi = DirectoryReader.open(CreateIndex.getIwriter(),
			// true);
			// singleSearchIndex.currentSearcher = new IndexSearcher(rdi);
			// IndexReader[] mrd = (IndexReader[]) singleSearchIndex.List
			// .toArray(new IndexReader[0]);
			// MultiReader mr = new MultiReader(mrd);
			// singleSearchIndex.allSearcher = new IndexSearcher(mr);
			//
			// IndexReader[] mrdhis = (IndexReader[]) singleSearchIndex.ListHis
			// .toArray(new IndexReader[0]);
			// MultiReader mrhis = new MultiReader(mrdhis);
			// singleSearchIndex.historySearcher = new IndexSearcher(mrhis);

		} catch (Exception e) {
			// TODO: handle exception
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();

		} finally {
			single.isChanged = false;
		}
	}

}
