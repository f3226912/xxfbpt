package com.xxfbpt.ssh.hbm;

import java.util.Date;


/**
 * XxptUsers entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class XxptUsers extends BaseObject {


    // Fields    

     private String userid;
     private String username;
     private String userpwd;
     private String deptid;
     private String zt;
     private String lrr;
     private String lrrxm;
     private String lrrbm;
     private String lrrbmkj;
     private Date lrsj;
     private String lrip;
     private String lrmac;


    // Constructors

    /** default constructor */
    public XxptUsers() {
    }

    
    /** full constructor */
    public XxptUsers(String username, String userpwd, String deptid, String zt, String lrr, String lrrxm, String lrrbm, String lrrbmkj, Date lrsj, String lrip, String lrmac) {
        this.username = username;
        this.userpwd = userpwd;
        this.deptid = deptid;
        this.zt = zt;
        this.lrr = lrr;
        this.lrrxm = lrrxm;
        this.lrrbm = lrrbm;
        this.lrrbmkj = lrrbmkj;
        this.lrsj = lrsj;
        this.lrip = lrip;
        this.lrmac = lrmac;
    }

   
    // Property accessors

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return this.userpwd;
    }
    
    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getDeptid() {
        return this.deptid;
    }
    
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getZt() {
        return this.zt;
    }
    
    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getLrr() {
        return this.lrr;
    }
    
    public void setLrr(String lrr) {
        this.lrr = lrr;
    }

    public String getLrrxm() {
        return this.lrrxm;
    }
    
    public void setLrrxm(String lrrxm) {
        this.lrrxm = lrrxm;
    }

    public String getLrrbm() {
        return this.lrrbm;
    }
    
    public void setLrrbm(String lrrbm) {
        this.lrrbm = lrrbm;
    }

    public String getLrrbmkj() {
        return this.lrrbmkj;
    }
    
    public void setLrrbmkj(String lrrbmkj) {
        this.lrrbmkj = lrrbmkj;
    }

    public Date getLrsj() {
        return this.lrsj;
    }
    
    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }

    public String getLrip() {
        return this.lrip;
    }
    
    public void setLrip(String lrip) {
        this.lrip = lrip;
    }

    public String getLrmac() {
        return this.lrmac;
    }
    
    public void setLrmac(String lrmac) {
        this.lrmac = lrmac;
    }
    
}