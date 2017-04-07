<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>main</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/frame.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.8.2.js"></script>
	<script type="text/javascript">
		function pageonload(){
			var lastpjsj = '${lastpjsj}';
			if(lastpjsj != null && lastpjsj != ''){
				var ispj = '${pjbean}';
				if(ispj == null || ispj == ''){
					window.location.href = "<%=request.getContextPath()%>/xtpj/xtpj_initEvaluation.action";
				}
			}
		}
	</script>
  </head>
  <body>
	<table width="100%" height="80%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center">
				<div id="main_welcome"></div>
				<div id="main_name">欢迎使用${SysAttribute.title }交警局窗口监管系统！</div>
			</td>
		</tr>
	</table>
  </body>
</html>
