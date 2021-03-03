package MainFunctionView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import FileManeger.JDBCConnection;

public class Test { 
	public List<Integer> parkSetting() { //오라클에서 주차 한 부분들을 받아오기
		JDBCConnection jdbc = JDBCConnection.getInstance();
		Connection conn = jdbc.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> currentParkPlace = new ArrayList<Integer>();
		try {
			String sql = "SELECT PARKINGNUMBER FROM PARKING";
			pstmt = conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				currentParkPlace.add(rs.getInt("PARKINGNUMBER"));
			}
			if (pstmt.executeUpdate() == 1)
				System.out.println("올바르게 입력됬다");
			else
				System.out.println("제대로 입력되지 않았음");

			return currentParkPlace;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
					System.out.println("pstmt 자원반납");
				} else {
					System.out.println("이미 반납됬거나 예외가 발생된 상태");
				}
			} catch (SQLException e) {
			}
			
		}
		return currentParkPlace;
	}
}
