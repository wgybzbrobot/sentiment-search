package com.yqjk.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ChangeCurrentIndex implements Runnable {

	public SearchIndex single = null;

	public ChangeCurrentIndex(SearchIndex index) {
		single = index;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 50);
			} catch (Exception e) {

			}

			try {
				// 当天
				Date dt = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String Today = sdf.format(dt);
				String path = ServerConfig.getInstance().getLocalIndexPath()
						+ Today + "/";
				File file = new File(path);

				if (!single.List.get(0).isCurrent()) {
					if (file.exists() && file.listFiles().length > 1) {
						Directory directory = FSDirectory.open(new File(
								ServerConfig.getInstance().getLocalIndexPath()
										+ Today + "/"));
						DirectoryReader rd = DirectoryReader.open(directory);
						single.List.set(0, rd);
					} else {
						DirectoryReader rd = DirectoryReader.open(CreateIndex
								.GetInstance().getIwriter(), true);
						single.List.set(0, rd);
					}
				}

			} catch (Exception e) {
				Logger logger = LogManager.getLogger("CreateIndex");
				logger.error(e.getMessage(), e);
				logger.exit();
			}

		}
	}
}
