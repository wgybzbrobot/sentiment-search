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
import com.sun.corba.se.spi.orbutil.fsm.State;
import com.yqjk.service.TestZhuanTiReturnObj.objectret;

@javax.jws.WebService(targetNamespace = "http://service.yqjk.com/", serviceName = "IndexServerService", portName = "IndexServerPort", wsdlLocation = "WEB-INF/wsdl/IndexServerService.wsdl")
public class IndexServerDelegate {

	com.yqjk.service.IndexServer indexServer = new com.yqjk.service.IndexServer();

	public void CreateIndexServer(IndexModel model) {
		indexServer.CreateIndexServer(model);
	}

	public List<DayCountGroupModel> GetZhuanTiCountGroupModel(int storetype,
			String searchkey, Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int grouptype) {
		return indexServer.GetZhuanTiCountGroupModel(storetype, searchkey,
				start, end, typenoinlist, sitenamelist, lyidinlist,
				typenooutlist, lyidoutList, qqqunlist, oldkeys, author,
				jingnei, listareain, listareaout, grouptype);
	}

	public List<DayCountModel> GetZhuanTiCountModel(int storetype,
			int sortbyfbtime, String searchkey, Date start, Date end,
			List<String> typenoinlist, List<String> sitenamelist,
			List<String> lyidinlist, List<String> typenooutlist,
			List<String> lyidoutList, List<String> qqqunlist, String oldkeys,
			String author, int jingnei, List<String> listareain,
			List<String> listareaout) {
		return indexServer.GetZhuanTiCountModel(storetype, sortbyfbtime,
				searchkey, start, end, typenoinlist, sitenamelist, lyidinlist,
				typenooutlist, lyidoutList, qqqunlist, oldkeys, author,
				jingnei, listareain, listareaout);
	}

	public List<NameKeySort> GetZhuanTiGroup(int storetype, String searchkey,
			Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int grouptype) {
		return indexServer.GetZhuanTiGroup(storetype, searchkey, start, end,
				typenoinlist, sitenamelist, lyidinlist, typenooutlist,
				lyidoutList, qqqunlist, oldkeys, author, jingnei, listareain,
				listareaout, grouptype);
	}

	public ReturnModel GetZhuanTiSearchAll(int storetype, int sortbyfbtime,
			String searchkey, Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex,
			int pagecount) {
		return indexServer.GetZhuanTiSearchAll(storetype, sortbyfbtime,
				searchkey, start, end, typenoinlist, sitenamelist, lyidinlist,
				typenooutlist, lyidoutList, qqqunlist, oldkeys, author,
				jingnei, listareain, listareaout, pageindex, pagecount);
	}

	public ReturnModel SearchAll(String stringkey, Date start, Date end,
			Date startspy, Date endspy, int typeNo, List<String> siteNameList,
			int pageindex, int pagecount) {
		return indexServer.SearchAll(stringkey, start, end, startspy, endspy,
				typeNo, siteNameList, pageindex, pagecount);
	}

	public ReturnModel GetMessageSearchAll(int storetype, int sortbyfbtime,
			String searchkey, Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex,
			int pagecount) {
		return indexServer.GetMessageSearchAll(storetype, sortbyfbtime,
				searchkey, start, end, typenoinlist, sitenamelist, lyidinlist,
				typenooutlist, lyidoutList, qqqunlist, oldkeys, author,
				jingnei, listareain, listareaout, pageindex, pagecount);
	}

	public ReturnModel GetNetSearchAll(int sortbyfbtime, String searchkey,
			Date start, Date end, List<String> typenoinlist,
			List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList,
			List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex,
			int pagecount) {
		return indexServer.GetNetSearchAll(sortbyfbtime, searchkey, start, end,
				typenoinlist, sitenamelist, lyidinlist, typenooutlist,
				lyidoutList, qqqunlist, oldkeys, author, jingnei, listareain,
				listareaout, pageindex, pagecount);
	}

	public boolean HistoryExist(String url) {
		return indexServer.HistoryExist(url);
	}

	public List<IndexModel> GetIndexModel(String url) {
		return indexServer.GetIndexModel(url);
	}

}