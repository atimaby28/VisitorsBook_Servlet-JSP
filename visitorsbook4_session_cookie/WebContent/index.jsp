<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Visitor</title>
</head>
<body>
	<div align="center">
		<h3>Visitors' Book with Session and Cookie!!!</h3>
		<%
		if(visitorDto == null) {
		%>
		<a href="<%= root %>/visitor/join.jsp">회원가입</a><br> 
		<a href="<%= root %>/visitor/login.jsp">로그인</a><br> 
		<%
		} else {
		%>
		<strong><%= visitorDto.getVisitorName() %>(<%= visitorDto.getEmail() %>)</strong>님 안녕하세요.
		<a href="<%= root %>/visitor?act=logout">로그아웃</a><br>
		<br>
		<a href="<%= root %>/visitorsbook/write.jsp">글쓰기</a><br> 
		<a href="<%= root %>/visitorsbook?act=list">글 목록</a>
		<%
		}
		%>
	</div>
</body>
</html>