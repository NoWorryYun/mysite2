package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController");
		
		//한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		//리스트
		if("list".equals(action)) {
			
			//boardList 만들기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoard();
			System.out.println(boardList);
			
			//request에 데이터 추가
			request.setAttribute("bList", boardList);
			
			//Forward
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
			//읽기
		} else if("read".equals(action)) {
			//no 파라미터 꺼내오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//readBoard 만들기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.readBoard(no);
			System.out.println(boardVo.getNo()+"번 읽기");
			
			//hitCount +1시키기
			boardDao.hitCount(boardVo);
			System.out.println(boardVo.getNo()+"번 게시글 조회수+1");
			//request에 데이터 추가
			
			boardVo = boardDao.readBoard(no);
			request.setAttribute("boardVo", boardVo);
			
			//forward
			WebUtil.forward(request, response, "WEB-INF/views/board/read.jsp");
			
			
			//수정폼
		} else if("modifyForm".equals(action)) {
			//no 파라미터 꺼내오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//Dao 만들기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.readBoard(no);
			
			//request에 추가
			request.setAttribute("boardVo", boardVo);
			
			//Forward
			WebUtil.forward(request, response, "WEB-INF/views/board/modifyForm.jsp");
			
			//수정
		} else if("modify".equals(action)) {
			//파라미터 꺼내오기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			
			//Vo에 담기
			BoardVo boardVo = new BoardVo();
			
			boardVo.setTitle(title);
			boardVo.setContent(content);
			boardVo.setNo(no);
			
			//updateboard에 적용
			BoardDao boardDao = new BoardDao();
			boardDao.updateBoard(boardVo);
			System.out.println(boardVo.getNo()+"번 수정");
			
			//redirect
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
			//쓰기폼
		} else if("writeForm".equals(action)) {
			
			WebUtil.forward(request, response, "WEB-INF/views/board/writeForm.jsp");
			
			//쓰기
		} else if("write".equals(action)) {
			//파라미터 꺼내오기
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			int hit = Integer.parseInt(request.getParameter("hit"));
			String content = request.getParameter("content");
			String title = request.getParameter("title");
			content = content.replace("\n", "<br>");
			
			//Vo 만들기
			BoardVo boardVo = new BoardVo(title, content, hit, userNo);

			//write에 적용
			BoardDao boardDao = new BoardDao();
			boardDao.writeBoard(boardVo);
			System.out.println("글쓰기");
			
			//Forward
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
			//삭제
		} else if("delete".equals(action)) {
			//파라미터 꺼내오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//deleteboard 적용
			BoardDao boardDao = new BoardDao();
			boardDao.deleteBoard(no);
			System.out.println(no+"번 삭제");
			
			//redirect
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		} else if("search".equals(action)) {
			String pot = request.getParameter("pot");
			
			//searchBoard 만들기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoard(pot);
			System.out.println("검색어" + pot + "이/가 포함된 title을 검색");
			
			//request에 데이터 추가
			request.setAttribute("bList", boardList);
			
			//Forward
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
