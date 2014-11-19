package com.sand.ibsmis.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.sand.ibsmis.handler.AbsAsynHandler;
import com.sand.ibsmis.service.inf.IBSBaseDataService;
import com.sand.ibsmis.util.IBSMisConf;




public class CompanyServiceHandler  extends AbsAsynHandler{
	private static final Log logger = LogFactory.getLog(CompanyServiceHandler.class);
	private IBSBaseDataService service;
	public JSONObject asynHandle(JSONObject queryJson) {
		//返回对象
		JSONObject respJson = new JSONObject();
		
		String channelId=null;
		String amt=null;
		String mid=null;
		String orderId=null;
		String orderDate=null;
		String result=IBSMisConf.IBSMIS_SERVICE_RESPSTR_FAIL;
		try {
			if(queryJson.has("channelId")&&queryJson.has("amt")&&queryJson.has("mid")&&queryJson.has("orderId")&&queryJson.has("orderDate")){
				channelId = queryJson.getString("channelId");
				amt=queryJson.getString("amt");
				mid=queryJson.getString("mid");
				orderId=queryJson.getString("orderId");
				orderDate=queryJson.getString("orderDate");
				if(channelId!=null&&amt!=null&&mid!=null&&orderId!=null&&orderDate!=null){
					result=service.updateMoneyByCompanyId(channelId, amt,mid,orderId,orderDate);
				}
				if(IBSMisConf.IBSMIS_SERVICE_RESPCODE_SUCCESS.equalsIgnoreCase(result)){
					logger.info("更新商户"+channelId+"的金额成功"+amt);
					respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_SUCCESS);
				}else if(IBSMisConf.IBSMIS_SERVICE_RESPCODE_NOMOREMONEY.equalsIgnoreCase(result)){
					logger.info("更新商户"+channelId+"的金额失败,余额不足");
					respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_NOMOREMONEY);
				}else{
					respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_FAIL);
					logger.info("更新商户"+channelId+"的金额失败"+amt);
				}
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_CODE,result);
			}else{
				logger.info("请求参数错误，操作失败");
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_FAIL);
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_CODE, IBSMisConf.IBSMIS_SERVICE_RESPCODE_FAIL);
			}
		} catch (Exception e) {
			try {
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_CODE, IBSMisConf.IBSMIS_SERVICE_RESPCODE_FAIL);
				respJson.put(IBSMisConf.IBSMIS_SERVICE_RESP_MSG,IBSMisConf.IBSMIS_SERVICE_RESPSTR_FAIL);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return respJson;
	}
	
	public IBSBaseDataService getService() {
		return service;
	}
	public void setService(IBSBaseDataService service) {
		this.service = service;
	}
}
