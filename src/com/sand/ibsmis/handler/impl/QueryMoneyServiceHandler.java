package com.sand.ibsmis.handler.impl;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.sand.ibsmis.handler.AbsAsynHandler;
import com.sand.ibsmis.service.inf.IBSBaseDataService;
import com.sand.ibsmis.util.IBSMisConf;




public class QueryMoneyServiceHandler  extends AbsAsynHandler{
	private static final Log logger = LogFactory.getLog(QueryMoneyServiceHandler.class);
	private IBSBaseDataService service;
	public JSONObject asynHandle(JSONObject queryJson) {
		//返回对象
		JSONObject respJson = new JSONObject();
		
		String channelId=null;
		HashMap<String, Object> comMap=null;
		try {
			if(queryJson.has("channelId")){
				channelId = queryJson.getString("channelId");
				if(channelId!=null){
					comMap=service.getCompanById(channelId);
				}
				if(comMap!=null&&comMap.size()>0&&comMap.containsKey("leftamt")){
					logger.info("查询商户"+channelId+"的余额成功");
					if(Double.parseDouble(comMap.get("leftamt").toString())>0){
						respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_CODE, IBSMisConf.IBSMIS_SERVICE_RESPCODE_SUCCESS);
						respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_SUCCESS);
					}else{
						respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_CODE, IBSMisConf.IBSMIS_SERVICE_RESPCODE_QUERYNOMOREMONEY);
						respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_NOMOREMONEY);
					}
				}else{
					logger.info("查询商户"+channelId+"的余额失败");
					respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_CODE, IBSMisConf.IBSMIS_SERVICE_RESPCODE_QUERYFAIL);
					respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_FAIL);
				}
			}else{
				logger.info("queryJson not contains key channelId");
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_CODE, IBSMisConf.IBSMIS_SERVICE_RESPCODE_QUERYFAIL);
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_FAIL);
			}
		} catch (Exception e) {
			try {
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_CODE, IBSMisConf.IBSMIS_SERVICE_RESPCODE_QUERYFAIL);
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_FAIL);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		logger.info("返回的请求数据为:"+respJson);
		return respJson;
	}
	
	public IBSBaseDataService getService() {
		return service;
	}
	public void setService(IBSBaseDataService service) {
		this.service = service;
	}
}
