package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

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

	public int addGuest(GuestbookVo guestbookVo) {

		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " insert into guestbook ";
			query += " values(seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, guestbookVo.getName()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, guestbookVo.getPassword()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setString(3, guestbookVo.getContent()); // ?(물음표) 중 3번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public List<GuestbookVo> getGuestList() {
		List<GuestbookVo> guestList = new ArrayList<GuestbookVo>();

		getConnection();

		try {
			// SQL 문 준비
			String query = "";
			query += " select  no,";
			query += "         name,";
			query += "         password,";
			query += "         content,";
			query += "         reg_date";
			query += " from guestbook";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행***
			// ResultSet 가져오기
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, regDate);

				guestList.add(guestbookVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return guestList;
	}
	
	//삭제
	public int guestDelete(int guestId) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " delete from guestbook ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, guestId);// ?(물음표) 중 1번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	//비밀번호가져오기
	public String guestPassword(int guestId) {
		String OraclePassword = "";
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " select password ";
			query += " from guestbook ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, guestId);// ?(물음표) 중 1번째, 순서중요

			// 실행***
			// ResultSet 가져오기
			rs = pstmt.executeQuery();

			// 4.결과처리
			while(rs.next()) {
				
				OraclePassword = rs.getString("password");
				
			}
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return OraclePassword;
	}
	
}
