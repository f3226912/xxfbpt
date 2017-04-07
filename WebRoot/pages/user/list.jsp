<%@ page language="java" pageEncoding="UTF-8"%>
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
		<title>用户列表</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/content.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/css/redmond/external/jquery/jquery.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.position.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.menu.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.autocomplete.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/css/redmond/jquery-ui.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/css/redmond/jquery-ui.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/jquery.artDialog.source.js?skin=chrome"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/plugins/iframeTools.source.js"></script>
		<style type="text/css">
			.ui-autocomplete-loading {
				background: white url('<%=request.getContextPath()%>/jquery/development-bundle/demos/autocomplete/images/ui-anim_basic_16x16.gif') right center no-repeat;
			}
			#susernameid { width: 12em; }
			.bnt2 {
				width: 76px;
				height: 27px;
				background: url('<%=request.getContextPath()%>/images/an3.gif') no-repeat;
				border: none;
				font-weight: bold;
			}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
				var sex = '${sex}';
				$("#sex").val(sex);
				
				var cache = {};
				
				$( "#susernameid" ).autocomplete({
					source: function( request, response ) {
						var term = request.term;
						if ( term in cache ) {
							response( $.map( cache[ term ], function( item ) {
								return {
									label: "ID:" + item.id + "姓名:" + item.userName,
									value: item.userName
								}
							}));
							return;
						}
						$.ajax({
							cache:false,
							async:false,
							url: "<%=request.getContextPath()%>/user_ajax/initUserListAjax.action",
							dataType: "json",
							data: {
								userNames: request.term
							},
							//ajax请求在error中捕获异常信息
							//error:function(XmlHttpRequest,textStatus, errorThrown){
							//	exception(XmlHttpRequest.responseText);
							//	return false;
							//},
							success: function( data ) {
								cache[ term ] = data;
								response( $.map( data, function( item ) {
									return {
										label: "ID:" + item.id + "姓名:" + item.userName,
										value: item.userName
									}
								}));
							}
						});
					},
					minLength: 1,
					delay: 0,
					select: function( event, ui ) {
						$("#susernameid").val(ui.value);
					},
					open: function() {
						$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
					},
					close: function() {
						$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
					}
				});
			});
			
			function insertUser() {
				window.location.href = "<%=request.getContextPath()%>/user/user_insertUser.action";
			}
			
			function getProcAndFun(){
				$.ajax({
					cache:false,
					async:false,
					type:'post',
					url: '<%=request.getContextPath()%>/user/user_getProcAndFun.action',
					data: {"userName":"警视通"},//发送的参数
					dataType: 'html',
					success:function(result){
						var message = result+"";
						if(message.indexOf('异常信息') == -1){
							alert(message);
						}else{
							exception(message);
						}
					}
				});
			}
			function deleteOne(id) {
				if(window.confirm('是否要删除选择的用户信息?')){
					$.ajax({
						cache:false,
						async:false,
						type:'GET',
						url: '<%=request.getContextPath()%>/user/user_deleteUser.action',
						data:{userId:id},//发送的参数
						dataType: 'html',
						success:function(data){
							var message = data+"";
							if(message.indexOf('异常信息') == -1){
								if(data == 0){
								    alert("删除用户信息成功!");
								    $("#"+id).parents("tr").remove();
								    //$("#productform").submit();
								}else if(data == 1){
									alert("删除首页信息失败!");
								}else{
									alert("系统繁忙,请稍候再试!");
								}
							}else{
								exception(message);
							}
						}
					});
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
		<style> 
			html{ 
				overflow:scroll;
				scrollbar-base-color:#c7e5ff;
				scrollbar-track-color:#FFFFFF;
			} 
		</style>
	</head>

	<body style="background:#c7e5ff;">
		<div class="content1" style="width:100%;">
			<div class="roundedBox" id="type1" style="width:95%;">
				<div class="right" style="width:90%;">
					<form action="<%=request.getContextPath()%>/user/user_initUserList.action" method="post" id="searchfromid">
						<table class="table1" width="100%" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<th class="th1" height="32" colspan="4">
									查询条件
								</th>
							</tr>
							<tr>
								<td class="tds" style="text-align: right;">
									用户名&nbsp;
								</td>
								<td class="tdl" style="text-align: left;">
									<input type="text" name="userNames" id="susernameid" value="${userNames}" />
									<c:set var="keywords" value="${userNames}" />
								</td>
								<td class="tds" style="text-align: right">
									部门&nbsp;
								</td>
								<td class="tdl" style="text-align: left;">
									<input type="text" name="deptNames" id="sdeptnameid" value="${deptNames}" />
									<c:set var="keywords3" value="${deptNames}" />
								</td>
							</tr>
							<tr>
								<td class="tds" style="text-align: right">
									性别&nbsp;
								</td>
								<td class="tdl" style="text-align: left;">
									<s:select list="#{'0':'男','1':'女'}" theme="simple" id="sex" headerKey="" headerValue="---请选择---" listKey="key" listValue="value" name="sex" value="#request.sex"></s:select>
								</td>
								<td class="tds" style="text-align: right">
									登录日期&nbsp;
								</td>
								<td class="tdl" style="text-align: left;">
									起
									<input name="s_date" id="s_date" value="${s_date}" size="10" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									止
									<input name="e_date" id="e_date" value="${e_date}" size="10" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
								</td>
							</tr>
							<tr>
								<td class="tds" colspan="4">
									<div align="center">
										<input class="bnt" type="submit" id="frombutid" value="查  询" style="cursor:pointer;" />
										<!-- <input class="bnt" type="reset" value="重  置" style="cursor:pointer;" /> -->
									</div>
								</td>
							</tr>
						</table>
					</form>
					<div style="width: 98%;height: 35px;padding-top: 10px;">
						<input class="bnt" type="button" value="添  加" onclick="javascript:insertUser();" style="cursor:pointer;" />
						<input class="bnt2" type="button" value="函数/过程" onclick="javascript:getProcAndFun();" style="cursor:pointer; width: 100px; background-repeat: no-repeat;" />
					</div>
					<table class="datalist" width="100%" border="0"
						cellpadding="0" cellspacing="0">
						<tr class="tr1">
							<th>
								序号
							</th>
							<th>
								ID
							</th>
							<th>
								用户名
							</th>
							<th>
								密码
							</th>
							<th>
								性别
							</th>
							<th>
								部门
							</th>
							<th>
								登录IP
							</th>
							<th>
								登录时间
							</th>
							<th>
								邮箱
							</th>
							<th>
								手机
							</th>
							<th>
								操作
							</th>
						</tr>
						<s:if test="#request.userList.size > 0">
							<s:iterator id="user" value="#request.userList" status="st">
								<tr class="<s:if test="#st.odd == false">altrow</s:if>">
									<td>
										${st.count + (map.currentpage-1) * map.pagesize}
									</td>
									<td>
										${user.id }
									</td>
									<td>
								 	<!-- 以下代码是为有"高亮"需求功能所写,如不需要可替换为普通显示   -->
										<c:set var="key1" value="," />
										<c:set var="keywords2" value="${fn:indexOf(keywords,key1)}" />
										<c:choose>
											<c:when test="${keywords2 >= 0}">
												<c:set var="arraykey" value="${fn:split(keywords,key1)}"/>
												<c:set var="countkey" value="${fn:length(arraykey)}"/>
												<c:set var="index" value="" />
												<c:forEach begin="0" var="k" varStatus="k2" items="${arraykey}" end="${countkey-1}">
													<c:choose>
														<c:when test="${k2.index == 0}">
															<c:if test="${fn:containsIgnoreCase(user.userName, k)}">
																<c:set var="key" value="<font color='#FF0000'>${k}</font>" />
																<c:set var="index" value="${fn:replace(user.userName, k, key)}" />
															</c:if>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${index == ''}">
																	<c:if test="${fn:containsIgnoreCase(user.userName, k)}">
																		<c:set var="key" value="<font color='#FF0000'>${k}</font>" />
																		<c:set var="index" value="${fn:replace(user.userName, k, key)}" />
																	</c:if>
																</c:when>
																<c:otherwise>
																	<c:if test="${fn:containsIgnoreCase(index, k)}">
																		<c:set var="key" value="<font color='#FF0000'>${k}</font>" />
																		<c:set var="index" value="${fn:replace(index, k, key)}" />
																	</c:if>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:forEach>
												${index}
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${fn:containsIgnoreCase(user.userName, keywords)}">
														<c:set var="key" value="<font color='#FF0000'>${keywords}</font>" />
														${fn:replace(user.userName, keywords, key)}
													</c:when>
													<c:otherwise>
														${user.userName}
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>&nbsp;
                           				<!-- 以上代码是为有"高亮"需求功能所写,如不需要可替换为普通显示   -->
									</td>
									<td>
										${user.password }
									</td>
									<td>
										<s:if test="#user.sex == 0">
						    				男
						    			</s:if>
						    			<s:elseif test="#user.sex == 1">
						    				女
						    			</s:elseif>
									</td>
									<td>
										${user.deptname}
									</td>
									<td>
										${user.loginIp }
									</td>
									<td>
										<s:date name="#user.loginTime" format="yyyy-MM-dd HH:mm:ss"/>
									</td>
									<td>
										${user.email }
									</td>
									<td>
										${user.phone }
									</td>
									<td>
										<a href="<%=request.getContextPath()%>/user/user_initUser.action?user.id=${user.id}">查看</a>
					    				<a href="<%=request.getContextPath()%>/user/user_updateUser.action?user.id=${user.id}">修改</a>
					    				<a href="javascript:void(0);" id="${user.id}" onclick="javascript:deleteOne(${user.id});">删除</a>
									</td>
								</tr>
							</s:iterator>
						</s:if>
						<s:else>
							<tr>
								<td colspan="11">
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
				<div class="corner bottomLeft"></div>
				<div class="corner bottomRight"></div>
			</div>
		</div>

	</body>
</html>
