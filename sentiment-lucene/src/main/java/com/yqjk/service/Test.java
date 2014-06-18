package com.yqjk.service;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Test {
	public static void mainX() {

		// Logger logger = LogManager.getLogger("CreateIndex");
		// logger.log(Level.ERROR, "测试!");
		// logger.exit();
		try {
			// CreateIndex cx = CreateIndex.GetInstance();
			// String temp_str = "";
			// Date dt = new Date();
			// Calendar calendar = Calendar.getInstance();
			// calendar.add(Calendar.DATE, -40);
			// dt= calendar.getTime();
			// // 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			// temp_str = sdf.format(dt);
			//
			// System.out.println(temp_str);
			// return temp_str;

			// CreateIndex cc=new CreateIndex();
			// cc=new CreateIndex();

			// TestThread t1=new TestThread();
			// TestThread t2=new TestThread();
			// Thread tr1=new Thread(t1);
			// Thread tr2=new Thread(t2);
			// tr1.start();
			// tr2.start();
			// long ll = DateTools.stringToTime(new Date().toString());
			// System.out.println(ll);
			// System.out.println(DateTools.timeToString(ll,
			// Resolution.SECOND));

			// System.out.println(DateTools.dateToString(new Date(),
			// Resolution.SECOND));
			// System.out.println(DateTools.stringToTime(DateTools.dateToString(
			// new Date(), Resolution.SECOND)));
			// System.out.println(DateTools.stringToDate(DateTools.dateToString(
			// new Date(), Resolution.SECOND)));

			// string getCount = "select count(*) as ct from SJCJ_WBL ";
			//
			// DataSet dsCT = DbHelperOra.Query(getCount);
			//
			// int maxct = int.Parse(dsCT.Tables[0].Rows[0]["ct"].ToString()) /
			// 1000 + 1;
			// for (int i = 0; i < maxct; i++)
			// {
			// Thread tr = new Thread(new ParameterizedThreadStart(Tr));
			// tr.Start(i);
			// if ((i + 1) % 100 == 0)
			// {
			// Thread.Sleep(1000 * 60);
			// }
			// }

			// /////////////////////

			// for (int i = 0; i < 1000; i++) {
			// Date dt = new Date();
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			// String Today = sdf.format(dt);
			//
			// Directory directory = FSDirectory.open(new File(ServerConfig
			// .getInstance().getLocalIndexPath() + Today + "\\"));
			// IndexReader rd = DirectoryReader.open(directory);
			//
			// System.out.println(rd.maxDoc());
			// System.out.println(rd.numDocs());
			// }

			// DirectoryReader rd=DirectoryReader.
			// DirectoryReader.openIfChanged(rd.open(directory));

			// ///////////////////////

			TestJdbc conn = new TestJdbc();

			ResultSet rsct;
			rsct = conn.executeQuery("select count(*) as ct from SJCJ_WBL ");

			int maxct = 0;
			try {
				while (rsct.next()) {
					maxct = rsct.getInt("ct") / 1000 + 1;
					// System.out.println(rs.getString("wbdz") + "--"
					// + rsct.getString("wblr"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}

			Thread trp = new Thread(new TestPrintClass());
			trp.start();

			// ExecutorService service = Executors.newCachedThreadPool();
			ExecutorService service = Executors.newFixedThreadPool(300);
			for (int i = 0; i < maxct; i++) {
				service.execute(new TestGetSQLThread(i));
				// rd = DirectoryReader.open(directory);
				// // rd
				// System.out.println(rd.maxDoc());
				// System.out.println(rd.numDocs());

				// Thread tr=new Thread(new TestGetSQLThread(i));
				// tr.start();
				System.out.println(i);
				// rd = DirectoryReader.open(directory);
				// System.out.println(rd.maxDoc());

				// try {
				// Date dt = new Date();
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				// String Today = sdf.format(dt);
				// //
				// Directory directory = FSDirectory.open(new File(ServerConfig
				// .getInstance().getLocalIndexPath() + Today + "\\"));
				// IndexReader rd = DirectoryReader.open(directory);
				//
				//
				// System.out.println(rd.maxDoc());
				// } catch (Exception e) {
				// // TODO: handle exception
				// }

				if (i == 60) {
					break;
				}
				if ((i + 1) % 100 == 0) {
					// break;

					Thread.sleep(1000 * 20);

				}
			}
			// service.shutdown();

			// for (int i = 0; i < 1000; i++) {
			// try {
			// Thread.sleep(1000);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// }
			// rd = DirectoryReader.open(directory);
			//
			// // rd
			// System.out.println(rd.maxDoc());
			// }

			// ResultSet rs;
			//
			// rs = conn.executeQuery("select * from SJCJ_wbL where rownum<5");
			// try {
			// while (rs.next()) {
			// System.out.println(rs.getString("wbdz") + "--"
			// + rs.getString("wblr"));
			// }
			// } catch (Exception e) {
			// e.printStackTrace();
			// }

		} catch (Exception e) {
			// TODO: handle exception
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}

	}

	public static void mainT(String[] args) throws IOException, ParseException,
			java.text.ParseException {
		// mainX();

		try {
			// Thread.sleep(1000 * 20);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// for (int i = 0; i < 10; i++) {

		Date st1 = new Date();
		BooleanQuery bf = new BooleanQuery();
		SearchIndex se = SearchIndex.GetInstance();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "coutent",
				CreateIndex.GetInstance().analyzer);
		qp.setDefaultOperator(Operator.OR);
		se.GetAllSearcher().search(qp.parse("自己按着教程开始建立索引"), 1000);

		Date dt2 = new Date();
		long dif1 = dt2.getTime() - st1.getTime();
		System.out.println("time@:----" + dif1);

		Date st3 = new Date();

		se = SearchIndex.GetInstance();
		QueryParser qpX = new QueryParser(Version.LUCENE_45, "coutent",
				CreateIndex.GetInstance().analyzer);
		// FuzzyQuery fq=new FuzzyQuery(term)
		qpX.setDefaultOperator(Operator.AND);
		se.GetAllSearcher().search(qpX.parse("叶博洋吃饭了"), 1000);

		Date dt4 = new Date();
		long dif2 = dt4.getTime() - st3.getTime();
		System.out.println("time@Real:----" + dif2);

		// TermQuery query1 = new TermQuery(new Term("coutent", "戴手表"));
		// TermQuery query2 = new TermQuery(new Term("coutent", "英特尔中国"));
		// TermQuery query4 = new TermQuery(new Term("coutent", "原图"));
		// Query query1 = qp.parse("不能微笑");
		// Query query2 = qp.parse("不能抽烟");
		// Query query4 = qp.parse("不能戴手表");

		// Query query3 = qp.parse("不能戴手表");
		// bf.add(query1, Occur.MUST);
		// bf.add(query2, Occur.MUST);
		// bf.add(query4, Occur.MUST);

		// FilteredQuery rf= new FilteredQuery()
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -160);
		Date startd = calendar.getTime();

		// DateTools.stringToTime(DateTools.dateToString(startd,
		// Resolution.SECOND))

		// Query ftrs = NumericRangeQuery.newLongRange("releasetime",
		// 1377619200000l, 1377619200000l, true, true);

		Query ftrs = NumericRangeQuery.newLongRange("releasetime", DateTools
				.stringToTime(DateTools.dateToString(startd,
						Resolution.MILLISECOND)), DateTools
				.stringToTime(DateTools.dateToString(new Date(),
						Resolution.MILLISECOND)), true, true);
		// bf.add(ftr, Occur.MUST);

		// se.analyzer.

		Query queryX = qp.parse("不能微笑,不能抽烟" + "不能戴手表");

		TokenStream tokenStream1 = CreateIndex.GetInstance().analyzer
				.tokenStream("txt", "巢湖");
		while (tokenStream1.incrementToken()) {
			CharTermAttribute charTermAttribute = tokenStream1
					.getAttribute(CharTermAttribute.class);
			String temstr = charTermAttribute.toString();
			Query querytem = new TermQuery(new Term("coutent", temstr));
			bf.add(querytem, Occur.MUST);
			// System.out.println(temstr);
		}

		TokenStream tokenStream2 = CreateIndex.GetInstance().analyzer
				.tokenStream("txt", "烟花");
		while (tokenStream2.incrementToken()) {
			CharTermAttribute charTermAttribute = tokenStream2
					.getAttribute(CharTermAttribute.class);
			String temstr = charTermAttribute.toString();
			Query querytem = new TermQuery(new Term("coutent", temstr));
			bf.add(querytem, Occur.MUST);
			// System.out.println(temstr);
		}
		TokenStream tokenStream3 = CreateIndex.GetInstance().analyzer
				.tokenStream("txt", "爆竹");
		while (tokenStream3.incrementToken()) {
			CharTermAttribute charTermAttribute = tokenStream3
					.getAttribute(CharTermAttribute.class);
			String temstr = charTermAttribute.toString();
			Query querytem = new TermQuery(new Term("coutent", temstr));
			bf.add(querytem, Occur.MUST);
			// System.out.println(temstr);
		}

		TokenStream tokenStream4 = CreateIndex.GetInstance().analyzer
				.tokenStream("txt", "爆炸");
		while (tokenStream3.incrementToken()) {
			CharTermAttribute charTermAttribute = tokenStream4
					.getAttribute(CharTermAttribute.class);
			String temstr = charTermAttribute.toString();
			Query querytem = new TermQuery(new Term("coutent", temstr));
			bf.add(querytem, Occur.MUST);
			// System.out.println(temstr);
		}

		// Query query5 = new TermQuery(new Term("coutent", "手表"));
		Date dt = new Date();
		IndexSearcher allSearcher = se.GetAllSearcher();
		IndexSearcher currentSearcher = se.GetCurrentSearcher();
		IndexSearcher historySearcher = se.GetHistorySearcher();
		if (allSearcher != null) {
			// se.ReopenCurrentSearcher();
			Query queryXX = qp.parse("有看过自己银行卡的余额吗");
			TopDocs dcsa = allSearcher.search(queryXX, 100000);

			TopDocs dcsc = currentSearcher.search(queryXX, 100000);
			TopDocs dcsh = historySearcher.search(queryXX, 100000);
			// se.ReopenCurrentSearcher();

			// Query queryXX = qp.parse("有看过自己银行卡的余额吗");
			// TopDocs dcsx = allSearcher.search(queryXX, 100000);
			System.out
					.println("maxA  " + allSearcher.getIndexReader().maxDoc());
			System.out.println("maxC  "
					+ currentSearcher.getIndexReader().maxDoc());
			System.out.println("maxH  "
					+ historySearcher.getIndexReader().maxDoc());
			// MultiFieldQueryParser.parse(arg0, arg1, arg2, arg3, arg4);
			ScoreDoc[] docs = dcsa.scoreDocs;
			System.out.println("countA:" + docs.length);
			System.out.println("totalA:" + dcsa.totalHits);

			ScoreDoc[] docsc = dcsc.scoreDocs;
			System.out.println("countC:" + docsc.length);
			System.out.println("totalC:" + dcsc.totalHits);

			ScoreDoc[] docsh = dcsh.scoreDocs;
			System.out.println("countH:" + docsh.length);
			System.out.println("totalH:" + dcsh.totalHits);

			int i = 0;
			for (ScoreDoc item : docs) {

				// Document doc=item.doc;
				i++;

				Document doc = allSearcher.doc(item.doc);
				// Document doc = allSearcher.doc(2047293);

				// if (doc.get("releasetime") != null) {
				System.out.println("doc=" + item.doc + "-----" + "score="
						+ item.score);
				System.out.println(i + "----" + doc.get("url"));

				// System.out.println(i
				// + "datetime----"
				// + DateTools.stringToDate(DateTools.timeToString(
				// Long.parseLong(doc.get("releasetime")),
				// Resolution.SECOND)));

				// System.out.println(i
				// + "DT----"
				// + DateTools.timeToString(
				// Long.parseLong(doc.get("releasetime")),
				// Resolution.SECOND));

				// DateTools.stringToDate(arg0)

				// System.out.println(i + "datetime----"
				// + DateTools.stringToDate(doc.get("releasetime")));
				// }

				if (i >= 1) {
					break;
				}
			}
			Date dten = new Date();
			long diff = dten.getTime() - dt.getTime();
			System.out.println("time:----" + diff);
			// }

			String[] querystr = new String[] { "安徽城管违法", "自动网站移动化最便捷工具搜索任务设置",
					"为什么我的滔滔打开就是系网站移动化最便捷工具统繁忙", "网站就网站移动化最便捷工具可以恢复正常访问了",
					"神级技术网站移动化最便捷工具", "余额宝云实践分享", "请大家自觉把下载速度网站移动化最便捷工具降低一下",
					"上海云角助网站移动化最便捷工具企业跳跃", "开发者服务网站移动化最便捷工具等话题分",
					"双十一的准备过程中技网站移动化最便捷工具术上的一些突破和亮点" };
			Date ststr = new Date();
			for (int x = 0; x < 100; x++) {

				for (int j = 0; j < 10; j++) {
					currentSearcher = se.GetCurrentSearcher();
					Query queryj = qp.parse(querystr[j]);
					TopDocs dcsj = currentSearcher.search(queryj, 10);
				}
			}
			Date stsend = new Date();
			long difff = stsend.getTime() - ststr.getTime();
			System.out.println("time@:----" + difff);
		}

		// se.allSearcher.
	}

	public static void mainAll(String[] args) throws java.text.ParseException {

		// mainX();
		IndexServer sf = new IndexServer();

		// Date dt = new Date();
		String time = "2013-08-02 9:25:00";

		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = formatter.parse(time);

		String time2 = "2013-09-04 9:26:00";
		Date dt2 = formatter.parse(time2);
		// 新浪微博
		// 腾讯微博

		ReturnModel rm = sf.SearchAll("我的隔壁是三居室 ", dt2, dt2, dt2, dt2, -1,
				null, 1, 10);

		System.out.println(rm.allcount);
		for (int i = 0; i < rm.modle.size(); i++) {
			SearchModel sm = rm.modle.get(i);
			System.out.println("url:" + sm.url);
			System.out.println("sqltype:" + sm.sqltype);
			System.out.println("tableType:" + sm.tabletype);
		}
	}

	public static void mainXXXX(String[] args) throws java.text.ParseException {

		// mainX();
		IndexServer sf = new IndexServer();

		// Date dt = new Date();
		String time = "2013-08-02 9:25:00";

		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = formatter.parse(time);

		String time2 = "2013-09-04 9:26:00";
		Date dt2 = formatter.parse(time2);
		// 新浪微博
		// 腾讯微博
		List<String> intype = new ArrayList<String>();
		intype.add("1");
		intype.add("2");
		intype.add("3");
		intype.add("5");

		List<String> outtype = new ArrayList<String>();
		outtype.add("6");
		outtype.add("4");

		List<String> sidetype = new ArrayList<String>();
		sidetype.add("合肥论坛");

		List<String> lyid = new ArrayList<String>();
		// lyintype.add("218");
		// lyintype.add("123");
		lyid.add("243");
		// lyintype.add("15");

		List<String> strarea = new ArrayList<String>();
		strarea.add("2");

		Date std1 = new Date();
		ReturnModel rm = sf.GetMessageSearchAll(1, 0, "我的隔壁是三居室", date, date,
				null, null, null, null, null, null, "", "", 0, null, null, 1,
				10);
		Date std2 = new Date();
		System.out.println(std2.getTime() - std1.getTime());
		System.out.println("allcount:" + rm.allcount);
		for (int i = 0; i < rm.modle.size(); i++) {
			SearchModel sm = rm.modle.get(i);
			System.out.println("url:" + sm.url);
			System.out.println("sqltype:" + sm.sqltype);
			System.out.println("tableType:" + sm.tabletype);
		}

	}

	public static void mainCC(String[] args) {
		IndexServer sf = new IndexServer();

		// Boolean bl =
		// sf.HistoryExist("http://www.cn0556.com/forum.php?mod=viewthread&tid=324351");
		// Boolean bl =
		// sf.HistoryExist("http://bbs.hefei.cc/thread-14304958-1-1.html");
		Boolean bl = sf
				.HistoryExist("http://bbs.hefei.cc/thread-14304963-1-1.html");

		System.out.println(bl);
		// System.out.println(bl2);
	}

	public static void main(String[] args) throws IOException,
			ParseException, java.text.ParseException {
		IndexServer sf = new IndexServer();
		Date std2 = new Date();

		// (1, "安徽合肥", std2, std2, null, null, null, null, null, null, null,
		// null, null, null, null, 1);
		Calendar c = Calendar.getInstance();
		// System.out.println(std2.getDate());
		// List<String> list=new ArrayList<String>();
		// list.add("7");
		// list.add("154");
		// list.add("64");
		// list.add("142");

		String time = "2002-10-28 00:00:01";

		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = formatter.parse(time);

		String time2 = "2014-01-08 23:59:59";
		Date dt2 = formatter.parse(time2);

		Date st1 = new Date();
		// "(\"合肥\" AND \"安徽\" AND \"城管\" AND \"违法\") NOT \"警察\""
		// for (int i = 0; i < 1; i++) {
//		sf.GetZhuanTiCountGroupModel(2, "(\"张成泽\")", date, dt2, null, null,
//				null, null, null, null, null, "", 0, null, null, 3);
		
		sf.GetZhuanTiCountModel(2, 0, "(\"贿赂滥用职权\" AND \"市长\")" , date, dt2, null, null, null,
		 null, null, null, "", "", 1, null, null);

		// ReturnModel rm = sf.SearchAll("夏天 ", date, date, -1, "", 1, 10);
		// }

		Date st2 = new Date();
		long difff = st2.getTime() - st1.getTime();
		System.out.println("time@:----" + difff);

		// try {
		// Thread.sleep(1000 * 60 * 2);
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

		// sf.GetZhuanTiCountGroupModel(2,
		// "(\"合肥\" AND \"安徽\" AND \"城管\" AND \"违法\") NOT \"警察\"", date, dt2,
		// null, null, null,
		// null, null, null, null, "", 0, null, null, 3);

		// sf.GetZhuanTiCountModel(2, 0, "安徽合肥", date, dt2, null, null, null,
		// null, null, null, "", "", 0, null, null);

		// sf.test("http://bbs.365jia.cn/thread-1448509-1-1.html");
		// sf.SearchTest("安徽合肥", 0, 1);

	}
}
