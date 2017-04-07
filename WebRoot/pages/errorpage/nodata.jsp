<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>exception</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/frame.css"/>
  </head>
  <body>
	<table width="100%" height="80%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
				<div id="exception_frame">
					${errorTip}
				<br />
					<a href="javascript:window.history.go(-1)">返回上一页！</a>
				</div>
			</td>
		</tr>
	</table>
  </body>
</html>