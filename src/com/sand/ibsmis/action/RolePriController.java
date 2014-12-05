package com.sand.ibsmis.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sand.ibsmis.bean.Button;
import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Resource;
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.service.inf.CompanyService;
import com.sand.ibsmis.service.inf.IBSBaseDataService;
import com.sand.ibsmis.service.inf.MenuService;
import com.sand.ibsmis.service.inf.RolePriService;
import com.sand.ibsmis.service.inf.RoleService;
import com.sand.ibsmis.util.IBSConstant;
import com.sand.ibsmis.util.MenuUtil;
import com.sand.ibsmis.util.PermissionUtil;

@Controller
@RequestMapping("/roleprivi")
public class RolePriController {
	private static final Log logger = LogFactory.getLog(RolePriController.class);
	private MenuService menuService;
	@Autowired
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	private RolePriService rolePriService;
	@Autowired
	public void setRolePriService(RolePriService rolePriService) {
		this.rolePriService = rolePriService;
	}
	private RoleService roleService;
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	private IBSBaseDataService userService;
	@Autowired(required=true)
	public void setService(IBSBaseDataService userService) {
		this.userService = userService;
	}
	@RequestMapping(value="/selectRole",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String selectRole(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Role> list=new ArrayList<Role>();
		list=roleService.findRoles();
		JSONArray array=new JSONArray();
		JSONObject json=new JSONObject();
		JSONArray outArray=new JSONArray();
		json.put("id", "00");
		json.put("text", "导航");
		json.put("checked", "true");
		if(list!=null&&list.size()>0){
			for(Role role : list){
				JSONObject object = new JSONObject();
				object.put("id", role.getRole_id());
				object.put("text", role.getRole_name());
				array.put(object);
			}
		}
		json.put("children", array);
		outArray.put(json);
		System.out.println(outArray.toString());
		return outArray.toString();
	}
	@RequestMapping(value="/selectUser",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String selectUser(@RequestParam(value="roleId")String roleId,HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<User> list=new ArrayList<User>();
		list=userService.findUsersByRoleId(roleId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", 0);
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
	@RequestMapping(value="/selectMenu",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String selectMenu(@RequestParam(value="roleId")String roleId,HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Resource> list=new ArrayList<Resource>();
		list=menuService.findMenusByRoleId(roleId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", 0);
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
	public String saveMenu(@ModelAttribute("resource") Resource resource) {
		String result="";
		int i=0;
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("menuManage:ADD");
		try {
			if (isPermitted) {
				i=menuService.saveMenu(resource);
				if(i==1){
					json.put(IBSConstant.RESPSUCCESSCODE, true);
					json.put(IBSConstant.RESPSUCCESSMSG, "保存菜单信息成功");
				}else{
					json.put(IBSConstant.RESPSUCCESSCODE, false);
					json.put(IBSConstant.RESPERRORMSG, "保存菜单信息失败");
				}
			} else {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPNOPRIOMSG);
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
		result = json.toString();
		logger.info(result);
		return result;
	}
	@RequestMapping(value="/edit",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String editMenu(@ModelAttribute("resource") Resource resource,HttpServletRequest request,HttpServletResponse response) {
		String result="";
		int i=0;
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("menuManage:EDIT");
		try {
			if (isPermitted) {
				i=menuService.updateMenu(resource);
				if(i==1){
					json.put(IBSConstant.RESPSUCCESSCODE, true);
					json.put(IBSConstant.RESPSUCCESSMSG, "修改菜单信息成功");
				}else{
					json.put(IBSConstant.RESPSUCCESSCODE, false);
					json.put(IBSConstant.RESPERRORMSG, "修改菜单信息失败");
				}
			} else {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPNOPRIOMSG);
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
		result = json.toString();
		logger.info(result);
		return result;
	}
	@RequestMapping(value="/delete",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String deleteMenu(@RequestParam(value="menucode") String menucode) {
		String result="";
		int i=0;
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("menuManage:DEL");
		try {
			if (isPermitted) {
				
				i=menuService.deleteMenu(menucode);
				if(i==1){
					json.put(IBSConstant.RESPSUCCESSCODE, true);
					json.put(IBSConstant.RESPSUCCESSMSG, "删除菜单信息成功");
				}else{
					json.put(IBSConstant.RESPSUCCESSCODE, false);
					json.put(IBSConstant.RESPERRORMSG, "删除菜单信息失败");
				}
			} else {
				json.put(IBSConstant.RESPSUCCESSCODE, false);
				json.put(IBSConstant.RESPERRORMSG, IBSConstant.RESPNOPRIOMSG);
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
		result = json.toString();
		logger.info(result);
		return result;
	}
}
