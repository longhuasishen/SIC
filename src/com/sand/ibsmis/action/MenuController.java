package com.sand.ibsmis.action;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Resource;
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.service.inf.CompanyService;
import com.sand.ibsmis.service.inf.MenuService;
import com.sand.ibsmis.util.MenuUtil;

@Controller
@RequestMapping("/menu")
public class MenuController {
	private static final Log logger = LogFactory.getLog(MenuController.class);
	private MenuService menuService;
	@Autowired
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	@RequestMapping(value="/select",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String select(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Resource> list=new ArrayList<Resource>();
		list=menuService.findMenus();
		JSONArray array=new JSONArray();
		JSONObject json=new JSONObject();
		json.put("comp_id", "0");
		json.put("comp_name", "请选择");
		json.put("selected", true);
		array.put(json);
		if(list!=null&&list.size()>0){
			
		}
		return array.toString();
	}
	@RequestMapping(value="/list",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String list(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Resource> list=new ArrayList<Resource>();
		list=menuService.findMenus();
		JSONArray parentArray=new JSONArray();
		parentArray=MenuUtil.getMenuTree(list, "00");
		String json = parentArray.toString();  
		System.out.println(json);  
        return json;
	}
	
}
