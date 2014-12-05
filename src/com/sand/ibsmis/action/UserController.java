package com.sand.ibsmis.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.PermissionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.service.inf.IBSBaseDataService;
import com.sand.ibsmis.util.IBSConstant;
import com.sand.ibsmis.util.IBSMisCommUtils;
import com.sand.ibsmis.util.Page;
import com.sand.ibsmis.util.PermissionUtil;
import com.sand.ibsmis.util.StringUtil;

@Controller
//@RequestMapping("/user")
public class UserController{
	private static final Log logger = LogFactory.getLog(UserController.class);
	
	private IBSBaseDataService service;
	@Autowired(required=true)
	public void setService(IBSBaseDataService service) {
		this.service = service;
	}
	HttpServletRequest request;
	@Resource
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	@RequestMapping("/loginAction.do")
	public String loginAction(@RequestParam(value="username")String username,
			@RequestParam(value="password") String password,
			@RequestParam(value="code") String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		boolean r=false;
		r = service.checkLogin(username,password,code);
		if (r) {
			logger.info(username+"登录成功!");
			String loginIP="";
			loginIP=StringUtil.getIp(request);
			logger.info("loginIP=:"+loginIP);
			r=service.updateLoginTime(username,loginIP);
			if(r){
				map.put("success", true);
				map.put("error", username+"登录成功!");
				return "redirect:/index";
			}else{
				map.put("success", false);
				map.put("error", "更新用户登录时间失败");
			}
		} else {
			map.put("success", false);
			map.put("error", "用户名或密码错误");
		}
		JSONObject jsonObject = new JSONObject(map);
		logger.info(jsonObject.toString());
		return "login";
	}
	@RequestMapping(value="/getPrivilegeAction",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getPrivilege(HttpServletRequest request,HttpServletResponse response) {
		JSONObject privlegeJsonObject=null;
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		privlegeJsonObject=service.getPrivilege(session.getAttribute("username").toString());
		logger.info(privlegeJsonObject.toString());
		return privlegeJsonObject.toString();
	}
	//@AuthPassort
	@RequestMapping(value="/user/list",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String user_list(@RequestParam(defaultValue="1")String page,@RequestParam(defaultValue="10")String rows,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		Page pager = new Page();
		String pageSizeDefault="10";
		pageSizeDefault=IBSMisCommUtils.getProperties("pageSize").toString();
		logger.info("pageSizeDefault=:"+pageSizeDefault);
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? pageSizeDefault:rows);  
        //每页的开始记录  第一页为1  第二页为number +1   
        int start = (intPage-1)*number;  
		try {
				pager = service.findUserList(start, number,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pager.getTotalCount());
			if(pager.getResult()==null||pager.getResult().size()<=0){
				map.put("rows", "");
			}else{
				map.put("rows", pager.getResult());
			}
			ObjectMapper objectMapper=new ObjectMapper();
			String json = "";  
	        try  
	        {  
	            json = objectMapper.writeValueAsString(map);  
	            logger.info(json);  
	        }  
	        catch(Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
	        return json; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="/user/save",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String saveUser(@ModelAttribute("user") User user) {
		String result="";
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("userManage:ADD");
		try {
			if (isPermitted) {
				String roleId=request.getParameter("roleid").toString();
				Role role=new Role();
				role.setRole_id(roleId);
				user.setRole(role);
				result=service.insertUser(user);
			} else {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPNOPRIOMSG);
				result = json.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPSERVERERRORMSG);
				result=json.toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		logger.info(result);
		return result;
	}

	@RequestMapping(value="/user/update",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String updateUser(@ModelAttribute("user") User user) {
		String result="";
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("userManage:EDIT");
		try {
			if (isPermitted) {
				String roleId = request.getParameter("roleid").toString();
				Role role = new Role();
				role.setRole_id(roleId);
				user.setRole(role);
				result = service.updateUser(user);
			} else {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPNOPRIOMSG);
				result = json.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPSERVERERRORMSG);
				result=json.toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		logger.info(result);
		return result;
	}
	//@RequiresPermissions此注解目前好像无效，采用硬编码方式PermissionUtil.iePermitted方式判断是否有此操作权限
	@RequiresPermissions("userManage:DEL")
	@RequestMapping(value="/user/delete",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String deleteUser(@RequestParam(value="username") String username) {
		String result="";
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("userManage:DEL");
		try {
			if(isPermitted){
				result=service.deleteUser(username);
			}else{
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPNOPRIOMSG);
				result=json.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPSERVERERRORMSG);
				result=json.toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		
		logger.info(result);
		return result;
	}
	@RequestMapping(value="/user/resetpass",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String resetPass(@RequestParam(value="username") String username) {
		int result=0;
//		result=service.deleteUser(username);
		JSONObject json=new JSONObject();
		try {
			if(result==1){
				json.put(IBSConstant.RESPSUCCESSCODE, true);
				json.put(IBSConstant.RESPSUCCESSMSG, "删除用户信息成功");
			}else{
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, "删除用户信息失败");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		logger.info(json.toString());
		return json.toString();
	}
}
