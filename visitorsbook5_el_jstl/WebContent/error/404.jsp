<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Visitor's Book - 글 목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#mvIndex").click(function () {
                location.href = "${root}";
            });
        });
    </script>
</head>

<body>
    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
            <div class="jumbotron">
                <h1 class="text-danger">요청하신 페이지를 찾을 수 없습니다.</h1>
                <p class="mt-4"><button type="button" id="mvIndex" class="btn btn-outline-dark">메인으로 이동</button>
                </p>
            </div>
        </div>
    </div>
</body>

</html>