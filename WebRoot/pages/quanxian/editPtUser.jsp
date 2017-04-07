<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>${editType }平台用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/module.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.position.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.menu.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.autocomplete.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-ui-1.9.1.custom.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/css/redmond/jquery-ui-1.9.1.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/css/redmond/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/jquery.artDialog.source.js?skin=chrome"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/plugins/iframeTools.source.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/public.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-lightbox-0.5/js/jquery.lightbox-0.5.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/jquery-lightbox-0.5/css/jquery.lightbox-0.5.css" edia="screen" />
	<script type="text/javascript">
		var cztype = '${request.editType}';
		$(document).ready(function() {
			var jkids = '${request.jkids}';
			if(jkids != null && jkids != ""){
				var list = jkids.split(",");
				for(var i = 0; i < list.length; i++){
					$("input[name=mids]").each(function(){
						if($(this).val() == $.trim(list[i])){
							$(this).attr("checked", "checked");
						}
					});
				}
			}
			if(cztype == '查看'){
				$("input").not(".btncla").each(function(){
					$(this).attr("disabled", "disabled");
				});
				$("#deptid").attr("disabled", "disabled");
				$("#flag").attr("disabled", "disabled");
			}
			if(cztype == '修改'){
				$("#userid").attr("readonly", "readonly");
			}
		});
		
		
		
		function check(){
			var userid = checknotnull(document.getElementById("userid"),'请输入用户帐号');
			if(userid != "true"){
				return false;
			}
			var userName = checknotnull(document.getElementById("username"),'请输入用户名称');
			if(userName != "true"){
				return false;
			}
			var cztype = '${request.editType}';
			if(cztype == '添加'){
				var userPassword = checknotnull(document.getElementById("userpwd"),'请输入密码');
				if(userPassword != "true"){
					return false;
				}
				var querenPassword = checknotnull(document.getElementById("querenPassword"),'请确认密码');
				if(querenPassword != "true"){
					return false;
				}
				var pwd1 = $("#userpwd").val();
				var pwd2 = $("#querenPassword").val();
				if(pwd1 != pwd2){
					alert("两次输入的密码不一致!");
					return false;
				}
			}
			var userDept = $("#deptid").val();
			if(userDept == null || userDept == ''){
				alert("请选择所属部门!");
				return false;
			}
			$.ajax({
				cache:false,
				async:false,
				url:'<%=request.getContextPath()%>/wfjb/user_editPtUserInfo.action',
				type:'post',
				data:$("#user_form").serialize(),
				dataType:'json',
				error:function(XmlHttpRequest,textStatus, errorThrown){
					exception(XmlHttpRequest.responseText);
					return false;
				},
				success: function(result){
					if(result == "1"){
						alert(cztype+"成功!");
						var win = art.dialog.open.origin; 
						win.location.href="<%=request.getContextPath()%>/wfjb/user_getPtUserList.action";
						window.close(); 
						art.dialog.close();
					}else{
						alert(cztype+"失败!");
					}
				}
			});
		}
		
		function checkPwd(){
			var pwd1 = $("#userPassword").val();
			var pwd2 = $("#querenPassword").val();
			if(pwd1 != null && pwd1 != "" && (pwd2 != null && pwd2 != "")){
				if(pwd1 != pwd2){
					$("#pwdinfo").html("两次输入的密码不一致!");
				}else{
					$("#pwdinfo").html("");
				}
			}
			
		}
		
		
		function cleanmyform(){
			$(".disabled").val("");
			$("select").val("");
			$("textarea").val("");
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
		
	</script>
  </head>
  <body>
	<center>
		<div id="admin_main">
			<form action="<%=request.getContextPath()%>/wfjb/user_editPtUserInfo.action" method="post" id="user_form" name="user_form">
				<table border="0" cellpadding="0" cellspacing="0" class="edittable" style="width: 550px;">
					<tr>
						<td style="text-align:right; width: 120px;"><font style="color:red;">*</font>用户帐号：
						</td>
						<td class="trs" style="text-align:left;">&nbsp;<input type="text" class="disabled" id="userid" name="user.userid" onkeyup="clearspace(this)" onblur="clearallspace(this); " value="${user.userid}" style="width: 150px;"/>
							<input type="hidden" id="cztype" name="cztype" value="${editType}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><font style="color:red;">*</font>用户名称：</td>
						<td class="trs" style="text-align:left;">
							&nbsp;<input type="text" class="disabled" id="username" name="user.username" style="width: 150px;" value="${user.username}" />
						</td>
					</tr>
					<s:if test="#request.editType == '添加'">
						<tr>
							<td style="text-align:right;"><font style="color:red;">*</font>密码：</td>
							<td class="trs" style="text-align:left;">
								&nbsp;<input type="password" class="disabled" id="userpwd" name="user.userpwd" onkeyup="checkPwd();" onblur="checkPwd();"  value="${user.userpwd}" style="width: 150px;"/>
							</td>
						</tr>
						<tr>
							<td style="text-align:right;"><font style="color:red;">*</font>确认密码：</td>
							<td class="trs" style="text-align:left;">
								&nbsp;<input type="password" class="disabled" id="querenPassword" name="querenPassword" onkeyup="checkPwd();" onblur="checkPwd();" style="width: 150px;"/><font id="pwdinfo" style="color: red;"></font>
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr style="display: none;">
							<td style="text-align:right;"><font style="color:red;">*</font>密码：</td>
							<td class="trs" style="text-align:left;">
								&nbsp;<input type="password" class="disabled" id="userpwd" name="user.userpwd" onkeyup="checkPwd();" onblur="checkPwd();"  value="${user.userpwd}" style="width: 150px;"/>
							</td>
						</tr>
					</s:else>
					
					<tr>
						<td style="text-align:right;"><font style="color:red;">*</font>所属部门：</td>
						<td class="trs" style="text-align:left;">
							&nbsp;<select id="deptid" style="width: 153px;" name="user.deptid">
									  <option value="">===请选择===</option>
									  <s:iterator value="#request.deptList" var="dept">
										   <option value="${dept[0]}" <s:if test="#request.dept[0] == #request.user.deptid">selected="selected"</s:if> >${dept[1]}</option>
									  </s:iterator>
								  </select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;" valign="middle">用户权限：</td>
						<td style="text-align:left; padding-left: 1px;">
							<s:iterator id="menu" value="#request.menuList" status="st">
								<div style="width: 130px; float: left;"><input type="checkbox" id="${menu[2]}" name="mids" onclick="clickCheckbox();" value="${menu[2]}">${menu[1]}</div>
								<s:if test="#request.st.count % 3 == 0"><br/></s:if>
							</s:iterator>
							<input type="hidden" value="${jkids}" name="jkids" id="jkids"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;">是否有效：</td>
						<td style="text-align:left;">
							&nbsp;<select id="flag" style="width: 153px;" name="user.zt">
								<option value="1" <s:if test="#request.user.zt == 1">selected="selected"</s:if>>有效</option>
								<option value="0" <s:if test="#request.user.zt == 0">selected="selected"</s:if>>无效</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="50" align="center">
							<s:if test="#request.editType != '查看'">
			    				<input type="button" class="btncla" id="tijiao" onclick="javascript:check();" value="提 交" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
			    				<input type="button" class="btncla" onclick="javascript:artclose();" value="关闭" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
			    			</s:if>
			    			<s:else>
			    				<input type="button" class="btncla" onclick="javascript:artclose();" value="关闭" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
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