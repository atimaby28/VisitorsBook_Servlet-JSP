<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.visitor.visitorsbook.dto.VisitorDto"%>
<%
	String root = request.getContextPath();
	
//  VisitorDto visitorDto = (VisitorDto) request.getAttribute("visitorinfo");
	VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
%>