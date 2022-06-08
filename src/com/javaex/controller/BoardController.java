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
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		//리스트
		if("board".equals(action)) {
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoard();
			System.out.println(boardList);
			
			//request에 데이터 추가
			request.setAttribute("bList", boardList);
			
			//Forward
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		} else if("read".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.readBoard(no);
			
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "WEB-INF/views/board/read.jsp");
			
		} else if("modifyForm".equals(action)) {
			
			
			WebUtil.forward(request, response, "WEB-INF/views/board/modifyForm.jsp");
		} else if("modify".equals(action)) {
			
			
			WebUtil.redirect(request, response, "/mysite2/board?action=board");
			
		} else if("writeForm".equals(action)) {
			
			WebUtil.forward(request, response, "WEB-INF/views/board/writeForm.jsp");
			
		} else if("write".equals(action)) {
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			int hit = Integer.parseInt(request.getParameter("hit"));
			String content = request.getParameter("content");
			String title = request.getParameter("title");

			BoardVo boardVo = new BoardVo(title, content, hit, userNo);

			BoardDao boardDao = new BoardDao();
			boardDao.writeBoard(boardVo);

			WebUtil.redirect(request, response, "/mysite2/board?action=board");
			
		} else if("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao boardDao = new BoardDao();
			boardDao.deleteBoard(no);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=board");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
