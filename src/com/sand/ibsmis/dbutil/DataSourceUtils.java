package com.sand.ibsmis.dbutil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

import com.sand.ibsmis.util.IBSMisConf;


/**
 * 用户中心AOP拦截器 在调用DAO方法之前注入DataSource类型
 * @author he.y
 *
 */
public class DataSourceUtils{
	
	private static Log logger = LogFactory.getLog(DataSourceUtils.class);
	
	public void setDataSourtceType(JoinPoint jointPoint){
		String methodName=jointPoint.getSignature().getDeclaringTypeName();
		if(methodName.toUpperCase().indexOf(IBSMisConf.IBSMIS_DATASOURCE) > 0){
			DataSourceContextHolder.setDataSourceType(DataSourceType.IBSBASE);
			logger.info("[.ibsmis.] [动态加载数据源--->] [IBS]");
		}
	}
}
