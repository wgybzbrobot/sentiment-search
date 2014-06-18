package com.yqjk.service;

import java.sql.ResultSet;
import java.util.Date;
import java.util.SortedMap;

//import javax.servlet.jsp.jstl.sql.Result;
//import javax.servlet.jsp.jstl.sql.ResultSupport;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;

public class TestGetSQLThread implements Runnable {

	int page = 0;

	// ExecutorService service =
	// Executors.newFixedThreadPool(Integer.MAX_VALUE);

	public TestGetSQLThread(int i) {
		page = i;

	}

	@Override
	public void run() {
		TestJdbc conn = new TestJdbc();
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM (SELECT T.*, ROWNUM RN FROM SJCJ_WBL T  WHERE  ROWNUM < "
				+ (page * 1000 + 1000) + ")	 WHERE RN >= " + (page * 1000);
		ResultSet rs;
		rs = conn.executeQuery(sql);
		
//		Result rst = ResultSupport.toResult(rs);
//		conn.close();
		try {
			
//			for (SortedMap item : rst.getRows()) {
////				String value="";
////				value=	(String) item.get("WBDZ");
////				System.out.println(value+page);
//				
//				 IndexModel model = new IndexModel();
//				 model.url =	(String) item.get("WBDZ");
//				 model.title = "";
//				 model.coutent = (String) item.get("WBLR");
//				 System.out.println(item.get("FBSJ"));
//				 model.releasetime = (Date) item.get("FBSJ");
//				 model.tablename = "SJCJ_WBL";
//				 model.auther = (String) item.get("FBYH");
//				 Thread tr=new Thread(new CreateIndexThread(model));
//				 tr.run();
//				// // service.execute(new CreateIndexThread(model));
//			}
//			
			
			
			 while (rs.next()) {
			 IndexModel model = new IndexModel();
			 model.url = rs.getString("WBDZ");
			 model.title = "";
			 model.coutent = rs.getString("WBLR");
//			 System.out.println(rs.getTimestamp("FBSJ"));
			 model.releasetime = rs.getTimestamp("FBSJ");
			 model.zdbs = "2";
			 model.auther = rs.getString("FBYH");
			 // service.execute(new CreateIndexThread(model));
			 
			 model.jingnei = "1";
			 
			 model.lzid="9";
			 model.zdmc="百度搜索";
			 model.qqgroupno="111111";
			 model.quyucode="203165";
			
			 CreateIndexThread ss=new CreateIndexThread(model);
			 ss.run();
			
			 // Thread trx=new Thread(new CreateIndexThread(model));
			 // trx.run();
			
			 // = rs.getInt("ct") ;
			 // System.out.println(rs.getString("wbdz") + "--"
			 // + rsct.getString("wblr"));
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			conn.close();
		}
	}
}
