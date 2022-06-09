package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
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
	//리스트
	public List<BoardVo> getBoard(){
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		getConnection();

		try {
			// SQL 문 준비
			String query = "";
			query += " select  b.no no, ";
			query += "         b.title title, ";
			query += "         b.content content, ";
			query += "         b.hit hit, ";
			query += "         b.reg_date regDate, ";
			query += "         b.user_no userNo, ";
			query += "         u.name name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " order by no desc ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행***
			// ResultSet 가져오기
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");

				BoardVo boardVo = new BoardVo(no, title, content, hit, regDate, userNo, name);
				boardList.add(boardVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return boardList;
	}
	//삭제
	public int deleteBoard(int no) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " delete from board ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	//읽기
	public BoardVo readBoard(int no) {
		BoardVo boardVo = null;
		getConnection();

		try {
			// SQL 문 준비
			String query = "";
			query += " select  b.no no, ";
			query += "         b.title title, ";
			query += "         b.content content, ";
			query += "         b.hit hit, ";
			query += "         b.reg_date regDate, ";
			query += "         b.user_no userNo, ";
			query += "         u.name name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.no= ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행***
			pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요

			rs = pstmt.executeQuery(); // 쿼리문 실행

			// 4.결과처리
			// 리스트로 만들기} catch (SQLException e)
			while (rs.next()) {
				int boardNo = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");

				boardVo = new BoardVo(boardNo, title, content, hit, regDate, userNo, name);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardVo;
	}
	//쓰기
	public int writeBoard(BoardVo boardVo) {

		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, ?, sysdate, ?) ";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, boardVo.getTitle()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, boardVo.getContent()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setInt(3, boardVo.getHit()); // ?(물음표) 중 3번째, 순서중요
			pstmt.setInt(4, boardVo.getUserNo()); // ?(물음표) 중 3번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	public int updateBoard(BoardVo boardVo) {
		int count = -1;
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, boardVo.getTitle()); 
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo()); 
			
			count = pstmt.executeUpdate(); // 쿼리문 실행
			System.out.println(count  +  "건이 수정되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	public int hitCount(BoardVo boardVo) {
		int count = -1;
		getConnection();
		
		try {
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, boardVo.getNo()); 
			
			count = pstmt.executeUpdate(); // 쿼리문 실행
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	//검색
	public List<BoardVo> searchBoard(String pot){
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		getConnection();

		try {
			// SQL 문 준비
			String query = "";
			query += " select  b.no no, ";
			query += "         b.title title, ";
			query += "         b.content content, ";
			query += "         b.hit hit, ";
			query += "         b.reg_date regDate, ";
			query += "         b.user_no userNo, ";
			query += "         u.name name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.title like '%"+pot+"%' ";
			query += " order by no desc ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery(); // 쿼리문 실행
			// 4.결과처리
			// 리스트로 만들기

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");

				BoardVo boardVo = new BoardVo(no, title, content, hit, regDate, userNo, name);
				boardList.add(boardVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return boardList;
	}
}

