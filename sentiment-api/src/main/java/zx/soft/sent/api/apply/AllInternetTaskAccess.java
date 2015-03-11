package zx.soft.sent.api.apply;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.dao.domain.allinternet.InternetTask;
import zx.soft.sent.dao.oracle.OracleJDBC;

/**
 * 读取Oracle数据库中的全网任务信息
 *
 * @author wanggang
 *
 */
public class AllInternetTaskAccess {

	private OracleJDBC oracleJDBC;

	// 查询需要更新缓存信息的任务
	public static final String QUERY_UPDATED = "select t1.id,t1.gjc,t1.cjsj,t1.jssj,t1.cjzid from "
			+ "JHRW_RWDD t1,JHRW_RWZX t2,FLLB_CJLB t3 where t1.rwzt in (0,1) and t1.bz=1 and "
			+ "t1.id=t2.rwjhid and t2.sswz=t3.id and t1.jssj>sysdate-30";

	// 查询最近一天内已经完成的任务
	public static final String QUERY_FINISHED = "select t1.id,t1.gjc,t1.cjsj,t1.jssj,t1.cjzid,t3.sourceid from "
			+ "JHRW_RWDD t1,JHRW_RWZX t2,FLLB_CJLB t3 where t1.rwzt=2 and t1.bz=1 and t1.id=t2.rwjhid "
			+ "and t2.sswz=t3.id and t1.jssj<sysdate-1";

	public AllInternetTaskAccess() {
		this.oracleJDBC = new OracleJDBC();
	}

	public static void main(String[] args) throws SQLException {
		AllInternetTaskAccess access = new AllInternetTaskAccess();
		List<InternetTask> tasks = access.getTasks(QUERY_UPDATED);
	}

	/**
	 * 获取需要更新的任务信息
	 * @throws SQLException
	 */
	public List<InternetTask> getTasks(String query) throws SQLException {
		List<InternetTask> result = new ArrayList<>();
		ResultSet resultSet = oracleJDBC.query(query);
		if (resultSet == null) {
			System.err.println("fails");
			return null;
		}
		int count = 0;
		while (resultSet.next()) {
			System.out.println(resultSet.getString("gjc") + "," + resultSet.getTimestamp("cjsj") + ","
					+ resultSet.getTimestamp("jssj") + "," + resultSet.getInt("cjzid"));
			count++;
		}
		System.out.println(count);

		return null;
	}

}
