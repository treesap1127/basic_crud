<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js" charset="utf-8"></script>
<jsp:include page="include/header.jsp"></jsp:include> 
<link rel="stylesheet" href="css/index.css">
<script src="js/index.js"></script>
<meta charset="UTF-8">
</head>
<body>
<div style="text-align: center;">
 <h1>연습용</h1>
 <a href=""></a>
</div>
 <ul>
      <li onclick="kakaoLogin();">
        <a href="javascript:void(0)">
            <span>카카오 로그인</span>
        </a>
      </li>

  </ul>
      <c:if test="${not empty sessionScope.kakaoInfo}">
      	<ul>
      		<li>id====${sessionScope.kakaoInfo.id}</li>
      		<li>profile_nickname====${sessionScope.kakaoInfo.profile_nickname}</li>
      		<li>profile_image====${sessionScope.kakaoInfo.profile_image}</li>
      		<li>thumbnail_image====${sessionScope.kakaoInfo.thumbnail_image}</li>
      		<li>account_email====${sessionScope.kakaoInfo.account_email}</li>
      		<li>age_range====${sessionScope.kakaoInfo.age_range}</li>
      		<li>birthday====${sessionScope.kakaoInfo.birthday}</li>
      		<li>gender====${sessionScope.kakaoInfo.gender}</li>
      		<a href="/kakaologout?code=${code}">로그아웃</a>
      	</ul>
      </c:if>
   <ul>
      <li onclick="naverLogin();">
        <a href="javascript:void(0)">
            <span>네이버 로그인</span>
        </a>
      </li>
  </ul>
  <c:if test="${not empty sessionScope.naverlogin}">
      	<ul>
      	    <li>id====${sessionScope.naverlogin.id}</li>
      	    <li>age====${sessionScope.naverlogin.age}</li>
      		<li>birthday====${sessionScope.naverlogin.birthday}</li>
      		<li>birthyear====${sessionScope.naverlogin.birthyear}</li>
      		<li>email====${sessionScope.naverlogin.email}</li>
      		<li>gender====${sessionScope.naverlogin.gender}</li>
      		<li>mobile====${sessionScope.naverlogin.mobile}</li>
      		<li>name====${sessionScope.naverlogin.name}</li>
      		<li>nickname====${sessionScope.naverlogin.nickname}</li>
      		<li>profile_image====${sessionScope.naverlogin.profile_image}</li>
      		<a href="/naverlogout">로그아웃</a>
      	</ul>
      </c:if>
</body>
</html>

  <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
  <script>
  //카카오로그인
  function kakaoLogin() {

    $.ajax({
        url: '/login/getKakaoAuthUrl',
        type: 'get',
        async: false,
        dataType: 'text',
        success: function (res) {
            location.href = res;
        }
    });
  }

  $(document).ready(function() {

      var kakaoInfo = '${kakaoInfo}';

      if(kakaoInfo != ""){
          var data = JSON.parse(kakaoInfo);

          alert("카카오로그인 성공 \n accessToken : " + data['accessToken']);
          alert(
          "user : \n" + "email : "
          + data['email']  
          + "\n nickname : " 
          + data['nickname']);
      }
  });  
	//네이버로그인
	 function naverLogin() {

    $.ajax({
        url: '/login/getNaverAuthUrl',
        type: 'get',
        async: false,
        dataType: 'text',
        success: function (res) {
            location.href = res;
        }
    });
  }
  </script>