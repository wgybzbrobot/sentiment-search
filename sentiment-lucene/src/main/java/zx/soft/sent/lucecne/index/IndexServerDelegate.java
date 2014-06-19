package zx.soft.sent.lucecne.index;

import java.util.Date;
import java.util.List;

import zx.soft.sent.lucene.domain.DayCountGroupModel;
import zx.soft.sent.lucene.domain.DayCountModel;
import zx.soft.sent.lucene.domain.NameKeySort;
import zx.soft.sent.lucene.domain.ReturnModel;

@javax.jws.WebService(targetNamespace = "http://service.yqjk.com/", serviceName = "IndexServerService", portName = "IndexServerPort", wsdlLocation = "WEB-INF/wsdl/IndexServerService.wsdl")
public class IndexServerDelegate {

	private static IndexServer indexServer;

	public IndexServerDelegate() {
		indexServer = new IndexServer();
	}

	public void close() {
		indexServer.close();
	}

	public void CreateIndexServer(IndexModel model) {
		indexServer.CreateIndexServer(model);
	}

	public List<DayCountGroupModel> GetZhuanTiCountGroupModel(int storetype, String searchkey, Date start, Date end,
			List<String> typenoinlist, List<String> sitenamelist, List<String> lyidinlist, List<String> typenooutlist,
			List<String> lyidoutList, List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int grouptype) {
		return indexServer.GetZhuanTiCountGroupModel(storetype, searchkey, start, end, typenoinlist, sitenamelist,
				lyidinlist, typenooutlist, lyidoutList, qqqunlist, oldkeys, author, jingnei, listareain, listareaout,
				grouptype);
	}

	public List<DayCountModel> GetZhuanTiCountModel(int storetype, int sortbyfbtime, String searchkey, Date start,
			Date end, List<String> typenoinlist, List<String> sitenamelist, List<String> lyidinlist,
			List<String> typenooutlist, List<String> lyidoutList, List<String> qqqunlist, String oldkeys,
			String author, int jingnei, List<String> listareain, List<String> listareaout) {
		return indexServer.GetZhuanTiCountModel(storetype, sortbyfbtime, searchkey, start, end, typenoinlist,
				sitenamelist, lyidinlist, typenooutlist, lyidoutList, qqqunlist, oldkeys, author, jingnei, listareain,
				listareaout);
	}

	public List<NameKeySort> GetZhuanTiGroup(int storetype, String searchkey, Date start, Date end,
			List<String> typenoinlist, List<String> sitenamelist, List<String> lyidinlist, List<String> typenooutlist,
			List<String> lyidoutList, List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int grouptype) {
		return indexServer.GetZhuanTiGroup(storetype, searchkey, start, end, typenoinlist, sitenamelist, lyidinlist,
				typenooutlist, lyidoutList, qqqunlist, oldkeys, author, jingnei, listareain, listareaout, grouptype);
	}

	public ReturnModel GetZhuanTiSearchAll(int storetype, int sortbyfbtime, String searchkey, Date start, Date end,
			List<String> typenoinlist, List<String> sitenamelist, List<String> lyidinlist, List<String> typenooutlist,
			List<String> lyidoutList, List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex, int pagecount) {
		return indexServer.GetZhuanTiSearchAll(storetype, sortbyfbtime, searchkey, start, end, typenoinlist,
				sitenamelist, lyidinlist, typenooutlist, lyidoutList, qqqunlist, oldkeys, author, jingnei, listareain,
				listareaout, pageindex, pagecount);
	}

	public ReturnModel SearchAll(String stringkey, Date start, Date end, Date startspy, Date endspy, int typeNo,
			List<String> siteNameList, int pageindex, int pagecount) {
		return indexServer.SearchAll(stringkey, start, end, startspy, endspy, typeNo, siteNameList, pageindex,
				pagecount);
	}

	public ReturnModel GetMessageSearchAll(int storetype, int sortbyfbtime, String searchkey, Date start, Date end,
			List<String> typenoinlist, List<String> sitenamelist, List<String> lyidinlist, List<String> typenooutlist,
			List<String> lyidoutList, List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex, int pagecount) {
		return indexServer.GetMessageSearchAll(storetype, sortbyfbtime, searchkey, start, end, typenoinlist,
				sitenamelist, lyidinlist, typenooutlist, lyidoutList, qqqunlist, oldkeys, author, jingnei, listareain,
				listareaout, pageindex, pagecount);
	}

	public ReturnModel GetNetSearchAll(int sortbyfbtime, String searchkey, Date start, Date end,
			List<String> typenoinlist, List<String> sitenamelist, List<String> lyidinlist, List<String> typenooutlist,
			List<String> lyidoutList, List<String> qqqunlist, String oldkeys, String author, int jingnei,
			List<String> listareain, List<String> listareaout, int pageindex, int pagecount) {
		return indexServer.GetNetSearchAll(sortbyfbtime, searchkey, start, end, typenoinlist, sitenamelist, lyidinlist,
				typenooutlist, lyidoutList, qqqunlist, oldkeys, author, jingnei, listareain, listareaout, pageindex,
				pagecount);
	}

	public boolean HistoryExist(String url) {
		return indexServer.HistoryExist(url);
	}

	public List<IndexModel> GetIndexModel(String url) {
		return indexServer.GetIndexModel(url);
	}

}