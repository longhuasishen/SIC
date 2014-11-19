package com.sand.ibsmis.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MultiLoginListener implements HttpSessionListener,HttpSessionAttributeListener,ServletContextListener{
	private static ConcurrentMap<String, HttpSession> onlineUsers=null;
	
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session=event.getSession();
		if(session!=null){
			String username=session.getAttribute("username").toString();
			if(onlineUsers.containsKey(username)){
				onlineUsers.remove(username);
			}
		}
	}

	public void attributeAdded(HttpSessionBindingEvent event) {
		String name=event.getName();
		Object value=event.getValue();
		System.out.println("name=:"+name+",value=:"+value);
		if(name != null && value != null){
			if(!onlineUsers.containsKey(name)){
				HttpSession session=event.getSession();
				onlineUsers.put(value.toString(),session);
			}else{
				 HttpSession session=onlineUsers.remove(value.toString());
			     session.invalidate();
			     onlineUsers.put(value.toString(),event.getSession());
			}
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		String name=event.getName();
		Object value=event.getValue();
		if(name != null && value != null){
			if(onlineUsers.containsKey(name)){
				onlineUsers.remove(value.toString());
			}
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		onlineUsers=null;
	}

	public void contextInitialized(ServletContextEvent sce) {
		onlineUsers=new ConcurrentHashMap<String, HttpSession>();
		System.out.println("MultiLoginListener初始化成功。。。");
	}
	/**
	 *  <h3>检测重复登录</h3> 
	 * @param name
	 * @return
	 */
	public static boolean checkIsMultiLogin(String name){
		if (onlineUsers != null && onlineUsers.containsKey(name)) {  
            return true;  
        } else {  
            return false;  
        }  
	}
	/** 
     * <h3>重置单例登录</h3> 
     * @param name   用户名
     */  
    public static void resetSingleLogin (String name) {  
        if (onlineUsers != null && onlineUsers.containsKey(name)) {  
            onlineUsers.remove(name);  
        }  
    }  
}
