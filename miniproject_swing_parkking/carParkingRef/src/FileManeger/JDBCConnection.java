package FileManeger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//싱글톤을 이용해서 connection을 어디에든 연결해주기

public class JDBCConnection {
	private static Connection conn;
	private static JDBCConnection instance;
	
	private JDBCConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
			conn = DriverManager.getConnection(url, "MINI", "1234");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		instanceCheck();
		return conn;
	}
	 
	public static JDBCConnection getInstance() {
		instanceCheck();
		return instance;
	}
	
	private static void instanceCheck() {
		if(instance==null) {
			instance= new JDBCConnection();
		} 
	}
	
	public void close() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				System.out.println("정상적인 close");
			} else {
				System.out.println("이미 close됬음");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}