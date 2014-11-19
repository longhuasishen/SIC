package com.sand.ibsmis.dbutil;

@SuppressWarnings("unchecked")
public class DataSourceContextHolder {
	
	private static final ThreadLocal sourceHandler = new ThreadLocal();
	
	public static DataSourceType getDataSourceType(){
		return (DataSourceType) sourceHandler.get();
	}
	
	public static void setDataSourceType(DataSourceType source){
		sourceHandler.set(source);
	}
	
	public static void clearDataSourceType(){
		sourceHandler.remove();
	}

}
