<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>资源信息审批</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
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
<style>
*{ margin:0 auto; font-size:12px;}
a{ color:blue;  text-decoration:none; font-size:14px;}
a:hover{ color:#red;}
.daxx{ width:100%; height:auto; padding-top:25px; background:none;}
.daxx div{position:relative;}
.p1{ width:940px; height:23px; padding:6px 0 0 30px; font-size:16px; letter-spacing:2px; color:#FFFFFF; font-weight:bold; background:url(<%=request.getContextPath()%>/images/rjlmt.jpg) no-repeat;}
.xxlr{ width:968px; height:auto; background:#f2f9fe; border:1px solid #CCCCCC; border-top:none; padding-top:15px; padding-bottom: 15px;}

.table{ border:1px solid #71b6cd;border-collapse:collapse; cellspacing:0px; cellpadding: 0px; height:28px; padding-bottom: 20px;}
.table td{ border:1px solid #71b6cd;border-collapse:collapse; cellspacing:0px;; cellpadding:0px;}
.td1{background:#c8ebff; text-align:right; width:90px;}
.td2{width:200px;}
.td3{background:#c8ebff; text-align: center;}
.td4{text-align: center;}
strong{ font-weight:normal; color:#CC0000;}

</style>

<script type="text/javascript">
	$(function(){
		$(".img01a").lightBox();
		
		$(".fy").each(function(){
			var availableTags = [];
			var i = $(this).attr("index");
			var showzd = $("#showzd"+i).val();
			var zdbm = $("#zdbm"+i).val();
			var zdtj = $("#zdtj"+i).val();
			var fyzd = $("#fyzd"+i).val();
			var tabname = $("#tabname"+i).val();
			var fytj = $("#fytj"+i).val();
			var xh = $("#xh"+i).val();
			var objs = xh.split("--");
			var val = $("#val"+i).val();
			$("#fy"+i).val(val);
			xh = objs[0];
			$.ajax({
				cache: false,
				async: false,
				type:'post',
				url: "<%=request.getContextPath()%>/wfjb/wfjb_getZdfy.action",
				data:{"showzd":showzd, "zdbm":zdbm, "zdtj":zdtj, "fyzd":fyzd, "tabname":tabname, "fytj":fytj, "xh":xh},
				dataType: 'json',
				success: function(data){
					if(data != null){
						for(var j = 0; j < data.length; j++){
							var obj = data[j];
							availableTags[j] = obj[0]+"---"+obj[1];
						}
					}
				}
			});
			i++;
			$(this).autocomplete({
				minLength: 2,
				source: availableTags
			});
		});
		
	});
	
	function submitfrom(){
		var cztype = '${cztype}';
		var jieguo = $("#result").find("option:selected").val();
		var reason = $("#reason").val();
		var zyid = $("#zyid").val();
		var keyid = $("#keyid").val();
		var gxzd = "";
		var bool = true;
		$(".fy").each(function(){
			var i = $(this).attr("index");
			var zd = $("#zdgx"+i).val();
			var val = $("#fy"+i).val();
			if(val == null || val == ''){
				if(jieguo == '1'){
					var tip  = $(this).attr("tip");
					alert("请选择"+tip);
					$(this).focus();
					bool = false;
					return false;
				}
				
			}else{
				if(zd == 'zt' || zd == 'tbyy'){
					//if(cztype == 'update'){
					//	gxzd = gxzd + zd+"卍"+val+"★";
					//}
				}else{
					gxzd = gxzd + zd+"卍"+val+"★";
				}
				
			}
			
		});
		if(!bool){
			return false;
		}
		$(".update").each(function(){
			var name = $(this).attr("name");
			var val = $(this).val();
			if(val == null || val == ''){
				if(jieguo == '1'){
					var tip  = $(this).attr("tip");
					alert("请输入"+tip);
					$(this).focus();
					bool = false;
					return false;
				}
				
			}else{
				if(name == 'zt' || name == 'tbyy'){
					//if(cztype == 'update'){
					//	gxzd = gxzd + name+"卍"+val+"★";
					//}
				}else{
					gxzd = gxzd + name+"卍"+val+"★";
				}
			}
			
		});
		
		if(!bool){
			return false;
		}else{
			if(jieguo != '1'){
				var reason = $("#reason").val();
				if(reason == null || reason == ''){
					var ms = $("#result").find("option:selected").html();
					alert("请填写"+ms+"原因");
					$("#reason").focus();
					return false;
				}
			}else{
				reason="";//审核通过时，不保存之前的退办原因
			}
		}
		gxzd = gxzd.substring(0, gxzd.length-1);
		$.ajax({
			cache:false,
			async:false,
			type:'post',
			url: "<%=request.getContextPath()%>/wfjb/wfjb_shenPiResource.action",
			data: {zyid:zyid,keyid:keyid,result:jieguo,reason:reason,gxzd:gxzd},
			dataType: 'html',
			success:function(data){
				if (data == 0) {
					alert("资源信息不存在!");
				} else if (data == 1){
					alert("审批成功!");
					var row = '${mx[0]}';
					var tableObj = window.parent.opener.document.getElementById(row);
					$(tableObj).remove();
					window.close();
				} else {
					exception(data);
					return false;
				}
			}
		});
	}
	
	function zhuanzhzx(){
		var cztype = '${cztype}';
		var zyid = $("#zyid").val();
		var keyid = $("#keyid").val();
		var gxzd = "";
		var bool = true;
		$(".fy").each(function(){
			var i = $(this).attr("index");
			var zd = $("#zdgx"+i).val();
			var val = $("#fy"+i).val();
			if(val != null && val != ''){
				if(zd == 'zt' || zd == 'tbyy'){
					//if(cztype == 'update'){
					//	gxzd = gxzd + zd+"卍"+val+"★";
					//}
				}else{
					gxzd = gxzd + zd+"卍"+val+"★";
				}
			}
		});
		$(".update").each(function(){
			var name = $(this).attr("name");
			var val = $(this).val();
			if(val != null && val != ''){
				if(name == 'zt' || name == 'tbyy'){
					//if(cztype == 'update'){
					//	gxzd = gxzd + name+"卍"+val+"★";
					//}
				}else{
					gxzd = gxzd + name+"卍"+val+"★";
				}
			}
			
		});
		gxzd = gxzd.substring(0, gxzd.length-1);
		$.ajax({
			cache:false,
			async:false,
			type:'post',
			url: "<%=request.getContextPath()%>/wfjb/wfjb_zhuanZhzx.action",
			data: {zyid:zyid,keyid:keyid,gxzd:gxzd},
			dataType: 'html',
			success:function(data){
				if (data == 0) {
					alert("资源信息不存在!");
				} else if (data == 1){
					alert("转指挥中心成功!");
					var row = '${mx[0]}';
					var tableObj = window.parent.opener.document.getElementById(row);
					$(tableObj).remove();
					window.close();
				} else {
					exception(data);
					return false;
				}
			}
		});
	}
	
	function updateInfo(){
		var cztype = '${cztype}';
		var zyid = $("#zyid").val();
		var keyid = $("#keyid").val();
		var gxzd = "";
		var bool = true;
		$(".fy").each(function(){
			var i = $(this).attr("index");
			var zd = $("#zdgx"+i).val();
			var val = $("#fy"+i).val();
			if(val == null || val == ''){
				var tip  = $(this).attr("tip");
				alert("请选择"+tip);
				$(this).focus();
				bool = false;
				return false;
			}
			if(zd == 'zt' || zd == 'tbyy'){
				//if(cztype == 'update'){
				//	gxzd = gxzd + zd+"卍"+val+"★";
				//}
			}else{
				gxzd = gxzd + zd+"卍"+val+"★";
			}
		});
		if(!bool){
			return false;
		}else{
			$(".update").each(function(){
				var name = $(this).attr("name");
				var val = $(this).val();
				if(val == null || val == ''){
					var tip  = $(this).attr("tip");
					alert("请输入"+tip);
					$(this).focus();
					bool = false;
					return false;
				}
				if(name == 'zt' || name == 'tbyy'){
					//if(cztype == 'update'){
					//	gxzd = gxzd + name+"卍"+val+"★";
					//}
				}else{
					gxzd = gxzd + name+"卍"+val+"★";
				}
			});
		}
		if(!bool){
			return false;	
		}
		var jieguo = $("#result").find("option:selected").val();
		var reason = $("#reason").val();
		if(jieguo != '1'){
			if(reason == null || reason == ''){
				var ms = $("#result").find("option:selected").html();
				alert("请填写"+ms+"原因");
				$("#reason").focus();
				return false;
			}
		}
		gxzd = gxzd + "zt卍"+jieguo+"★";
		gxzd = gxzd + "tbyy卍"+reason+"★";
		gxzd = gxzd.substring(0, gxzd.length-1);
		$.ajax({
			cache:false,
			async:false,
			type:'post',
			url: "<%=request.getContextPath()%>/wfjb/wfjb_updateInfo.action",
			data: {zyid:zyid,keyid:keyid,gxzd:gxzd},
			dataType: 'html',
			success:function(data){
				if (data == 0) {
					alert("资源信息不存在!");
				} else if (data == 1){
					alert("修改成功!");
					window.close();
				} else {
					exception(data);
					return false;
				}
			}
		});
	}
	
	//回填文本框信息
	function textchange(txtid1, txtid2){
		$("#"+txtid2).val($("#"+txtid1).val());
	}
	
	function change(obj){
		var str="";
		 $("input[name='tbyy']").each(function(i,index){
			if ($(this).attr("checked")=="checked") {
				str+=$(this).val()+",";
				if($(this).val()=='其他'){
				   $("#bzrow").show();
				}else{
					$("#bzrow").hide();
				}
			}else{
			   $("#bzrow").hide();
			}	 	  		
		 });
		$("#reason").val(str.substring(0,str.length-1));
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
<div class="main">                                                   
   <div style="width:98%; height:auto; margin: 0px 0px 0px 0px;">
		<div class="daxx">
			<input type="hidden" name="zyid" id="zyid" value="${zyid}"/>
			<input type="hidden" name="keyid" id="keyid" value="${keyid}"/>
			<p class="p1">${resouceType }信息</p>
			<div class="xxlr" style="padding-bottom:10px;">
		   		<table width="930" border="0" class="table" >
		   			<tr class="tr1">
   					<s:iterator id="tit" value="#request.title" status="st">
						<td class="td1" height="28" >${tit[2]}：</td>	
						<td class="td2" <s:if test="#request.title.size%2!=0 && #st.index+1==#request.title.size">colspan="3"</s:if> >
							&nbsp;
							<s:if test="#tit[5] == 1">
								<a class="img01a" href="<%=request.getContextPath()%>/wfjb/wfjb_getPic.action?colname=<s:property value="#request.mx[#st.index]" />&keyid=<s:property value="#request.keyid" />&zyid=<s:property value="#request.zyid" />" title="图片">
				    				<img style="border:0;" height="200" width="200" src="<%=request.getContextPath()%>/wfjb/wfjb_getPic.action?colname=<s:property value="#request.mx[#st.index]" />&keyid=<s:property value="#request.keyid" />&zyid=<s:property value="#request.zyid" />"/>
				    			</a>
							</s:if>
							<s:else>
								<s:if test="#tit[14] == 1">
									<s:if test="#request.cztype != 'select'">
										<input type="text" id="fy${st.index+1}" class="fy" index="${st.index+1}" style="width: 180px;" tip="${tit[2]}"/>
										<input type="hidden" name="showzd${st.index+1}" id="showzd${st.index+1}" value="${tit[15]}"/>
										<input type="hidden" name="zdbm${st.index+1}" id="zdbm${st.index+1}" value="${tit[16]}"/>
										<input type="hidden" name="zdtj${st.index+1}" id="zdtj${st.index+1}" value="${tit[17]}"/>
										<input type="hidden" name="fyzd${st.index+1}" id="fyzd${st.index+1}" value="${tit[18]}"/>
										<input type="hidden" name="tabname${st.index+1}" id="tabname${st.index+1}" value="${tit[19]}"/>
										<input type="hidden" name="fytj${st.index+1}" id="fytj${st.index+1}" value="${tit[20]}"/>
										<input type="hidden" name="zdgx${st.index+1}" id="zdgx${st.index+1}" value="${tit[21]}"/>
										<input type="hidden" name="xh${st.index+1}" id="xh${st.index+1}" value="<s:property value="#request.mx[#st.index]" />"/>
										<input type="hidden" name="val${st.index+1}" id="val${st.index+1}" value="${mx[st.index]}"/>
									</s:if>
									<s:else>
										<s:if test="#tit[22] != null && #tit[22] != '' && #request.cztype == 'update'">
											<s:if test="#tit[13] == 'DATE'">
												<input type="text" name="${tit[22]}" id="${tit[22]}" class="update" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${mx[st.index]}" tip="${tit[2]}"/>
											</s:if>
											<s:else>
												<input type="text" name="${tit[22]}" id="${tit[22]}" class="update" value="${mx[st.index]}" tip="${tit[2]}"/>
											</s:else>
											
										</s:if>
										<s:else>
											${mx[st.index]}
										</s:else>
									</s:else>
								</s:if>
								<s:else>
									<s:if test="#tit[22] != null && #tit[22] != '' && #request.cztype != 'select'">
										<s:if test="#tit[13] == 'DATE'">
											<input type="text" name="${tit[22]}" id="${tit[22]}" class="update" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${mx[st.index]}" tip="${tit[2]}"/>
										</s:if>
										<s:else>
											<input type="text" name="${tit[22]}" id="${tit[22]}" class="update" value="${mx[st.index]}" tip="${tit[2]}"/>
										</s:else>
										
									</s:if>
									<s:else>
										${mx[st.index]}
									</s:else>
								</s:else>
							</s:else>
						</td>
						
						<s:if test="(#st.index+1)%2==0">
							</tr>
							<s:if test="#st.index+1 != #request.title.size">
								<tr>
							</s:if>
						</s:if>	
					</s:iterator>
				</table>
				<br/>
			</div>
		</div>
		
		<!-- 图片库 -->
		<s:if test="#request.photos !=null  && #request.photos.size > 0">
		   <div class="daxx">
			<p class="p1">图片类型信息</p>
			<div class="xxlr" style="padding-bottom:10px;">
		   		<table width="930" border="0" class="table" >
		   			<tr class="tr1">		   			
   					<s:iterator id="phTemp" value="#request.photos" status="temp">
						<td class="td1" height="28" >
						<s:iterator id="pType" value="#request.phoTypes">
						
		   			       <s:if test="#phTemp[2]== #pType[1]">
		   			          ${pType[2]}:
		   			       </s:if>
		   			    </s:iterator>
						</td>	
						<td class="td2" <s:if test="#request.photos.size%2!=0 && #temp.index+1==#request.photos.size">colspan="3"</s:if> >
							&nbsp;							
							<a class="img01a" href="<%=request.getContextPath()%>/servlet/imageServlet?keyid=${phTemp[0]}&phType=${phTemp[2]}" title="图片">
                                 <img style="border:0;" height="200" width="200" src="<%=request.getContextPath()%>/servlet/imageServlet?keyid=${phTemp[0]}&phType=${phTemp[2]}"/>
				    	    </a>					
						</td>						
						<s:if test="(#temp.index+1)%2==0">
							</tr>
						
						</s:if>	
					</s:iterator>
				</table>
				<br/>
			</div>
		</div>		
	</s:if>
			
		
	<div class="daxx">
			<p class="p1">审批信息</p>
			<div class="xxlr" style="padding-bottom:10px;">
		   		<table width="930" border="0" class="table" >
					<s:if test='#request.cztype == "select"'>
						<tr>
							<td class="td1" height="28">审核人：</td>
							<td class="td2" height="28">&nbsp;${shInfo[0]}</td>
							<td class="td1" height="28">审核人姓名：</td>
							<td class="td2" height="28">&nbsp;${shInfo[1]}</td>
						</tr>
						<tr>
							<td class="td1" height="28">审核时间：</td>
							<td class="td2" height="28">&nbsp;${shInfo[2]}</td>
							<td class="td1" height="28">审核人部门：</td>
							<td class="td2" height="28">&nbsp;${shInfo[3]}</td>
						</tr>
						<tr>
							<td class="td1" height="28">审核IP：</td>
							<td class="td2" height="28" colspan="3">&nbsp;${shInfo[4]}</td>
						</tr>
					</s:if>
					<tr>
						<td class="td1" height="28">
							审批结果：
						</td>
						<td class="td2" height="28">
							&nbsp;<SELECT name="result" id="result" <s:if test='#request.cztype == "select"'>disabled="disabled"</s:if>>
								<s:if test="#request.cztype == 'select'">
									<option value="" <s:if test='#request.shInfo[5] == 0'>selected="selected"</s:if>>未审批</option>
								</s:if>
								<s:iterator var="jg" value="#request.shjg">
									<option value="${jg[1]}" <s:if test='#request.shInfo[5] == #request.jg[1]'>selected="selected"</s:if>>${jg[2]}</option>
								</s:iterator>
							</SELECT>
						</td>
						<td class="td1" height="28">
							退办原因：
						</td>
						<td class="td2" height="28">
							<s:if test="#request.cztype != 'select'">							
								<s:iterator var="tb" value="#request.tbyy" status="index">																										
								    <input type="checkbox" name="tbyy" id="tbyy"   onclick="change(this);" <s:if test='#request.shInfo[6] == #request.tb[2]'>checked="checked"</s:if>  value="${tb[2]}" /> ${tb[2]}<br>							    
								</s:iterator>
							</s:if>
							<s:else>
								${shInfo[6]}
							</s:else>
							<input type="hidden" name="reason" id="reason" value="${shInfo[6]}" size="30"/>
						</td>
					</tr>
					<tr style="display: none;" id="bzrow">
						<td class="td1" height="28">
							备注：
						</td>
						<td class="td2" height="28" colspan="3">
							&nbsp;<input type="text" name="bz" id="bz" style="width: 160px;" value="${shInfo[6]}" onchange="textchange('bz', 'reason')" onkeydown="textchange('bz', 'reason')" onkeyup="textchange('bz', 'reason')"  <s:if test='#request.cztype=="select"'>disabled="disabled"</s:if>/>
						</td>
					</tr>
					<tr>
						<td colspan="4" height="50" align="center">
							<s:if test="#request.cztype == 'shenpi'">
								<input type="button" style="width:94px; height:23px; background:url(<%=request.getContextPath()%>/images/fhan.gif) no-repeat; border:none; color:#FFFFFF; font-weight:bold; cursor:pointer;" onclick="javascript:submitfrom();" value="审 批" class="bnt" />
								<s:if test="#request.zyid == 1">
									<input type="button" style="width:94px; height:23px; background:url(<%=request.getContextPath()%>/images/fhan.gif) no-repeat; border:none; color:#FFFFFF; font-weight:bold; cursor:pointer;" onclick="javascript:zhuanzhzx();" value="转指挥中心审批" class="bnt" />
								</s:if>
							</s:if>
							<s:if test="#request.cztype == 'update'">
								<input type="button" style="width:94px; height:23px; background:url(<%=request.getContextPath()%>/images/fhan.gif) no-repeat; border:none; color:#FFFFFF; font-weight:bold; cursor:pointer;" onclick="javascript:updateInfo();" value="提交" class="bnt" />
							</s:if>
							<input type="button" id="returnbtn" style="width:94px; height:23px; background:url(<%=request.getContextPath()%>/images/fhan.gif) no-repeat; border:none; color:#FFFFFF; font-weight:bold; cursor:pointer;" onclick="javascript:window.history.go(-1);" value="返 回"/>
						</td>
					</tr>
				</table>
				<br/>
			</div>
		</div>
		
		<br/><br/>
  </div>
</div>

</body>
</html>