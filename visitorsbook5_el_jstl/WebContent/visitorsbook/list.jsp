<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,com.visitor.visitorsbook.dto.VisitorsBookDto"%>
<%@ include file="/include/header.jsp" %>
<c:if test="${empty visitorinfo}">
	<script type="text/javascript">
	alert("로그인 후 이용 가능합니다.");
	location.href = "${root}/visitor/login.jsp";
	</script>
</c:if>
<!-- if(visitorDto == null) { 
	<script>
		alert("로그인 후 이용 가능합니다.");
		location.href = "${root}/visitor/login.jsp";
	</script>
	} else {
-->
<c:if test="${!empty visitorinfo}">
<!-- 
	} else {
	List<VisitorsBookDto> list = (List<VisitorsBookDto>) request.getAttribute("articles");
 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Visitor's Book - 글 목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        mark.sky {
            background: linear-gradient(to top, #54fff9 20%, transparent 30%);
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#mvRegisterBtn").click(function () {
                location.href = "${root}/visitorsbook/write.jsp";
            });
        });
    </script>
</head>
<body>
    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
            <h2 class="p-3 mb-3 shadow bg-light"><mark class="sky">글 목록</mark></h2>
            <div class="m-3 text-right">
                <button type="button" id="mvRegisterBtn" class="btn btn-link">글 작성</button>
            </div>
            <c:if test="${!empty articles}"></c:if>
            	<c:forEach var="article" items="${articles}">
<!--
		if(list != null && list.size() != 0) {
			for(VisitorsBookDto article : list) {
-->
			<table class="table table-active text-left">
				<tbody>
					<tr class="table-info">
						<td>작성자 : ${article.visitorId} <!-- article.getVisitorId() --></td>
						<td class="text-right">작성일 : ${article.regTime}<!-- article.getRegTime() --></td>
					</tr>
					<tr>
						<td colspan="2" class="table-danger">
							<strong>${article.articleNo}. ${article.subject}<!-- article.getArticleNo(). article.getSubject()--></strong>
						</td>
					</tr>
					<tr>
						<td class="p-4" colspan="2">
							${article.content}<!-- article.getContent() -->
						</td>
					</tr>
				</tbody>
			</table>
<!--
			}
		} else {
-->		
			</c:forEach>
			<c:if test="${!empty articles}">
				<table class="table table-active text-center">
					<tr class="table-info">
						<td>작성한 글이 없습니다.</td>
					</tr>
				</table>
			</c:if>
<!--
		}
-->
		</div>
    </div>
</body>
</html>
</c:if>