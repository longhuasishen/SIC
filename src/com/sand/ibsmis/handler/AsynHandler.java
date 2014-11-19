package com.sand.ibsmis.handler;


import java.util.concurrent.Callable;

import org.json.JSONObject;

/**
 * @author Nano
 * 
 * 异步通知接收处理接口
 */
public interface AsynHandler extends Callable<JSONObject>{

	/**
	 * @param reqJsonObject 异步请求的数据
	 * 
	 * @return 执行结果
	 */

	public JSONObject asynHandle(JSONObject reqJsonObject);
	
}

