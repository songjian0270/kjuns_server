<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tags" prefix="date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <meta name="viewport" content="width=device-width, initial-scale=1" />
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
<style>
.zhaiyao{
font-family:STHeitiSC-Light;
font-size:16px;
color:#999999;
letter-spacing:0px;
line-height:20px;
text-align:center;
margin:4%;
}
.biaoti{
font-family:STHeitiSC-Light;
font-size:22px;
color:#333333;
letter-spacing:0px;
line-height:26px;
text-align:left;
margin:5%;
}

.notifi{
font-family:STHeitiSC-Light;
font-size:14px;
color:#da3710;
letter-spacing:-0.22px;
line-height:14px;
text-align:left;
}
.msgFrom{
font-family:STHeitiSC-Light;
font-size:12px;
color:#808080;
letter-spacing:-0.41px;
line-height:12px;
text-align:left;
}

.tagStyle{
background:#f2f2f2;
border-radius:20%/50%;
font-family:STHeitiSC-Light;
font-size:13px;
color:#333333;
height:101%;
line-height:170%;text-align:center;
float:left;
padding-left:3%;
padding-right:3%;
margin-right:2%;
margin-top:1%;
}

.grayBlock{
background:#f2f2f2;
width:18%;
height:3%;

font-family:STHeitiSC-Light;
font-size:14px;
color:#333333;
letter-spacing:-0.22px;
line-height:150%;
text-align:center;
}

.tuijianyuedu div a{
font-family:STHeitiSC-Medium;
font-size:14px;
color:#333333;
letter-spacing:0.03px;
line-height:14px;
text-align:left;
padding-top:3%;
}

.tuijianyuedu div a:link{
text-decoration:none;
}
.tuijianyuedu div a:visited{
text-decoration:none;
}
.tuijianyuedu div a:hover{
text-decoration:none;
}
.tuijianyuedu div a:active{
text-decoration:none;
}

.tuijianyuedu{
text-align:left;
margin-left:3%;
}

.zhengwen{
font-family:STHeitiSC-Light;
font-size:17px;
color:#333333;
letter-spacing:-0.26px;
line-height:22px;
text-align:left;text-overflow:clip;
word-break:break-all;
}

.zhengwen img{
width:100%;
background:black;
}
.zhengwen p{
padding:0px 6%;
}

.comment_cell .comment_text{
font-family:STHeitiSC-Light;
font-size:14px;
color:#999999;
letter-spacing:-0.26px;
line-height:14px;
text-align:left;
}

.comment_cell{
 height:auto;
 margin:0 auto;
 min-height:50px
}

.comment_cell .comment_nickName{
font-family:STHeitiSC-Medium;
font-size:14px;
color:#333333;
letter-spacing:0.03px;
line-height:14px;
text-align:left;
}

.comment_cell .comment_senddate{
font-family:PingFangSC-Regular;
font-size:14px;
color:#cccccc;
letter-spacing:0.03px;
line-height:14px;
text-align:left;
}

.comment_cell .comment_face{
width:41px;
height:41px;
background:#d8d8d8;
border:1px solid #979797;
border-radius:100%;
background-size: cover;
}

.comment_like{
width:10%;
height:10%;
}


.comment_cell .comment_like_img{
width:14px;
height:12px;
float:left;
position: relative;
background-image:url(http://7xwu0j.com1.z0.glb.clouddn.com/7033F66D-AF2B-42B5-8E7B-097D67D5A089@3x.png);
background-size:cover;margin-right:-14px;
}
.comment_cell .comment_like_count{
font-family:.PingFangSC-Light;
font-size:14px;
color:#999999;
letter-spacing:0px;
line-height:14px;
text-align:left;
margin:0px;
padding-left:20px;
}

.senderName{
margin:0px;
font-family:STHeitiSC-Light;
font-size:15px;
color:#333333;
letter-spacing:0px;
line-height:15px;
text-align:left;
padding-left:1vw;
float:left;
}

.sendDate{
font-family:STHeitiSC-Light;
font-size:14px;
color:#999999;
letter-spacing:0px;
line-height:15px;
text-align:left;float:right;
}
body{

margin:0;
}

.senderFace{

background-image:url(${content.issuerFaceSrc});
background-size:cover;margin-right:-14px;
}

</style>

</head>
<body>
<div style="width:100%;height:667px;text-align:center;max-width:600px;
margin-left: auto; margin-right: auto;">
	<div style="width:100%;height:37%"><img style="background-color:black;width:100%;height:100%" src="${content.mindMap}"/></div>
	<div ><p class="biaoti">${content.title}</p></div>
	<div style="height:5%;margin:5%;">
		<div style="height:100%;float:left;width:50%">
			<div class="senderFace" style="height:20px;width:20px;margin:0 auto;float:left"></div>
			<p class="senderName">${content.issuerName }</p>
		</div>
		<div class="sendDate"><date:date value ="${fn:substring(content.createDate,0,10)}"/></div>
	</div>

<div style="margin-top:4%;">
	<p class="zhaiyao">${content.summary}</p>
</div>
<div style="border:1px solid #ebebeb;
width:27%;
height:0px;text-align:center;
margin:0 auto;"></div>
<div style="width:100%" class="zhengwen">
${ content.content}
</div>
<div style="margin:5% 5% 0px 5%">
<div style="height:2%;width:100%" class="notifi">看军事社区规范：直抒胸臆  理性爱国</div>
<div style="height:2%;width:100%;margin-top:2%" class="msgFrom">消息参考来源：${content.source }</div>

<c:if test="${content.contentTagList.size()>0}">
	<div style="width:100%;margin-top:2%">
	<c:forEach var="item" items="${content.contentTagList}" varStatus="status">
	<div class="tagStyle">${item.tagName}</div>
	</c:forEach>
	<div style="clear:both;"></div>
	</div>
</c:if>
</div>

<c:if test="${isFull=='1'}">
<jsp:include page="comments.jsp" />
</c:if>
</div>
</body>
<script>
function getAllImg(){
 return document.getElementsByTagName("img");
}

function initImage(){ 
	if(document.getElementsByName){ 
		var alltr = getAllImg();
		for(var i=0;i<alltr.length;i++){
			alltr[i].onclick=function(){
				clickImg(this);
			}
		}
	}
}

function clickImg(target){
	if(navigator.userAgent.match(/(iPhone|iPod|ios)/i)){
		window.location.href = 'kanjunshi://content/img/'+ target.getAttribute('src');
	}
	else{
		window.KanjunshiAndroid.showImgs(target.getAttribute('src')+"|"+getImgSrc(getAllImg()));
	}
}

function getImgSrc(target){
	var result ;
	var alltr = getAllImg();
	for(var i=0;i<alltr.length;i++){
		result = result+alltr[i].getAttribute('src')+"|"
	}
	return result;
}
window.onload=initImage(); 
</script>
</html>

