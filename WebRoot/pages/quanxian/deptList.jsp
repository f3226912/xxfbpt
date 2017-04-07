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
		<title>部门管理</title>
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
				$("#esfrom").attr("action", "<%=request.getContextPath()%>/wfjb/user_getDeptList.action");
				$("#esfrom").submit();
			}
			
			function openpage(deptid, cztype){
				art.dialog.open('<%=request.getContextPath()%>/wfjb/user_initDeptShouquan.action?deptid='+deptid, {width:650,height:300,title:'部门资源授权', opacity: 0.4});
				
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
					<form action="<%=request.getContextPath()%>/wfjb/user_getDeptList.action" method="post" id="esfrom">
						<table class="table1" width="100%" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<th class="th1" height="32" colspan="4">
									查询条件
								</th>
							</tr>
							<tr>
								<td class="tds" style="text-align: right; width: 15%;">
									部门代号：&nbsp;
								</td>
								<td class="tdl" style="text-align: left;">
									<input type="text" id="deptId" name="deptId" onkeyup="clearspace(this)" onblur="clearallspace(this)"  value="${deptId}" style="width: 100px;"/>
								</td>
								<td class="tds" style="text-align: right; width: 15%;">
									部门名称：&nbsp;
								</td>
								<td class="tdl" style="text-align: left;">
									<input type="text" id="deptName" name="deptName" onkeyup="clearspace(this)" onblur="clearallspace(this)"  value="${deptName}" style="width: 100px;"/>
								</td>
							</tr>
							<tr>
								<td class="tds" colspan="4">
									<div align="center">
										<input class="bnt" type="button" value="查  询" onclick="javascript:check();" style="cursor:pointer;" />
									</div>
								</td>
							</tr>
						</table>
					</form>
					
					<table class="datalist" width="100%" border="0"
						cellpadding="0" cellspacing="0">
						<tr class="tr1">
							<th>
								序号
							</th>
							<th>
								部门代号
							</th>
							<th>
								部门名称
							</th>
							<th>
								部门地址
							</th>
							<th>
								部门责任人
							</th>
							<th>
								部门联系电话
							</th>
							<th>
								操作
							</th>
						</tr>
						<s:if test="#request.deptList.size > 0">
							<s:iterator id="dept" value="#request.deptList" status="st">
								<tr class="<s:if test="#st.odd == false">altrow</s:if>">
									<td>
										${st.count + (map.currentpage-1) * map.pagesize}
									</td>
									<td>
										${dept[0] }&nbsp;
									</td>
									<td>
										${dept[1]}&nbsp;
									</td>
									<td>
										${dept[2]}&nbsp;
									</td>
									<td>
										${dept[3]}&nbsp;
									</td>
									<td>
										${dept[4]}&nbsp;
									</td>
									<td>
								 		<a href="javascript: openpage('${dept[0]}','update');">资源授权</a>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<s:else>
							<tr>
								<td colspan="8">
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
