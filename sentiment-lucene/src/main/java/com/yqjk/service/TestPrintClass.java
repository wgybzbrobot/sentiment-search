package com.yqjk.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class TestPrintClass implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("A1");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("A2");
			}
			try {
				Date dt = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String Today = sdf.format(dt);
				//
				Directory directory = FSDirectory.open(new File(ServerConfig
						.getInstance().getLocalIndexPath() + Today + "\\"));
				IndexReader rd = DirectoryReader.open(directory);

				System.out.println(rd.maxDoc());
			} catch (Exception e) {
				// TODO: handle exception
				// Logger logger = LogManager.getLogger("CreateIndex");
				// logger.error(e.getMessage(), e);
				// logger.exit();
			}
		}

	}

}
