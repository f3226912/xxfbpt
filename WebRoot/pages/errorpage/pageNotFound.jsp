<%@ page language="java" pageEncoding="UTF-8" isErrorPage="true"%>
<%response.setStatus(HttpServletResponse.SC_OK);%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>404</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/frame.css">
  </head>
  <body>
	<table width="100%" height="80%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center">
				<div id="warn_frame">
					<div id="warn_frame_img"><img src="${contextPath}/images/error.png"></div>
					<div id="warn_frame_msg">
						该页面不存在！<a href="javascript:window.history.go(-1)">返回上一页！</a>
					</div>
				</div>
			</td>
		</tr>
	</table>
  </body>
</html>