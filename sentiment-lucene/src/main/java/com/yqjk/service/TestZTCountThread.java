package com.yqjk.service;

import java.util.List;

import com.yqjk.service.TestSendZhuanTiObj.objectsendMoudel;

public class TestZTCountThread implements Runnable {

	public int index;
	objectsendMoudel Model;

	public List<DayCountModel> retmodel;

	public TestZTCountThread(objectsendMoudel mo) {
		Model = mo;
	}

	@Override
	public void run() {

		IndexServer sr = new IndexServer();
		retmodel = sr.GetZhuanTiCountModel(Model.storetype, Model.sortbyfbtime,
				Model.searchkey, Model.start, Model.end, Model.typenoinlist,
				Model.sitenamelist, Model.lyidinlist, Model.typenooutlist,
				Model.lyidoutList, Model.qqqunlist, Model.oldkeys,
				Model.author, Model.jingnei, Model.listareain,
				Model.listareaout);
	}
}
