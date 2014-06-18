package com.yqjk.service;

import java.io.Serializable;
import java.util.Date;

/**
 * 索引对象实体扩充，新增‘抓取时间’
 * 
 */
public class IndexModelExt  implements Serializable {

	private static final long serialVersionUID = 2766274206307532333L;
	
	public Date fetchTime = null;

}
