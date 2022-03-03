package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class NotiDAO {
	
	public Connection getConnection() {
		Connection conn = null;

		try {
			InitialContext ic = new InitialContext();

			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/ridi");

			conn = ds.getConnection();
			
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}
	
	public void checkUpdate() {
		// 신규 알림 체크
	}
	
	
}
