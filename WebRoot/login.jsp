<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>信息发布平台</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.position.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.menu.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.autocomplete.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/css/redmond/jquery-ui.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/css/redmond/jquery-ui.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/jquery.artDialog.source.js?skin=chrome"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/plugins/iframeTools.source.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#img").hide();
				$("#code").click(function(){
					$("#img").show();
				});
				$("#code").keyup(function(){
					$("#img").show();
				});
			});
			function reloadImage(url) {
				var img = document.getElementById("img");
				img.src = url + "?Code=" + Math.random();
			}
			document.onkeydown = function(e) {
				if (!e)
					e = window.event;
				if ((e.keyCode || e.which) == 13) {
					checklogin(); //转到验证的函数
				}
			}
			function resetlogin(){
				$("#username").val("");
				$("#password").val("");
				$("#code").val("");
			}
			function checklogin() {
				var cusername = $("#username").val();
				var cpassword = $("#password").val();
				var ccode = $("#code").val();
				var nullReg = /[^\f\n\r\t\v]/;
			
				if (!nullReg.test(cusername)) {
					alert("请输入用户名!");
					$("#username").focus();
					return false;
				} else if (!nullReg.test(cpassword)) {
					alert("请输入密码!");
					$("#password").focus();
					return false;
				} else if (!nullReg.test(ccode)) {
					alert("请输入验证码!");
					$("#code").focus();
					return false;
				}
				$.ajax({
					cache:false,
					async:false,
					type:'post',
					url: "<%=request.getContextPath()%>/wfjb/user_login.action",
					data: {userid:cusername,password:cpassword,ccode:ccode},
					dataType: 'html',
					success:function(data){
						if (data == 0) {
							window.location.href = '<%=request.getContextPath()%>/pages/frame.jsp';
						} else if (data == 1){
							alert("用户不存在!");
							reloadImage('img.jsp');
							return false;
						} else if (data == 2){
							alert("用户密码错误!");
							reloadImage('img.jsp');
							return false;
						} else if (data == 3){
							alert("验证码错误!");
							reloadImage('img.jsp');
							return false;
						} else {
							exception(data);
							reloadImage('img.jsp');
							return false;
						}
					}
				});
			}
			
			
			function exception(content){
				art.dialog({
					width:'50%',
				    content: content,
				    title: '系统异常',
				    cancelVal: '关闭',
					cancel: true,
					lock: true,
				    opacity: 0.87,
				    icon: 'error'
				});
			}
		</script>
	</head>
	<body style="margin: 0; overflow: hidden">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td bgcolor="#1075b1">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td height="608" background="<%=request.getContextPath()%>/images/login_03.gif">
					<table width="847" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="318" valign="bottom" background="<%=request.getContextPath()%>/images/login_04.gif"></td>
						</tr>
						<tr>
							<td height="84">
								<table width="100%" height="84" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="381" background="<%=request.getContextPath()%>/images/login_06.gif">
											&nbsp;
										</td>
										<td width="162" background="<%=request.getContextPath()%>/images/login_07.gif">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" style="font-size: 12px; color: #adc9d9;">
												<tr valign="bottom">
													<td width="50" height="24" align="right">
														用户名：
													</td>
													<td>
														<input name="username" type="text" id="username"
															style="width: 100px; height: 17px; border: solid 1px #153966; font-size: 12px; color: #283439; background-color: #87adbf;">
													</td>
												</tr>
												<tr valign="bottom">
													<td height="24" align="right">
														密&nbsp;&nbsp;码：
													</td>
													<td>
														<input name="password" type="password" id="password"
															style="width: 100px; height: 17px; border: solid 1px #153966; font-size: 12px; color: #283439; background-color: #87adbf;">
													</td>
												</tr>
												<tr valign="bottom">
													<td height="24" align="right">
														验证码：
													</td>
													<td>
														<input name="code" type="text" id="code"
															style="width: 40px; height: 17px; border: solid 1px #153966; font-size: 12px; color: #283439; background-color: #87adbf;">
														<img id="img" onclick="javascript:reloadImage('img.jsp');"
															title="点击刷新" style="cursor: pointer;" border="0"
															src="<%=request.getContextPath()%>/img.jsp" />
													</td>
												</tr>
											</table>
										</td>
										<td width="26" background="<%=request.getContextPath()%>/images/login_08.gif">
											&nbsp;
										</td>
										<td width="67" background="<%=request.getContextPath()%>/images/login_09.gif">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td height="25" align="center">
														<img onclick="checklogin();" style="cursor: pointer;"
															src="<%=request.getContextPath()%>/images/submit.gif"
															width="57" height="20">
													</td>
												</tr>
												<tr>
													<td height="25" align="center">
														<img src="<%=request.getContextPath()%>/images/reset.gif" style="cursor: pointer;"
															onclick="resetlogin();" width="57" height="20">
													</td>
												</tr>
											</table>
										</td>
										<td width="211" background="<%=request.getContextPath()%>/images/login_05.gif">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="206" background="<%=request.getContextPath()%>/images/login_02.gif">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td bgcolor="#152753">
					&nbsp;
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
				</td>
			</tr>
		</table>
	</body>
</html>
