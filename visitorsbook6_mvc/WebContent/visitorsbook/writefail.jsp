<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#mvRegisterBtn").click(function () {
                location.href = "${root}/visitorsbook/write.jsp";
            });
        });
    </script>

    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
            <div class="jumbotron">
                <h1 class="text-danger">글 작성을 실패했습니다.</h1>
                <p class="mt-4"><button type="button" id="mvRegisterBtn" class="btn btn-outline-dark">글쓰기 페이지로 이동</button>
                </p>
            </div>
        </div>
    </div>
</body>

</html>