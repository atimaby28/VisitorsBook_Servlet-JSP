<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,com.visitor.visitorsbook.model.VisitorsBookDto"%>
<%@ include file="/include/header.jsp" %>
<c:if test="${empty visitorinfo}">
	<script type="text/javascript">
	alert("로그인 후 이용 가능합니다.");
	location.href = "${root}/visitor/login.jsp";
	</script>
</c:if>

<c:if test="${!empty visitorinfo}">
	<script> 
	 $(document).ready(function () {
	 	$("#mvRegisterBtn").click(function () {
			location.href = "${root}/visitorsbook?act=mvregister";
				});
			});
	</script>

    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
            <h2 class="p-3 mb-3 shadow bg-light"><mark class="sky">글 목록</mark></h2>
            <div class="m-3 text-right">
                <button type="button" id="mvRegisterBtn" class="btn btn-link">글 작성</button>
            </div>
            <div class="m-3 text-right">
                <form action="${root}/visitorsbook">
                <input type="hidden" name="act" value="list">
                <select name="key">
                	<option value="visitorid"> 아이디
                	<option value="subject"> 제목
                </select>
                <input type="text" name="word">
                <button type="submit" class="btn btn-link">검색</button>
                </form>
            </div>
            <c:if test="${!empty articles}"></c:if>
            	<c:forEach var="article" items="${articles}">
			<table class="table table-active text-left">
				<tbody>
					<tr class="table-info">
						<td>작성자 : ${article.visitorId} <!-- article.getVisitorId() --></td>
						<td class="text-right">작성일 : ${article.regTime}<!-- article.getRegTime() --></td>
					</tr>
					<tr>
						<td colspan="2" class="table-danger">
							<strong>${article.articleNo}. ${article.subject}<!-- article.getArticleNo(). article.getSubject()--></strong>
							<c:if test="${ visitorinfo.visitorId eq article.visitorId }">
								<a href="${root}/visitorsbook?act=mvmodify&articleno=${ article.articleNo }">수정</a>
								<a href="${root}/visitorsbook?act=delete&articleno=${ article.articleNo }">삭제</a>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="p-4" colspan="2">
							${article.content}<!-- article.getContent() -->
						</td>
					</tr>
				</tbody>
			</table>
		
			</c:forEach>
			<c:if test="${empty articles}">
				<table class="table table-active text-center">
					<tr class="table-info">
						<td>작성한 글이 없습니다.</td>
					</tr>
				</table>
			</c:if>

		</div>
    </div>
</body>
</html>
</c:if>