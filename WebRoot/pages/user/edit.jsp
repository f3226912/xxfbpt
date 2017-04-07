<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/module.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/css/redmond/external/jquery/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.position.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.menu.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.autocomplete.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/css/redmond/jquery-ui.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/css/redmond/jquery-ui.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/jquery.artDialog.source.js?skin=chrome"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/plugins/iframeTools.source.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/public.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-lightbox-0.5/js/jquery.lightbox-0.5.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/jquery-lightbox-0.5/css/jquery.lightbox-0.5.css" edia="screen" />
	<script type="text/javascript">
		$(document).ready(function() {
			$(".disabled").attr("disabled","disabled");
			$("select").attr("disabled","disabled");
			$("textarea").attr("disabled","disabled");
		});
		
	</script>
  </head>
  <body>
	<center>
		<div id="admin_title">
			<div class="logo"><img src="<%=request.getContextPath()%>/images/frame_19.gif"></div>
			<div class="title">${editType }用户信息</div>
		</div>
		<div id="admin_main">
			<form action="<%=request.getContextPath()%>/user/user_editUser.action" method="post" id="user_form" name="user_form" target="abc">
				<iframe id="abc" name="abc" width="0" height="0" style="display: none;"></iframe>
				<table border="0" cellpadding="0" cellspacing="0" class="edittable">
					<tr>
						<td style="text-align:right;"><font style="color:red;">*</font>用户名：
						</td>
						<td class="trs" style="text-align:left;">&nbsp;<input type="text" class="disabled" id="u_username" name="user.userName" value="${userbean.userName}" size="45" maxlength="45"/></td>
					</tr>
					<tr>
						<td style="text-align:right;">性别：</td>
						<td style="text-align:left;">&nbsp;<s:select list="#{'0':'男','1':'女'}" theme="simple" id="u_sex" headerKey="" headerValue="---请选择---" listKey="key" listValue="value" name="user.sex" value="#request.user.sex"></s:select></td>
					</tr>
					<tr>
						<td style="text-align:right;">邮箱：</td>
						<td style="text-align:left;">&nbsp;<input type="text" class="disabled" name="user.email" id="u_email" value="${user.email}" size="45" maxlength="45"/></td>
					</tr>
					<tr>
						<td style="text-align:right;">手机：</td>
						<td style="text-align:left;">&nbsp;<input type="text" class="disabled" name="user.phone" onkeyup="javascript:checknumber2(this);" id="u_phone" value="${user.phone}" size="45" maxlength="45"/></td>
					</tr>
					<tr>
						<td style="text-align:right;">备注信息：</td>
						<td style="text-align:left;">&nbsp;<textarea name="user.remark" id="u_remark" cols="35" rows="5">${user.remark}</textarea> </td>
					</tr>
					<tr>
						<td colspan="2" height="30" align="center">
							<span id="msg" style="color:red;margin-left: 180px;"></span>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="50" align="center">
							<s:if test="#request.editType != '查看'">
			    				<input type="button" onclick="javascript:check();" value="提 交" style="margin-left: 170px;cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
			    				<input type="button" onclick="javascript:window.history.go(-1);" value="返 回" style="cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
			    			</s:if>
			    			<s:else>
			    				<input type="button" onclick="javascript:window.history.go(-1);" value="返 回" style="margin-left: 200px;cursor:pointer;width: 79px;height: 28px;border: none;background: url(<%=request.getContextPath()%>/images/an2.gif) no-repeat;color: #FFFFFF;font-weight: bold;">
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