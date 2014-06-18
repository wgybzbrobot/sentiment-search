package com.yqjk.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateIndexCommit implements Runnable {

	// CreateIndex indexcreate;

	public static CreateIndexCommit once;

	public CreateIndex singleindexwrite = null;

	// private CreateIndexCommit(CreateIndex index) {

	// indexcreate = index;
	// }

	private CreateIndexCommit(CreateIndex indexwrite) {
		singleindexwrite = indexwrite;
	}

	public static CreateIndexCommit GetInstance(CreateIndex indexwrite) {
		if (once == null) {
			// once = new CreateIndexCommit(index);
			System.out.println("run1");
			once = new CreateIndexCommit(indexwrite);
			System.out.println("run2");
		}
		return once;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (singleindexwrite.getIwriter() != null) {
				try {
					System.out.println("runx1");
					Thread.sleep(1000 * ServerConfig.getInstance()
							.getIndexCommitSecond());
					System.out.println("runx2");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("Erunx2");
					Logger logger = LogManager.getLogger("CreateIndex");
					logger.error(e.getMessage(), e);
					logger.exit();
				}

				try {
					System.out.println("comm1");
					if (!singleindexwrite.getIsChanged()) {
						singleindexwrite.getIwriter().commit();
					}
					System.out.println("comm2");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
