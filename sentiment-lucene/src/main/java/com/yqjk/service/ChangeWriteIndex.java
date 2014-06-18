package com.yqjk.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ChangeWriteIndex implements Runnable {

	CreateIndex single = null;

	public ChangeWriteIndex(CreateIndex index) {
		single = index;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			try {
				Thread.sleep(1000 * 60);
			} catch (Exception e) {
				// TODO: handle exception
			}

			Date dt = new Date();
			String temp_str = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			temp_str = sdf.format(dt);
			// CreateIndex single = CreateIndex.GetInstance();
			if (!single.getToday().equals(temp_str) && !single.getIsChanged()) {
				synchronized (single.objlock) {
					ChangeIndexBase(single);
				}
			}

		}

	}

	private void ChangeIndexBase(CreateIndex single) {
		// TODO Auto-generated method stub
		while (single.getUsedcount().get() != 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}

		try {

			single.setIsChanged(true);
			if (single.getIwriter() != null) {
				single.getIwriter().forceMerge(1, true);
				single.getIwriter().forceMerge(1, true);
				single.getIwriter().close(true);
			}
			single.setIwriter(null);
			// 构造分词器
			single.analyzer = new IKAnalyzer();
			// 初始化IndexWriter
			IndexWriterConfig cf = new IndexWriterConfig(Version.LUCENE_45,
					single.analyzer);
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			single.setToday(sdf.format(dt));

			Directory directory = FSDirectory.open(new File(ServerConfig
					.getInstance().getLocalIndexPath()
					+ single.getToday()
					+ "/")); // FSDirectory(new
			single.setIwriter(new IndexWriter(directory, cf));
			// System.out.println("初始化！");

		} catch (Exception e) {
			// TODO: handle exception
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();

		} finally {
			single.setIsChanged(false);
		}
	}

}
