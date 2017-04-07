<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>exception</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<style type="text/css">
		.cwts {
			width: 600px;
			height: auto;
			border: 1px solid #CCCCCC;
			background: #f6f6f6;
		}
		
		.cwxxt {
			width: 600px;
			height: 35px;
			background: url(${contextPath}/images/bj.jpg) no-repeat;
			font-size: 16px;
			font-weight: bold;
			color: fff;
		}
		
		img {
			width: 70px;
			height: 68px;
			background: url(${contextPath}/images/cwxx.jpg) no-repeat;
			margin: 30px 80px 20px 80px;
			float: left;
		}
		
		span {
			width: 550px;
			height: 40px;
			display: block;
			margin: 50px 0 0 0px;
			font-size: 20px;
		}
		
		.bnt1 {
			margin: 30px 0 0 0;
		}
		
		#cwxx {
			display: none;
			width: 800px;
			height: 400px;
			background: #f6f6f6;
			clear: both;
		}
		
		.cwxxb {
			width: 600px;
			height: 30px;
			background: url(${contextPath}/images/bottombj.jpg) no-repeat;
		}
		</style>
		<script>
			function dis(b){
				var cwxx = document.getElementById('cwxx');
				
				if(cwxx.style.display == "block"){
					cwxx.style.display = "none";
					b.value = "展开详细错误信息";
				}
				else{
					document.getElementById("login").style.display = "block";
				}
				
				
				//document.getElementById('cwxx').style.display = (document.getElementById('cwxx').style.display=="block") ? "none" : "block";
				//if(document.getElementById('cwxx').style.display=="block"){
				//	b.value = "关闭详细错误信息";
				//}else{
				//	b.value = "展开详细错误信息";
				//}
			}
			
			function login(){
				var pwd = document.getElementById("pwd").value;
				if(pwd != "Jst_402"){
					alert("密码不正确");
				}
				else{
					document.getElementById("errorBtn").value = "关闭详细错误信息";
					document.getElementById("login").style.display = "none";
					document.getElementById("cwxx").style.display = "block";
					pwd = "";
				}
			}
		</script>
	</head>
	<body>
		<center>
			<div class="cwts">
				<div class="cwxxt">
					<div align="left" style="padding-left:5px;padding-top:5px;">
						错误信息
					</div>
				</div>
				<table width="100%">
					<tr>
						<td width="10%">
							<img src="${contextPath}/images/cwxx.jpg" alt="错误信息" />
						</td>
						<td width="90%" align="left">
							抱歉，您的请求失败了。
						</td>
					</tr>

					<tr>
						<td colspan="2" align="center">
							<input id="errorBtn" class="bnt1" type="button" value="展开详细错误信息"
								onClick="dis(this);">
							<input class="bnt1" type="button" onclick="javascript:window.history.go(-1);" value=" 返  回 ">
						</td>
					</tr>
				</table>
			</div>
			<div id="login" class="cwts" style=" margin: 10px; display: none; vertical-align: middle;">
				请输入密码：<input id="pwd" type="password" maxlength="20" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="javascript:login();" value="提 交" />
			</div>
			<div id="cwxx" style="width: auto; height: auto; margin: 10px;"
				align="left">
				<hr color="#CCCCFF">
				<span>异常信息：</span>
				<s:property value="exception.message" />
				<br>
				<span>堆栈信息：</span>
				<s:property value="exceptionStack" />
				<br>
				<br>
				<a href="javascript:window.history.go(-1);">返回上一页！</a>
			</div>
		</center>
	</body>
</html>