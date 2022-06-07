<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.javaex.dao.GuestbookDao"%>
<%@ page import="com.javaex.vo.GuestbookVo"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
int no = Integer.parseInt(request.getParameter("no"));
UserVo authUser = (UserVo)session.getAttribute("authUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">
	
		<!-- c:import header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- /c:import header -->

		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="/mysite2/guestbook?action=addList">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>방명록</h2>
				<ul>
					<li>일반방명록</li>
					<li>ajax방명록</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">
			
				<div id="content-head">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="guestbook">
					<form action="" method="get">
					<input type="hidden" name="action" value="delete">
						<table id="guestDelete">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 25%;">
								<col style="width: 25%;">
							</colgroup>
							<tr>
								<td>비밀번호</td>
								<td><input type="password" name="password" value=""></td>
								<td class="text-left"><button type="submit">삭제</button></td>
								<td><a href="/mysite2/main">[메인으로 돌아가기]</a></td>
							</tr>
						</table>
						<input type="hidden" name="no" value="<%=no%>">
						<input type='hidden' name="" value="">
					</form>
					
				</div>
				<!-- //guestbook -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->
		
		<!-- c:import footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- /c:import footer -->

	</div>
	<!-- //wrap -->

</body>

</html>
