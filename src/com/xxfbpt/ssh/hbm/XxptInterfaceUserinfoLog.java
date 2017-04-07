package com.xxfbpt.ssh.hbm;

import java.util.Date;

/**
 * XxptInterfaceUserinfo entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class XxptInterfaceUserinfoLog extends BaseObject {

	// Fields

	private String userid;
	private String password;
	private String username;
	private String keys;
	private String usertype;
	private String userip;
	private String usermac;
	private String czr;
	private String czrxm;
	private String czrbm;
	private String czrkjbm;
	private Date czsj;
	private String cznr;
	private String czip;
	private String czmac;

	// Constructors

	/** default constructor */
	public XxptInterfaceUserinfoLog() {
	}

	/** full constructor */
	public XxptInterfaceUserinfoLog(String password, String username, String keys,
			String usertype, String userip, String usermac) {
		this.password = password;
		this.username = username;
		this.keys = keys;
		this.usertype = usertype;
		this.userip = userip;
		this.usermac = usermac;
	}

	// Property accessors

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKeys() {
		return this.keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip = userip;
	}

	public String getUsermac() {
		return this.usermac;
	}

	public void setUsermac(String usermac) {
		this.usermac = usermac;
	}

	public String getCzr() {
		return czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	public String getCzrxm() {
		return czrxm;
	}

	public void setCzrxm(String czrxm) {
		this.czrxm = czrxm;
	}

	public String getCzrbm() {
		return czrbm;
	}

	public void setCzrbm(String czrbm) {
		this.czrbm = czrbm;
	}

	public String getCzrkjbm() {
		return czrkjbm;
	}

	public void setCzrkjbm(String czrkjbm) {
		this.czrkjbm = czrkjbm;
	}

	public Date getCzsj() {
		return czsj;
	}

	public void setCzsj(Date czsj) {
		this.czsj = czsj;
	}

	public String getCzip() {
		return czip;
	}

	public void setCzip(String czip) {
		this.czip = czip;
	}

	public String getCzmac() {
		return czmac;
	}

	public void setCzmac(String czmac) {
		this.czmac = czmac;
	}

	public String getCznr() {
		return cznr;
	}

	public void setCznr(String cznr) {
		this.cznr = cznr;
	}
	
}