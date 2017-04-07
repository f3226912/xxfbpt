package com.xxfbpt.ssh.hbm;

/**
 * XxptInterfaceInfo entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class XxptInterfaceInfo extends BaseObject {

	// Fields

	private String xh;
	private String jkid;
	private String jkm;
	private String jksrcs;
	private String jksccs;
	private String jklx;
	private String jklb;
	private String jkurl;
	private String jkzt;
	private String jkms;
	private String zdm;

	// Constructors

	/** default constructor */
	public XxptInterfaceInfo() {
	}

	/** full constructor */
	public XxptInterfaceInfo(String jkid, String jkm, String jksrcs,
			String jksccs, String jklx, String jklb, String jkurl, String jkzt,
			String jkms) {
		this.jkid = jkid;
		this.jkm = jkm;
		this.jksrcs = jksrcs;
		this.jksccs = jksccs;
		this.jklx = jklx;
		this.jklb = jklb;
		this.jkurl = jkurl;
		this.jkzt = jkzt;
		this.jkms = jkms;
	}

	// Property accessors

	public String getXh() {
		return this.xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getJkid() {
		return this.jkid;
	}

	public void setJkid(String jkid) {
		this.jkid = jkid;
	}

	public String getJkm() {
		return this.jkm;
	}

	public void setJkm(String jkm) {
		this.jkm = jkm;
	}

	public String getJksrcs() {
		return this.jksrcs;
	}

	public void setJksrcs(String jksrcs) {
		this.jksrcs = jksrcs;
	}

	public String getJksccs() {
		return this.jksccs;
	}

	public void setJksccs(String jksccs) {
		this.jksccs = jksccs;
	}

	public String getJklx() {
		return this.jklx;
	}

	public void setJklx(String jklx) {
		this.jklx = jklx;
	}

	public String getJklb() {
		return this.jklb;
	}

	public void setJklb(String jklb) {
		this.jklb = jklb;
	}

	public String getJkurl() {
		return this.jkurl;
	}

	public void setJkurl(String jkurl) {
		this.jkurl = jkurl;
	}

	public String getJkzt() {
		return this.jkzt;
	}

	public void setJkzt(String jkzt) {
		this.jkzt = jkzt;
	}

	public String getJkms() {
		return this.jkms;
	}

	public void setJkms(String jkms) {
		this.jkms = jkms;
	}

	public String getZdm() {
		return zdm;
	}

	public void setZdm(String zdm) {
		this.zdm = zdm;
	}

}