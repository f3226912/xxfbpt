<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>修改密码</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/module.css">
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.8.2.js"></script>
		<script type="text/javascript">
			function check() {
				var pwd = $("#oldPwd").val();
				var npwd = $("#new_spwd").val();
				var npwdtoo = $("#confirm_spwd").val();
				var msg = document.getElementById("msg");
				var msg2 = document.getElementById("msg2");
				var message = document.getElementById("message");
				if (pwd != null && pwd != "") {
					msg.innerHTML = "";
					if (npwd != "" && npwd != null) {
				    	msg2.innerHTML = "";
						if (npwd != npwdtoo) {
							message.innerHTML = "确认密码与新密码不一致!";
							return false;
						}
					}else{
						msg2.innerHTML = "请输入新密码!";
						return false;
					}
					$.ajax({
						cache:false,
						async:false,
						type:'post',
						url: '<%=request.getContextPath()%>/wfjb/user_updatePassword.action',
						data:{"oldpassword":pwd, "password": npwd},//发送的参数
						dataType: 'html',
						success:function(result){
							var message = result+"";
							if(message.indexOf('异常信息') == -1){
								if($.trim(result) == '1'){
									alert("修改成功,下次登录系统生效!");
									window.close();
								}else if($.trim(result) == '3'){
									msg.innerHTML = "当前密码输入错误！";
									return false;
								}else if($.trim(result) == '2'){
								 	msg2.innerHTML = "新密码与原始密码相同！";
								 	return false;
								}else{
									alert("修改失败!");
									return false;
								}
							}else{
								exception(message);
								return false;
							}
						}
					});
				}else{
					msg.innerHTML = "请输入当前密码！";
					return false;
				}
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
	<body>
		<center>
			<div id="admin_title">
				<div class="logo">
					<img src="<%=request.getContextPath()%>/images/frame_19.gif">
				</div>
				<div class="title">
					用户密码修改
				</div>
			</div>
			<div id="admin_main">
				<form action="<%=request.getContextPath()%>/user/user_updatePwd.action"
					method="post" id="password_form" name="password_form">
					<table border="0" cellpadding="0" cellspacing="0" class="edittable">
						<tr>
							<th style="text-align: right; width: 150px;">
								当前密码：
							</th>
							<td style="text-align: left;">
								&nbsp;&nbsp;<input type="password" id="oldPwd" name="user.spwd"
									id="user.spwd" size="20" maxlength="20" />
								&nbsp;
								<span id="msg" style="color: red;"></span>
							</td>
						</tr>
						<tr>
							<th style="text-align: right;">
								新密码：
							</th>
							<td style="text-align: left;">
								&nbsp;&nbsp;<input type="password" name="new_spwd" id="new_spwd" size="20"
									maxlength="20" />&nbsp;
								<span id="msg2" style="color: red;"></span>
							</td>
						</tr>
						<tr>
							<th style="text-align: right;">
								确认新密码：
							</th>
							<td style="text-align: left;">
								&nbsp;&nbsp;<input type="password" name="confirm_spwd" id="confirm_spwd"
									size="20" maxlength="20"/>
								&nbsp;
								<span id="message" style="color: red;"></span>
							</td>
						</tr>
						<tr>
							<td colspan="2" height="50" align="center">
								<input type="button" id="but" onclick="check();"
									style="cursor:pointer; width:76px; height:27px; background:url(<%=request.getContextPath()%>/images/an2.gif) no-repeat; border:none;  font-weight:bold;" value=" 确 定  " />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</center>
		<div></div>
	</body>
</html>