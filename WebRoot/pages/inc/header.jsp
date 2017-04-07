<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>top</title>
	<meta http-equiv="pragma" content="no-cache">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/frame.css"></link>
	<script type="text/javascript">
		function openPage(){
			window.open('<%=request.getContextPath()%>/pages/user/updatepwd.jsp', '修改用户密码', 'width=700, height=500, location=no, status=no');
		}
	</script>
  </head>
  <body>
	<div id="header_img_1">
		<div id="header_img_2"></div>
		<div id="header_img_3">
			<ul>
				<li><a href="<%=request.getContextPath()%>/wfjb/user_loginOut.action" target="_top"><img src="<%=request.getContextPath()%>/images/frame_10.gif"></a></li>
				<li><a href="javascript: void(0);" onclick="openPage();" target="mainFrame"><img src="<%=request.getContextPath()%>/images/frame_09.gif"></a></li>
				<li><a href="javascript: void(0);"><img src="<%=request.getContextPath()%>/images/czsc.jpg"></a></li>
				<li><a href="<%=request.getContextPath()%>/pages/frame.jsp" target="_top"><img src="<%=request.getContextPath()%>/images/frame_07.gif"></a></li>
			</ul>
		</div>
	</div>
	<div id="header_img_4">
		<div id="header_img_5"></div>
		<div id="header_menu">
			<div id="header_menu_logo"><img src="<%=request.getContextPath()%>/images/frame_18.gif" align="top"/></div>
			<span id="header_menu_text">管理菜单</span>
		</div>
		<div id="header_img_6"></div>
		<div id="header_text">当前登录用户：${userbean.username}</div>
		<div id="header_img_7"></div>
	</div>
	
  </body>
</html>
