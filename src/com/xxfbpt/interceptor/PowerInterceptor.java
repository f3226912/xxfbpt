package com.xxfbpt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xxfbpt.global.SysConst;

public class PowerInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpSession session=request.getSession();
		String uri=request.getRequestURI();
		String path=request.getContextPath();
		String action=uri.substring(uri.lastIndexOf("/")+1);
		if(SysConst.interceptorList.contains(action)){
			return invocation.invoke();
		}else{
			if(session.getAttribute(SysConst.USERBEAN)==null){
				response.getWriter().println("<script>window.top.location.replace('"+path+"/login.jsp')</script>");
				return null;
			}
			return invocation.invoke();
		}
	}
}
