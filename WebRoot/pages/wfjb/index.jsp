<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cvi_busy_lib.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/public.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/jquery.artDialog.source.js?skin=chrome"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/artDialog4.1.6/plugins/iframeTools.source.js"></script>
<title>信息平台资源审核</title>
<style>
*{ margin:0 auto; font-size:12px;}
body{ background:#e0e4e9 url(<%=request.getContextPath()%>/images/bodybj.gif) repeat-x;}
.main{ margin-top:1px;}
ul{ list-style:none; width:920px; height:49px; display:block; list-style:none; margin-top:10px; margin-bottom:0px; padding:0px;}
.logo{width:1042px; height:132px; background:url(<%=request.getContextPath()%>/images/top1.jpg) no-repeat center top; position:relative; margin-bottom:2px;}
.ul{
	list-style: none;
	width: 500px;
	height: 30px;
	display: block;
	color: #FFFFFF;
	position: absolute;
	top: 100px;
	left: 40px;
	padding: 0;
	font-weight: bold;
}
/* left:810px; width:220px; */
.ula{
	list-style: none;
	width: 580px;
	height: 30px;
	display: block;
	color: #FFFFFF;
	position: absolute;
	top: 101px;
	left: 480px;
	padding: 0;
	font-weight: bold;
}
.ul1{ list-style:none; width:918px; height:49px; display:block; list-style:none; margin-top:10px; margin-bottom:0px; padding:0px;}
.li{ float:left; margin-right:15px;}
a{ text-decoration:none; font-size:14px;}

h2{letter-spacing:4px; position:absolute;left:170px; top:126px; color:#FFFFFF; font-size:18px; font-weight:bold; display:inline;}
.a1{position:absolute;left:276px; top:126px;font-size:18px; text-decoration:none; color:#FFFFFF; font-weight:bold;}
a:hover{ color:blue;}

.wgz,.zdy{width:920px; height:170px;  margin-bottom:8px;}
.zdgz{ width:470px; height:30px; display:block; padding:0px; z-index: -1; float:right; list-style:none; margin-top:0px;}
.zdgz li{ width:70px; height:30px; background:url(<%=request.getContextPath()%>/images/zzdff2.jpg) no-repeat 0px 4px; line-height:30px; padding-left:19px; letter-spacing:1px; text-align: left; display:block; float: right; margin-right:2px; }
.zdgz li a{ color:#626262; text-decoration:none; font-size:12px;}
.zdgz li a:hover{ color:#CC0000; text-decoration:underline; font-size:12px;}


.zdw{font-size:14px; font-weight:bolder; color:#666666; text-align: center; display:block ; padding-top: 20px; }
.lxss{ width:920px; height:31px; background:url(<%=request.getContextPath()%>/images/lxss.jpg) no-repeat;}
.wgzt{ width:920px; height:31px; background:url(<%=request.getContextPath()%>/images/wgz.jpg) no-repeat;}
.sjlb{ width:920px; height:31px; background:url(<%=request.getContextPath()%>/images/sjlb.gif) no-repeat;}
.dxCount{ width:920px; height:31px; background:url(<%=request.getContextPath()%>/images/dxCount.jpg) no-repeat;}
.sjkSjly{ width:920px; height:31px; background:url(<%=request.getContextPath()%>/images/sjkSjly.jpg) no-repeat;}
.zdyt{ width:920px; height:31px; background:url(<%=request.getContextPath()%>/images/jgz.jpg) no-repeat;}

.zy_1,.zy_2 ,.zy_3{border-right:1px dashed #ccc; width:291px; height:auto; float:left; scrollbar-shadow-color: #ffffff; scrollbar-highlight-color: #ffffff; scrollbar-face-color: #d9d9d9; scrollbar-3dlight-color: #d9d9d9; scrollbar-darkshadow-color: #d9d9d9; scrollbar-track-color: #ffffff; scrollbar-arrow-color: #ffffff;} 
.zdgzx{width:270px; height:150px; text-align:center;  display:block;  margin-top:0px; margin-right:0px;  padding-top:3px; padding-left:3px;
}
.zdgzx li{ float:left; width:130px; height:25px;  display:block;  text-align:left; }
.zdgzx li input{ }
.zdgzx li strong{ }


.lia{ float:left; width:92px; height:47px; text-align:center; margin-left:9px; }
.li1{ background:url(<%=request.getContextPath()%>/images/gzbj1.jpg) no-repeat center; cursor:pointer; }

dl{ float:left; width:220px; margin-top:10px;}
.dl1{ position:relative;}
.text{ width:150px; height:18px; line-height:18px; position:absolute; top:13px; left:20px;}
.wz1{ width:150px; height:18px; line-height:18px; position:absolute; top:13px; left:20px;}

.bottom1{ width:1024px; height:25px; display:block;  font-weight:normal; margin-top:0px; border-top:1px solid #e2ebf0; line-height:35px; font-size:12px; text-align:center; color:#FFFFFF; font-style:normal; font-weight: bold; letter-spacing:12px; margin-bottom:0px;}
.span1{float:left; line-height:27px; color:#4da1e0;margin-left:10px; letter-spacing:4px;}
.span2{float:right; line-height:27px; color:#4da1e0; margin-right:10px;}

.tabs{height: 32px;border-bottom:1px solid #cccccc;border-left: 1px solid #999;width: 100%; color:#ccc;}
.tabs li{height:31px; width:150px; text-align:center;line-height:31px;float:left;border:1px solid #cccccc;border-left:none;margin-bottom: -1px;background: #f2f9fe;overflow: hidden;position: relative; color:#999;}
.tabs li a {display: block;padding: 0 20px;border: 1px solid #fff;outline: none; color:#333333; }
.tabs li a:hover {background: #a2d8ff; color:#666666;}	

.strong{ width:150px; height:28px; color:white; display:block; float:left;}
.str1{
	position: absolute;
	top: 5px;
	left: 35px;
}
.str2{
	position: absolute;
	top: 5px;
	left: 328px;
}
.str3{
	position: absolute;
	top: 5px;
	left: 620px;
}
.str4{
	position: absolute;
	top: 5px;
	left: 682px;
}
.str5{ position:absolute; top:5px; left:729px;}
</style>

<style>
*{margin:0 auto;padding:0;}
body{font-size:12px;}
li{list-style:none;}
.tab1 ,.tab2{width:606px;height:50px;border:1px #369ed3 solid;border-bottom:0;background:url(<%=request.getContextPath()%>/images/ulbj.jpg) repeat-x;}

.tab1 ul ,.tab2 ul{margin:0;padding:0;}

.tab1 li,.tab2 li{ float:left; width:303px;height:50px;line-height:50px;text-align:center;cursor:pointer; }

.tab1 li.now,.tab2 li.now{color:#fff;font-weight:bold; font-size:20px; letter-spacing:2px; background:url(<%=request.getContextPath()%>/images/xza1.jpg) no-repeat;}

.block{display: block;}

.span{ width:100px; height:30px; text-align:center; display:block; float:left;letter-spacing:2px; }
.span3{ width:500px; height:30px; display:block; text-align:center;letter-spacing:2px; color:#000;}
.ssan{
	width: 150px;
	height: 34px;
	background: url(<%=request.getContextPath()%>/images/ssan.jpg) no-repeat;
	border: none;
	cursor: pointer;
}

.clx {height:18px; border:1px solid #d5d8db; line-height:18px; }
.cph {height:18px; width:200px; border:1px solid #d5d8db; line-height:18px; }
.table_cc{margin-top: 20px; margin-bottom: 20px; width:950px;}

.bjtm{width:1048px; height:auto;  margin-top:3px; margin:0 auto; width:1042px; background-image: url(<%=request.getContextPath()%>/images/ct2.png)!important;background-repeat: repeat-y;
 padding-top:25px; filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="<%=request.getContextPath()%>/images/ct2.png"); background:none;}
.xxlr{ height:auto; border-top:none; padding-top:15px; padding-bottom:15px;}

.table{ border:1px solid #71b6cd;border-collapse:collapse; height:28px; width: 950px;}
.table td{ border:1px solid #71b6cd;border-collapse:collapse; cellspacing:0px; cellpadding: 0px; height:28px;}
.td3{background:#c8ebff; text-align: center; height:28px;}
.td4{text-align: center; height:28px;}
.sj_a{ color:#00487e; cursor:pointer;}
.sj_a:hover{ color:#a66200;}


/*         以下为 "近期新增资源"  样式    */
.topbg{ width:321px; height:31px; background:url(<%=request.getContextPath()%>/images/topbg2.jpg) no-repeat; position:relative;}
.topbg a{ width:20px; height:20px; display:block; position:absolute; left:294px; top:10px;}

#divf{position:absolute; bottom:1px; right: 1px; width:321px; background:url(<%=request.getContextPath()%>/images/cbg.jpg) repeat-y; }
.bbg{ width:321px; height:11px; background:url(<%=request.getContextPath()%>/images/bbg.jpg) no-repeat;}

.d1 {
 margin:10px auto;
 width:280px;
 border:#dedede 1px solid;
 background-color:#fff;
 height:auto;
 overflow:hidden;
 white-space:nowrap;
}

.div2 {
 width:auto;
 height:auto;
 font-size:12px;
 float:left;
 overflow:hidden;
}
.div2 ul{
margin:0px;
padding:9px;
list-style:none;
line-height:19px;
font-size:12px;
width: 500px;
}
.div2 ul li{
}
/*
.topbg{ width:321px; height:31px; background:url(<%=request.getContextPath()%>/images/topbg.jpg) no-repeat;}
.table_p,.table_p .td_p, .table_p th{border:1px solid #d0dade;border-collapse:collapse;  word-break:break-all;word-wrap:break-word; font-size:12px;}
.table_p td{ height:22px;  text-align:center; vertical-align:middle;}
.table_p td{ text-align:center; background:#fff;}
.top_a{  background:url(<%=request.getContextPath()%>/images/bg-1.gif) repeat-x;}
#divf{position:absolute; bottom:60%; left: 73%; top:200px; width:321px; background:url(<%=request.getContextPath()%>/images/cbg.jpg) repeat-y; }
.bbg{ width:321px; height:11px; background:url(<%=request.getContextPath()%>/images/bbg.jpg) no-repeat;}
*/
</style>

<style>

.float_layer { width: 321px;  display:none; background: #fff;}
.float_layer h3 { width:321px; height:31px; display:block; background:#666666; background:url(<%=request.getContextPath()%>/images/topbg.jpg) no-repeat;  }
.float_layer .min { width: 21px; height: 20px;  position: absolute; top: 2px; right: 25px; }
.float_layer .min:hover {  }
.float_layer .max { width: 21px; height: 20px;  position: absolute; top: 2px; right: 25px; }
.float_layer .max:hover { }
.float_layer .close { width: 21px; height: 20px;  position: absolute; top: 4px; right: 4px; }
.float_layer .close:hover {  }
.float_layer .content { height: 120px; overflow: hidden; font-size: 14px; line-height: 18px; color: #666; text-indent: 28px; width:321px; background:url(<%=request.getContextPath()%>/images/cbg.jpg) repeat-y; }
.float_layer .wrap {  }
.bottom_t{ width:321px; height:11px; background:url(<%=request.getContextPath()%>/images/bbg.jpg) no-repeat;}
</style>
	<script type="text/javascript">
		function block_viewport(){
			lyFrom.submit();
		}
		
		function shenpi(keyid){
				
		}
	</script>
</head>

	<body>
		<div class="main" >
			<div class="logo">
				<ul class="ul">
					<li class="li">
						<a href="<%=request.getContextPath()%>/ly/getLyPjhs.action"
							target="_parent">首 页</a>
					</li>
				</ul>
				<ul class="ula">
					<li class="li">
						<a href="<%=request.getContextPath()%>/jsjlb/getDdPlList.action"
							target="_black">各大队使用频率</a>
					</li>
					<li class="li">
						<a href="<%=request.getContextPath()%>/ly/RbqRdCountList.action"
							target="_black2">资源热度排名</a>
					</li>
					<li class="li">
						<a href="<%=request.getContextPath()%>/ly/getCountValues.action"
							target="_black3">手机库情况分析</a>
					</li>
					<li class="li">
						<a href="<%=request.getContextPath()%>/ssq/initSsq.action">搜索器管理</a>
					</li>
					<li class="li">
						<a
							href="<%=request.getContextPath()%>/ly/selZhcxMoreResourceList.action">友情链接</a>
					</li>
					<li class="li">
						<a href="<%=request.getContextPath()%>/jtUser_logout.action">退出</a>
					</li>
				</ul>
			</div>
			<div
				style=" width:1042px; height:auto; background:url(<%=request.getContextPath()%>/images/sybj.jpg) no-repeat; margin-top:0px; padding-top:5px;   margin:0 auto;">
				<form method="post"
					action="<%=request.getContextPath()%>/wfjb/wfjb_getResourcemx.action"
					name="lyFrom">

					<div
						style="width: 1000px; height: auto; margin-top: 10px; margin-bottom: 10px; background: #FFFFFF; position: relative;">

						<div style="border: 1px solid #dfdfdf;">
							<div id="tagContent1" style="width: 950px; overflow:auto; ">
								<table class="table_cc">
									<tbody>
										<tr>
											<td width="30%" style="text-align: left;">
												资源对象名:
												<select name="zyid" id="zyid"
													style="width: 100px;">
													<option value="">
														===请选择===
													</option>
													<s:iterator id="resource" value="#request.resourceList">
														<option value="${resource[0]}" <s:if test="#request.zyid == #request.resource[0]">selected="selected"</s:if> >
															${resource[1]}
														</option>
													</s:iterator>
												</select>
											</td>
											<td style="text-align: left;">
												<input class="ssan" onclick="block_viewport();" name="" type="button" />
											</td>
										</tr>
									</tbody>
								</table>
								<table border="0" cellpadding="0" cellspacing="0" class="table">
									<s:if test="#request.title != null">
										<tr  class="tr1">
											<s:iterator id="tit" value="#request.title">
												<td class="td3">${tit[2]}</td>
											</s:iterator>
											<td class="td3">操作</td>
										</tr>
									</s:if>
									
									<s:iterator var="mx" value="#request.mxList" >
										<tr>
											<s:iterator id="tit" value="#request.title" status="st">
												<td  class="td4">
													<s:property value="#mx[#st.index]" />
												</td>
											</s:iterator>
											<td class="td4">
												<a href="javascript: shenpi(${mx[0]});">审批</a>
												<a href="javascript: shenpi(${mx[0]});">查看</a>
											</td>
										</tr>
									</s:iterator>
									
								</table>
								<br/>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>