<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	
<div style="border:0.5px solid #ebebeb;;margin-top:3.5%"></div>
<div class="grayBlock" style="margin-left:3%">推荐阅读</div>
<div style="margin-left:3%;">
	<div class="tuijianyuedu">
		<div>
			<c:forEach var="item" items="${content.contentRelatedArticlesList}" varStatus="status">
				<p><a href="view.h5?isFull=1&id=${item.relatedArticlesId}">${item.relatedArticlesName }</a></p>
			</c:forEach>
		</div>
	</div>
</div>

<div style="height:auto;width:100%">
<div style="border:0.5px solid #ebebeb;;margin-top:3.5%"></div>
<div class="grayBlock" style="margin-left:3%;margin-bottom:2%">精彩评论</div>
<div style="margin-left:3%;height:auto;">

	<c:forEach var="item" items="${hotComments.list}" varStatus="status">
	<div class="comment_cell" >
		<div style="float:left;"> 
			<img class="comment_face" src="${item.faceSrc }"></img>
		</div>
		<div  style="float:left;width:80%;margin-left:2%">
		<div style="float:left" class="comment_nickname">${item.nickName }</div>
		<div style="float:left;padding-left:2%" class="comment_senddate"><date:date value ="${fn:substring(item.createDate,0,10)}"  hommization="true"/></div>
		<div style="float:right;" class="comment_like">
			<div class="comment_like_img" ></div>
			<div class="comment_like_count">${item.likeCount}</div>
			<div style="clear:both;"></div>
		</div>
		<div style="padding-top:2%;padding-bottom:0px" class="comment_text"><p>${item.content}</p></div>
		</div>
		<c:if test="${!status.last}">
			<div style="float:left;width:100%;border:0.5px solid #ebebeb;margin-bottom:2%;margin-top:2%;"></div>
		</c:if>
		<div style="clear:both;"></div>
	</div>
	</c:forEach>
</div>
</div>

<div style="height:auto;width:100%">
<div style="border:0.5px solid #ebebeb;"></div>
<div class="grayBlock" style="margin-left:3%;margin-bottom:2%">最新评论</div>
<div style="margin-left:3%;height:auto;">
	<c:forEach var="item" items="${newComments.list}" varStatus="status">
	<div class="comment_cell" >
		<div style="float:left;"> 
			<img class="comment_face" src="${item.faceSrc }"></img>
		</div>
		<div  style="float:left;width:80%;margin-left:2%">
		<div style="float:left" class="comment_nickname">${item.nickName }</div>
		<div style="float:left;padding-left:2%" class="comment_senddate"><date:date value ="${fn:substring(item.createDate,0,10)}" hommization="true"/></div>
		<div style="float:right;" class="comment_like">
			<div class="comment_like_img" ></div>
			<div class="comment_like_count">${item.likeCount}</div>
		</div>
		<div style="padding-top:2%;padding-bottom:0px" class="comment_text"><p>${item.content}</p></div>
		</div>
		<div style="float:left;width:100%;border:0.5px solid #ebebeb;margin-bottom:2%;margin-top:2%;"></div>
		<div style="clear:both;"></div>
	</div>
	</c:forEach>
</div>
</div>
</body>
</html>