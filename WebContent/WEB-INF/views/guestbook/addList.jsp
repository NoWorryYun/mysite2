<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.dao.GuestbookDao"%>
<%@ page import="com.javaex.vo.GuestbookVo"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%

	List<GuestbookVo> guestList = (List<GuestbookVo>)request.getAttribute("gList");
	
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
				<li><a href="/mysite2/board?action=board">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="/mysite2/guestbook?action=addList">방명록</a></li>
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
				
				<div id="content-head" class="clearfix">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
				</div>
				<!-- //content-head -->

				<div id="guestbook">
					<form action="" method="get">
					<input type="hidden" name="action" value="add">
						<table id="guestAdd">
							<colgroup>
								<col style="width: 70px;">
								<col>
								<col style="width: 70px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th><label class="form-text" for="input-uname">이름</label></th>
									<td><input id="input-uname" type="text" name="name"></td>
									<th><label class="form-text" for="input-pass">패스워드</label></th>
									<td><input id="input-pass"type="password" name="pass"></td>
								</tr>
								<tr>
									<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
								</tr>
								<tr class="button-area">
									<td colspan="4" class="text-center"><button type="submit">등록</button></td>
								</tr>
							</tbody>
							
						</table>
						<!-- //guestWrite -->
						<input type="hidden" name="action" value="add">
						
					</form>	
					<c:forEach items="${requestScope.gList}" var="guestbookVo" varStatus="status">
						<table class="guestRead">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 40%;">
								<col style="width: 10%;">
							</colgroup>
							<tr>
								<td>${guestbookVo.no}</td>
								<td>${guestbookVo.name}</td>
								<td>${guestbookVo.regDate}</td>
								<td><a href="/mysite2/guestbook?action=deleteForm&no=${guestbookVo.no}">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4 class="text-left">${guestbookVo.content}</td>
							</tr>
						</table>
					</c:forEach>
					<!-- //guestRead -->
					
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