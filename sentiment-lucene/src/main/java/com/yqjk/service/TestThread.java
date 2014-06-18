package com.yqjk.service;

//import java.io.IOException;

//import java.io.IOException;
//import java.nio.CharBuffer;

public class TestThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		CreateIndex cc = CreateIndex.GetInstance();
		for (int i = 0; i < 100; i++) {
			// System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				cc = CreateIndex.GetInstance();
				cc = CreateIndex.GetInstance();
				cc = CreateIndex.GetInstance();
				cc = CreateIndex.GetInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
