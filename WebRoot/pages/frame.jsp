<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>信息发布平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/frame.css">
  </head>
  <frameset rows="110,*,29" border="0" framespacing="0" frameborder="no" id="Framefoot">
  	<frame src="<%=request.getContextPath()%>/pages/inc/header.jsp" frameborder="0" name="headerFrame" scrolling="no"/>
  	<frameset cols="190,10,*,8" border="0" frameborder="no" framespacing="0" id="FrameLeft" >
  		<frame src="<%=request.getContextPath()%>/pages/inc/menu.jsp" scrolling="no" name="menuFrame">
  		<frame id="middleFrame" name="middleFrame" src="<%=request.getContextPath()%>/pages/inc/middle.jsp" scrolling="no" noresize="noresize"  /> 
  		<frame src="<%=request.getContextPath()%>/pages/inc/main.jsp" name="mainFrame" id="mainFrame" scrolling="yes"/>
  		<frame src="<%=request.getContextPath()%>/pages/inc/right.jsp" name="rightFrame"/>
  	</frameset>
  	<frame src="<%=request.getContextPath()%>/pages/inc/footer.jsp" frameborder="0" name="footerFrame"/>
  </frameset>
  <noframes>
  <body>
	<div id="noframe">该浏览器不支持框架模式，请换一个再试，谢谢！</div>
  </body>
  </noframes>
</html>
