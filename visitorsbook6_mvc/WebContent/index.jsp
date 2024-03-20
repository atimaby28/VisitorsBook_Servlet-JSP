<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>

	<div align="center">
		<h3>Atimaby visitor's Book with MVC!!!</h3>
		<c:if test="${empty visitorinfo}">
			<a href="${root}/visitor?act=mvregister">회원가입</a><br> 
			<a href="${root}/visitor?act=mvlogin">로그인</a><br> 
		</c:if>
		<c:if test="${!empty visitorinfo}">
			<strong>${visitorinfo.visitorName}(${visitorinfo.visitorId})님 안녕하세요.</strong><br>
			<a href="${root}/visitor?act=logout">로그아웃</a><br>
			<a href="${root}/visitorsbook?act=list">글 목록</a>
		</c:if>
	</div>
</body>
</html>