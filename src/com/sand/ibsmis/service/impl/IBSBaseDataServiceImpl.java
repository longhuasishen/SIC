package com.sand.ibsmis.service.impl;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sand.ibsmis.bean.CheckPaper;
import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Resource;
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.Rule;
import com.sand.ibsmis.bean.TransJnl;
import com.sand.ibsmis.bean.TransType;
import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.dao.inf.IBSBaseDataDao;
import com.sand.ibsmis.dao.inf.UserMapper;
import com.sand.ibsmis.service.inf.IBSBaseDataService;
import com.sand.ibsmis.util.ComparatorType;
import com.sand.ibsmis.util.Des3Util;
import com.sand.ibsmis.util.IBSConstant;
import com.sand.ibsmis.util.IBSMisConf;
import com.sand.ibsmis.util.MD5Util;
import com.sand.ibsmis.util.Page;
import com.sand.ibsmis.util.StringUtil;
import com.sun.org.apache.bcel.internal.generic.Type;

@Service("service")
public class IBSBaseDataServiceImpl implements IBSBaseDataService{
	
	private static Log logger = LogFactory.getLog(IBSBaseDataServiceImpl.class);
	HttpServletRequest request;
	private UserMapper userMapper;
	
	@Autowired
	public void setUserDao(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public boolean checkLogin(String username,String password,String code) {
		//subject理解成权限对象。类似user
		Subject subject=SecurityUtils.getSubject();
		Session session=subject.getSession();
		//验证验证码是否正确
		if(!session.getAttribute("ccode").equals(code)){
			request.setAttribute("errMsg", "验证码错误");
			return false;
		}
		//创建用户名和密码的令牌
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		//记录该令牌，如果不记录则类似购物车功能不能使用。
		token.setRememberMe(true);
		logger.info("username=["+username+"]");
		try {
			subject.login(token);
		}catch (UnknownAccountException ex) {	
			request.setAttribute("errMsg", "用户不存在");
			return false;
		} catch (IncorrectCredentialsException ex) {
			request.setAttribute("errMsg", "密码错误");
			return false;
		}catch (LockedAccountException e) {
			request.setAttribute("errMsg", "用户名已被锁住");
			return false;
		}catch (AuthenticationException e) {
			request.setAttribute("errMsg", "服务器忙...");
			return false;
		}
		//验证是否成功登录的方法
		if(subject.isAuthenticated()){
			request.removeAttribute("errMsg");
			session.setAttribute("username", username);
			/*session.setAttribute("rolename", curuser.getRole().getRole_name());
			session.setAttribute("roleid", curuser.getRole().getRole_id());
			session.setAttribute("company", curuser.getUsername());*/
			return true;
		}
		return false;
	}
	public boolean updateLoginTime(String username,String loginIp) {
		String loginTime="1900-01-01 01:01:01";
		boolean r=false;
		int i=-1;
		try {
			loginTime=StringUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
			User curuser=getUser(username);
			curuser.setLast_ip(loginIp);
			curuser.setLast_login(loginTime);
			i=userMapper.updateUser(curuser);
			if(i>0){
				r=true;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}
	public JSONObject getPrivilege(String username) {
		JSONObject privlegeJsonObject=new JSONObject();
		List<HashMap<String, Object>> parentList = null;
		parentList=userMapper.getParentMenu(username);//查询一级菜单列表
		try {
			if(parentList!=null&&!parentList.isEmpty() && parentList.size() > 0){
	        	for (int i = 0; i < parentList.size(); i++) {
	        		HashMap<String, Object> map=parentList.get(i);
	        		if(map!=null&&map.get("parentmenucode").toString().equalsIgnoreCase("00")){
	        				JSONObject outerJson=new JSONObject();
	        				outerJson.put("menucode", map.get("menucode").toString());
	        				outerJson.put("menuname", map.get("menuname").toString());
	        				outerJson.put("icon", map.get("menuicon").toString());
	        				JSONObject innerJson=new JSONObject();
	        				outerJson.put("menus",innerJson);
							privlegeJsonObject.put(map.get("menucode").toString(), outerJson);
	        		}
	        	}
	        	List<HashMap<String, Object>> subList = null;
	        	subList=userMapper.getSubMenu(username);//查询二级菜单列表
	        	if(subList!=null&&subList.size()>0){
		        	for (int i = 0; i < subList.size(); i++) {
		        		HashMap<String, Object> map=subList.get(i);
		        		if(map!=null&&map.size()>0){
		        			JSONObject outerJson=new JSONObject();
		        			outerJson=privlegeJsonObject.getJSONObject(map.get("parentmenucode").toString());	
		        			JSONObject innerJson=new JSONObject();
		        			innerJson=outerJson.getJSONObject("menus");
		        			JSONObject leafJson=new JSONObject();
		        			leafJson.put("menucode", map.get("menucode").toString());
		        			leafJson.put("menuname", map.get("menuname").toString());
		        			leafJson.put("icon", map.get("menuicon").toString());
		        			leafJson.put("menuurl", map.get("menuurl").toString());
		        			innerJson.append( map.get("menucode").toString(), leafJson);
		        			outerJson.put("menus",innerJson);
		        			privlegeJsonObject.put(outerJson.get("menucode").toString(), outerJson);
		        		}
		        	}
	        	}else{
	        		logger.info("查询用户菜单信息getSubMenu为空");
	        	}
	        }else{
	        	logger.info("查询用户菜单信息getParentMenu为空");
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return privlegeJsonObject;
	}

	public Page findUserList(int currentPage, int limitNum,Map<String, Object> paramMap) {
		Page<User> pager = new Page<User>();
		if(paramMap==null){
			paramMap=new HashMap<String, Object>();
		}
		paramMap.put("start", currentPage);
		paramMap.put("pagesize", limitNum);
		List<User> list=userMapper.getAllUser(paramMap);
		int count=userMapper.getUserCount();
		pager.setResult(list);
		pager.setTotalCount(count);
		return pager;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	@Autowired
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public User getUser(String username) {
		User user=new User();
		try {
			user = userMapper.getUser(username);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return user;
	}

	public List<String> findPermissions(String roleId) {
		List<HashMap<String, Object>> permissionList=userMapper.getPermissions(roleId);
		List<String> resultList=new ArrayList<String>();
		for(HashMap<String, Object> map : permissionList){
			resultList.add(map.get("permission").toString());
		}
		return resultList;
	}
	public List<String> findUrlList(String roleId) {
		List<HashMap<String, Object>> permissionList=userMapper.getUrlList(roleId);
		List<String> resultList=new ArrayList<String>();
		for(HashMap<String, Object> map : permissionList){
			resultList.add(map.get("menuurl").toString()+":list");
		}
		return resultList;
	}

	public String insertUser(User user) {
		int result=0;
		if(user.getUstate()!=null&&"on".equalsIgnoreCase(user.getUstate())){
			user.setUstate("0");
		}else{
			user.setUstate("1");
		}
		if(user.getCompany_id()==null||"0".equalsIgnoreCase(user.getCompany_id())){
			user.setCompany_id("");
		}
		if("1".equalsIgnoreCase(user.getRole().getRole_id())){
			user.setCompany_id("");
		}
		JSONObject json=new JSONObject();
		if(userMapper.getUser(user.getUsername())!=null){
			result=2;
		}else{
			Des3Util des3Util=new Des3Util(IBSMisConf.SICKEY);
			user.setPassword(des3Util.getEncString(user.getPassword()));
			result=userMapper.insertUser(user);
		}
		try {
			if(result==1){
				json.put(IBSConstant.RESPSUCCESSCODE, true);
				json.put(IBSConstant.RESPSUCCESSMSG, "保存用户信息成功");
			}else if(result==2){
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPSUCCESSMSG, "用户名已存在");
			}else{
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPSUCCESSMSG, "保存用户信息失败");
			}
		} catch (JSONException e) {
			try {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPSUCCESSMSG, "保存用户信息异常");
			} catch (JSONException e1) {
				logger.info("JSON错误"+e1.getMessage());
			}
			e.printStackTrace();
		}
		return json.toString();
	}
	public String updateUser(User user) {
		int result=0;
		if(user.getUstate()!=null&&"on".equalsIgnoreCase(user.getUstate())){
			user.setUstate("0");
		}else{
			user.setUstate("1");
		}
		Des3Util des3Util=new Des3Util(IBSMisConf.SICKEY);
		user.setPassword(des3Util.getEncString(user.getPassword()));
		if("1".equalsIgnoreCase(user.getRole().getRole_id())){
			user.setCompany_id("");
		}
		result=userMapper.updateUser(user);
		JSONObject json=new JSONObject();
		try {
			if(result==1){
				json.put(IBSConstant.RESPSUCCESSCODE, true);
				json.put(IBSConstant.RESPSUCCESSMSG, "更新用户信息成功");
			}else{
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, "更新用户信息失败");
			}
		} catch (JSONException e) {
			try {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, "更新用户信息异常");
			} catch (JSONException e1) {
				logger.info("JSON错误"+e1.getMessage());
			}
			e.printStackTrace();
		}
		return json.toString();
	}
	public String deleteUser(String username) throws JSONException {
		int result=0;
		result=userMapper.deleteUser(username);
		JSONObject json=new JSONObject();
		if(result==1){
			json.put(IBSConstant.RESPSUCCESSCODE, true);
			json.put(IBSConstant.RESPSUCCESSMSG, "删除用户信息成功");
		}else{
			json.put(IBSConstant.RESPSUCCESSCODE, false);
			json.put(IBSConstant.RESPERRORMSG, "删除用户信息失败");
		}
		return json.toString();
	}
	public List<User> findUsersByRoleId(String roleId) {
		
		return userMapper.findUsersByRoleId(roleId);
	}
}
