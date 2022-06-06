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
	// 필드
	private static final long serialVersionUID = 1L;

	// 기본생성자 사용

	// GS

	// 일반

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// action을 꺼낸다
		String action = request.getParameter("action");

		if ("joinForm".equals(action)) {// 회원가입폼
			System.out.println("usercontroller > joinFrom");

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {// 회원가입
			System.out.println("usercontroller > join");

			// 파라메터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// Vo만들기

			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);

			// Dao를 이용해서 저장하기
			UserDao userDao = new UserDao();
			userDao.insert(userVo);

			// 회원가입 완료 메세지
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginForm".equals(action)) {
			System.out.println("usercontroller > loginForm");

			WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {
			System.out.println("usercontroller > login");

			// 파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");

			// Vo만들기
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password);

			// Dao만들기
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.getUser(userVo); // id / password >> user전체

			// authUser가 주소값이 있으면 --> 로그인 성공
			// authUser가 주소값이 null이면 --> 로그인 실패
			if (authUser == null) {
				System.out.println("로그인 실패");
			} else {
				System.out.println("로그인 성공");

				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
			}

			// 메인 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");

		} else if ("logout".equals(action)) {

			System.out.println("usercontroller = > logout");

			// 세션 지우기
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();

			// 메인 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");
		} else if ("modifyForm".equals(action)) {

			//로그인체크
			
			//로그인X >> 리다이렉트(로그인폼)
			
			//로그인O
			
			
			
			// 로그인 사용자의 no값을 세션에서 꺼내오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			int no = authUser.getNo();

			// no로 사용자 정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no); // no id password name gender 정보 가져오기

			// request의 attribute에 userVo를 넣어서 forward
			request.setAttribute("userVo", userVo);

			WebUtil.forward(request, response, "WEB-INF/views/user/modifyForm.jsp");

		} else if ("modify".equals(action)) {
			
			//세션에서 no로 꺼내개ㅣ
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			
			//파라미터를 꺼낸다
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//묶어준다
			UserVo userVo = new UserVo();

			userVo.setName(name);
			userVo.setPassword(password);
			userVo.setGender(gender);
			userVo.setNo(no);
			
			//Dao를 사용한다
			UserDao userDao = new UserDao();
			userDao.update(userVo);
			
			authUser = userDao.getUser(userVo);
			
			//후반 --> 응답/결과
			session.setAttribute("authUser", authUser);
			WebUtil.redirect(request, response, "/mysite2/main");
		
		
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
