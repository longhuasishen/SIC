package com.sand.ibsmis.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Spring上下文
 * @author li.sy
 *
 */
public class IBSMisApplicationContextUtil {
	
	public static WebApplicationContext wactx = null;
	
	public static void initApplicationContext(ServletContext sc){
		wactx = WebApplicationContextUtils.getWebApplicationContext(sc);
		System.out.println("加载spring上下文成功..."+wactx);
	}
	
	public static Object getBean(String name){
		return wactx.getBean(name);
	}

}
