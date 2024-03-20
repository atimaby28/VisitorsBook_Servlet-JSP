<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>

<c:if test="${cookie.visitor_id.value ne null}">
	<c:set var="svid" value="${ cookie.visitor_id.value }"/>
	<c:set var="ckyn" value="checked"/>
</c:if>
<!--  
	String svid = "";
	String ckyn = "";
	Cookie[] cookies = request.getCookies();
	if(cookies != null) {
		for(Cookie cookie : cookies) {
			if("visitor_id".equals(cookie.getName())){
				svid = cookie.getValue();
				ckyn = "checked";
				break;
			}
		}
	}
-->
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visitor's Book - 로그인</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        mark.orange {
            background: linear-gradient(to top, orange 20%, transparent 30%);
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#loginBtn").click(function () {
                if (!$("#visitorid").val()) {
                    alert("아이디 입력!!!");
                    return;
                } else if (!$("#visitorpwd").val()) {
                    alert("비밀번호 입력!!!");
                    return;
                } else {
                    $("#loginform").attr("action", "${root}/visitor").submit();
                }
            });
            $("#mvRegisterBtn").click(function () {
                location.href = "${root}/visitor/join.jsp";
            });
        });
    </script>
</head>
<body>
    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
            <h2 class="p-3 mb-3 shadow bg-light"><mark class="orange">로그인</mark></h2>
            <form id="loginform" class="text-left mb-3" method="post" action="">
                <input type="hidden" name="act" id="act" value="login">
                <div class="form-group form-check text-right">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" id="idsave" name="idsave" value="saveok" ${ckyn}> 아이디저장
                    </label>
                </div>
                <div class="form-group">
                    <label for="visitorid">아이디</label>
                    <input type="text" class="form-control" id="visitorid" name="visitorid" placeholder="" value="${svid}">
                </div>
                <div class="form-group">
                    <label for="visitorpwd">비밀번호</label>
                    <input type="password" class="form-control" id="visitorpwd" name="visitorpwd" placeholder="">
                </div>
                <div class="text-danger mb-2">${msg}<!-- msg == null ? "" : msg --></div>
                <div class="form-group text-center">
                    <button type="button" id="loginBtn" class="btn btn-outline-warning">로그인</button>
                    <button type="button" id="mvRegisterBtn" class="btn btn-outline-primary">회원가입</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>