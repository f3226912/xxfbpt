package com.xxfbpt.ssh.action.wfjb;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.xxfbpt.global.SysConst;
import com.xxfbpt.ssh.action.BaseAction;
import com.xxfbpt.ssh.hbm.XxptInterfaceInfo;
import com.xxfbpt.ssh.hbm.XxptInterfaceUserinfo;
import com.xxfbpt.ssh.hbm.XxptUsers;
import com.xxfbpt.ssh.service.wfjb.IXxptUserService;
import com.xxfbpt.util.StringUtil;

public class XxptUserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3974718677927297893L;
	
	private static final Logger log = Logger.getLogger(XxptUserAction.class);
	private IXxptUserService xxptUserService;
	private int currentpage = 1;
	private XxptInterfaceUserinfo interfaceUserinfo;
	private XxptUsers user;
	
	/**
	 * 用户登录
	 * @return 0-登录成功  1-用户不存在  2-密码错误  3-验证码错误  4-系统异常
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String login() throws Exception{
		log.info("XxptUserAction method login.....");
		PrintWriter out = null;
		try {
			HttpSession session = request.getSession();
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String rand = (String) session.getAttribute("rand");
			String code = request.getParameter("ccode");
			if(rand.equals(code)){
				List<XxptUsers> userList = this.xxptUserService.getXxptUser(request);
				if(userList != null && userList.size() > 0){
					XxptUsers users = userList.get(0);
					String password = request.getParameter("password");
					if(password.equals(users.getUserpwd())){
						session.setAttribute(SysConst.USERBEAN, users);
						//查询菜单权限
						List nodeList = this.xxptUserService.getPower(users.getUserid());
						request.getSession().setAttribute("usernode", nodeList);
						out.println("0");
					}else{
						out.println("2");
					}
				}else{
					out.println("1");
				}
			}else{
				out.println("3");
			}
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 */
	public String updatePassword() throws Exception{
		PrintWriter out = null;
		String oldpwd = request.getParameter("oldpassword");
		String password = request.getParameter("password");
		XxptUsers user = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
		String usercode = user.getUserid();
		try {
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			if(!oldpwd.equals(user.getUserpwd())){
				out.println("3");
			}else if(password.equals(user.getUserpwd())){
				out.println("2");
		    }else{
				int jieguo = this.xxptUserService.updatePwssword(usercode, password, request);
				out.println(jieguo);
			}
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	/**
	 * 登录退出
	 * @return
	 * @throws Exception
	 */
	public String loginOut() throws Exception{
		request.getSession().removeAttribute(SysConst.USERBEAN);
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return null;
	}
	
	/**
	 * 授信单位查询
	 * @return
	 * @throws Exception
	 */
	public String getInterfaceUserList() throws Exception{
		log.info("XxptUserAction method getInterfaceUserList.....");
		this.xxptUserService.getInterfaceUserList(request, currentpage, "select");
		return "interfaceUserlist";
	}
	
	/**
	 * 初始化编辑授信单位
	 * @return
	 * @throws Exception
	 */
	public String getInterfaceUserInfo() throws Exception{
		log.info("XxptUserAction method getInterfaceUserInfo.....");
		String userid = request.getParameter("userid");
		String cztype = request.getParameter("cztype");
		if(!StringUtil.isNull(userid)){
			XxptInterfaceUserinfo interfaceUserinfo = this.xxptUserService.getInterfaceUserInfoByuserId(request, userid);
			request.setAttribute("interfaceUserinfo", interfaceUserinfo);
		}
		if("add".equals(cztype)){
			request.setAttribute("editType", "添加");
		}else if("update".equals(cztype)){
			request.setAttribute("editType", "修改");
		}else{
			request.setAttribute("editType", "查看");
		}
		List<XxptInterfaceInfo> interfaceList = this.xxptUserService.getInterfaceInfoList(request);
		request.setAttribute("interfaceList", interfaceList);
		return "initInterfaceUser";
	}
	
	/**
	 * 验证授信单位是否存在
	 * @return
	 * @throws Exception
	 */
	public String yanzInterfaceUserinfo() throws Exception{
		log.info("XxptUserAction method yanzInterfaceUserinfo.....");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String userid = request.getParameter("userid");
			List<XxptInterfaceUserinfo> list = this.xxptUserService.getInterfaceUserInfo(request, userid);
			if(list != null && list.size() > 0){
				out.println("1");
			}else{
				out.println("0");
			}
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	/**
	 * 编辑授信单位
	 * @return
	 * @throws Exception
	 */
	public String editInterfaceUserinfo() throws Exception{
		log.info("XxptUserAction method editInterfaceUserinfo.....");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String jkids = request.getParameter("jkids");
			this.xxptUserService.editInterfaceUserinfo(request, interfaceUserinfo, jkids);
			out.println("1");
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	/**
	 * 平台用户分页查询
	 * @return
	 * @throws Exception
	 */
	public String getPtUserList() throws Exception{
		log.info("XxptUserAction method getPtUserList.....");
		this.xxptUserService.getPtUserList(request, currentpage, "select");
		return "ptUserList";
	}
	
	/**
	 * 初始化编辑平台用户
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String initEditPtUser() throws Exception{
		log.info("XxptUserAction method initEditPtUser.....");
		String userid = request.getParameter("userid");
		String cztype = request.getParameter("cztype");
		if(!StringUtil.isNull(userid)){
			XxptUsers user = this.xxptUserService.getXxptUserInfo(request, userid);
			request.setAttribute("user", user);
		}
		if("add".equals(cztype)){
			request.setAttribute("editType", "添加");
		}else if("update".equals(cztype)){
			request.setAttribute("editType", "修改");
		}else{
			request.setAttribute("editType", "查看");
		}
		List menuList = this.xxptUserService.getSysMdlList(request, "");
		List deptList = this.xxptUserService.getXxptDeptList(request, "");
		request.setAttribute("menuList", menuList);
		request.setAttribute("deptList", deptList);
		return "initPtUser";
	}
	
	/**
	 * 编辑平台用户信息
	 * @return
	 * @throws Exception
	 */
	public String editPtUserInfo() throws Exception{
		log.info("XxptUserAction method editPtUserInfo.....");
		PrintWriter out = null;
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		out = response.getWriter();
		if(user != null){
			try {
				String jkids = request.getParameter("jkids");
				this.xxptUserService.editPtUserinfo(request, user, jkids);
				out.println("1");
			} catch (Exception e) {
				String exceptionstr = "异常信息:";
				e.printStackTrace();
				log.error(e);
				StackTraceElement stackTraceElement = e.getStackTrace()[0];
				String estr = e.getMessage();
				if(estr != null){
					estr = estr.replaceAll("\r", "");
					estr = estr.replaceAll("\n", "");
					estr = estr.replaceAll("\t", "");
					estr = estr.replaceAll("\f", "");
					estr = estr.replaceAll("\b", "");
					exceptionstr += estr + "</br>文件名:"
							+ stackTraceElement.getFileName() + "</br>行数:"
							+ stackTraceElement.getLineNumber() + "</br>方法名:"
							+ stackTraceElement.getMethodName();
				}else{
					exceptionstr += " 获取连接异常或提交信息为空!";
				}
				out.println(exceptionstr);
			}finally{
				if(out != null){
					out.flush();
					out.close();
				}
			}
		}else{
			out.println("-1");
			if(out != null){
				out.flush();
				out.close();
			}
		}
		return null;
	}
	
	/**
	 * 部门信息分页查询
	 * @return
	 * @throws Exception
	 */
	public String getDeptList() throws Exception{
		log.info("XxptUserAction method getDeptList.....");
		this.xxptUserService.getDeptList(request, currentpage, "select");
		return "deptList";
	}
	
	/**
	 * 初始化部门资源授权
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String initDeptShouquan() throws Exception{
		log.info("XxptUserAction method initDeptShouquan.....");
		//所有资源权限
		List resourceList = this.xxptUserService.getXxptResourceInfoList(request);
		//当前部门权限
		List deptSorceList = this.xxptUserService.getDeptResourceList(request);
		if(deptSorceList != null && deptSorceList.size() > 0){
			String jkids = "";
			for(int i = 0; i < deptSorceList.size(); i++){
				Object[] objs = (Object[])deptSorceList.get(i);
				jkids = jkids+objs[2]+",";
			}
			request.setAttribute("jkids", jkids.substring(0, jkids.length()-1));
		}
		String deptid = request.getParameter("deptid");
		request.setAttribute("resourceList", resourceList);
		request.setAttribute("deptid", deptid);
		return "deptshouquan";
	}
	
	/**
	 * 授权部门资源
	 * @return
	 * @throws Exception
	 */
	public String editDeptResource() throws Exception{
		log.info("XxptUserAction method editDeptResource.....");
		PrintWriter out = null;
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		out = response.getWriter();
		try {
			this.xxptUserService.editDeptResource(request);
			out.println("1");
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常或提交信息为空!";
			}
			out.println(exceptionstr);
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}
		}
		return null;
	}
	
	public IXxptUserService getXxptUserService() {
		return xxptUserService;
	}
	public void setXxptUserService(IXxptUserService xxptUserService) {
		this.xxptUserService = xxptUserService;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public XxptInterfaceUserinfo getInterfaceUserinfo() {
		return interfaceUserinfo;
	}

	public void setInterfaceUserinfo(XxptInterfaceUserinfo interfaceUserinfo) {
		this.interfaceUserinfo = interfaceUserinfo;
	}

	public XxptUsers getUser() {
		return user;
	}

	public void setUser(XxptUsers user) {
		this.user = user;
	}
	
}
