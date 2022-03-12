<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<fmt:parseDate value = "${now}" var="data123" pattern="yyyy-MM-dd"/>
<%
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
 <style>
   .cls1 {text-decoration:none;}
   .cls2{text-align:center; font-size:30px;}
  </style>
  <meta charset="UTF-8">
  <title>글목록창</title>
</head>
<script>
	function fn_articleForm(isLogOn,articleForm,loginForm){
	  if(isLogOn != '' && isLogOn != 'false'){
	    location.href=articleForm;
	  }else{
	    alert("로그인 후 글쓰기가 가능합니다.")
	    location.href=loginForm+'?action=/board/articleForm.do';
	  }
	}
</script>
<body>
<table align="center" border="1"  width="80%"  >
  <tr height="10" align="center"  bgcolor="lightgreen">
     <td >글번호</td>
     <td >작성자</td>              
     <td >제목</td>
     <td >작성일</td>
     <td >조회수</td>
  </tr>
  
  <style>
  .no-uline {text-decoration:none;}
  .sel-page{text-decoration:none;color:red;}
  .cls1{text-decoration:none;}
  .cls2{text-align:center; font-size:30px;}
  </style>
<c:choose>
  <c:when test="${paginMap.articleList ==null }" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
 
  <!-- 페이징과 게시판을 위해 구현  10개씩 자름-->
  <c:when test="${paginMap.articleList !=null}" >
  
    <c:forEach  var="article" items="${paginMap.articleList }" varStatus="articleNum" >
     <tr align="center">
	<td width="5%">${articleNum.count}</td>
	<td width="10%">${article.id }</td>
	<td align='left'  width="35%">
	  <span style="padding-right:30px"></span>
	   <c:choose>
	      <c:when test='${article.level > 1 }'>  
	         <c:forEach begin="1" end="${article.level }" step="1">
	              <span style="padding-left:20px"></span>    
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
	       <c:if test="${article.articleNOn == '1'}">[공지]</c:if>
                   <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title} </a>
                   <c:if test="${article.writeDate == now}">[새글]</c:if>
	          </c:when>
	          <c:otherwise>
	          <c:if test="${article.articleNOn == '1'}">[공지]</c:if>
	            <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
	            <c:if test="${article.writeDate == now }">[새글]</c:if>
	          </c:otherwise>
	        </c:choose>
	  </td>
	  <td  width="10%">${article.writeDate}</td> 
	  <td width="10%">${article.counting }</td>
	</tr>
	</c:forEach>
     </c:when>
    </c:choose>
	
    
</table>
<div class ="txt_center">
	<c:if test ="${paginMap.totArticles != null }">
	<c:choose>
	<c:when test="${paginMap.totArticles > 100}">
	<c:forEach var="page" begin ="1" end="10" step="1">
	<c:if test ="${paginMap.section > 1 && page == 1 }">
	<a class ="no-uline" href="${contextPath}/board/listArticles.do?section=${paginMap.section-1 }&pageNum=${(paginMap.section-1)*10+1 }">&nbsp;pre</a>
	</c:if>
	<a class ="no-uline" href ="${contextPath}/board/listArticles.do?section=${paginMap.section}&pageNum=${page}">${(paginMap.section-1)*10+page }</a>
	<c:if test ="${page ==10 }">
	<a class="no-uline" href="${contextPath}/board/listArticles.do?section=${paginMap.section-1 }&pageNum=${(paginMap.section-1)*10+1 }">&nbsp;next</a>
	</c:if>
	</c:forEach>
	</c:when>
	<c:when test = "${paginMap.totArticles ==100 }">
	<c:forEach var="page" begin="1" end="10" step="1">
	<a class="no-uline" href = "#">${page }</a>
	</c:forEach>
	</c:when>
	
	<c:when test="${paginMap.totArticles < 100}">
	<c:forEach var="page" begin="1" end ="${paginMap.totArticles/10+1 }" step="1">
	<c:choose>
	<c:when test ="${page==pageNum }">
	<a class="sel-page" href="${contextPath}/board/listArticles.do?section=${paginMap.section}&pageNum=${page}">${page }</a>
	</c:when>
	<c:otherwise>
	<a class ="no-uline" href="${contextPath}/board/listArticles.do?section=${paginMap.section}&pageNum=${page}">${page }</a>
	</c:otherwise>
	</c:choose>
	</c:forEach>
	</c:when>
	</c:choose>
	</c:if>
	</div>
<!-- <a  class="cls1"  href="#"><p class="cls2">글쓰기</p></a> -->
<a  class="cls1"  href="javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do', 
                                                    '${contextPath}/member/loginForm.do')"><p class="cls2">글쓰기</p></a>
</body>
</html>