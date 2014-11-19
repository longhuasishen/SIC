package com.sand.ibsmis.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sand.ibsmis.bean.Token;
import com.sand.ibsmis.bean.TokenCompany;
import com.sand.ibsmis.bean.TokenUser;
import com.sand.ibsmis.dao.impl.AddSoapHeader;
import com.sand.ibsmis.dao.inf.ITokenDao;
import com.sand.ibsmis.service.inf.ITokenService;
import com.sand.ibsmis.util.IBSMisCommUtils;
import com.sand.ibsmis.util.Page;

public class TokenServiceImpl implements ITokenService {
	private static Log logger = LogFactory.getLog(TokenServiceImpl.class);
	private ITokenDao tokenDao;
	public void setTokenDao(ITokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}
	public ITokenDao getTokenDao() {
		return tokenDao;
	}
	public Client getClient(){
		Client client = null;
		 try {
			 JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
			 client = factory.createClient(IBSMisCommUtils.getProperties("method"));
			 return client;
		} catch (Exception e) {
			logger.info("获取client出错:"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 组织发送到 神州服务的参数
	 * @return  
	 * @throws Exception 
	 */
	public String organizeTokenParam(Map<String, Object> map, String method) throws Exception{
		try {
			JSONObject retJson = new JSONObject();
			retJson.put("action", method);
			JSONObject paramJson = new JSONObject();
			for(Map.Entry<String, Object> entry : map.entrySet() ){
				paramJson.put(entry.getKey(), entry.getValue() );
			}
			retJson.put("param", paramJson);
			logger.info("组织发送到方法:"+method+"的参数为:"+retJson.toString());
			return retJson.toString();
		} catch (Exception e) {
			logger.info("组织发送到方法:"+method+"的参数出错"+e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 获取用户列表
	 * @throws Exception 
	 */
	public Page<TokenUser> getUsers(int start, int end, Map<String, Object> queryCondition) throws Exception{
		Page page;
		try {
			page =  tokenDao.getUsers(start, end,queryCondition);
			List<TokenUser> lists = page.getResult();
			if(lists != null && lists.size() > 0){
				Client client = getClient();
				for(TokenUser user : lists){
					Map<String, Object> paraMap=new HashMap<String, Object>();
					if(user.getTokenId()!= null && !"".equals(user.getTokenId())){
						paraMap.put("eid",  user.getEid());
						paraMap.put("uid", user.getUserName());
						client.getOutInterceptors().add(new AddSoapHeader());
						String parmJson = organizeTokenParam(paraMap, "tokenStatus");
						Object[] res	 = client.invoke("process", parmJson); //通过CXF调用神州接口
						logger.info("调用:tokenStatus方法,神州令牌服务返回的结果为:"+res[0]);
						if (res != null && !"".equals(res)) {
							InputStream in = new ByteArrayInputStream(((String) res[0]).getBytes());
							SAXReader reader = new SAXReader();
							Document doc = reader.read(in);
							Element el = doc.getRootElement();
							if("0".equals(el.elementText("result"))){
								Element statusEl = el.element("status");
								user.setLock(statusEl.elementText("lock"));
								user.setLockTime(statusEl.elementText("lockDTM"));
								user.setFailedCount(statusEl.elementText("failedCount"));
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info("获取令牌用户出错"+e.getMessage());
			throw new Exception();
		}
		return page;
	}
	public Page<Token> getTokens(int start, int end, Map<String, Object> queryCondition) throws Exception{
		try {
			return tokenDao.getTokens(start, end,queryCondition);
		} catch (Exception e) {
			throw new Exception();
		}
	}
	/**
	 * 获取用户列表
	 * @throws Exception 
	 */
	public Page<TokenCompany> getTokenCompany(int start, int end, Map<String, Object> queryCondition) throws Exception{
		try {
			return tokenDao.getTokenCompany(start, end,queryCondition);
		} catch (Exception e) {
			throw new Exception();
		}
	}
	/**
	 * 取消令牌关联操作
	 */
	public Map<String, Object> removeAssociation(String eid, String userName) {
		Object[] res = null;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Client client = getClient();
			paraMap.put("eid",  eid);
			paraMap.put("uid", userName);
			client.getOutInterceptors().add(new AddSoapHeader());
			String parmJson = organizeTokenParam(paraMap, "removeAssociation");
			res	 = client.invoke("process", parmJson); //通过CXF调用神州接口
			logger.info("调用:removeAssociation方法,神州令牌服务返回的结果为:"+res[0]);
			if(res != null && !"".equals(res)){
				String status = parseResult((String) res[0]);
				if("0".equals(status)){
					paraMap.put("success", true);
					paraMap.put("errorMsg", "回收令牌成功");
				}else if("1".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "用户不存在");
				}else if("2".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未关联令牌");
				}else{
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未知异常");
				}
			}else{
				paraMap.put("success",false);
				paraMap.put("errorMsg", "未知异常");
			}
		} catch (Exception e) {
			logger.info("调用:removeAssociation方法,神州令牌服务出错:"+e.getMessage());
			paraMap.put("success",false);
			paraMap.put("errorMsg", "未知异常");
		}
		return paraMap;
	}
	/**
	 * {action:'tokenLock',param:{eid:' ',uid:' ',lock:' '}}  lock true 上锁 fasle 解锁
	 * @param eid
	 * @param userName
	 * @return
	 */
	public Map<String, Object> tokenLock(String eid, String userName, Boolean lock) {
		Object[] res = null;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Client client = getClient();
			paraMap.put("eid",  eid);
			paraMap.put("uid", userName);
			paraMap.put("lock", lock);
			client.getOutInterceptors().add(new AddSoapHeader());
			String parmJson = organizeTokenParam(paraMap, "tokenLock");
			res	 = client.invoke("process", parmJson); //通过CXF调用神州接口
			logger.info("调用:tokenLock方法,神州令牌服务返回的结果为:"+res[0]);
			if(res != null && !"".equals(res)){
				String status = parseResult((String) res[0]);
				if("0".equals(status)){
					paraMap.put("success", true);
					paraMap.put("errorMsg", lock?"锁令牌成功":"解令牌成功");
				}else if("1".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "用户不存在");
				}else if("2".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未关联令牌");
				}else{
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未知异常");
				}
			}else{
				paraMap.put("success",false);
				paraMap.put("errorMsg", "未知异常");
			}
		} catch (Exception e) {
			logger.info("调用:tokenLock方法,神州令牌服务出错:"+e.getMessage());
			paraMap.put("success",false);
			paraMap.put("errorMsg", "未知异常");
		}
		return paraMap;
	}
	
	/**
	 * {action:'associate',param:{eid:' ',uid:' ',pwd:' '}}
	 * @param userName
	 * @return
	 */
	public Map<String, Object> associateToken(String eid,String userName) {
		Object[] res = null;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Client client = getClient();
			paraMap.put("eid",  eid);
			paraMap.put("uid", userName);
			client.getOutInterceptors().add(new AddSoapHeader());
			String parmJson = organizeTokenParam(paraMap, "associate");
			res	 = client.invoke("process", parmJson); //通过CXF调用神州接口
			logger.info("调用:associate方法,神州令牌服务返回的结果为:"+res[0]);
			if(res != null && !"".equals(res)){
				String status = parseResult((String) res[0]);
				if("0".equals(status)){
					paraMap.put("success", true);
					paraMap.put("errorMsg", "关联成功");
				}else if("1".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "用户不存在");
				}else if("2".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "用户已关联令牌");
				}else if("3".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "无令牌可用");
				}else{
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未知异常");
				}
			}else{
				paraMap.put("success",false);
				paraMap.put("errorMsg", "未知异常");
			}
		} catch (Exception e) {
			logger.info("调用:removeAssociation方法,神州令牌服务出错:"+e.getMessage());
			paraMap.put("success",false);
			paraMap.put("errorMsg", "未知异常");
		}
		return paraMap;
	}
	
	/**
	 *{action:'changePwd',param:{eid:' ',uid:' ',pwd:' '}}
	 *密码修改
	 * @param userName
	 * @return
	 */
	public Map<String, Object> editPwd(String eid, String userName, String pwd) {
		Object[] res = null;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Client client = getClient();
			paraMap.put("eid",  eid);
			paraMap.put("uid", userName);
			paraMap.put("pwd", pwd);
			client.getOutInterceptors().add(new AddSoapHeader());
			String parmJson = organizeTokenParam(paraMap, "changePwd");
			res	 = client.invoke("process", parmJson); //通过CXF调用神州接口
			logger.info("调用:changePwd方法,神州令牌服务返回的结果为:"+res[0]);
			if(res != null && !"".equals(res)){
				String status = parseResult((String) res[0]);
				if("0".equals(status)){
					paraMap.put("success", true);
					paraMap.put("errorMsg", "修改成功");
				}else if("1".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "用户不存在");
				}else{
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未知异常");
				}
			}else{
				paraMap.put("success",false);
				paraMap.put("errorMsg", "未知异常");
			}
		} catch (Exception e) {
			logger.info("调用:removeAssociation方法,神州令牌服务出错:"+e.getMessage());
			paraMap.put("success",false);
			paraMap.put("errorMsg", "未知异常");
		}
		return paraMap;
	}
	/**
	 * 删除用户
	 */
	public Map<String, Object> delUser(String eid, String userName){
		Object[] res = null;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Client client = getClient();
			paraMap.put("eid",  eid);
			paraMap.put("uid", userName);
			client.getOutInterceptors().add(new AddSoapHeader());
			res	 = client.invoke("process", organizeTokenParam(paraMap, "delUser")); //通过CXF调用神州接口
			logger.info("调用:delUser方法,神州令牌服务返回的结果为:"+res[0]);
			if(res != null && !"".equals(res)){
				String status = parseResult((String) res[0]);
				if("0".equals(status)){
					paraMap.put("success",true);
					paraMap.put("errorMsg", "删除成功");
				}else if("1".equals(status)){
					paraMap.put("success",true);
					paraMap.put("errorMsg", "用户不存在");
				}else{
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未知异常");
				}
			}else{
				paraMap.put("success",false);
				paraMap.put("errorMsg", "未知异常");
			}
		} catch (Exception e) {
			logger.info("调用:removeAssociation方法,神州令牌服务出错:"+e.getMessage());
			paraMap.put("success",false);
			paraMap.put("errorMsg", "未知异常");
		}
		return paraMap;
	}
	/**
	 * 删除企业
	 * @param userName
	 * @return
	 */
	public Map<String, Object> delTokenCompany(String userName){
		Object[] res = null;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Client client = getClient();
			paraMap.put("eid",  userName);
			client.getOutInterceptors().add(new AddSoapHeader());
			res	 = client.invoke("process", organizeTokenParam(paraMap, "delEnterprise")); //通过CXF调用神州接口
			logger.info("调用:delEnterprise方法,神州令牌服务返回的结果为:"+res[0]);
			if(res != null && !"".equals(res)){
				String status = parseResult((String) res[0]);
				if("0".equals(status)){
					paraMap.put("success",true);
					paraMap.put("errorMsg", "删除成功");
				}else if("1".equals(status)){
					paraMap.put("success",true);
					paraMap.put("errorMsg", "企业不存在");
				}else{
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未知异常");
				}
			}else{
				paraMap.put("success",false);
				paraMap.put("errorMsg", "未知异常");
			}
		} catch (Exception e) {
			logger.info("调用:delEnterprise方法,神州令牌服务出错:"+e.getMessage());
			paraMap.put("success",false);
			paraMap.put("errorMsg", "未知异常");
		}
		return paraMap;
	}
	public   String parseResult(String result) {
		try {
			if (result != null && !"".equals(result)) {
				InputStream in = new ByteArrayInputStream(result.getBytes());
				SAXReader reader = new SAXReader();
				Document doc = reader.read(in);
				Element el = doc.getRootElement();
				return el.elementText("result");
			}
		} catch (Exception e) {
			logger.info("解析出错" + e.getMessage());
		}
		return "";
	}
	public static void main(String[] args) {
		System.out.println("0".equals(0));
	}
	/**
	 * {action:'createEnterprise',param:{eid:' ',ename:' ',max:' ',expire:' '}} 
	 */
	public Map<String, Object> saveCompany(String eid, String companyName, String maxQty,
			String expireTime) {
		Object[] res = null;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Client client = getClient();
			paraMap.put("eid",  eid);
			paraMap.put("ename", companyName == null ?"":companyName);
			if(maxQty != null && !"".equals(maxQty)){
				paraMap.put("max", maxQty);
			}
			if(expireTime != null && !"".equals(expireTime)){
				paraMap.put("expire", expireTime);
			}
			client.getOutInterceptors().add(new AddSoapHeader());
			String parmJson = organizeTokenParam(paraMap, "createEnterprise");
			res	 = client.invoke("process", parmJson); //通过CXF调用神州接口
			logger.info("调用:createEnterprise方法,神州令牌服务返回的结果为:"+res[0]);
			if(res != null && !"".equals(res)){
				String status = parseResult((String) res[0]);
				if("0".equals(status)){
					paraMap.put("success", true);
					paraMap.put("errorMsg", "保存成功");
				}else if("1".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "企业编号已存在");
				}else{
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未知异常");
				}
			}else{
				paraMap.put("success",false);
				paraMap.put("errorMsg", "未知异常");
			}
		} catch (Exception e) {
			logger.info("调用:createEnterprise方法,神州令牌服务出错:"+e.getMessage());
			paraMap.put("success",false);
			paraMap.put("errorMsg", "未知异常");
		}
		return paraMap;
	}
	/**
	 * {action:'modifyEnterprise',param:{eid:' ',ename:' ',max:' ',expire:' '}}
	 * 企业修改
	 * @param eid
	 * @param companyName
	 * @param maxQty
	 * @param expireTime
	 * @return
	 */
	public Map<String, Object> updateCompany(String eid, String companyName, String maxQty,
			String expireTime) {
		Object[] res = null;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		try {
			Client client = getClient();
			paraMap.put("eid",  eid);
			paraMap.put("ename", companyName == null ?"":companyName);
			if(maxQty != null && !"".equals(maxQty)){
				paraMap.put("max", maxQty);
			}
			if(expireTime != null && !"".equals(expireTime)){
				paraMap.put("expire", expireTime);
			}
			client.getOutInterceptors().add(new AddSoapHeader());
			String parmJson = organizeTokenParam(paraMap, "modifyEnterprise");
			res	 = client.invoke("process", parmJson); //通过CXF调用神州接口
			logger.info("调用:modifyEnterprise方法,神州令牌服务返回的结果为:"+res[0]);
			if(res != null && !"".equals(res)){
				String status = parseResult((String) res[0]);
				if("0".equals(status)){
					paraMap.put("success", true);
					paraMap.put("errorMsg", "修改成功");
				}else if("1".equals(status)){
					paraMap.put("success",false);
					paraMap.put("errorMsg", "企业编号已存在");
				}else{
					paraMap.put("success",false);
					paraMap.put("errorMsg", "未知异常");
				}
			}else{
				paraMap.put("success",false);
				paraMap.put("errorMsg", "未知异常");
			}
		} catch (Exception e) {
			logger.info("调用:modifyEnterprise方法,神州令牌服务出错:"+e.getMessage());
			paraMap.put("success",false);
			paraMap.put("errorMsg", "未知异常");
		}
		return paraMap;
	}
}
