package com.sand.ibsmis.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.service.inf.RoleService;
import com.sand.ibsmis.util.IBSConstant;
import com.sand.ibsmis.util.Page;
import com.sand.ibsmis.util.PermissionUtil;

@Controller
@RequestMapping("/role")
public class RoleController {
	private static final Log logger = LogFactory.getLog(RoleController.class);
	private RoleService roleService;
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	HttpServletRequest request;
	@Resource
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	@RequestMapping(value="/select",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String select(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Role> list=new ArrayList<Role>();
		list=roleService.findRoles();
		JSONArray array=new JSONArray();
		JSONObject json=new JSONObject();
		json.put("role_id", "0");
		json.put("role_name", "请选择");
		json.put("role_comment", "默认项");
		json.put("selected", true);
		array.put(json);
		if(list!=null&&list.size()>0){
			for (Role role : list) {
				JSONObject object=new JSONObject();
				object.put("role_id", role.getRole_id().toString());
				object.put("role_name", role.getRole_name());
				object.put("role_comment", role.getRole_comment());
				array.put(object);
			}
		}
		return array.toString();
	}
	@RequestMapping(value="/list",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String list(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Role> list=new ArrayList<Role>();
		list=roleService.findRoles();
		int roleCount=roleService.queryRoleCount();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", roleCount);
		map.put("rows", list);
		ObjectMapper objectMapper=new ObjectMapper();
		String json = "";  
        try  
        {  
            json = objectMapper.writeValueAsString(map);  
            System.out.println(json);  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
        return json;
	}
	@RequestMapping(value="/save",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String saveRole(@ModelAttribute("role") Role role) {
		String result="";
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("roleManage:ADD");
		try {
			if (isPermitted) {
				result=roleService.insertRole(role);
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
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		logger.info(result);
		return result;
	}
}
