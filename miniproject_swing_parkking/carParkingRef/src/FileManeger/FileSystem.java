package FileManeger;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Information.ParkCarInfo;

public class FileSystem { 
	public void connection(ParkCarInfo ref){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String URL = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(URL, "MINI", "1234");
			
			String sql = "INSERT INTO PARKING(PARKINGCODE, CARINTIME, PARKINGNUMBER, CARNUM)"
							+ " VALUES (? ,?, ? ,? )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ref.getParkingCode());
			pstmt.setString(2, ref.getCarInTime());
			pstmt.setInt(3, ref.getparkPlaceNum());
			pstmt.setString(4, ref.getcarNum());
			if (pstmt.executeUpdate() == 1)
				System.out.println("올바르게 입력됬다");
			else
				System.out.println("제대로 입력되지 않았음");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				
				if (pstmt!= null && !pstmt.isClosed()) {
					pstmt.close();					
				}else {				
				}
				
				if (conn != null && !conn.isClosed()) {
					conn.close();			
				} else {					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}