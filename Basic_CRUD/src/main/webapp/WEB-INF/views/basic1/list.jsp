<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../include/header.jsp"></jsp:include> 
<meta charset="UTF-8">
<link rel="stylesheet" href="css/basic1.css">
<title>Insert title here</title>
</head>
<body style="text-align: center;">
	   <div class="search" style="left: 50%;position: relative;transform: translateX(-50%);width: 400px;">
        <div class="inner_case">
                    <form class="row">
                    <div style="display: flex;">
	                    <div class="search_move_1">
	                           <select name="search" class="form-select control">
	                              <option value="0">검색항목</option>
	                              <option value="1" ${pager.search==1?'selected':''}>번호</option>
	                              <option value="2" ${pager.search==2?'selected':''}>날짜</option>
	                              <option value="3" ${pager.search==3?'selected':''}>제목</option>
	                         </select>
	                     </div>
	                     <div class="search_move_2"><input type="text" name="keyword" value="${pager.keyword eq 'null'?'':pager.keyword}" class="form-control control"></div>
	                     <div class="search_move_3"><button class="btn btn-search">검색</button></div>
                    </div>
                    </form>
        </div>
    </div>
    <div>
        <div class="inner_case">
            <table border="1" class="table_all" style="left: 50%;position: relative;transform: translateX(-50%);width: 1000px;">
                 <thead class="table_head">
                    <tr>
                        <td>번호</td>
                        <td>작성일</td>
                        <td>제목</td>
                    </tr>
                </thead>
    
                <tbody class="table_body">
                    <c:forEach var="item" items="${list}">
                        <!-- list를 items에 제이쿼리로 넘김 item선언은 덤 반복!시작! -->
                        <tr>
                            <!--  여기서 내용을 담아줌! -->
                            <td>${item.one}</td>
                            <td>${item.date}</td>
                            <td>${item.name}</td>
                            <td class="deleteUpdate"><a href="delete/${item.one}" class="btn btn-sm btn-danger">삭제</a> 
    												<a href="update/${item.one}" class="btn btn-sm btn-warning">변경</a></td>
                        </tr>
                        <!-- 딜리트 버튼 누르면 바로 보내네  update누르면 update로 바로보냄-->
                    </c:forEach>
                    <c:if test="${list.size() < 1||list==null}">
                        <!-- list의 길이가  -->
                        <tr>
                            <td colspan="3">등록 된 자료가 없습니다</td>
                        </tr>
                    </c:if>
                </tbody>
                <tfoot class="table_foot">
                    <tr>
                        <td colspan="5">
                        <div class="pagination justify-content-center table_padding" >
                            <div class="page-item"><a href="?page=1${pager.query}" class="page-link button">처음</a></div>
                            <div class="page-item"><a href="?page=${pager.prev}${pager.query}" class="page-link button">이전</a></div> 
                            
                            <c:forEach var="page" items="${pager.list}">
                                <div class="page-item ${page == pager.page ? 'active':''}"><a href="?page=${page}${pager.query}" class="page-link button">${page}</a></div>
                            </c:forEach>
                            <c:if test="${pager.size() < 1||pager==null}">
		                        <div class="page-link button">1</div>
		                    </c:if>	
    
                            <div class="page-item"><a href="?page=${pager.next}${pager.query}" class="page-link button">다음</a></div>
                            <div class="page-item"><a href="?page=${pager.last}${pager.query}" class="page-link button">마지막</a>
                            </div>
                        </div>
                        </td>
						<td class="addtag"></td>
                    </tr>
                </tfoot>
            </table>
            <a href="add" class="btn btn-primary">등록</a>
        </div>
    </div>
</body>
</html>