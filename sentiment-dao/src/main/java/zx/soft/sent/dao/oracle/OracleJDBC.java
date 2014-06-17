package zx.soft.sent.dao.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.Config;

/**
 * Oracle数据库访问类
 * 
 * @author wanggang
 *
 */
public class OracleJDBC {

	private static Logger logger = LoggerFactory.getLogger(OracleJDBC.class);

	private String db_url;
	private String db_username;
	private String db_password;

	private Connection conn;
	private Statement statement;

	public OracleJDBC() {
		doInit();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conn = DriverManager.getConnection(db_url, db_username, db_password);
			statement = conn.createStatement();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException();
		}
	}

	private void doInit() {
		db_url = Config.get("db_url");
		db_username = Config.get("db_username");
		db_password = Config.get("db_password");
	}

	/**
	 * 更新数据
	 */
	public boolean update(String sql) {
		try {
			statement.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询数据
	 */
	public ResultSet query(String sql) {
		try {
			return statement.executeQuery(sql);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		try {
			conn.close();
			statement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
