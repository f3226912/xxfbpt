package com.xxfbpt.ssh.hbm;

/**
 * XxptInterfaceUserinfo entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class XxptInterfaceUserinfo extends BaseObject {

	// Fields

	private String userid;
	private String password;
	private String username;
	private String keys;
	private String usertype;
	private String userip;
	private String usermac;

	// Constructors

	/** default constructor */
	public XxptInterfaceUserinfo() {
	}

	/** full constructor */
	public XxptInterfaceUserinfo(String password, String username, String keys,
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

}