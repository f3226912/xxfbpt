<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>${editType }授信单位信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/module.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/jquery.artDialog.source.js?skin=chrome"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/plugins/iframeTools.source.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/public.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			var jkids = '${request.jkids}';
			if(jkids != null && jkids != ""){
				$("#jkids").val(jkids);
				var list = jkids.split(",");
				for(var i = 0; i < list.length; i++){
					$("input[name=mids]").each(function(){
						if($(this).val() == $.trim(list[i])){
							$(this).attr("checked", "checked");
						}
					});
				}
			}
			var cztype = '${request.editType}';
			if(cztype == '查看'){
				$("input").not(".btncla").each(function(){
					$(this).attr("disabled", "disabled");
				});
			}
			if(cztype == '修改'){
				$("#userid").attr("readonly", "readonly");
			}
			
			//alert(randomWord(true, 24, 24));
		});
		
		function check(){
			var userid = checknotnull(document.getElementById("userid"),'请输入单位账号');
			if(userid != "true"){
				return false;
			}
			var username = checknotnull(document.getElementById("username"),'请输入单位名称');
			if(username != "true"){
				return false;
			}
			var password = checknotnull(document.getElementById("password"),'请输入密码');
			if(password != "true"){
				return false;
			}
			var keys = checknotnull(document.getElementById("keys"),'请输入密钥');
			if(keys != "true"){
				return false;
			}
			var userip = checknotnull(document.getElementById("userip"),'请输入授信IP');
			if(userip != "true"){
				return false;
			}
			var userid = $("#userid").val();
			var username = $("#username").val();
			
			//验证单位账号是否存在
			var html = $("#nameDesc").html();
			if(html != null && html != ''){
				alert(html);
				return false;
			}
			$.ajax({
				cache:false,
				async:false,
				url:'<%=request.getContextPath()%>/wfjb/user_editInterfaceUserinfo.action',
				type:'post',
				data:$("#role_form").serialize(),
				dataType:'json',
				error:function(XmlHttpRequest,textStatus, errorThrown){
					exception(XmlHttpRequest.responseText);
					return false;
				},
				success: function(result){
					if(result == "1"){
						var title = $("#cztype").val();
						alert(title+"成功!");
						var win = art.dialog.open.origin; 
						win.location.reload(); 
						window.close(); 
						art.dialog.close();
					}else{
						alert(title+"失败!");
					}
				}
			});
		}
		
		function checkUserid(userid){
			var cztype = '${request.editType}';
			if("添加" == cztype){
				if(userid != null && userid != ''){
					$.ajax({
						cache:false,
						async:false,
						url:'<%=request.getContextPath()%>/wfjb/user_yanzInterfaceUserinfo.action',
						type:'post',
						data:{"userid": userid},
						dataType:'json',
						error:function(XmlHttpRequest,textStatus, errorThrown){
							exception(XmlHttpRequest.responseText);
							return false;
						},
						success: function(result){
							if(result == "1"){
								$("#nameDesc").html("单位账号已存在");
							}else if(result == "0"){
								$("#nameDesc").html("");
							}else{
								$("#nameDesc").html("验证失败");
							}
						}
					});
				}
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
		
		function artclose(){
			var win = art.dialog.open.origin;//来源页面
			// 如果父页面重载或者关闭其子对话框全部会关闭
			//win.location.reload();
			//return false;  
			window.close();  
			art.dialog.close(); 
		}
		
		function clickCheckbox(){
			var jkids = "";
			$("input[name=mids]:checked").each(function(){
				jkids = jkids + $(this).val()+",";
			});
			$("#jkids").val(jkids.substring(0,jkids.length - 1));
		}
		
		function randomWord(randomFlag, min, max){
			var str = "",
				range = min,
				arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
		
			// 随机产生
			if(randomFlag){
				range = Math.round(Math.random() * (max-min)) + min;
			}
			for(var i=0; i<range; i++){
				pos = Math.round(Math.random() * (arr.length-1));
				str += arr[pos];
			}
			$("#keys").val(str);
		}
		
	</script>
  </head>
  <body>
	<center>
		<div id="admin_main">
			<form action="<%=request.getContextPath()%>/xtwh/role_editRoleInfo.action" method="post" id="role_form" name="role_form">
				<table border="0" cellpadding="0" cellspacing="0" class="edittable">
					<tr>
						<td style="text-align:right; width: 80px;"><font style="color:red;">*</font>单位账号：
						</td>
						<td class="trs" style="text-align:left;">&nbsp;<input type="text" id="userid" name="interfaceUserinfo.userid"  value="${interfaceUserinfo.userid}" onblur="checkUserid(this.value)" maxlength="50" size="25"/>
							<font id="nameDesc" style="color: red;"></font>
							<input type="hidden" id="cztype" name="cztype" value="${editType}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><font style="color:red;">*</font>单位名称：</td>
						<td class="trs" style="text-align:left;">
							&nbsp;<input type="text" id="username" name="interfaceUserinfo.username" value="${interfaceUserinfo.username}" maxlength="50" size="25"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><font style="color:red;">*</font>密码：</td>
						<td style="text-align:left;">
							&nbsp;<input type="password" id="password" name="interfaceUserinfo.password" value="${interfaceUserinfo.password}" maxlength="50" size="27"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><font style="color:red;">*</font>密钥：</td>
						<td style="text-align:left;">
							&nbsp;<input type="text" id="keys" name="interfaceUserinfo.keys" value="${interfaceUserinfo.keys}" maxlength="50" size="25"/>
							&nbsp;&nbsp;<a href="javascript: randomWord(true, 24, 24);">随机生成密钥</a>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><font style="color:red;">*</font>授信IP：</td>
						<td style="text-align:left;">
							&nbsp;<input type="text" id="userip" name="interfaceUserinfo.userip" value="${interfaceUserinfo.userip}" maxlength="50" size="25"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;" valign="middle"><font style="color:red;">*</font>单位权限：</td>
						<td style="text-align:left; padding-left: 1px;">
							<s:iterator id="interface" value="#request.interfaceList" status="st">
								<div style="width: 130px; float: left;"><input type="checkbox" id="${interface.jkid}" name="mids" onclick="clickCheckbox();" value="${interface.jkid}">${interface.jkid}--${interface.jkms}</div>
								<s:if test="#request.st.count % 3 == 0"><br/></s:if>
							</s:iterator>
							<input type="hidden" value="${jkids}" name="jkids" id="jkids"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="50" align="center">
							<s:if test="#request.editType != '查看'">
			    				<input type="button" id="tijiao" class="btncla" onclick="javascript:check();" value="提 交" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
			    				<input type="button" id="guanbi" class="btncla" onclick="javascript:artclose();" value="关闭" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
			    			</s:if>
			    			<s:else>
			    				<input type="button" id="guanbi" class="btncla" onclick="javascript:artclose();" value="关闭" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
			    			</s:else>
							
						</td>
					</tr>
				</table>
			</form>
		</div>
	</center>
	<div></div>
  </body>
</html>