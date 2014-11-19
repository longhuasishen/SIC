package com.sand.ibsmis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IBSMisCommUtils {
	
	private static Log logger = LogFactory.getLog(IBSMisCommUtils.class);

	//处理集合
	public static Properties  commPros = new Properties();
	
	public static void initCommProperties(ServletContext webContext){
		InputStream in = null;
		try {
			in = webContext.getResourceAsStream("/WEB-INF/conf/comm.properties");
			commPros.load(in);
			logger.info("加载文件参数文件[comm.properties]成功....");
		}catch (IOException e) {
			logger.error("加载文件参数文件[comm.properties]失败,原因:"+ e.getMessage());
	    } finally{
			if(in  != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static String getProperties(String busiType){
	    String trankt = "";
		if(commPros != null){
			trankt = commPros.getProperty(busiType);
		}
		return trankt;
	}
}
