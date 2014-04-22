package zx.soft.sent.solr.demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

/**
 * 从数据库中添加数据到Solr中
 * @author wgybzb
 *
 */
public class WriteDataFromDb {

	private static int fetchSize = 1000;
	private static String url = "http://test1:8983/solr/sentiment";
	private static HttpSolrServer solrCore;

	public WriteDataFromDb() throws MalformedURLException {
		solrCore = new HttpSolrServer(url);
	}

	/**
	 * 将数据库中的数据集批量添加到Solr
	 * @param rs： 数据集合
	 * @return ： 添加到Solr中的文档数
	 */
	public long addResultSet(ResultSet rs) throws SQLException, SolrServerException, IOException {

		long count = 0;
		int innerCount = 0;
		Collection<SolrInputDocument> docs = new ArrayList<>();
		ResultSetMetaData rsm = rs.getMetaData();
		int numColumns = rsm.getColumnCount();
		String[] colNames = new String[numColumns + 1];

		/**
		 * JDBC从1开始编码，Java从0开始编码
		 */
		for (int i = 1; i < (numColumns + 1); i++) {
			colNames[i] = rsm.getColumnName(i).replaceAll("_", "");
		}

		/**
		 * 循环ResultSet
		 */
		while (rs.next()) {

			count++;
			innerCount++;

			SolrInputDocument doc = new SolrInputDocument();

			for (int j = 1; j < (numColumns + 1); j++) {
				if (colNames[j] != null) {
					Object f;
					switch (rsm.getColumnType(j)) {
					case Types.BIGINT: {
						f = rs.getLong(j);
						break;
					}
					case Types.INTEGER: {
						f = rs.getInt(j);
						break;
					}
					case Types.DATE: {
						f = rs.getDate(j);
						break;
					}
					case Types.FLOAT: {
						f = rs.getFloat(j);
						break;
					}
					case Types.DOUBLE: {
						f = rs.getDouble(j);
						break;
					}
					case Types.TIME: {
						f = rs.getDate(j);
						break;
					}
					case Types.BOOLEAN: {
						f = rs.getBoolean(j);
						break;
					}
					default: {
						f = rs.getString(j);
					}
					}
					doc.addField(colNames[j], f);
				}
			}
			docs.add(doc);

			/**
			 * 文档数达到fetchSize时, 添加索引数据，并重置innerCount
			 */
			if (innerCount == fetchSize) {
				solrCore.add(docs);
				docs.clear();
				innerCount = 0;
			}
		}

		/**
		 * 添加剩余文档
		 */
		if (innerCount != 0) {
			solrCore.add(docs);
		}
		return count;
	}

}
