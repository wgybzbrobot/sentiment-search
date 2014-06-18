package com.yqjk.service;

import java.io.File;
//import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.ParallelCompositeReader;
//import org.apache.lucene.index.ParallelAtomicReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//import org.apache.lucene.util.Version;
//import org.wltea.analyzer.lucene.IKAnalyzer;

//import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

public class SearchIndex {

	/**
	 * 单例搜索类
	 */
	private static SearchIndex singleSearchIndex = null;
	/**
	 * 当天搜索
	 */
	// public IndexSearcher currentSearcher = null;
	/**
	 * 所有搜索
	 */
	// public IndexSearcher allSearcher = null;
	/**
	 * 历史搜索
	 */
	public IndexSearcher historySearcher = null;
	/**
	 * IndexReader对象数组
	 */
	public ArrayList<DirectoryReader> List = new ArrayList<DirectoryReader>();

	public ArrayList<DirectoryReader> ListHis = new ArrayList<DirectoryReader>();

	/**
	 * 当天
	 */
	public String Today = "";

	/**
	 * 
	 */
	public Boolean isChanged = false;

	/**
	 * IK分词器
	 */
	// public Analyzer analyzer = new IKAnalyzer();

	/**
	 * 更新索引库
	 */
	public Thread trc = null;

	public Thread trChangeCurrent = null;

	public Object objlock = new Object();

	public static SearchIndex GetInstance() {

		// Date dt = new Date();
		// String temp_str = "";
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// temp_str = sdf.format(dt);

		if (getSingleSearchIndex() == null) {
			syncInit();
		}
		// else if (!Today.equals(temp_str) && !isChanged) {
		// 更改索引库
		// synchronized (objlock) {
		// ChangeSearchBase();
		// }

		// }

		return getSingleSearchIndex();
	}

	// private static void ChangeSearchBase() {
	// // TODO Auto-generated method stub
	// try {
	//
	// isChanged = true;
	// singleSearchIndex.List.clear();
	// singleSearchIndex.ListHis.clear();
	// Date dt = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	//
	// for (int i = 0; i < ServerConfig.getInstance().getMaxSearchDays(); i++) {
	// Calendar calendar = Calendar.getInstance();
	// calendar.add(Calendar.DATE, -i);
	// dt = calendar.getTime();
	// String Today = sdf.format(dt);
	// String path = ServerConfig.getInstance().getLocalIndexPath()
	// + Today + "\\";
	// File file = new File(path);
	//
	// if (i == 0) {// 当天用近实时搜索
	// if (CreateIndex.getIwriter() == null) {
	// CreateIndex.GetInstance();
	// }
	// DirectoryReader rd = DirectoryReader.open(
	// CreateIndex.getIwriter(), true);
	// singleSearchIndex.List.add(rd);
	// } else if (file.exists() && file.listFiles().length > 0) {
	// // 创建IndexSearcher 对象
	//
	// Directory directory = FSDirectory.open(new File(
	// ServerConfig.getInstance().getLocalIndexPath()
	// + Today + "\\"));
	// // DirectoryReader dr = DirectoryReader.open(directory);
	// DirectoryReader rd = DirectoryReader.open(directory);
	// // searcharray[i] = new IndexSearcher(dr);
	// // System.out.println(path);
	// // MultiReader md = new MultiReader(rd);
	// // IndexSearcher ssr = new IndexSearcher(md);
	// // MultiSearcher fc=new
	// singleSearchIndex.List.add(rd);
	// singleSearchIndex.ListHis.add(rd);
	// } else {// 不存在文件不添加查询索引
	//
	// }
	//
	// //
	// }
	//
	// // if (CreateIndex.getIwriter() == null) {
	// // CreateIndex.GetInstance();
	// // }
	// // IndexReader rdi = DirectoryReader.open(CreateIndex.getIwriter(),
	// // true);
	// // singleSearchIndex.currentSearcher = new IndexSearcher(rdi);
	// // IndexReader[] mrd = (IndexReader[]) singleSearchIndex.List
	// // .toArray(new IndexReader[0]);
	// // MultiReader mr = new MultiReader(mrd);
	// // singleSearchIndex.allSearcher = new IndexSearcher(mr);
	// //
	// // IndexReader[] mrdhis = (IndexReader[]) singleSearchIndex.ListHis
	// // .toArray(new IndexReader[0]);
	// // MultiReader mrhis = new MultiReader(mrdhis);
	// // singleSearchIndex.historySearcher = new IndexSearcher(mrhis);
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// Logger logger = LogManager.getLogger("CreateIndex");
	// logger.error(e.getMessage(), e);
	// logger.exit();
	//
	// } finally {
	// isChanged = false;
	// }
	// }

	private static synchronized void syncInit() {
		// TODO Auto-generated method stub
		if (getSingleSearchIndex() == null) {
			// System.out.println("SearchIndex");
			setSingleSearchIndex(new SearchIndex());
		}

	}

