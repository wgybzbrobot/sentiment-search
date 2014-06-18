package com.yqjk.service;

import java.util.List;

import com.yqjk.service.TestSendZhuanTiObj.objectsendGroupMoudle;

public class TestZTGroutCountThread implements Runnable {

	public int index;
	objectsendGroupMoudle Model;

	List<NameKeySort> retmodel;

	public TestZTGroutCountThread(objectsendGroupMoudle mo) {
		Model = mo;
	}

	@Override
	public void run() {

		IndexServer sr = new IndexServer();
		retmodel = sr.GetZhuanTiGroup(Model.storetype, Model.searchkey,
				Model.start, Model.end, Model.typenoinlist, Model.sitenamelist,
				Model.lyidinlist, Model.typenooutlist, Model.lyidoutList,
				Model.qqqunlist, Model.oldkeys, Model.author, Model.jingnei,
				Model.listareain, Model.listareaout, Model.grouptype);
	}

}
