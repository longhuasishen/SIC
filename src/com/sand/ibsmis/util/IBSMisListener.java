package com.sand.ibsmis.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.sand.ibsmis.util.IBSMisApplicationContextUtil;
import com.sand.ibsmis.util.IBSMisCommUtils;

/**
 * 清算监听器
 * @author li.sy
 *
 */
public class IBSMisListener extends ContextLoaderListener{
	
	private static Log logger = LogFactory.getLog(IBSMisListener.class);

	
	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext context = event.getServletContext();
		super.contextInitialized(event);
		//加载spring上下文
		IBSMisApplicationContextUtil.initApplicationContext(context);
		logger.info("初始化spring上下文结束...");

		IBSMisCommUtils.initCommProperties(context);
		
	}
	

}
