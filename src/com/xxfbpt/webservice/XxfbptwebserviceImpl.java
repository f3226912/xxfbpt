package com.xxfbpt.webservice;

import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.XFireServletController;

import com.xxfbpt.ssh.dao.IXxfbptDao;
import com.xxfbpt.ssh.hbm.XxptInterfaceInfo;
import com.xxfbpt.ssh.hbm.XxptInterfaceUserinfo;
import com.xxfbpt.ssh.hbm.XxptWebsericeLog;
import com.xxfbpt.ssh.service.IXxfbptService;
import com.xxfbpt.util.DESCorder;

public class XxfbptwebserviceImpl implements IXxfbptwebservice {

	private static final Logger log = Logger
			.getLogger(XxfbptwebserviceImpl.class);
	private IXxfbptService xxfbptServiceimpl;
	private IXxfbptDao xxfbptDao;
	private long num = 0;

	public String example(String message) throws Exception {
		log.info("send:" + message);
		log.info("request:" + "成功");
		return "成功";
	}

	public String xxptSchuding(String userid, String userpwd, String jkid,
			String srcs) throws Exception {
		log.info("XxfbptwebserviceImpl method xxptSchuding.....");
		long number = add();
		String picStr = "";
		String returnxml = xxptSchudingMethod(userid, userpwd, jkid, srcs,
				picStr, number);
		log.info("【响应报文-" + number + "】:" + returnxml);
		return returnxml;
	}

