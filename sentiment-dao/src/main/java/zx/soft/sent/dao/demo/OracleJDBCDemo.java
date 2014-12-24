package zx.soft.sent.dao.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import zx.soft.sent.dao.oracle.OracleJDBC;

public class OracleJDBCDemo {

	public static void main(String[] args) throws SQLException {
		OracleJDBC oracleJDBC = new OracleJDBC();
		String sql = "SELECT COUNT(*) as ct FROM SJCJ_WBL";
		ResultSet rs = oracleJDBC.query(sql);
		if (rs.next()) {
			System.out.println(rs.getInt("ct"));
		} else {
			System.err.println("Error!");
		}
	}

}
