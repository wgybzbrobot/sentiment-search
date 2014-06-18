package com.yqjk.service;

public class CreateIndexThread implements Runnable {

	public IndexModel indexmodel = null;

	public CreateIndexThread(IndexModel mod) {
		indexmodel = mod;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		CreateIndex cc = CreateIndex.GetInstance();
		//
		// if (cc.trc != null && cc.trc.getState() == Thread.State.NEW) {
		// cc.startCommitThread();
		// }

		while (cc.getIwriter() == null) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				// TODO: handle exception
				// e.printStackTrace();
			}

		}
		cc.WriteIndex(indexmodel);
	}

}
