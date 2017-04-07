<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>menu</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/development-bundle/themes/base/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.accordion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/development-bundle/ui/jquery.ui.button.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-ui-1.9.1.custom.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/css/redmond/jquery-ui-1.9.1.custom.css" />
	<style type="text/css">
		.ui-widget-content{
			border:0;
		}
		html,body{
			height:100%;
			margin:0;
			padding:0;
		}
		a:link {
			text-decoration: none;
			color:black;
		}
		a:visited {
			text-decoration: none;
			color:black;
		}
		a:hover {
			text-decoration: none;
			color:blue;
		}
		a:active {
			text-decoration: none;
			color:blue;
		}
		#accordion{
			margin:0;
			padding:0;
			width:182px;
			font-size:12px;
		}
		#accordion ul{
			margin:0;
			padding:0;
		}
		#accordion ul li{
			list-style-type: none;
		    margin: 20px auto 20px 18px;
		    padding: 0;
		    text-align: left;
		}
	</style>
	<script type="text/javascript">
	    $(function(){
	    	var icons = {
				header: "ui-icon-circle-arrow-e",
				headerSelected: "ui-icon-circle-arrow-s"
			};
			$("#accordion").accordion({
				autoHeight: false,
				navigation: true,
				fillSpace: false,
				collapsible: false,
				icons: icons
			});
		});
		$(document).ready(function() {
			$(".aclass").click(function(){
				$(".aclass").attr("style", "");
				$(this).attr("style","color:red");
			});
		});
		function HideOrShow(){
			var showed = document.getElementById("showed3");
	     	if(showed.value!=null){
	     		if(showed.value=="true"){
                    window.parent.FrameLeft.cols="8,*,8";
	     			showed.value="false";
	     			$("#tddivid").show();
	     		}else{
                    window.parent.FrameLeft.cols="200,*,8"
	        		showed.value="true";
	        		$("#tddivid").hide();
	     		}
	     	}else{
	     		alert("Error");
	     	}
		}
    </script>
  </head>
  <body>
	<table width="200" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="8" style="background:url(<%=request.getContextPath()%>/images/frame_12.gif) 0 0 repeat-y">
				<div id="tddivid" style="display: none;"><img title="点击显示导航菜单" style="cursor: pointer;" onclick="HideOrShow()" height="50" src="<%=request.getContextPath()%>/images/show_left.gif" width="8" name="pic"></div>
			</td>
			<td width="182" valign="top">
			<div id="accordion">
				<s:iterator id="menu" value="#session.usernode">
					<s:if test="#menu[5]==0">
						<h3><a href="#">${menu[2]}</a></h3>
						<ul>
							<s:iterator id="item" value="#session.usernode">
								<s:if test="#item[5]==#menu[0]">
									<li><img src="<%=request.getContextPath()%>/images/xgmm.jpg" width="12" height="12" border="0" >&nbsp;<a href="<%=request.getContextPath()%>/${item[3]}" target="mainFrame">${item[2]}</a></li>
								</s:if>
							</s:iterator>
						</ul>
					</s:if>
				</s:iterator>
			</div>
			</td>
			<td width="10" style="background:url(<%=request.getContextPath()%>/images/frame_13.gif) 0 0 repeat-y">
				<img title="点击隐藏导航菜单" style="cursor: pointer;" onclick="HideOrShow()" height="50" src="<%=request.getContextPath()%>/images/hide_left.gif" width="8" name="pic">
			</td>
		</tr>
	</table>
	<input name="showed" type="hidden" value="true" id="showed3" width="0">
	<input name="showtop" type="hidden" value="true" id="showtop" width="0">
  </body>
</html>
