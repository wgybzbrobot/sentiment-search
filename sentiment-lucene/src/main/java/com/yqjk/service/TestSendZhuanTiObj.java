package com.yqjk.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TestSendZhuanTiObj  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 766274206307532555L;
	
	List<testObj> ObjSend;
	
	public class testObj{
		int indexX;//序号
		objectsendMoudel md;//发送的GetZhuanTiCountModel
		objectsendGroupMoudle gmd;//发送的GetZhuanTiCountModel
	}
	

	public class objectsendGroupMoudle{	
		int storetype;
		String searchkey;
		Date start;
		Date end;
		List<String> typenoinlist;
		List<String> sitenamelist;
		List<String> lyidinlist;
		List<String> typenooutlist;
		List<String> lyidoutList;
		List<String> qqqunlist;
		String oldkeys;
		String author;
		int jingnei;	
		List<String> listareain;
		List<String> listareaout;
		int grouptype;
	}
	
	public class objectsendMoudel{
		
		int storetype;
		int sortbyfbtime;
		String searchkey;
		Date start;
		Date end;
		List<String> typenoinlist;
 List<String> sitenamelist;
		List<String> lyidinlist;
		List<String> typenooutlist;
		List<String> lyidoutList;
		List<String> qqqunlist;
		String oldkeys;
		String author;
		int jingnei;
		List<String> listareain;
		List<String> listareaout;
	}
}



