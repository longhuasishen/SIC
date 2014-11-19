package com.sand.ibsmis.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sand.ibsmis.action.UserController;

import sun.util.logging.resources.logging;

public class AuthInterceptor implements HandlerInterceptor{
	private static final Log logger = LogFactory.getLog(AuthInterceptor.class);
	private List<String> excludedUrls;  
	   
	public void setExcludedUrls(List<String> excludedUrls) {  
	    this.excludedUrls = excludedUrls;  
	}  
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse response, Object handler, Exception exception)throws Exception {
		
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView mv) throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			boolean r=false;
			AuthPassort authPassort=((HandlerMethod) handler).getMethodAnnotation(AuthPassort.class);
			//没有声明需要权限,或者声明不验证权限
            if(authPassort == null || authPassort.validate() == false){
            	return true;
            }else{                
	            //在这里实现自己的权限验证逻辑
            	String username="";
            	HttpSession session=request.getSession();
            	if(session.getAttribute("username")!=null){
            		username=session.getAttribute("username").toString();
            		logger.info("requestUri=:"+request.getRequestURI()+",username=:"+username);
            	}
            	if(!"".equalsIgnoreCase(username)){
            		if("GET".equalsIgnoreCase(request.getMethod()) && !"/SIC/index".equalsIgnoreCase(request.getRequestURI())){
            			response.sendRedirect("index");
            		}
            		r=true;
            	}else{
            		r=false;
            	}
	            if(r){//如果验证成功返回true（这里直接写false来模拟验证失败的处理）
	                return true;
	            }else//如果验证失败
	            {
	                //返回到登录界面
	                response.sendRedirect("login");
	                return false;
	            }       
	        }
	    }else
	        return true; 
		}
	
}
