<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Visitor's Book</title>
</head>
<body>
	<div align="center">
		<h3>Atimaby visitor's Book with EL and JSTL!!!</h3>
		<c:if test="${empty visitorinfo}">
			<a href="${root}/visitor/join.jsp">회원가입</a><br> 
			<a href="${root}/visitor/login.jsp">로그인</a><br> 
		</c:if>
		<c:if test="${!empty visitorinfo}">
			<strong>${visitorinfo.visitorName}(${visitorinfo.visitorId})님 안녕하세요.</strong><br>
			<a href="${root}/visitor?act=logout">로그아웃</a><br>
			<a href="${root}/visitorsbook/write.jsp">글쓰기</a><br> 
			<a href="${root}/visitorsbook?act=list">글 목록</a>
		</c:if>
	</div>
</body>
</html>