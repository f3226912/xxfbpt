package com.xxfbpt.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.xxfbpt.global.SysConst;




public class SysFilter implements Filter {
	private Set<String> exclude = new HashSet<String>();

	public void destroy() {

	}

	@SuppressWarnings({ "unchecked" })
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String path = request.getContextPath();
		String uri = request.getRequestURI();
		//过滤特殊字符
		Iterator its = request.getParameterMap().values().iterator();
		while(its.hasNext()){
			String[] params = (String[])its.next();
			for(int i = 0; i < params.length; i ++){
				params[i] = params[i].replaceAll("<", "&lt;");
				params[i] = params[i].replaceAll(">", "&gt;");
				//params[i] = params[i].replaceAll("/", "");
				params[i] = params[i].replaceAll("”", "");
				params[i] = params[i].replaceAll("“", "");
//				params[i] = params[i].replaceAll("'", "");
//				params[i] = params[i].replaceAll("=", "");
//				params[i] = params[i].replaceAll(" ", "");
//				params[i] = params[i].replaceAll("&", "");
			}
		}
		session.setAttribute("contextPath", path);
		if(uri.endsWith("/xxfbpt/services/xxfbptservice")){
			chain.doFilter(request, response);
			return;
		}
		if (allow(path, uri, exclude)) {
			chain.doFilter(request, response);
			return;
		}
		if (session.getAttribute(SysConst.USERBEAN) != null) {
			chain.doFilter(request, response);
			return;
		}
		response.getWriter().println("<script>window.top.location.replace('"+path+"/login.jsp')</script>");
	}

	/**
	 * GET提交方式中文编码过滤
	 * 
	 * @param request
	 */
	private void encoding(HttpServletRequest request) {
		Iterator<?> iter = request.getParameterMap().values().iterator();
		while (iter.hasNext()) {
			String[] parames = (String[]) iter.next();
			for (int i = 0; i < parames.length; i++) {
				try {
					parames[i] = new String(parames[i].getBytes("ISO-8859-1"),
							"UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * URL地址过滤
	 * 
	 * @param path
	 * @param uri
	 * @param urls
	 * @return boolean
	 */
	private boolean allow(String path, String uri, Set<String> urls) {
		for (String url : urls) {
			if (url.startsWith("*")) {
				if (uri.endsWith(url.substring(1))) {
					return true;
				}
			} else if (url.endsWith("*")) {
				if (uri.startsWith(url.substring(0, url.length() - 1))) {
					return true;
				}
			} else {
				if (uri.equals(path + url)) {
					return true;
				}
			}
		}
		return false;
	}

	public void init(FilterConfig config) throws ServletException {
		String values = config.getInitParameter("exclude");
		if (!"".equals(values) && values != null) {
			String[] params = values.split("\\|");
			for (String param : params) {
				exclude.add(param);
			}
		}
	}
}
