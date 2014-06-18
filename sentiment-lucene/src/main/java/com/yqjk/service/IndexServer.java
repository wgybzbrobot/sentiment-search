package com.yqjk.service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections; 
import java.util.Date; 
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser; 
import org.apache.lucene.queryparser.classic.QueryParser.Operator; 
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

public class IndexServer {

	/**
	 * 将<code>IndexModel</code>创建索引服务
	 */
	public void CreateIndexServer(IndexModel model) {
		Thread thread = new Thread(new CreateIndexThread(model));
		thread.run();
	}

	/**
	 * 
	 * @param storetype
	 *            搜索索引类型，0：当前索引数据，1：历史索引数据，2：全部索引数据历史+当天
	 * @param searchkey
	 *            搜索关键字
	 * @param start
	 *            抓取时间：开始时间 必须小于结束时间
	 * @param end
	 *            抓取时间：结束时间
	 * @param typenoinlist
	 *            包含类型列表（1资讯2论坛3微博4博客5QQ6搜索0其他-回复数据）
	 * @param sitenamelist
	 *            包含站点名称列表 （如：合肥论坛，新浪微博 等）
	 * @param lyidinlist
	 *            包含来源ID列表（分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param typenooutlist
	 *            不包含类型列表 （1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * @param lyidoutList
	 *            不包含来源列表 （分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param qqqunlist
	 *            腾讯群号列表
	 * @param oldkeys
	 *            在次搜索时的上次搜索关键字
	 * @param author
	 *            发布用户
	 * @param jingnei
	 *            境内境外 0：所有，1：境内，2：境外
	 * @param listareain
	 *            所在区域列表
	 * @param listareaout
	 *            区域列表，结果不包含此区域内数据
	 * @param grouptype
	 *            分组标识 1：站点名称,2:作者,3:站点标识
	 * @return 最多15个集合列表按数量排序 name:名称，count:数量
	 *         ，当按站点标识分组时name返回（1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * 
	 */
	public List<DayCountGroupModel> GetZhuanTiCountGroupModel(int storetype,
			String searchkey, Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int grouptype) {

		List<DayCountGroupModel> retlist = new ArrayList<DayCountGroupModel>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(start);
		Date tempdate = calendar.getTime(); // 这个时间就是日期往后推一天的结果

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 格式字符串

		while (tempdate.before(end)) {

			Calendar endcal = new GregorianCalendar();
			endcal.setTime(tempdate);
			endcal.add(Calendar.HOUR, 24);
			Date tenpendtime = endcal.getTime();
			List<NameKeySort> modell = GetZhuanTiGroup(storetype, searchkey,
					tempdate, tenpendtime, typenoinlist, sitenamelist,
					lyidinlist, typenooutlist, lyidoutList, qqqunlist, oldkeys,
					author, jingnei, listareain, listareaout, grouptype);
			DayCountGroupModel md = new DayCountGroupModel();
			md.date = format.format(tempdate);
			md.modellist = modell;
			retlist.add(md);

			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			tempdate = calendar.getTime();
		}

		return retlist;

	}

	/**
	 * 
	 * @param storetype
	 *            搜索索引类型，0：当前索引数据，1：历史索引数据，2：全部索引数据历史+当天
	 * @param sortbyfbtime
	 *            发布时间排序选项 0：不排序，1：发布时间降序，2：发布时间升序
	 * @param searchkey
	 *            搜索关键字
	 * @param start
	 *            抓取时间：开始时间 必须小于结束时间
	 * @param end
	 *            抓取时间：结束时间
	 * @param typenoinlist
	 *            包含类型列表（1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * @param sitenamelist
	 *            包含站点名称列表 （如：合肥论坛，新浪微博 等）
	 * @param lyidinlist
	 *            包含来源ID列表（分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param typenooutlist
	 *            不包含类型列表 （1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * @param lyidoutList
	 *            不包含来源列表 （分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param qqqunlist
	 *            腾讯群号列表
	 * @param oldkeys
	 *            在次搜索时的上次搜索关键字
	 * @param author
	 *            发布用户
	 * @param jingnei
	 *            境内境外 0：所有，1：境内，2：境外
	 * @param listareain
	 *            所在区域列表
	 * @param listareaout
	 *            区域列表，结果不包含此区域内数据
	 * @return
	 */
	public List<DayCountModel> GetZhuanTiCountModel(int storetype,
			int sortbyfbtime, String searchkey, Date start, Date end,
			List<String> typenoinlist, List<String> sitenamelist,
			List<String> lyidinlist, List<String> typenooutlist,
			List<String> lyidoutList, List<String> qqqunlist, String oldkeys,
			String author, int jingnei, List<String> listareain,
			List<String> listareaout) {

		List<DayCountModel> retlist = new ArrayList<DayCountModel>();

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(start);
		Date tempdate = calendar.getTime(); // 这个时间就是日期往后推一天的结果

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 格式字符串

		while (tempdate.before(end)) {

			Calendar endcal = new GregorianCalendar();
			endcal.setTime(tempdate);
			endcal.add(Calendar.HOUR, 24);
			Date tenpendtime = endcal.getTime();
			ReturnModel model = GetZhuanTiSearchAll(storetype, sortbyfbtime,
					searchkey, tempdate, tenpendtime, typenoinlist,
					sitenamelist, lyidinlist, typenooutlist, lyidoutList,
					qqqunlist, oldkeys, author, jingnei, listareain,
					listareaout, 1, 1);
			DayCountModel md = new DayCountModel();
			md.date = format.format(tempdate);
			md.count = model.allcount;
			retlist.add(md);
			// System.out.println(md.date+":"+md.count);

			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			tempdate = calendar.getTime();
		}
		return retlist;
	}

