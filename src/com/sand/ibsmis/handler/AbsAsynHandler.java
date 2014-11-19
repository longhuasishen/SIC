package com.sand.ibsmis.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

/**
 * @author Nano
 * 
 * 业务处理抽象实现
 */
public abstract class AbsAsynHandler implements AsynHandler {

	private static final Log logger = LogFactory.getLog(AbsAsynHandler.class);
	
	protected JSONObject received;

	public JSONObject getReceived() {
		return received;
	}
	
	public void setReceived(JSONObject received) {
		this.received = received;
	}


	
	public JSONObject call() throws Exception {
		try{
			return this.asynHandle(received);
		}catch(Exception e){
			logger.error("业务异步处理过程失败,原因如下：" + e.getMessage());
			throw e;
		}
	}
	
	

}
