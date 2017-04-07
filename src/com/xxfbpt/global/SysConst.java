package com.xxfbpt.global;

import java.util.ArrayList;
import java.util.List;

public class SysConst {
	public static final String USERBEAN = "userbean";// 登录用户信息
	public static final String USERMENU = "usermenu";// 登录用户授权菜单
	public static final String USERACTION = "useraction";// 登录用户授权控件
	public static final int PAGESIZE = 12;// 每页显示数据量
	public static final int YXSC = 30;		//身份验证码有效时长
	public static final String XTFILEPATH = "\\xtpagram.properties";
	public static final String ASCDESC_KEY = "95F9BE903D7F569CFACB188475FCAC73";
	public static final String PJBEAN = "pjbean";
	public static final String LASTPJSJ = "lastpjsj";
	public static final String ISMUST = "ismust";
	public static final List<String> interceptorList = new ArrayList<String>();
	static {
		interceptorList.add("user_login.action");
		interceptorList.add("xtpj_initEvaluation.action");
		interceptorList.add("xtpj_editPjxx.action");
		interceptorList.add("user_login.action");
		interceptorList.add("user_loginOut.action");
	}
	
}
