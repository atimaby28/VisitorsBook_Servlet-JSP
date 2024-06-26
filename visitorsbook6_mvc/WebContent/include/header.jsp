<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <!--import="com.visitor.visitorsbook.dto.VisitorDto"  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="root" value="${pageContext.request.contextPath}"></c:set>

<%
//	String root = request.getContextPath(); 
//  = <c:set var="root" value="${pageContext.request.contextPath}"></c:set>
	
//  VisitorDto visitorDto = (VisitorDto) request.getAttribute("visitorinfo");
//	VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<title>Visitor's Book</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        mark.sky {
            background: linear-gradient(to top, #54fff9 20%, transparent 30%);
        }
        mark.orange {
            background: linear-gradient(to top, orange 20%, transparent 30%);
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>

<body>