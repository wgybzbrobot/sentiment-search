/**
 * 作者：罗培胤
 * 日期： 2013-10-28
 */
package com.yqjk.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 创建索引类，单例
 * 
 * @author Luopy
 * 
 */
public class CreateIndex {
	/**
	 * 索引写对象
	 */
	private IndexWriter iwriter = null;

	public Object objlock = new Object();
	/**
	 * IK分词器
	 */
	public Analyzer analyzer = new IKAnalyzer();
	/**
	 * 创建索引类实例
	 */
	public static CreateIndex singleCreate = null;
	/**
	 * 当天
	 */
	private String Today = "";
	/**
	 * 使用数量原子操作，没有使用时才能更改 索引写对象 iwriter
	 * 
	 */
	private AtomicInteger usedcount = new AtomicInteger(0);
	/**
	 * 是否是在改变索引写对象状态
	 */
	public Boolean isChanged = false;
	/**
	 * 提交索引线程
	 */
	public Thread trc = null;
	/**
	 * 修改索引线程
	 */
	public Thread tchange = null;

	/**
	 * 单例构造，创建索引实例
	 */
	private CreateIndex() {

		try {
			if (getIwriter() != null) {
				return;
			}
			setIsChanged(true);

			// 构造分词器
			analyzer = new IKAnalyzer();
			// 初始化IndexWriter
			IndexWriterConfig cf = new IndexWriterConfig(Version.LUCENE_45,
					analyzer);
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			setToday(sdf.format(dt));

			Directory directory = FSDirectory.open(new File(ServerConfig
					.getInstance().getLocalIndexPath() + getToday() + "/")); //

			try {
				synchronized (objlock) {
					if (getIwriter() != null) {
						return;
					}
					setIwriter(new IndexWriter(directory, cf));
				}
				// System.out.println("run");
				trc = new Thread(CreateIndexCommit.GetInstance(this));
				trc.start();

				tchange = new Thread(new ChangeWriteIndex(this));
				tchange.start();

				// System.out.println("runn");
			} catch (Exception e) {
				Logger logger = LogManager.getLogger("CreateIndex");
				logger.error(e.getMessage(), e);
				logger.exit();
			}

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();

		} finally {
			setIsChanged(false);
		}

	}

	private static synchronized void syncInit() {
		if (singleCreate == null) {
			System.out.println("single");
			singleCreate = new CreateIndex();
		}
	}

