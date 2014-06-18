package com.yqjk.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReturnModel  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000555555L;
	/**
	 * 总记录数
	 */
	public int allcount = 0;
	/**
	 * 搜索对象结果集
	 */
	public  List<SearchModel> modle = new ArrayList<SearchModel>();
	/**
	 * 分词结果集合
	 */
	public List<String> analyzerlist=new ArrayList<String>(); 
}
