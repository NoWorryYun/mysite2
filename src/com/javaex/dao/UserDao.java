package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	// 생성자

	// Gs

	// 일반

	private void getConnection() {
		try {
			// 1. JDBC 드라이버(Oracle) 로딩
			Class.forName(driver); // 오라클 접속
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버로딩실패-" + e);
		}

	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	//회원가입 --> 회원정보 저장
	public int insert(UserVo userVo) {

		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, userVo.getId()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, userVo.getPassword()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setString(3, userVo.getName()); // ?(물음표) 중 3번째, 순서중요
			pstmt.setString(4, userVo.getGender()); // ?(물음표) 중 4번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	//사용자 정보 가져오기(로그인시 사용)
	public UserVo getUser(UserVo userVo) {
		UserVo authUser = null;
		getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " select   no, ";
			query += "  		id, ";
			query += " 		 	password, ";
			query += " 			name, ";
			query += " 		 	gender ";
			query += " from users ";
			query += " where id = ?";
			query += " and	 password = ?";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, userVo.getId()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, userVo.getPassword()); // ?(물음표) 중 2번째, 순서중요

			rs = pstmt.executeQuery(); // 쿼리문 실행

			// 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				authUser = new UserVo(no, id, password, name, gender);
			}
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return authUser;
	}
	
}