	public void WriteIndex(IndexModel model) {
		while (getIsChanged()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Logger logger = LogManager.getLogger("CreateIndex");
				logger.error(e.getMessage(), e);
				logger.exit();
			}
		}
		getUsedcount().incrementAndGet();
		try {
			Document doc = new Document();
			// URL
			if (!model.url.equalsIgnoreCase("")) {
				Field fdurl = new StringField("url", model.url, Store.YES);
				doc.add(fdurl);
				System.out.println("url: " + model.url);
			}
			// 标题
			if (!model.title.equalsIgnoreCase("")) {
				Field fdtitle = new TextField("title", model.title, Store.YES);
				doc.add(fdtitle);
			}
			// 内容
			if (!model.coutent.equalsIgnoreCase("")) {
				Field fdcoutent = new TextField("coutent", model.coutent,
						Store.YES);
				doc.add(fdcoutent);
			}
			// 发布人
			if (!model.auther.equalsIgnoreCase("")) {
				// Field fdauther = new TextField("auther", model.auther,
				// Store.YES);
				// doc.add(fdauther);
				// 发布人不分词
				Field fdautherstatic = new StringField("auther", model.auther,
						Store.YES);
				doc.add(fdautherstatic);
			}

			try {
				if (model.releasetime == null) {
					model.releasetime = new Date();
				}
				// 发布时间
				LongField fdreleasetime = new LongField("releasetime",
						DateTools.stringToTime(DateTools.dateToString(
								model.releasetime, Resolution.MILLISECOND)),
						Store.YES);

				doc.add(fdreleasetime);
			} catch (Exception e) {
				// 没有时间用系统时间代替
				LongField fdreleasetime = new LongField("releasetime",
						DateTools.stringToTime(DateTools.dateToString(
								new Date(), Resolution.MILLISECOND)), Store.YES);
				doc.add(fdreleasetime);
			}

			// 站点标识
			if (!model.zdbs.equalsIgnoreCase("")) {
				Field fdurl = new StringField("zdbs", model.zdbs, Store.YES);
				doc.add(fdurl);
			}

			// 站点名称
			if (!model.zdmc.equalsIgnoreCase("")) {
				Field fdzdmc = new StringField("zdmc", model.zdmc, Store.YES);
				doc.add(fdzdmc);
			}

			// 来源ID(来自ID) FLLB_CJLB 的ID (必须)
			if (!model.lzid.equalsIgnoreCase("")) {
				Field fdlzid = new StringField("lzid", model.lzid, Store.YES);
				doc.add(fdlzid);
			}

			// QQ群号
			if (!model.qqgroupno.equalsIgnoreCase("")) {
				Field fdqqgroupno = new StringField("qqgroupno",
						model.qqgroupno, Store.YES);
				doc.add(fdqqgroupno);
			}

			// 境内：1，境外：2
			if (!model.jingnei.equalsIgnoreCase("")) {
				Field fdjingnei = new StringField("jingnei", model.jingnei,
						Store.YES);
				doc.add(fdjingnei);
			}

			// 区域代码
			if (!model.quyucode.equalsIgnoreCase("")) {
				Field fdquyucode = new StringField("quyucode", model.quyucode,
						Store.YES);
				doc.add(fdquyucode);
			}

			try {
				// 抓取时间
				LongField fdreleasetime = new LongField("spy",
						DateTools.stringToTime(DateTools.dateToString(
								new Date(), Resolution.MILLISECOND)), Store.YES);

				doc.add(fdreleasetime);
			} catch (Exception e) {
				Logger logger = LogManager.getLogger("CreateIndex");
				logger.error(e.getMessage(), e);
				logger.exit();
			}

			// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

			// LongField fdreleasetime = new LongField("releasetime",
			// DateTools.stringToTime(sdf.format(model.releasetime)),Store.YES);

			// doc.add(fdreleasetime);

			// System.out.println(DateTools.dateToString(
			// model.releasetime, Resolution.MILLISECOND));
			// System.out.println( model.releasetime);

			// LongDocValuesField fdreleasetimex = new
			// LongDocValuesField("releasetimex",
			// DateTools.stringToTime(DateTools.dateToString(
			// model.releasetime, Resolution.SECOND)));
			//
			// doc.add(fdreleasetimex);
			//

			// Field fdreleasetimex = new StoredField("releasetimex",
			// DateTools.stringToTime(DateTools.dateToString(
			// model.releasetime, Resolution.SECOND)));
			//
			// doc.add(fdreleasetimex);

			while (getIwriter() == null) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Logger logger = LogManager.getLogger("CreateIndex");
					logger.error(e.getMessage(), e);
					logger.exit();
				}
			}
			// 写入文档
			getIwriter().addDocument(doc);
			// 提交数据
			// iwriter.commit(); //TODO,定时提交否则影响吞吐量

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		} finally {
			getUsedcount().decrementAndGet();
		}

	}

	// public synchronized void startCommitThread() {
	// if (trc != null && trc.getState() == Thread.State.NEW) {
	// trc.run();
	// }
	// }

	/**
	 * 构建写索引对象
	 * 
	 * @return 索引类实例对象单例
	 */
	public static CreateIndex GetInstance() {
		try {
			// Date dt = new Date();
			// String temp_str = "";
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			// temp_str = sdf.format(dt);
			if (singleCreate == null) {

				syncInit();
				// singleCreate = new CreateIndex();

			}
			// else if (!Today.equals(temp_str) && !getIsChanged()) {
			// // 更改索引库
			// synchronized (objlock) {
			// ChangeIndexBase();
			// }
			//
			// }
			// } else {
			// return singleCreate;
			// }

		} catch (Exception e) {
			// TODO: handle exception
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
		return singleCreate;

	}

	// private static void ChangeIndexBase() {
	// // TODO Auto-generated method stub
	// while (usedcount.get() != 0) {
	// try {
	// Thread.sleep(10);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// }
	// }
	//
	// try {
	//
	// setIsChanged(true);
	// if (getIwriter() != null) {
	// getIwriter().forceMerge(1, true);
	// getIwriter().close(true);
	// }
	// setIwriter(null);
	// // 构造分词器
	// analyzer = new IKAnalyzer();
	// // 初始化IndexWriter
	// IndexWriterConfig cf = new IndexWriterConfig(Version.LUCENE_45,
	// analyzer);
	// Date dt = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	// Today = sdf.format(dt);
	//
	// Directory directory = FSDirectory.open(new File(ServerConfig
	// .getInstance().getLocalIndexPath() + Today + "\\")); // FSDirectory(new
	// setIwriter(new IndexWriter(directory, cf));
	// // System.out.println("初始化！");
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// Logger logger = LogManager.getLogger("CreateIndex");
	// logger.error(e.getMessage(), e);
	// logger.exit();
	//
	// } finally {
	// setIsChanged(false);
	// }
	// }

	/**
	 * 获取iwriter
	 * 
	 * @return
	 */
	public IndexWriter getIwriter() {
		return iwriter;
	}

	/**
	 * 设置iwriter
	 * 
	 * @param iwriter
	 */
	public void setIwriter(IndexWriter iwriter) {
		this.iwriter = iwriter;
	}

	public Boolean getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}

	public String getToday() {
		return Today;
	}

	public void setToday(String today) {
		this.Today = today;
	}

	public AtomicInteger getUsedcount() {
		return usedcount;
	}

	public void setUsedcount(AtomicInteger usedcount) {
		this.usedcount = usedcount;
	}
}
