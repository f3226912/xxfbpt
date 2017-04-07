<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="com.xxfbpt.common.PageTag" prefix="pt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<title>授信单位管理</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/content.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/public.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/jquery.artDialog.source.js?skin=chrome"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/plugins/iframeTools.source.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				
			});
			function check(){
				$("#esfrom").attr("action", "<%=request.getContextPath()%>/wfjb/user_getInterfaceUserList.action");
				$("#esfrom").submit();
			}
			
			function openpage(userid, cztype){
				var title = "";
				if(cztype == 'query'){
					title = "查看";
				}else if(cztype=='update'){
					title = "修改";
				}else{
					title = "添加";
				}
				art.dialog.open('<%=request.getContextPath()%>/wfjb/user_getInterfaceUserInfo.action?userid='+userid+'&cztype='+cztype, {width:650,height:400,title:title+'授信单位信息', opacity: 0.4});
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
		<style> 
			html{ 
				overflow:scroll;
				scrollbar-base-color:#c7e5ff;
				scrollbar-track-color:#FFFFFF;
			} 
		</style>
	</head>

	<body style="background:#c7e5ff;">
		<div class="content1" style="width: 100%;">
			<div class="roundedBox" id="type1" style="width: 95%;">
				<div class="right" style="width: 99%;">
					<form action="<%=request.getContextPath()%>/wfjb/user_getInterfaceUserList.action" method="post" id="esfrom">
						<table class="table1" width="100%" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<th class="th1" height="32" colspan="4">
									查询条件
								</th>
							</tr>
							<tr>
								<td class="tds" style="text-align: right; width: 15%;">
									单位名称：&nbsp;
								</td>
								<td class="tdl" style="text-align: left;">
									<input type="text" id="username" name="username" onkeyup="clearspace(this)" onblur="clearallspace(this)"  value="${username}" style="width: 100px;"/>
								</td>
							</tr>
							<tr>
								<td class="tds" colspan="2">
									<div align="center">
										<input class="bnt" type="button" value="查  询" onclick="javascript:check();" style="cursor:pointer;" />
									</div>
								</td>
							</tr>
						</table>
					</form>
					<div style="width: 98%;height: 35px;">
						<input class="bnt" type="button" value="添加单位" onclick="javascript:openpage('', 'add');" id="excelid" style="cursor:pointer;" />
					</div>
					
					<table class="datalist" width="100%" border="0"
						cellpadding="0" cellspacing="0">
						<tr class="tr1">
							<th>
								序号
							</th>
							<th>
								单位账号
							</th>
							<th>
								单位名称
							</th>
							<th>
								用户密钥
							</th>
							<th>
								类型
							</th>
							<th>
								操作
							</th>
						</tr>
						<s:if test="#request.interfaceUserList.size > 0">
							<s:iterator id="user" value="#request.interfaceUserList" status="st">
								<tr class="<s:if test="#st.odd == false">altrow</s:if>">
									<td>
										${st.count + (map.currentpage-1) * map.pagesize}
									</td>
									<td>
										${user.userid }&nbsp;
									</td>
									<td>
										${user.username}&nbsp;
									</td>
									<td>
										${user.keys}&nbsp;
									</td>
									<td>
										${user.usertype}&nbsp;
									</td>
									<td>
								 		<a href="javascript: openpage('${user.userid}','update');">修改</a>
								 		<a href="javascript: openpage('${user.userid}', 'query');">查看</a>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<s:else>
							<tr>
								<td colspan="7">
									<span style="color: red">暂时没有相关数据</span>
								</td>
							</tr>
						</s:else>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="padding-top: 5px;">
						<pt:write uri="${map.uri}" pagesize="${map.pagesize}" rscount="${map.rscount}" currentpage="${map.currentpage}"></pt:write>
					</table>
					
				</div>

				<div class="corner topLeft"></div>
				<div class="corner topRight"></div>
			</div>
		</div>
	</body>
</html>
