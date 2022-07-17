<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="../include/header.jsp"></jsp:include> 
<meta charset="UTF-8">
<style>
.form-control{
	width:200px;
	position: relative;
	left: 50%;
	transform:translateX(-50%);
}
</style>
</head>
<body style="text-align: center;">
	  <div class="inner_case" style="padding-bottom: 10rem;">
		<form:form method="post">
			<div class="form-floating">
				<div class="formName">이름</div>
				<input type="text" name="name" class="form-control" > 
				<spring:hasBindErrors name="one">
					<c:if test="${errors.hasFieldErrors('name') }">
					${errors.getFieldError('name').defaultMessage}<br />
					</c:if>
				</spring:hasBindErrors>
			</div>

			<div class="add_info">
				<label>제목</label>
				<input type="text" name="title" class="form-control">
				<spring:hasBindErrors name="one">
					<c:if test="${errors.hasFieldErrors('title') }">
					${errors.getFieldError('title').defaultMessage}<br />
					</c:if>
				</spring:hasBindErrors>
			</div>
			<div class="form-floating">
				<div class="formName">비밀번호</div>
				<input type="password" name="passwd" class="form-control" > 
				<spring:hasBindErrors name="one">
					<c:if test="${errors.hasFieldErrors('passwd') }">
					${errors.getFieldError('passwd').defaultMessage}<br />
					</c:if>
				</spring:hasBindErrors>
			</div>
			
			<div class="add_button">
				<button class="end_button">등록</button>
				<a href="list" class="back_button">뒤로가기</a>
			</div>
		</form:form>
		<div>
			
		</div>
	</div>
</body>
</html>