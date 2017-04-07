<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
<base href="<%=basePath%>">
<title>打开关闭左边菜单</title>
<style type="text/css">
body{margin:0px;padding:0px;font-size:12px; border-right: 1px solid #BFD0FF;} 
#Heade{position:absolute;top:250px;vertical-align:middle;color:#777777; }
</style>
</head>
<body id="bodyid" style="background:url(<%=request.getContextPath()%>/images/frame_13.gif) 0 0 repeat-y">
	<div id="page" style="height: 100%; cursor: pointer;" onclick="switchBar(this)">
	<div id="Heade">
 	<img id="imgHead" src="images/ctt-btn.png" width="8px" height="34px" title="关闭/左边菜单"/></div></div>
</body>
 <script type="text/javascript" src="jquery/js/jquery-1.8.2.js"></script>
 <script type="text/javascript">
  var displayBar=true;
  function switchBar(obj){
  	if(displayBar) {  
  		 $("#FrameLeft",parent.document.body).attr("cols","0,10,*,8"); 
  		 displayBar=false; 
 		 $("#imgHead").attr("src","images/ctt2-btn.png"); 
 		 $("#imgHead").attr("title","打开/左边菜单");   
 		 $("#bodyid").attr("style", "background:url(<%=request.getContextPath()%>/images/frame_12.gif) 0 0 repeat-y");
 	}else{
 		$("#FrameLeft",parent.document.body).attr("cols","190,10,*,8");
 		displayBar=true;
 		$("#imgHead").attr("src","images/ctt-btn.png");
 		$("#imgHead").attr("title","关闭/左边菜单"); 
 		$("#bodyid").attr("style", "background:url(<%=request.getContextPath()%>/images/frame_13.gif) 0 0 repeat-y");
 	}
  }
</script>
</html>  