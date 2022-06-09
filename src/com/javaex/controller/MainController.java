package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;

@WebServlet("/main")
public class MainController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
       
	
	//생성자(디폴트 생성자 사용)
	
	//GS
	
	//일반

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//코드
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	

		//한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		doGet(request, response);
	}

}
