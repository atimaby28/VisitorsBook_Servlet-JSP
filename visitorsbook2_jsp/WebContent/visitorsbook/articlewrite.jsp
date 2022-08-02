<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.visitor.util.DBUtil, java.sql.*"%>
<%! private final DBUtil util = DBUtil.getInstance(); %>
<%
//1. data get
request.setCharacterEncoding("utf-8"); // 받는 인코딩 처리, post에서만 !
String id = request.getParameter("visitorid");
String subject = request.getParameter("subject");
String content = request.getParameter("content");
System.out.println(subject);

//2. logic
Connection conn = null;
PreparedStatement pstmt = null;
int cnt = 0;

try {
	conn = util.getConnection();
	String sql = "insert into visitorsbook (visitorid, subject, content) \n";
	sql += "values (?, ?, ?)";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, id);
	pstmt.setString(2, subject);
	pstmt.setString(3, content);
	cnt = pstmt.executeUpdate();
} catch (SQLException e) {
	e.printStackTrace();
} finally {
	util.close(pstmt, conn);
}
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Visitor - 글목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#mvListBtn").click(function () {
                location.href = "/visitorsbook2_jsp/visitorsbook/list.jsp";
            });
            $("#mvRegisterBtn").click(function () {
                location.href = "/visitorsbook2_jsp/visitorsbook/write.jsp";
            });
        });
    </script>
</head>

<body>
    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
        <%
        if(cnt != 0) {
        %>
            <div class="jumbotron">
                <h1 class="text-primary">글작성을 성공했습니다.</h1>
                <p class="mt-4"><button type="button" id="mvListBtn" class="btn btn-outline-dark">글목록 페이지로 이동</button>
                </p>
            </div>
		<%
        } else {
		%>
            <div class="jumbotron">
                <h1 class="text-danger">글작성을 실패했습니다.</h1>
                <p class="mt-4"><button type="button" id="mvRegisterBtn" class="btn btn-outline-dark">글쓰기 페이지로 이동</button>
                </p>
            </div>
        <%
        }
        %>
        </div>
    </div>
</body>

</html>