	/**
	 * 
	 * @param storetype
	 *            搜索索引类型，0：当前索引数据，1：历史索引数据，2：全部索引数据历史+当天
	 * @param searchkey
	 *            搜索关键字
	 * @param start
	 *            抓取时间：开始时间 （和结束时间相等则不按时间搜索）
	 * @param end
	 *            抓取时间：结束时间 （和开始时间相当则不按时间搜索）
	 * @param typenoinlist
	 *            包含类型列表（1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * @param sitenamelist
	 *            包含站点名称列表 （如：合肥论坛，新浪微博 等）
	 * @param lyidinlist
	 *            包含来源ID列表（分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param typenooutlist
	 *            不包含类型列表 （1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * @param lyidoutList
	 *            不包含来源列表 （分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param qqqunlist
	 *            腾讯群号列表
	 * @param oldkeys
	 *            在次搜索时的上次搜索关键字
	 * @param author
	 *            发布用户
	 * @param jingnei
	 *            境内境外 0：所有，1：境内，2：境外
	 * @param listareain
	 *            所在区域列表
	 * @param listareaout
	 *            区域列表，结果不包含此区域内数据
	 * @param grouptype
	 *            分组标识 1：站点名称,2:作者,3:站点标识
	 * @return 最多15个集合列表按数量排序 name:名称，count:数量
	 *         ，当按站点标识分组时name返回（1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * 
	 */
	public List<NameKeySort> GetZhuanTiGroup(int storetype, String searchkey,
			Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int grouptype) {
		List<NameKeySort> objreturn = new ArrayList<NameKeySort>();
		List<NameKeySort> objlist = new ArrayList<NameKeySort>();
		SearchIndex se = SearchIndex.GetInstance();
		try {

			BooleanQuery blfilterquery = new BooleanQuery();// 过滤器查询对象

			BooleanQuery blsearchquery = new BooleanQuery();// 查询对象

			// 构建filter
			Filter flmain = null;
			// 先构建filter的查询
			// 查询时间不相等时增加过滤时间
			if (start.equals(end)) {

			} else {
				Query timequeryfilter = NumericRangeQuery.newLongRange(
						"releasetime", DateTools.stringToTime(DateTools
								.dateToString(start, Resolution.MILLISECOND)),
						DateTools.stringToTime(DateTools.dateToString(end,
								Resolution.MILLISECOND)), true, false);
				blfilterquery.add(timequeryfilter, Occur.MUST);
			}
			// 包含类型列表(站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他))
			if (typenoinlist != null && !typenoinlist.isEmpty()) {
				BooleanQuery typeboolquery = new BooleanQuery();
				for (String typeno : typenoinlist) {
					Term tr = new Term("zdbs", typeno);
					Query ternquery = new TermQuery(tr);
					typeboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(typeboolquery, Occur.MUST);
			}

			// 站点名称列表
			if (sitenamelist != null && !sitenamelist.isEmpty()) {
				BooleanQuery siteboolquery = new BooleanQuery();
				for (String sitename : sitenamelist) {
					Term tr = new Term("zdmc", sitename);
					Query ternquery = new TermQuery(tr);
					siteboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(siteboolquery, Occur.MUST);
			}
			// 来源ID列表
			if (lyidinlist != null && !lyidinlist.isEmpty()) {
				BooleanQuery lyboolquery = new BooleanQuery();
				for (String lyid : lyidinlist) {
					Term tr = new Term("lzid", lyid);
					Query ternquery = new TermQuery(tr);
					lyboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(lyboolquery, Occur.MUST);
			}

			// 不包含类型列表 (站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他))
			if (typenooutlist != null && !typenooutlist.isEmpty()) {
				for (String typeno : typenooutlist) {
					Term tr = new Term("zdbs", typeno);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// 不包含来源ID列表
			if (lyidoutList != null && !lyidoutList.isEmpty()) {
				for (String lyid : lyidoutList) {
					Term tr = new Term("lzid", lyid);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// QQ群号
			if (qqqunlist != null && !qqqunlist.isEmpty()) {
				BooleanQuery qqqunboolquery = new BooleanQuery();
				for (String qqqun : qqqunlist) {
					Term tr = new Term("qqgroupno", qqqun);
					Query ternquery = new TermQuery(tr);
					qqqunboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(qqqunboolquery, Occur.MUST);
			}

			// 多域查询分词
			String[] strfield = { "title", "coutent" };
			MultiFieldQueryParser qp = new MultiFieldQueryParser(
					Version.LUCENE_45, strfield,
					CreateIndex.GetInstance().analyzer);
			qp.setDefaultOperator(Operator.AND);
			// 旧词过滤器
			if (oldkeys != null && !oldkeys.equals("")) {
				Query oldkeyquery = qp.parse(oldkeys);
				blfilterquery.add(oldkeyquery, Occur.MUST);

			} else {
				blfilterquery.add(new MatchAllDocsQuery(), Occur.MUST);
			}

			// 按发布人过滤
			if (author != null && !author.equals("")) {

				Term tr = new Term("auther", author);
				Query tempquery = new TermQuery(tr);

				blfilterquery.add(tempquery, Occur.MUST);
			}

			// 过滤境内境外 0全部：1境内，2境外
			if (jingnei != 0) {
				Term tr = new Term("jingnei", String.valueOf(jingnei));
				Query ternquery = new TermQuery(tr);
				blfilterquery.add(ternquery, Occur.MUST);
			}

			// 本地搜索 当前本地区域列表
			if (listareain != null && !listareain.isEmpty()) {
				BooleanQuery tempbool = new BooleanQuery();
				for (String quyucode : listareain) {
					Term tr = new Term("quyucode", quyucode);
					Query ternquery = new TermQuery(tr);
					tempbool.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(tempbool, Occur.MUST);
			}

			// 外地搜索 当前区域列表
			if (listareaout != null && !listareaout.isEmpty()) {

				for (String quyucode : listareaout) {
					Term tr = new Term("quyucode", quyucode);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// 关键词检索
			if (!searchkey.equals("")) {
				Query oldkeyquery = qp.parse(searchkey);
				blsearchquery.add(oldkeyquery, Occur.MUST);

			} else {
				blsearchquery.add(new MatchAllDocsQuery(), Occur.MUST);
			}
			// 封装过滤器
			flmain = new QueryWrapperFilter(blfilterquery);

			IndexSearcher searcher = null;
			if (storetype == 0) {// 当天数据
				searcher = se.GetCurrentSearcher();
			} else if (storetype == 1) {// 历史数据
				searcher = se.GetHistorySearcher();
			} else {// 所有数据
				searcher = se.GetAllSearcher();
			}

			// 没有索引库直接退出
			if (searcher == null) {
				return objreturn;
			}

			String groupField = "zdbs"; // 默认站点标识
			switch (grouptype) {
			case 1:
				groupField = "zdmc";// 站点名称
				break;
			case 2:
				groupField = "auther"; // 作者
				break;
			case 3:
				groupField = "zdbs"; // 站点标识
				break;
			default:
				break;
			}

			GroupingSearch gSearch = new GroupingSearch(groupField);// 分组查询按照place分组
			TopGroups<Object> t = gSearch.search(searcher, flmain,
					blsearchquery, 0, 1000);// 设置返回数据

			GroupDocs<Object>[] g = t.groups;// 获取分组总数

			for (int i = 0; i < g.length; i++) {
				ScoreDoc[] sd = g[i].scoreDocs;
				String str = searcher.doc(sd[0].doc).get(groupField);
				NameKeySort stmod = new NameKeySort();
				stmod.name = str;
				stmod.count = g[i].totalHits;
				objlist.add(stmod);
			}

			Collections.sort(objlist, new ComparatorNameKeySort());

			for (int i = 0; i < objlist.size(); i++) {
				if (objlist.get(i).name == null)
					continue;
				objreturn.add(objlist.get(i));
				if (objreturn.size() >= 10) {
					break;
				}
			}
		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}

		return objreturn;
	}

	/**
	 * 
	 * @param storetype
	 *            搜索索引类型，0：当前索引数据，1：历史索引数据，2：全部索引数据历史+当天
	 * @param sortbyfbtime
	 *            发布时间排序选项 0：不排序，1：发布时间降序，2：发布时间升序
	 * @param searchkey
	 *            搜索关键字
	 * @param start
	 *            抓取时间：开始时间 （和结束时间相等则不按时间搜索）
	 * @param end
	 *            抓取时间：结束时间 （和开始时间相当则不按时间搜索）
	 * @param typenoinlist
	 *            包含类型列表（1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * @param sitenamelist
	 *            包含站点名称列表 （如：合肥论坛，新浪微博 等）
	 * @param lyidinlist
	 *            包含来源ID列表（分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param typenooutlist
	 *            不包含类型列表 （1资讯2论坛3微博4博客5QQ6搜索0其他）
	 * @param lyidoutList
	 *            不包含来源列表 （分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param qqqunlist
	 *            腾讯群号列表
	 * @param oldkeys
	 *            在次搜索时的上次搜索关键字
	 * @param author
	 *            发布用户
	 * @param jingnei
	 *            境内境外 0：所有，1：境内，2：境外
	 * @param listareain
	 *            所在区域列表
	 * @param listareaout
	 *            区域列表，结果不包含此区域内数据
	 * @param pageindex
	 *            当前页码 如第一页 传1
	 * @param pagecount
	 *            每页数据量 每页十条就传10
	 * @return
	 */
	public ReturnModel GetZhuanTiSearchAll(int storetype, int sortbyfbtime,
			String searchkey, Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex,
			int pagecount) {
		ReturnModel retmodel = new ReturnModel();
		SearchIndex se = SearchIndex.GetInstance();
		try {
			BooleanQuery blfilterquery = new BooleanQuery();// 过滤器查询对象

			BooleanQuery blsearchquery = new BooleanQuery();// 查询对象

			// 构建filter
			Filter flmain = null;
			// 先构建filter的查询
			// 查询时间不相等时增加过滤时间
			if (start.equals(end)) {

			} else {
				Query timequeryfilter = NumericRangeQuery.newLongRange(
						"releasetime", DateTools.stringToTime(DateTools
								.dateToString(start, Resolution.MILLISECOND)),
						DateTools.stringToTime(DateTools.dateToString(end,
								Resolution.MILLISECOND)), true, false);
				blfilterquery.add(timequeryfilter, Occur.MUST);
			}
			// 包含类型列表(站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他))
			if (typenoinlist != null && !typenoinlist.isEmpty()) {
				BooleanQuery typeboolquery = new BooleanQuery();
				for (String typeno : typenoinlist) {
					Term tr = new Term("zdbs", typeno);
					Query ternquery = new TermQuery(tr);
					typeboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(typeboolquery, Occur.MUST);
			}

			// 站点名称列表
			if (sitenamelist != null && !sitenamelist.isEmpty()) {
				BooleanQuery siteboolquery = new BooleanQuery();
				for (String sitename : sitenamelist) {
					Term tr = new Term("zdmc", sitename);
					Query ternquery = new TermQuery(tr);
					siteboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(siteboolquery, Occur.MUST);
			}
			// 来源ID列表
			if (lyidinlist != null && !lyidinlist.isEmpty()) {
				BooleanQuery lyboolquery = new BooleanQuery();
				for (String lyid : lyidinlist) {
					Term tr = new Term("lzid", lyid);
					Query ternquery = new TermQuery(tr);
					lyboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(lyboolquery, Occur.MUST);
			}

			// 不包含类型列表 (站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他))
			if (typenooutlist != null && !typenooutlist.isEmpty()) {
				for (String typeno : typenooutlist) {
					Term tr = new Term("zdbs", typeno);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// 不包含来源ID列表
			if (lyidoutList != null && !lyidoutList.isEmpty()) {
				for (String lyid : lyidoutList) {
					Term tr = new Term("lzid", lyid);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// QQ群号
			if (qqqunlist != null && !qqqunlist.isEmpty()) {
				BooleanQuery qqqunboolquery = new BooleanQuery();
				for (String qqqun : qqqunlist) {
					Term tr = new Term("qqgroupno", qqqun);
					Query ternquery = new TermQuery(tr);
					qqqunboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(qqqunboolquery, Occur.MUST);
			}

			// 多域查询分词
			String[] strfield = { "title", "coutent" };
			MultiFieldQueryParser qp = new MultiFieldQueryParser(
					Version.LUCENE_45, strfield,
					CreateIndex.GetInstance().analyzer);
			qp.setDefaultOperator(Operator.AND);
			// 旧词过滤器
			if (!oldkeys.equals("")) {
				Query oldkeyquery = qp.parse(oldkeys);
				blfilterquery.add(oldkeyquery, Occur.MUST);

			} else {
				blfilterquery.add(new MatchAllDocsQuery(), Occur.MUST);
			}

			// 按发布人过滤
			if (author != null && !author.equals("")) {

				Term tr = new Term("auther", author);
				Query tempquery = new TermQuery(tr);

				blfilterquery.add(tempquery, Occur.MUST);
			}

			// 过滤境内境外 0全部：1境内，2境外
			if (jingnei != 0) {
				Term tr = new Term("jingnei", String.valueOf(jingnei));
				Query ternquery = new TermQuery(tr);
				blfilterquery.add(ternquery, Occur.MUST);
			}

			// 本地搜索 当前本地区域列表
			if (listareain != null && !listareain.isEmpty()) {
				BooleanQuery tempbool = new BooleanQuery();
				for (String quyucode : listareain) {
					Term tr = new Term("quyucode", quyucode);
					Query ternquery = new TermQuery(tr);
					tempbool.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(tempbool, Occur.MUST);
			}

			// 外地搜索 当前区域列表
			if (listareaout != null && !listareaout.isEmpty()) {

				for (String quyucode : listareaout) {
					Term tr = new Term("quyucode", quyucode);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// 关键词检索
			if (!searchkey.equals("")) {
				Query oldkeyquery = qp.parse(searchkey);
				blsearchquery.add(oldkeyquery, Occur.MUST);

			} else {
				blsearchquery.add(new MatchAllDocsQuery(), Occur.MUST);
			}
			// 封装过滤器
			flmain = new QueryWrapperFilter(blfilterquery);
			TopDocs docsall = null;
			IndexSearcher searcher = null;
			if (storetype == 0) {// 当天数据
				searcher = se.GetCurrentSearcher();
			} else if (storetype == 1) {// 历史数据
				searcher = se.GetHistorySearcher();
			} else {// 所有数据
				searcher = se.GetAllSearcher();
			}

			// 没有索引库直接退出
			if (searcher == null) {
				return retmodel;
			}

			if (sortbyfbtime == 0) {// 不排序
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount);
			} else if (sortbyfbtime == 1) {// 发布时间降序
				Sort str = new Sort();
				str.setSort(new SortField("releasetime", Type.LONG, true));
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount, str);
			} else {// 发布时间升序
				Sort str = new Sort();
				str.setSort(new SortField("releasetime", Type.LONG, false));
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount, str);
			}

			ScoreDoc[] docs = docsall.scoreDocs;
			retmodel.allcount = docsall.totalHits;// 总数量
			// 当前页数据
			for (int i = pageindex * pagecount - pagecount; i < docs.length
					&& i < pageindex * pagecount; i++) {
				SearchModel model = new SearchModel();
				Document doc = searcher.doc(docs[i].doc);

				model.url = doc.get("url");
				model.tabletype = doc.get("zdbs");

				if (doc.get("spy") != null) {
					Date dt = DateTools.stringToDate(DateTools.timeToString(
							Long.parseLong(doc.get("spy")), Resolution.SECOND));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					Date dtnow = new Date();
					dtnow = sdf.parse(sdf.format(dtnow));
					if (dt.before(dtnow)) {
						model.sqltype = "1";
					} else {
						model.sqltype = "0";
					}
				}
				retmodel.modle.add(model);
			}
		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
		return retmodel;
	}

	/**
	 * 全网搜索接口
	 * 
	 * @param stringkey
	 *            搜索关键词
	 * @param start
	 *            发布时间开始时间
	 * @param end
	 *            发布时间结束时间
	 * @param startspy
	 *            监测时间开始时间
	 * @param endspy
	 *            监测时间结束时间
	 * @param typeNo
	 *            站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他)
	 * @param siteName
	 *            站点名称
	 * @param pageindex
	 *            当前页
	 * @param pagecount
	 *            每页多少条
	 * @return
	 */
	public ReturnModel SearchAll(String stringkey, Date start, Date end,
			Date startspy, Date endspy, int typeNo, List<String> siteNameList,
			int pageindex, int pagecount) {
		ReturnModel retmodel = new ReturnModel();
		try {

			// 分词
			retmodel.analyzerlist = AnalyzerCN.DoAnalyzerCN(stringkey);
			// 初始化搜索
			SearchIndex se = SearchIndex.GetInstance();
			// 检索字段 标题 内容
			String[] strfield = { "title", "coutent" };
			// 分词器 全命中推出
			MultiFieldQueryParser qp = new MultiFieldQueryParser(
					Version.LUCENE_45, strfield,
					CreateIndex.GetInstance().analyzer);
			qp.setDefaultOperator(Operator.AND);
			// 查询条件
			BooleanQuery bfquery = new BooleanQuery();
			// 过滤条件
			BooleanQuery blfilterquery = new BooleanQuery();
			// 默认过滤条件为全部数据
			blfilterquery.add(new MatchAllDocsQuery(), Occur.MUST);
			// 查询时间不相等时增加过滤发布时间
			if (!start.equals(end)) {
				Query timequeryfilter = NumericRangeQuery.newLongRange(
						"releasetime", DateTools.stringToTime(DateTools
								.dateToString(start, Resolution.MILLISECOND)),
						DateTools.stringToTime(DateTools.dateToString(end,
								Resolution.MILLISECOND)), true, true);
				blfilterquery.add(timequeryfilter, Occur.MUST);
			}
			// 监测时间不相等时增加检查时间过滤条件
			if (!startspy.equals(endspy)) {
				Query timequeryfilter = NumericRangeQuery.newLongRange("spy",
						DateTools.stringToTime(DateTools.dateToString(startspy,
								Resolution.MILLISECOND)), DateTools
								.stringToTime(DateTools.dateToString(endspy,
										Resolution.MILLISECOND)), true, true);
				blfilterquery.add(timequeryfilter, Occur.MUST);
			}
			// 过滤站点标识 -1是所有站点 站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他)
			if (typeNo != -1) {
				Query qrtyep = null;

				Term tr = new Term("zdbs", String.valueOf(typeNo));
				qrtyep = new TermQuery(tr);
				blfilterquery.add(qrtyep, Occur.MUST);
			}
			
			// 站点名称列表
			if (siteNameList != null && !siteNameList.isEmpty()) {
				BooleanQuery siteboolquery = new BooleanQuery();
				for (String sitename : siteNameList) {
					Term tr = new Term("zdmc", sitename);
					Query ternquery = new TermQuery(tr);
					siteboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(siteboolquery, Occur.MUST);
			}

			// 主关键词过滤
			Query qrkey = null;
			if (!stringkey.equals("")) {
				qrkey = qp.parse(stringkey);

			} else {
				return retmodel;
			}
			// 把过滤条件转换成过滤器
			Filter flmain = new QueryWrapperFilter(blfilterquery);

			// 主关键词过滤
			if (qrkey != null) {
				bfquery.add(qrkey, Occur.MUST);
			}
			// 构建搜索器
			IndexSearcher allSearcher = se.GetAllSearcher();
			// 搜索
			TopDocs docsall = allSearcher.search(bfquery, flmain, pageindex
					* pagecount);
			// 计数
			ScoreDoc[] docs = docsall.scoreDocs;
			retmodel.allcount = docsall.totalHits;
			// 构建返回对象
			for (int i = pageindex * pagecount - pagecount; i < docs.length
					&& i < pageindex * pagecount; i++) {
				SearchModel model = new SearchModel();
				Document doc = allSearcher.doc(docs[i].doc);

				model.url = doc.get("url");
				model.tabletype = doc.get("zdbs");

				if (doc.get("spy") != null) {
					Date dt = DateTools.stringToDate(DateTools.timeToString(
							Long.parseLong(doc.get("spy")), Resolution.SECOND));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					Date dtnow = new Date();
					dtnow = sdf.parse(sdf.format(dtnow));
					if (dt.before(dtnow)) {
						model.sqltype = "1";
					} else {
						model.sqltype = "0";
					}
				}
				retmodel.modle.add(model);
			}

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
		return retmodel;
	}

	/**
	 * 舆情信息
	 * 
	 * @param storetype
	 *            搜索索引类型，0：当前索引数据，1：历史索引数据，2：全部索引数据历史+当天
	 * @param sortbyfbtime
	 *            发布时间排序选项 0：不排序，1：发布时间降序，2：发布时间升序
	 * @param searchkey
	 *            搜索关键字
	 * @param start
	 *            发布时间：开始时间 （和结束时间相等则不按时间搜索）
	 * @param end
	 *            发布时间：结束时间 （和开始时间相当则不按时间搜索）
	 * @param typenoinlist
	 *            包含类型列表（1资讯2论坛3微博4博客5QQ6搜索0其他,7回复）
	 * @param sitenamelist
	 *            包含站点名称列表 （如：合肥论坛，新浪微博 等）
	 * @param lyidinlist
	 *            包含来源ID列表（分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param typenooutlist
	 *            不包含类型列表 （1资讯2论坛3微博4博客5QQ6搜索0其他,7回复）
	 * @param lyidoutList
	 *            不包含来源列表 （分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param qqqunlist
	 *            腾讯群号列表
	 * @param oldkeys
	 *            在次搜索时的上次搜索关键字
	 * @param author
	 *            发布用户
	 * @param jingnei
	 *            境内境外 0：所有，1：境内，2：境外
	 * @param listareain
	 *            所在区域列表
	 * @param listareaout
	 *            区域列表，结果不包含此区域内数据
	 * @param pageindex
	 *            当前页码 如第一页 传1
	 * @param pagecount
	 *            每页数据量 每页十条就传10
	 * @return
	 */
	public ReturnModel GetMessageSearchAll(int storetype, int sortbyfbtime,
			String searchkey, Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex,
			int pagecount) {
		ReturnModel retmodel = new ReturnModel();
		SearchIndex se = SearchIndex.GetInstance();
		try {
			BooleanQuery blfilterquery = new BooleanQuery();

			BooleanQuery blsearchquery = new BooleanQuery();

			// 构建filter
			Filter flmain = null;
			// 先构建filter的查询
			// 查询时间不相等时增加过滤时间
			if (start.equals(end)) {

			} else {
				Query timequeryfilter = NumericRangeQuery.newLongRange(
						"releasetime", DateTools.stringToTime(DateTools
								.dateToString(start, Resolution.MILLISECOND)),
						DateTools.stringToTime(DateTools.dateToString(end,
								Resolution.MILLISECOND)), true, true);
				blfilterquery.add(timequeryfilter, Occur.MUST);
			}
			// 包含类型列表(站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他))
			if (typenoinlist != null && !typenoinlist.isEmpty()) {
				BooleanQuery typeboolquery = new BooleanQuery();
				for (String typeno : typenoinlist) {
					Term tr = new Term("zdbs", typeno);
					Query ternquery = new TermQuery(tr);
					typeboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(typeboolquery, Occur.MUST);
			}

			// 站点名称列表
			if (sitenamelist != null && !sitenamelist.isEmpty()) {
				BooleanQuery siteboolquery = new BooleanQuery();
				for (String sitename : sitenamelist) {
					Term tr = new Term("zdmc", sitename);
					Query ternquery = new TermQuery(tr);
					siteboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(siteboolquery, Occur.MUST);
			}
			// 来源ID列表
			if (lyidinlist != null && !lyidinlist.isEmpty()) {
				BooleanQuery lyboolquery = new BooleanQuery();
				for (String lyid : lyidinlist) {
					Term tr = new Term("lzid", lyid);
					Query ternquery = new TermQuery(tr);
					lyboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(lyboolquery, Occur.MUST);
			}

			// 不包含类型列表 (站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他))
			if (typenooutlist != null && !typenooutlist.isEmpty()) {
				// BooleanQuery typeoutboolquery = new BooleanQuery();
				for (String typeno : typenooutlist) {
					Term tr = new Term("zdbs", typeno);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
				// blfilterquery.add(typeoutboolquery, Occur.MUST_NOT);
			}

			// 不包含来源ID列表
			if (lyidoutList != null && !lyidoutList.isEmpty()) {
				// BooleanQuery lyoutboolquery = new BooleanQuery();
				for (String lyid : lyidoutList) {
					Term tr = new Term("lzid", lyid);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
				// blfilterquery.add(lyoutboolquery, Occur.MUST);
			}

			// QQ群号
			if (qqqunlist != null && !qqqunlist.isEmpty()) {
				BooleanQuery qqqunboolquery = new BooleanQuery();
				for (String qqqun : qqqunlist) {
					Term tr = new Term("qqgroupno", qqqun);
					Query ternquery = new TermQuery(tr);
					qqqunboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(qqqunboolquery, Occur.MUST);
			}

			// 多域查询分词
			String[] strfield = { "title", "coutent" };
			MultiFieldQueryParser qp = new MultiFieldQueryParser(
					Version.LUCENE_45, strfield,
					CreateIndex.GetInstance().analyzer);
			qp.setDefaultOperator(Operator.AND);
			// 旧词过滤器
			if (!oldkeys.equals("")) {
				Query oldkeyquery = qp.parse(oldkeys);
				blfilterquery.add(oldkeyquery, Occur.MUST);

			} else {
				blfilterquery.add(new MatchAllDocsQuery(), Occur.MUST);
			}

			// 按发布人过滤
			if (author != null && !author.equals("")) {

				// QueryParser aqp = new QueryParser(Version.LUCENE_45,
				// "auther",
				// CreateIndex.GetInstance().analyzer);
				// aqp.setDefaultOperator(Operator.AND);
				// Query tempquery = aqp.parse(author);

				Term tr = new Term("auther", author);
				Query tempquery = new TermQuery(tr);

				blfilterquery.add(tempquery, Occur.MUST);
			}

			// 过滤境内境外 0全部：1境内，2境外
			if (jingnei != 0) {
				Term tr = new Term("jingnei", String.valueOf(jingnei));
				Query ternquery = new TermQuery(tr);
				blfilterquery.add(ternquery, Occur.MUST);
			}

			// 本地搜索 当前本地区域列表
			if (listareain != null && !listareain.isEmpty()) {
				BooleanQuery tempbool = new BooleanQuery();
				for (String quyucode : listareain) {
					Term tr = new Term("quyucode", quyucode);
					Query ternquery = new TermQuery(tr);
					tempbool.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(tempbool, Occur.MUST);
			}

			// 外地搜索 当前区域列表
			if (listareaout != null && !listareaout.isEmpty()) {

				for (String quyucode : listareaout) {
					Term tr = new Term("quyucode", quyucode);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// 关键词检索
			if (!searchkey.equals("")) {
				Query oldkeyquery = qp.parse(searchkey);
				blsearchquery.add(oldkeyquery, Occur.MUST);

			} else {
				blsearchquery.add(new MatchAllDocsQuery(), Occur.MUST);
			}
			// 封装过滤器
			// if (!blfilterquery.toString().equalsIgnoreCase("")) {
			flmain = new QueryWrapperFilter(blfilterquery);
			// } else {
			//
			// }

			TopDocs docsall = null;
			IndexSearcher searcher = null;
			if (storetype == 0) {// 当天数据
				searcher = se.GetCurrentSearcher();
			} else if (storetype == 1) {// 历史数据
				searcher = se.GetHistorySearcher();
			} else {// 所有数据
				searcher = se.GetAllSearcher();
			}
			// 没有索引库直接退出
			if (searcher == null) {
				return retmodel;
			}

			if (sortbyfbtime == 0) {// 不排序
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount);
			} else if (sortbyfbtime == 1) {// 发布时间降序
				Sort str = new Sort();
				str.setSort(new SortField("releasetime", Type.LONG, true));
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount, str);
			} else {// 发布时间升序
				Sort str = new Sort();
				str.setSort(new SortField("releasetime", Type.LONG, false));
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount, str);
			}

			// docsall = searcher.search(blsearchquery, pageindex
			// * pagecount);

			ScoreDoc[] docs = docsall.scoreDocs;
			retmodel.allcount = docsall.totalHits;

			for (int i = pageindex * pagecount - pagecount; i < docs.length
					&& i < pageindex * pagecount; i++) {
				SearchModel model = new SearchModel();
				Document doc = searcher.doc(docs[i].doc);

				model.url = doc.get("url");
				model.tabletype = doc.get("zdbs");

				if (doc.get("spy") != null) {
					Date dt = DateTools.stringToDate(DateTools.timeToString(
							Long.parseLong(doc.get("spy")), Resolution.SECOND));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					Date dtnow = new Date();
					dtnow = sdf.parse(sdf.format(dtnow));

					if (dt.before(dtnow)) {
						model.sqltype = "1";

					} else {
						model.sqltype = "0";
					}
				}

				retmodel.modle.add(model);
			}

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}

		return retmodel;
	}

	/**
	 * 网络巡检
	 * 
	 * @param sortbyfbtime
	 *            监测时间排序选项 0：不排序，1：发布时间降序，2：发布时间升序
	 * @param searchkey
	 *            搜索关键字
	 * @param start
	 *            发布时间：开始时间 （和结束时间相等则不按时间搜索）
	 * @param end
	 *            发布时间：结束时间 （和开始时间相当则不按时间搜索）
	 * @param typenoinlist
	 *            包含类型列表（1资讯2论坛3微博4博客5QQ6搜索0其他,7回复）
	 * @param sitenamelist
	 *            包含站点名称列表 （如：合肥论坛，新浪微博 等）
	 * @param lyidinlist
	 *            包含来源ID列表（分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param typenooutlist
	 *            不包含类型列表 （1资讯2论坛3微博4博客5QQ6搜索0其他,7回复）
	 * @param lyidoutList
	 *            不包含来源列表 （分类列表_采集站点：FLLB_CJLB 的ID列表）
	 * @param qqqunlist
	 *            腾讯群号列表
	 * @param oldkeys
	 *            在次搜索时的上次搜索关键字
	 * @param author
	 *            发布用户
	 * @param jingnei
	 *            境内境外 0：所有，1：境内，2：境外
	 * @param listareain
	 *            所在区域列表
	 * @param listareaout
	 *            区域列表，结果不包含此区域内数据
	 * @param pageindex
	 *            当前页码 如第一页 传1
	 * @param pagecount
	 *            每页数据量 每页十条就传10
	 * @return
	 */
	public ReturnModel GetNetSearchAll(int sortbyfbtime, String searchkey,
			Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex,
			int pagecount) {
		ReturnModel retmodel = new ReturnModel();
		SearchIndex se = SearchIndex.GetInstance();
		try {
			// 分词
			retmodel.analyzerlist = AnalyzerCN.DoAnalyzerCN(searchkey);

			BooleanQuery blfilterquery = new BooleanQuery();

			BooleanQuery blsearchquery = new BooleanQuery();

			// 构建filter
			Filter flmain = null;

			// 先构建filter的查询
			// 查询时间不相等时增加过滤时间
			if (start.equals(end)) {

			} else {
				Query timequeryfilter = NumericRangeQuery.newLongRange(
						"releasetime", DateTools.stringToTime(DateTools
								.dateToString(start, Resolution.MILLISECOND)),
						DateTools.stringToTime(DateTools.dateToString(end,
								Resolution.MILLISECOND)), true, true);
				blfilterquery.add(timequeryfilter, Occur.MUST);
			}

			// 包含类型列表(站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他))
			if (typenoinlist != null && !typenoinlist.isEmpty()) {
				BooleanQuery typeboolquery = new BooleanQuery();
				for (String typeno : typenoinlist) {
					Term tr = new Term("zdbs", typeno);
					Query ternquery = new TermQuery(tr);
					typeboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(typeboolquery, Occur.MUST);
			}

			// 站点名称列表
			if (sitenamelist != null && !sitenamelist.isEmpty()) {
				BooleanQuery siteboolquery = new BooleanQuery();
				for (String sitename : sitenamelist) {
					Term tr = new Term("zdmc", sitename);
					Query ternquery = new TermQuery(tr);
					siteboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(siteboolquery, Occur.MUST);
			}

			// 来源ID列表
			if (lyidinlist != null && !lyidinlist.isEmpty()) {
				BooleanQuery lyboolquery = new BooleanQuery();
				for (String lyid : lyidinlist) {
					Term tr = new Term("lzid", lyid);
					Query ternquery = new TermQuery(tr);
					lyboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(lyboolquery, Occur.MUST);
			}

			// 不包含类型列表 (站点标识 (1资讯2论坛3微博4博客5QQ6搜索0其他))
			if (typenooutlist != null && !typenooutlist.isEmpty()) {
				// BooleanQuery typeoutboolquery = new BooleanQuery();
				for (String typeno : typenooutlist) {
					Term tr = new Term("zdbs", typeno);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
				// blfilterquery.add(typeoutboolquery, Occur.MUST_NOT);
			}

			// 不包含来源ID列表
			if (lyidoutList != null && !lyidoutList.isEmpty()) {
				// BooleanQuery lyoutboolquery = new BooleanQuery();
				for (String lyid : lyidoutList) {
					Term tr = new Term("lzid", lyid);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// QQ群号
			if (qqqunlist != null && !qqqunlist.isEmpty()) {
				BooleanQuery qqqunboolquery = new BooleanQuery();
				for (String qqqun : qqqunlist) {
					Term tr = new Term("qqgroupno", qqqun);
					Query ternquery = new TermQuery(tr);
					qqqunboolquery.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(qqqunboolquery, Occur.MUST);
			}

			// 多域查询分词
			String[] strfield = { "title", "coutent" };
			MultiFieldQueryParser qp = new MultiFieldQueryParser(
					Version.LUCENE_45, strfield,
					CreateIndex.GetInstance().analyzer);
			qp.setDefaultOperator(Operator.AND);
			// 旧词过滤器
			if (!oldkeys.equals("")) {
				Query oldkeyquery = qp.parse(oldkeys);
				blfilterquery.add(oldkeyquery, Occur.MUST);

			} else {
				blfilterquery.add(new MatchAllDocsQuery(), Occur.MUST);
			}

			// 按发布人过滤
			if (author != null && !author.equals("")) {
				Term tr = new Term("auther", author);
				Query tempquery = new TermQuery(tr);

				blfilterquery.add(tempquery, Occur.MUST);
			}

			// 过滤境内境外 0全部：1境内，2境外
			if (jingnei != 0) {
				Term tr = new Term("jingnei", String.valueOf(jingnei));
				Query ternquery = new TermQuery(tr);
				blfilterquery.add(ternquery, Occur.MUST);
			}

			// 本地搜索 当前本地区域列表
			if (listareain != null && !listareain.isEmpty()) {
				BooleanQuery tempbool = new BooleanQuery();
				for (String quyucode : listareain) {
					Term tr = new Term("quyucode", quyucode);
					Query ternquery = new TermQuery(tr);
					tempbool.add(ternquery, Occur.SHOULD);
				}
				blfilterquery.add(tempbool, Occur.MUST);
			}

			// 外地搜索 当前区域列表
			if (listareaout != null && !listareaout.isEmpty()) {

				for (String quyucode : listareaout) {
					Term tr = new Term("quyucode", quyucode);
					Query ternquery = new TermQuery(tr);
					blfilterquery.add(ternquery, Occur.MUST_NOT);
				}
			}

			// 关键词检索
			if (!searchkey.equals("")) {
				Query oldkeyquery = qp.parse(searchkey);
				blsearchquery.add(oldkeyquery, Occur.MUST);

			} else {
				blsearchquery.add(new MatchAllDocsQuery(), Occur.MUST);
			}

			// 封装过滤器
			flmain = new QueryWrapperFilter(blfilterquery);

			TopDocs docsall = null;
			IndexSearcher searcher = null;

			searcher = se.GetAllSearcher();

			// 没有索引库直接退出
			if (searcher == null) {
				return retmodel;
			}

			if (sortbyfbtime == 0) {// 不排序 // bug
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount);
			} else if (sortbyfbtime == 1) {// 监测时间降序
				Sort str = new Sort();
				str.setSort(new SortField("releasetime", Type.LONG, true)); //
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount, str);
			} else {// 监测时间升序
				Sort str = new Sort();
				str.setSort(new SortField("releasetime", Type.LONG, false)); // 巡检按发布时间（王总）
				docsall = searcher.search(blsearchquery, flmain, pageindex
						* pagecount, str);
			}

			ScoreDoc[] docs = docsall.scoreDocs;
			retmodel.allcount = docsall.totalHits;

			for (int i = pageindex * pagecount - pagecount; i < docs.length
					&& i < pageindex * pagecount; i++) {
				SearchModel model = new SearchModel();
				Document doc = searcher.doc(docs[i].doc);

				model.url = doc.get("url");
				model.tabletype = doc.get("zdbs");
				
				System.out.println(model.url);

				if (doc.get("spy") != null) {
					
					System.out.println(doc.get("spy") );
					
					Date dt = DateTools.stringToDate(DateTools.timeToString(
							Long.parseLong(doc.get("spy")), Resolution.SECOND));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					Date dtnow = new Date();
					dtnow = sdf.parse(sdf.format(dtnow));
					
					System.out.println(dt);
					
					if (dt.before(dtnow)) {
						model.sqltype = "1";
					} else {
						model.sqltype = "0";
					}
				}

				retmodel.modle.add(model);
			}

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}

		return retmodel;
	}

	/**
	 * 去重服务
	 * 
	 * @param url
	 *            需要去重的URL地址
	 * @return 存在true,不存在false
	 */
	public boolean HistoryExist(String url) {
		try {
			// 去重
			if (url == null || url.equals("")) {
				return true;
			}
			ReplaceIndex se = ReplaceIndex.GetInstance();
			Term tr = new Term("url", url);
			Query urlquery = new TermQuery(tr);
			IndexSearcher search = se.GetHistorySearcher();
			if (search != null) {
				TopDocs docsall = search.search(urlquery, 1);

				if (docsall.totalHits > 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}

		return false;
	}

	/**
	 * 获取索引目标对象 
	 */
	public List<IndexModel> GetIndexModel(String url) {

		List<IndexModel> modelList = new ArrayList<IndexModel>();

		try {

			if (url == null || url.equals("")) {
				return Collections.emptyList();
			}

			SearchIndex se = SearchIndex.GetInstance();

			Term term = new Term("url", url);
			Query urlquery = new TermQuery(term);
			IndexSearcher searcher = se.GetAllSearcher();

			if (searcher != null) {
				TopDocs topDocs = searcher.search(urlquery, 1);

				ScoreDoc[] docs = topDocs.scoreDocs;

				for (ScoreDoc scoreDoc : docs) {
					Document doc = searcher.doc(scoreDoc.doc);
					IndexModel model = new IndexModel();
					model.url = url;
					model.title = doc.get("title");
					model.coutent = doc.get("coutent");
					model.auther = doc.get("auther");
					model.zdbs = doc.get("zdbs");
					model.zdmc = doc.get("zdmc");
					model.lzid = doc.get("lzid");
					model.qqgroupno = doc.get("qqgroupno");
					model.jingnei = doc.get("jingnei");
					model.quyucode = doc.get("quyucode");

					if (doc.get("releasetime") != null) {
						model.releasetime = DateTools.stringToDate(DateTools
								.timeToString(
										Long.parseLong(doc.get("releasetime")),
										Resolution.SECOND));
					}

					if (doc.get("spy") != null) {
						model.fetchTime = DateTools.stringToDate(DateTools
								.timeToString(Long.parseLong(doc.get("spy")),
										Resolution.SECOND));
					}
					modelList.add(model);
				}
				return modelList;
			} else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}

		return Collections.emptyList();
	}
}