	@SuppressWarnings("unchecked")
	public String xxptSchudingMethod(String userid, String userpwd,
			String jkid, String srcs, String picStr, long number)
			throws Exception {
		String code = "0000";
		String msg = "成功";
		log.info("【请求报文密文-" + number + "】:" + srcs);
		XxptWebsericeLog websericeLog = new XxptWebsericeLog();
		websericeLog.setJkid(jkid);
		websericeLog.setUserid(userid);
		websericeLog.setUserpwd(userpwd);
		websericeLog.setRequestTime(new Date());
		HttpServletRequest request = XFireServletController.getRequest();
		String ip = request.getRemoteAddr() == null ? "" : request
				.getRemoteAddr();
		websericeLog.setHrefurl(ip);
		websericeLog.setInputmsg(srcs);
		try {
			XxptInterfaceUserinfo userinfo = this.xxfbptServiceimpl
					.getUserInfo(userid, userpwd);
			if (userinfo == null) {
				code = "0002";
				msg = "用户不存在";
				String xml = xmlString(code, msg);
				websericeLog.setResponseTime(new Date());
				websericeLog.setResponseResult(code);
				websericeLog.setResponseOutmsg(xml);
				this.xxfbptServiceimpl.addObject(websericeLog);
				return xml;
			}
			if (!userpwd.equals(userinfo.getPassword())) {
				code = "0003";
				msg = "密码错误";
				String xml = xmlString(code, msg);
				websericeLog.setResponseTime(new Date());
				websericeLog.setResponseResult(code);
				websericeLog.setResponseOutmsg(xml);
				this.xxfbptServiceimpl.addObject(websericeLog);
				return xml;
			}
			List list = this.xxfbptServiceimpl.getUserinterface(userid);
			if (!list.contains(jkid)) {
				code = "0004";
				msg = "该用户没有访问此接口权限";
				String xml = xmlString(code, msg);
				websericeLog.setResponseTime(new Date());
				websericeLog.setResponseResult(code);
				websericeLog.setResponseOutmsg(xml);
				this.xxfbptServiceimpl.addObject(websericeLog);
				return xml;
			}

			// if(!ip.equals(userinfo.getUserip())){
			if (userinfo.getUserip().indexOf(ip) == -1) {
				code = "0007";
				msg = "非授信用户访问IP";
				String xml = xmlString(code, msg);
				websericeLog.setResponseTime(new Date());
				websericeLog.setResponseResult(code);
				websericeLog.setResponseOutmsg(xml);
				this.xxfbptServiceimpl.addObject(websericeLog);
				return xml;
			}
			// String mwsrcs = this.xxfbptDao.getDecrypt(srcs,
			// userinfo.getKeys());
			String mwsrcs = "";
			try {
				mwsrcs = DESCorder.decryptMode(srcs, userinfo.getKeys(),
						"utf-8");
			} catch (Exception e) {
				code = "0005";
				msg = "用户密钥错误";
				String xml = xmlString(code, msg);
				websericeLog.setResponseTime(new Date());
				websericeLog.setResponseResult(code);
				websericeLog.setResponseOutmsg(xml);
				this.xxfbptServiceimpl.addObject(websericeLog);
				return xml;
			}
			log.info("【请求报文明文-" + number + "】:" + mwsrcs);
			
			String mwsrcs_temp = mwsrcs;
			mwsrcs_temp = xxfbptServiceimpl.replaceBase(jkid, mwsrcs_temp);
			websericeLog.setInputmsg(mwsrcs_temp);
			
			XxptInterfaceInfo interfaceInfo = this.xxfbptServiceimpl
					.getInterfaceInfo(jkid);
			if (interfaceInfo == null) {
				code = "0006";
				msg = "此接口不存在";
				String xml = xmlString(code, msg);
				websericeLog.setResponseTime(new Date());
				websericeLog.setResponseResult(code);
				websericeLog.setResponseOutmsg(xml);
				this.xxfbptServiceimpl.addObject(websericeLog);
				return xml;
			}
			String xmlstr = "";
			if ("XXPT".equals(interfaceInfo.getJklb())) {
				// 本系统调用函数
				xmlstr = this.xxfbptServiceimpl.getXxptProc(jkid, mwsrcs,
						picStr);
			} else {
				// 外部系统调用外部webservice
				Client client = new Client(new URL(interfaceInfo.getJkurl()));
				Object[] results = client.invoke(interfaceInfo.getJkm(),
						new Object[] { mwsrcs });
				if (client != null) {
					client.close();
				}
				xmlstr = results[0] + "";
			}
			code = "0000";
			// xmlstr = this.xxfbptDao.getEncrypt(xmlstr, userinfo.getKeys());
			String xmltemp = xmlString(code, xmlstr);
			log.info("【响应报文明文-" + number + "】:" + xmltemp);
			xmlstr = DESCorder.encryptModeToString(xmlstr, userinfo.getKeys());
			String xml = xmlString(code, xmlstr);
			websericeLog.setResponseTime(new Date());
			websericeLog.setResponseResult(code);
			websericeLog.setResponseOutmsg(xmltemp);
			this.xxfbptServiceimpl.addObject(websericeLog);
			return xml;

		} catch (Exception e) {
			e.printStackTrace();
			String exceptionstr = "异常信息:";
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if (estr != null) {
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				estr = estr.replaceAll("<", "&lt;");
				estr = estr.replaceAll(">", "&gt;");
				exceptionstr += estr + "文件名:" + stackTraceElement.getFileName()
						+ "行数:" + stackTraceElement.getLineNumber() + "方法名:"
						+ stackTraceElement.getMethodName();
			} else {
				exceptionstr += " 获取连接异常";
			}
			String xml = xmlString("0001", exceptionstr);
			websericeLog.setResponseTime(new Date());
			websericeLog.setResponseResult(code);
			websericeLog.setResponseOutmsg(xml);
			this.xxfbptServiceimpl.addObject(websericeLog);
			return xml;
		}
	}

	private String xmlString(String code, String msg) {
		StringBuffer buffer = new StringBuffer("<return>");
		buffer.append("<code>" + code + "</code>");
		buffer.append("<msg>" + msg + "</msg>");
		buffer.append("</return>");
		return buffer.toString();
	}

	public synchronized long add() {
		num = num + 1;
		return num;
	}

	public IXxfbptService getXxfbptServiceimpl() {
		return xxfbptServiceimpl;
	}

	public void setXxfbptServiceimpl(IXxfbptService xxfbptServiceimpl) {
		this.xxfbptServiceimpl = xxfbptServiceimpl;
	}

	public IXxfbptDao getXxfbptDao() {
		return xxfbptDao;
	}

	public void setXxfbptDao(IXxfbptDao xxfbptDao) {
		this.xxfbptDao = xxfbptDao;
	}

}