<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visitor - 회원가입</title>
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
            $("#registerBtn").click(function () {
                if (!$("#visitorname").val()) {
                    alert("이름 입력!!!");
                    return;
                } else if (!$("#visitorid").val()) {
                    alert("아이디 입력!!!");
                    return;
                } else if (!$("#visitorpwd").val()) {
                    alert("비밀번호 입력!!!");
                    return;
                } else if ($("#visitorpwd").val() != $("#pwdcheck").val()) {
                    alert("비밀번호 확인!!!");
                    return;
                } else {
                    $("#memberform").attr("action", "<%= root %>/visitor").submit();
                }
            });

            // $('#zipcode').focusin(function () {
            //     $('#zipModal').modal();
            // });
        });
    </script>
</head>
<body>
    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
            <h2 class="p-3 mb-3 shadow bg-light"><mark class="orange">회원가입</mark></h2>
            <form id="memberform" class="text-left mb-3" method="post" action="">
                <input type="hidden" name="act" id="act" value="register">
                <div class="form-group">
                    <label for="visitorname">이름</label>
                    <input type="text" class="form-control" id="visitorname" name="visitorname" placeholder="이름입력...">
                </div>
                <div class="form-group">
                    <label for="visitorid">아이디</label>
                    <input type="text" class="form-control" id="visitorid" name="visitorid" placeholder="아이디입력...">
                </div>
                <div class="form-group">
                    <label for="visitorpwd">비밀번호</label>
                    <input type="password" class="form-control" id="visitorpwd" name="visitorpwd" placeholder="비밀번호입력...">
                </div>
                <div class="form-group">
                    <label for="pwdcheck">비밀번호재입력</label>
                    <input type="password" class="form-control" id="pwdcheck" name="pwdcheck" placeholder="비밀번호재입력...">
                </div>
                <div class="form-group">
                    <label for="emailid">이메일</label><br>
                    <div id="email" class="custom-control-inline">
                        <input type="text" class="form-control" id="emailid" name="emailid" placeholder="이메일아이디입력..."
                            size="25"> @
                        <select class="form-control" id="emaildomain" name="emaildomain">
                            <option value="visitor.com">방문자</option>
                            <option value="naver.com">네이버</option>
                            <option value="kakao.com">카카오</option>
                            <option value="google.com">구글</option>
                        </select>
                    </div>
                </div>
                <!--
                <div class="form-group">
                    <label for="tel1">전화번호</label>
                    <div id="tel" class="custom-control-inline">
                        <select class="form-control" id="tel1" name="tel1">
                            <option value="010">010</option>
                            <option value="02">02</option>
                            <option value="031">031</option>
                            <option value="032">032</option>
                            <option value="041">041</option>
                            <option value="051">051</option>
                            <option value="061">061</option>
                        </select> _
                        <input type="text" class="form-control" id="tel2" name="tel2" placeholder=""> _
                        <input type="text" class="form-control" id="tel3" name="tel3" placeholder="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="zipcode">주소</label><br>
                    <div id="addressdiv" class="custom-control-inline">
                        <input type="text" class="form-control" id="zipcode" name="zipcode" placeholder="우편번호" size="7"
                            maxlength="5" readonly="readonly">
                    </div>
                    <input type="text" class="form-control" id="address" name="address" placeholder="">
                    <input type="text" class="form-control" id="address_detail" name="address_detail" placeholder="">
                </div>
                -->
                <div class="form-group text-center">
                    <button type="button" class="btn btn-outline-primary" id="registerBtn">회원가입</button>
                    <button type="reset" class="btn btn-outline-danger">초기화</button>
                </div>
            </form>
        </div>
    </div>
    <!-- 우편번호 검색 모달 -->
    <!--
    <div id="zipModal" class="modal fade" role="dialog">
        <h5 class="modal-title" id="myModalLabel">우편번호검색</h5>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body text-center">
                    <form id="zip_codeForm">
                        <div align="center">
                            <label>도로명 주소검색</label>
                        </div>
                        <div class="input-group">
                            <input type="text" class="form-control" id="doro" name="doro"
                                placeholder="검색 할 도로명 입력(예: 대왕판교로, 학하서로)">
                            <span class="input-group-btn">
                                <input type="submit" class="btn btn-warning" value="검색" id="searchBtn">
                            </span>
                        </div>
                    </form>
                    <div style="width:100%; height:200px; overflow:auto">
                        <table class="table text-center">
                            <thead>
                                <tr>
                                    <th style="width:150px;">우편번호</th>
                                    <th style="width:600px;">주소</th>
                                </tr>
                            </thead>
                            <tbody id="zip_codeList"></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    -->
</body>
</html>