package com.xxfbpt.ssh.hbm;

import java.util.Date;

@SuppressWarnings("serial")
public class XxptWebsericeLog extends BaseObject{
	
	private String xh;
	private String jkid;
	private String userid;
	private String userpwd;
	private Date requestTime;
	private String hrefurl;
	private String inputmsg;
	private Date responseTime;
	private String responseResult;
	private String responseOutmsg;


	public XxptWebsericeLog() {
		
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getJkid() {
		return jkid;
	}

	public void setJkid(String jkid) {
		this.jkid = jkid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getHrefurl() {
		return hrefurl;
	}

	public void setHrefurl(String hrefurl) {
		this.hrefurl = hrefurl;
	}

	public String getInputmsg() {
		return inputmsg;
	}

	public void setInputmsg(String inputmsg) {
		this.inputmsg = inputmsg;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}

	public String getResponseOutmsg() {
		return responseOutmsg;
	}

	public void setResponseOutmsg(String responseOutmsg) {
		this.responseOutmsg = responseOutmsg;
	}
	

}
