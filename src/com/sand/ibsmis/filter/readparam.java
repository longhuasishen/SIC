package com.sand.ibsmis.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sand.ibsmis.dbutil.DBConnectionManager;

public class readparam extends HttpServlet implements Filter {
	private static final Log logger = LogFactory.getLog(readparam.class);
	private static final long serialVersionUID = 1L;
	private String sessionKey = null;
	private String redirectURL = null;
	private List notCheckURLList = new ArrayList();

	public void destroy() {
		
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		if (sessionKey == null) {
			filterChain.doFilter(request, response);
			return;
		}
		logger.info(session.getAttribute(sessionKey));
		if ((!checkRequestURIIntNotFilterList(request))//用户没登录则一律请求到登录页
				&& session.getAttribute(sessionKey) == null) {
			response.sendRedirect(request.getContextPath() + redirectURL);
			return;
		}
		String uri = request.getServletPath()
		+ (request.getPathInfo() == null ? "" : request.getPathInfo());
		if(session.getAttribute(sessionKey) != null && uri.equalsIgnoreCase("/login.jsp")){
			response.sendRedirect(request.getContextPath() + "/index.jsp");//用户已登录则新开登录窗口的时候直接跳转到主页
		}else{
			if(notCheckURLList.contains(uri)){
				filterChain.doFilter(servletRequest, servletResponse);
			}else{
				if(uri.endsWith("action")&&request.getMethod().equalsIgnoreCase("GET")){
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				List<String> identity=(List<String>)request.getSession().getAttribute("url");
				if(identity==null||(!identity.contains(uri.substring(1,uri.length()))&&uri.endsWith("jsp")&&!uri.endsWith("index.jsp"))){
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}else{
					filterChain.doFilter(servletRequest, servletResponse);
				}
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		redirectURL = filterConfig.getInitParameter("redirectURL");
		sessionKey = filterConfig.getInitParameter("checkSessionKey");
		String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
		if (notCheckURLListStr != null) {
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
			notCheckURLList.clear();
			while (st.hasMoreTokens()) {
				notCheckURLList.add(st.nextToken());
			}
		}
	}

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		String uri = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo());
		return notCheckURLList.contains(uri);
	}
}
