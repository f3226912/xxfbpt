//身份证号码正则验证
function checksfzhm(obj) {
	var sfzhm = obj.value;
	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
	if (!(/(^FH)|(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(sfzhm))) {
		alert("输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X，或者香港同胞以'FH'开头的的证件号码。");
		return "false";
	} else {
		return "true";
	}
}

//数字正则验证(数字+小数点)
function checknumber(obj) {
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g, "");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g, "");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");
}

//数字正则验证(数字)
function checknumber2(obj) {
	//先把非数字的都替换掉，除了数字
	obj.value = obj.value.replace(/[^\d]/g, "");
}

//验证非空
function checknotnull(obj, alerttype) {
	if (obj.value != "" && obj.value != " " && obj.value != null
			&& obj.value != undefined) {
		obj.style.borderColor = '';
		return "true";
	} else {
		alert(alerttype);
		obj.style.borderColor = '#FF0000';
		obj.focus();
		return "false";
	}
}

//验证邮箱地址(正则)
function checkemail(str) {
	if (!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
			.test(str))) {
		alert("邮箱地址输入错误!");
		return "false";
	} else {
		return "true";
	}
}

//网址验证		
function checkurl() {
	var url = $("#homePage").val();
	if ("" != url) {
		var urlreg = /^(http:\/\/)*[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
		if (urlreg.test(url)) {
			return "true";
		} else {
			alert("网址格式不正确！");
			return "false";
		}
	}
}

//手机信息格式验证
function checkmobile(mobile){
	var cmcc=/^(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$/;
	var cucc=/^(13[0-9]|15[0-9])[0-9]{8}$/;
	var cnc=/^(1[358][0-9]{1})[0-9]{8}$/;
	var cnc2=/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}$/;
	var cnc3=/^[1]{1}[8]{1}[89]{1}[0-9]{8}$/;
	var cnc4=/^[1]{1}(([3]{1}[0-3]{1})|([5]{1}[3]{1}))[0-9]{8}$/;
	var flag = "false";
	if(cmcc.test(mobile)){
		flag = "true";
	}else if(cucc.test(mobile)){
		flag = "true";
	}else if(cnc.test(mobile)){
		flag = "true";
	}else if(cnc2.test(mobile)){
		flag = "true";
	}else if(cnc3.test(mobile)){
		flag = "true";
	}else if(cnc4.test(mobile)){
		flag = "true";
	}else{
		alert("手机格式不正确");
		flag = "false";
	}
	return flag;
}

//电话格式验证
function checkphone(phone){
	var rule=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	var result = "false";
	if(rule.test(phone)){
		result = "true";
	}else{
		alert("电话格式不正确");
		result = "false";
	}
	return result;
}

//禁止页面按退格键跳转页面,不影响文本编辑    <body onkeydown="javascript:PreventBSK();">
function PreventBSK(){
    var bskEventCancel = false;
    var _EVENT = window.event;
    bskEventCancel = _EVENT && _EVENT.altKey && (_EVENT.keyCode == 8 || _EVENT.keyCode == 37 || _EVENT.keyCode == 39);
    if(_EVENT.keyCode == 8)
    {
        var tagName = _EVENT.srcElement.tagName.toUpperCase();
        if(tagName == "TEXTAREA" || tagName == "INPUT")//文本操作不受影响
            bskEventCancel = _EVENT.srcElement.readOnly;
        else
            bskEventCancel = true;
    }
    _EVENT.cancelBubble = bskEventCancel;
    _EVENT.returnValue = !bskEventCancel;
    return !bskEventCancel;
}

//验证日期格式
function checkdate(date){
	var rule=/^((^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(10|12|0?[13578])([-\/\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(11|0?[469])([-\/\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(0?2)([-\/\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([3579][26]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][13579][26])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][13579][26])([-\/\._])(0?2)([-\/\._])(29)$))$/;
	var result = "false";
	if(rule.test(date)){
		result = "true";
	}else{
		alert("日期格式不正确,如:1988-07-28");
		result = "false";
	}
	return result;
}

//清除空格和'$'
function clearspace(obj){
	obj.value = obj.value.replace(" ","");
	obj.value = obj.value.replace("　","");
	obj.value = obj.value.replace("（","(");
	obj.value = obj.value.replace("）",")");
}

//清除所有的空格
function clearallspace(obj){
	var val = obj.value;
	while(val.indexOf(" ") != -1){
		val = val.replace(" ", "");
	}
	obj.value = val;
	return obj.value;
}

//获取时间 date:时间；  是否显示星期  ； 连接符（'-','/'）
function getnowdate(date, format, iswed, limit){
	var xinqis = new Array(["星期天"], ["星期一"], ["星期二"], ["星期三"], ["星期四"],["星期五"], ["星期六"]);
	var nowyy = date.getFullYear();
	var nowmm = date.getMonth()+1;
	var nowday = date.getDate();
	var nowxinqi = date.getDay();
	var nowhours = date.getHours();
	var nowmis = date.getMinutes();
	var nowseconds = date.getSeconds();
	if(iswed == 'yes'){
		if(format == 'yyyy-MM-dd HH:mm:ss'){
			return nowyy+limit+nowmm+limit+nowday+" "+nowhours+":"+nowmis+":"+nowseconds+" "+xinqis[nowxinqi];
		}else if(format == 'yyyy-MM-dd'){
			return nowyy+limit+nowmm+limit+nowday+" "+xinqis[nowxinqi];
		}else{
			return "";
		}
	}else{
		if(format == 'yyyy-MM-dd HH:mm:ss'){
			return nowyy+limit+nowmm+limit+nowday+" "+nowhours+":"+nowmis+":"+nowseconds;
		}else if(format == 'yyyy-MM-dd'){
			return nowyy+limit+nowmm+limit+nowday;
		}else{
			return "";
		}
	}
}

//判断特殊字符
function containSpecial(obj,alerttype){       
    var containSpecial = RegExp(/[(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\‘)(\“)(\、)(\：)(\——)(\（)(\）)(\,)(\/)(\<)(\>)(\?)(\)]+/);       
    if(containSpecial.test(obj.value)){
    	prompt(obj,alerttype);
		return "false";
    }
    else{
    	removePrompt(obj)
		return "true";
    }
}