package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
      
	//기본생성자 사용
	
	//GS
	
	//일반

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//action을 꺼낸다
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) {//회원가입폼
			System.out.println("usercontroller > joinFrom");

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
				
		} else if("join".equals(action)) {//회원가입
			System.out.println("usercontroller > join");
			
			//파라메터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//Vo만들기
			
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);
			
			//Dao를 이용해서 저장하기
			UserDao userDao = new UserDao();
			userDao.insert(userVo);
			
			//회원가입 완료 메세지
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		} else if("loginForm".equals(action)) {
			System.out.println("usercontroller > loginForm");
			
			WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
			
		} else if("login".equals(action)) {
			System.out.println("usercontroller > login");
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			//Vo만들기
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password);
			
			//Dao만들기
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.getUser(userVo); //id / password  >> user전체

			//authUser가 주소값이 있으면  --> 로그인 성공
			//authUser가 주소값이 null이면 --> 로그인 실패
			if(authUser== null) {
				System.out.println("로그인 실패");
			} else {
				System.out.println("로그인 성공");
				
				HttpSession session = request.getSession();
				session.setAttribute("yn", "y");
			}
			
			//메인 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");
		
		}
		
		
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
