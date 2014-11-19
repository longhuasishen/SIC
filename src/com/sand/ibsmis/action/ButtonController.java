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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sand.ibsmis.bean.Button;
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.service.inf.ButtonService;
import com.sand.ibsmis.service.inf.RoleService;
import com.sand.ibsmis.util.IBSConstant;
import com.sand.ibsmis.util.Page;
import com.sand.ibsmis.util.PermissionUtil;

@Controller
@RequestMapping("/button")
public class ButtonController {
	private static final Log logger = LogFactory.getLog(ButtonController.class);
	private ButtonService buttonService;
	@Autowired
	public void setRoleService(ButtonService buttonService) {
		this.buttonService = buttonService;
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
		return "";
	}
	@RequestMapping(value="/list",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String list(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Button> list=new ArrayList<Button>();
		list=buttonService.findButtonList();
		int buttonCount=buttonService.queryButtonCount();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", buttonCount);
		map.put("rows", list);
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
	}
	@RequestMapping(value="/role/save",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String saveRole(@ModelAttribute("role") Role role) {
		String result="";
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("roleManage:ADD");
		try {
			if (isPermitted) {
//				result=roleService.insertRole(role);
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