	private SearchIndex() {
		// baseReader= IndexReader.open(writer, applyAllDeletes);
		// IndexReader.open(directory)

		// DirectoryReader dr = DirectoryReader.open(CreateIndex.getIwriter(),
		// true);

		// @SuppressWarnings("unused")
		// IndexReader rd=DirectoryReader.open(CreateIndex.getIwriter(),
		// true);
		//
		//
		//
		// IndexSearcher sr=new IndexSearcher();

		// List.add(e);

		// searcharray = new IndexSearcher[ServerConfig.getInstance()
		// .getMaxSearchDays()];

		try {
			List.clear();
			ListHis.clear();
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			this.Today = sdf.format(dt);
			for (int i = 0; i < ServerConfig.getInstance().getMaxSearchDays(); i++) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -i);
				dt = calendar.getTime();
				String Today = sdf.format(dt);
				String path = ServerConfig.getInstance().getLocalIndexPath()
						+ Today + "/";
				File file = new File(path);

				if (i == 0) {// 当天用近实时搜索
					// if (CreateIndex.GetInstance().getIwriter() == null) {
					// CreateIndex.GetInstance();
					// }
					DirectoryReader rd = DirectoryReader.open(CreateIndex
							.GetInstance().getIwriter(), true);

					List.add(rd);
				} else if (file.exists() && file.listFiles().length > 1) {
					// 创建IndexSearcher 对象

					Directory directory = FSDirectory.open(new File(
							ServerConfig.getInstance().getLocalIndexPath()
									+ Today + "/"));

					// IndexWriterConfig cf = new IndexWriterConfig(
					// Version.LUCENE_45, analyzer);
					// IndexWriter rd1 = null;
					// rd1 = new IndexWriter(directory, cf);
					// rd1.forceMerge(1, true);
					// rd1.close();

					// DirectoryReader dr = DirectoryReader.open(directory);
					DirectoryReader rd = DirectoryReader.open(directory);
					// searcharray[i] = new IndexSearcher(dr);
					// System.out.println(path);
					// MultiReader md = new MultiReader(rd);
					// IndexSearcher ssr = new IndexSearcher(md);
					// MultiSearcher fc=new
					List.add(rd);
					ListHis.add(rd);

				} else {// 不存在文件不添加查询索引

				}

				//
			}
			// if (CreateIndex.getIwriter() == null) {
			// CreateIndex.GetInstance();
			// }
			// IndexReader rdi = DirectoryReader.open(CreateIndex.getIwriter(),
			// true);
			// currentSearcher = new IndexSearcher(rdi);
			// IndexReader[] mrd = (IndexReader[]) List
			// .toArray(new IndexReader[0]);
			// MultiReader mr = new MultiReader(mrd);
			// allSearcher = new IndexSearcher(mr);
			//
			DirectoryReader[] mrdhis = (DirectoryReader[]) ListHis
					.toArray(new DirectoryReader[0]);
			MultiReader mrhis = new MultiReader(mrdhis);
			historySearcher = new IndexSearcher(mrhis);

			// if (mrd.length > 1) {
			// IndexReader[] mrdhis = new MultiReader[mrd.length - 1];
			// // System.arraycopy(mrd, 1, mrdhis, 0, mrd.length - 1);
			// for (int i = 0; i < mrdhis.length; i++) {
			// mrdhis[i]=null;
			// mrdhis[i] =List.get(i);
			// }
			// MultiReader mrhis = new MultiReader(mrdhis);
			// historySearcher = new IndexSearcher(mrhis);
			// }

			trc = new Thread(new ChangeSearchIndex(this));
			trc.start();

			trChangeCurrent = new Thread(new ChangeCurrentIndex(this));
			trChangeCurrent.start();
			
		} catch (Exception e) {
			// TODO: handle exception
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}

	}

	public IndexSearcher GetCurrentSearcher() {
		IndexSearcher currentSearcher = null;
		try {
			// if (CreateIndex.getIwriter() == null) {
			// CreateIndex.GetInstance();
			// }
			// DirectoryReader rd =
			// DirectoryReader.open(CreateIndex.getIwriter(),
			// true);

			// if (!List.get(0).isCurrent()) {
			// DirectoryReader rd = DirectoryReader.openIfChanged(List.get(0),
			// CreateIndex.GetInstance().getIwriter(), false);
			// if (rd != null) {
			// // List.get(0).close();
			// List.set(0, rd);
			// }
			// }

			currentSearcher = new IndexSearcher(List.get(0));

		} catch (Exception e) {
			// TODO: handle exception
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
		return currentSearcher;

	}

	public IndexSearcher GetAllSearcher() {
		IndexSearcher allSearcher = null;
		// MultiSearcher CHr=new MultiSearcher();
		try {
			// if (CreateIndex.getIwriter() == null) {
			// CreateIndex.GetInstance();
			// }
			// DirectoryReader rd =
			// DirectoryReader.open(CreateIndex.getIwriter(),
			// true);
			
			
			// if (!List.get(0).isCurrent()) {
			// DirectoryReader rd = DirectoryReader.openIfChanged(List.get(0),
			// CreateIndex.GetInstance().getIwriter(), true);
			// if (rd != null) {
			// // List.get(0).close();
			// List.set(0, rd);
			// }
			// }

			DirectoryReader[] mrd = (DirectoryReader[]) List
					.toArray(new DirectoryReader[0]);
			MultiReader mr = new MultiReader(mrd);

			allSearcher = new IndexSearcher(mr);
		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
		return allSearcher;
	}

	public IndexSearcher GetHistorySearcher() {
		if (historySearcher != null) {
			return historySearcher;
		}
		try {
			// if (CreateIndex.getIwriter() == null) {
			// CreateIndex.GetInstance();
			// }

			if (getSingleSearchIndex().ListHis.size() == 0) {
				return null;
			}

			DirectoryReader[] mrdhis = (DirectoryReader[]) getSingleSearchIndex().ListHis
					.toArray(new DirectoryReader[0]);
			MultiReader mrhis = new MultiReader(mrdhis);
			historySearcher = new IndexSearcher(mrhis);

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
		return historySearcher;
	}

	public static SearchIndex getSingleSearchIndex() {
		return singleSearchIndex;
	}

	public static void setSingleSearchIndex(SearchIndex singleSearchIndex) {
		SearchIndex.singleSearchIndex = singleSearchIndex;
	}
}
