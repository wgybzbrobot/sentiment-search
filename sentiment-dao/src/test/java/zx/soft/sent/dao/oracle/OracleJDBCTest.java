package zx.soft.sent.dao.oracle;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleJDBCTest {

	private static Logger logger = LoggerFactory.getLogger(OracleJDBCTest.class);

	private static OracleJDBC dataOJDBC;

	@BeforeClass
	public void doInit() {
		logger.info("Start DataOJDBC ...");
		dataOJDBC = new OracleJDBC();
	}

	@AfterClass
	public void doClose() {
		logger.info("Close DataOJDBC ...");
		dataOJDBC.close();
	}

	@Ignore
	public void testQueryOne() throws Exception {
		String sql = "SELECT COUNT(*) AS data_count FROM SJCJ_WBL";
		ResultSet rs = dataOJDBC.query(sql);
		assertTrue(rs.next());
		assertTrue(rs.getInt("data_count") > 0);
	}

	@Ignore
	public void testQueryTwo() throws Exception {
		String sql = "select * from (select rownum as no1,t.* from SJCJ_WBL t where rownum <200) where no1>100";
		ResultSet rs = dataOJDBC.query(sql);
		assertTrue(rs.getFetchSize() > 0);
	}

}
