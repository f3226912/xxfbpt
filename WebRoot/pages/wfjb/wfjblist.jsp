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
		<title>信息平台资源审核</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/content.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
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
			$(document).ready(function(){
				var exportData = '${request.exportData}';
				if(exportData != null && exportData != ""){
					alert(exportData);
				}
				
				changeztsjzd();
			});
		
			function block_viewport(){
				lyFrom.submit();
			}
			
			function shenpi(keyid, zyid, cztype){
				var type = $("#zyid").find("option:selected").text();
				var info = window.open("<%=request.getContextPath()%>/wfjb/wfjb_getResourceDetail.action?resouceType="+type+"&keyid="+keyid+"&zyid="+zyid+"&cztype="+cztype ,'info','width='+(window.screen.availWidth-100)+',height='+(window.screen.availHeight-200)+ ',top=50,left=50,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes');
			}
			
			function jiansuo(){
				$("#lyFrom").attr("action", "<%=request.getContextPath()%>/wfjb/wfjb_getResourcemx.action?isshow=1");
				lyFrom.submit();
			}
			
			function daochu(){
				var xhs = $("#xhs").val();
				if(xhs == null || xhs == ''){
					alert("请勾选需要导出的数据!");
					return false;
				}
				if(confirm("导出较慢，请耐心等候.....")){
					var zyid = $("#zyid").find("option:selected").val();
					var xhs = $("#xhs").val();
					var zt = $("#selzt").val();
					var s_date = $("#s_lrsj").val();
					var e_date = $("#e_lrsj").val();
					$.ajax({
						cache:false,
						async:false,
						type:'post',
						url: "<%=request.getContextPath()%>/wfjb/wfjb_exportWfjbExcel.action",
						data: {zyid:zyid, isshow:2, xhs: xhs, zt: zt, s_lrsj: s_date, e_lrsj: e_date},
						dataType: 'html',
						success:function(data){
							if (data.length > 1) {
								window.location.href = '<%=request.getContextPath()%>/wfjb/downloadAction.action?zipName='+data;
							} else if (data == 0){
								alert("导出文件为空!");
							} else {
								exception(data);
								return false;
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
			
			function checkBox(){
				var check = $("#checkall").attr("checked");
				if(check == "checked"){
					$("input[name=single]").each(function(){
						$(this).attr("checked", true);
					});
				}else{
					$("input[name=single]").each(function(){
						$(this).attr("checked", false);
					});
				}
				setval();
			}
			
			function setval(){
				var xhs = "";
				$("input[name=single]:checked").each(function(){
					xhs = xhs + $(this).val()+",";
				});
				$("#xhs").val(xhs.substring(0,xhs.length - 1));
			}
			
			function changeztsjzd(){
				var type = $("#type").val();
				var zt = '${zt}';
				if(type != 'shenpi'){
					var zdval = $("#zyid").val();
					if(zdval == null || zdval == ""){
						$("#selzt option").remove();
						$("#selzt").append('<option value="">===请选择===</option>');
					}else{
						$.ajax({
							cache: false,
							async: false,
							type:'post',
							url:'<%=request.getContextPath()%>/wfjb/wfjb_getZyztzd.action',
							data:{"zyid": zdval},
							dataType:'json',
							success: function(data){
								$("#selzt option").remove();
								$("#selzt").append('<option value="">===请选择===</option>');
								if(data != null){
									if(zt == '0'){
										$("#selzt").append('<option value="0" selected="selected">待审</option>');
									}else{
										$("#selzt").append('<option value="0">待审</option>');
									}
									
									for(var i = 0; i < data.length; i++){
										var obj = data[i];
										if(obj[1] == zt){
											$("#selzt").append('<option value="'+obj[1]+'" selected="selected">'+obj[2]+'</option>');
										}else{
											$("#selzt").append('<option value="'+obj[1]+'">'+obj[2]+'</option>');
										}
										
									}
								}
							}
							
						});
					}
				}
				
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
			<div class="roundedBox" id="type1" style="width:98%;">
				<div class="right" style="width:95%;">
					<form action="<%=request.getContextPath()%>/wfjb/wfjb_getResourcemx.action" method="post" id="lyFrom" name="lyFrom">
						<table class="table1" width="100%" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<th class="th1" height="32" colspan="4">
									查询条件
								</th>
							</tr>
							<tr>
								<td class="tds" style="text-align: right;">
									资源对象名:&nbsp;
								</td>
								<td class="tdl" style="text-align: left;">
									<select name="zyid" id="zyid"
										style="width: 150px;" onclick="changeztsjzd();">
										<option value="">
											===请选择===
										</option>
										<s:iterator id="resource" value="#request.resourceList">
											<option value="${resource[0]}" <s:if test="#request.zyid == #request.resource[0]">selected="selected"</s:if> >
												${resource[1]}
											</option>
										</s:iterator>
									</select>
									<input type="hidden" name="type" id="type" value="${type}"/>
									<input type="hidden" name="xhs" id="xhs" value=""/>
								</td>
								
								<td class="tds" style="text-align: right;">
										序号:&nbsp;
								</td>
								<td class="tdl" style="text-align: left;"  >
									<input name="jbxh" id="jbxh" value="${jbxh}" size="15" type="text" />
								</td>
								
							</tr>
							<tr>
								<s:if test="#request.type == 'select'">
									<td class="tds" style="text-align: right;">
										状态:&nbsp;
									</td>
									<td class="tdl" style="text-align: left;"  >
										<select name="zt" id="selzt"
											style="width: 150px;">
											<option value="">
												===请选择===
											</option>
										</select>
										<input type="hidden" name="type" id="type" value="${type}"/>
									</td>
								</s:if>
								<td class="tds" style="text-align: right;">
									录入时间:&nbsp;
								</td>
								<td class="tdl" style="text-align: left;" colspan='3' >
									起
									<input name="s_lrsj" id="s_lrsj" value="${s_lrsj}" size="10" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									止
									<input name="e_lrsj" id="e_lrsj" value="${e_lrsj}" size="10" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									<input type="hidden" name="zt" id="zt" value="${zt}"/>
								</td>
							</tr>
							<s:if test="#request.type == 'select'">
								<tr>
									<td class="tds" style="text-align: right;">
										审核人:&nbsp;
									</td>
									<td class="tdl" style="text-align: left;">
										<input type="text" name="shr" id="shr" value="${shr}"/>
									</td>
									<td class="tds" style="text-align: right;">
										审核人部门:&nbsp;
									</td>
									<td class="tdl" style="text-align: left;">
										<input type="text" name="shbm" id="shbm" value="${shbm}"/>
									</td>
								</tr>
								<tr>
									<td class="tds" style="text-align: right;">
										审核时间:&nbsp;
									</td>
									<td class="tdl" style="text-align: left;" colspan="3" <s:if test="#request.type != 'select'">colspan="3"</s:if> >
										起
										<input name="s_shsj" id="s_shsj" value="${s_shsj}" size="10" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
										止
										<input name="e_shsj" id="e_shsj" value="${e_shsj}" size="10" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</td>
								</tr>
							</s:if>
							<tr>
								<td class="tds" colspan="4">
									<div align="center">
										<input class="bnt" type="button" onclick="jiansuo();" id="frombutid" value="查 询" style="cursor:pointer;" />
										<s:if test='#request.type == "select"'>
											<input class="bnt" type="button" id="exportButton" onclick="daochu();" value="导  出" style="cursor:pointer;" />
										</s:if>
										<!-- <input class="bnt" type="reset" value="重  置" style="cursor:pointer;" /> -->
									</div>
								</td>
							</tr>
							
						</table>
					
					<table id="datatable" class="datalist" width="100%" border="0"
						cellpadding="0" cellspacing="0">
						<s:if test="#request.title != null">
							<tr  class="tr1">
								<s:if test="#request.type == 'select'">
									<th>&nbsp;<input type="checkbox" name="checkall" id="checkall" onclick="checkBox();"/></th>
								</s:if>
								
								<s:iterator id="tit" value="#request.title">
									<s:if test='#request.tit[5] == 0'>
										<th>${tit[2]}</th>
									</s:if>
									
								</s:iterator>
								<th>操作</th>
							</tr>
						</s:if>
						<s:iterator var="mx" value="#request.mxList" status="st">
							<tr id="${mx[0]}" class="<s:if test="#st.odd == false">altrow</s:if>">
								<s:iterator id="tit" value="#request.title" status="st">
									<s:if test="#st.index == 0 && #request.type == 'select'">
										<td><input type="checkbox" name="single" onclick="setval();" value="<s:property value="#mx[#st.index]" />"/></td>
									</s:if>
									<s:if test='#request.tit[5] == 0'>
										<td>
											${mx[st.index]}
										</td>
									</s:if>
								</s:iterator>
								<td>
									<s:if test='#request.type == "shenpi"'>
										<a href="javascript: shenpi('${mx[0]}', ${zyid}, 'shenpi');">审批</a>
									</s:if>
									<s:if test='#request.type == "select"'>
										<a href="javascript: shenpi('${mx[0]}', ${zyid}, 'update');">修改</a>
									</s:if>
									<a href="javascript: shenpi('${mx[0]}', ${zyid}, 'select');">查看</a>
								</td>
							</tr>
						</s:iterator>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="padding-top: 5px;">
						<pt:write uri="${map.uri}" pagesize="${map.pagesize}" rscount="${map.rscount}" currentpage="${map.currentpage}"></pt:write>
					</table>
					</form>
				</div>

				<div class="corner topLeft"></div>
				<div class="corner topRight"></div>
				<div class="corner bottomLeft"></div>
				<div class="corner bottomRight"></div>
			</div>
		</div>

	</body>
</html>
