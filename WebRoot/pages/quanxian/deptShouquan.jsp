<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>部门资源授权</title>
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
		});
		
		function check(){
			var deptid = checknotnull(document.getElementById("deptid"),'请输入单位账号');
			if(deptid != "true"){
				return false;
			}
			$.ajax({
				cache:false,
				async:false,
				url:'<%=request.getContextPath()%>/wfjb/user_editDeptResource.action',
				type:'post',
				data:$("#role_form").serialize(),
				dataType:'json',
				error:function(XmlHttpRequest,textStatus, errorThrown){
					exception(XmlHttpRequest.responseText);
					return false;
				},
				success: function(result){
					if(result == "1"){
						alert("授权成功!");
						var win = art.dialog.open.origin; 
						window.close(); 
						art.dialog.close();
					}else{
						alert(title+"失败!");
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
			<form action="<%=request.getContextPath()%>/xtwh/role_editRoleInfo.action" method="post" id="role_form" name="role_form">
				<table border="0" cellpadding="0" cellspacing="0" class="edittable">
					<tr>
						<td style="text-align:right; width: 80px;"><font style="color:red;">*</font>部门代号：
						</td>
						<td class="trs" style="text-align:left;">&nbsp;<input type="text" id="deptid" name="deptid"  value="${deptid}" readonly="readonly" onblur="checkUserid(this.value)" maxlength="50" size="25"/>
							<font id="nameDesc" style="color: red;"></font>
							<input type="hidden" id="cztype" name="cztype" value="${editType}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;" valign="middle"><font style="color:red;">*</font>资源信息：</td>
						<td style="text-align:left; padding-left: 1px;">
							<s:iterator id="interface" value="#request.resourceList" status="st">
								<div style="width: 130px; float: left;"><input type="checkbox" id="${interface[0]}" name="mids" onclick="clickCheckbox();" value="${interface[0]}">${interface[1]}</div>
								<s:if test="#request.st.count % 3 == 0"><br/></s:if>
							</s:iterator>
							<input type="hidden" value="${jkids}" name="jkids" id="jkids"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="50" align="center">
							<input type="button" id="tijiao" class="btncla" onclick="javascript:check();" value="提 交" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
			    			<input type="button" id="guanbi" class="btncla" onclick="javascript:artclose();" value="关闭" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</center>
	<div></div>
  </body>
</html>