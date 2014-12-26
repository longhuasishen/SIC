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
import com.sand.ibsmis.service.inf.CompanyService;
import com.sand.ibsmis.service.inf.MenuService;
import com.sand.ibsmis.util.IBSConstant;
import com.sand.ibsmis.util.MenuUtil;
import com.sand.ibsmis.util.PermissionUtil;

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
		JSONArray outArray=new JSONArray();
		JSONObject json=new JSONObject();
		json.put("id", "00");
		json.put("text", "导航菜单");
		if(list!=null&&list.size()>0){
			for(Resource resource : list){
				if("00".equalsIgnoreCase(resource.getParentmenucode())){
					JSONObject object=new JSONObject();
					object.put("id", resource.getMenucode());
					object.put("text", resource.getMenuname());
					array.put(object);
				}
			}
		}
		json.put("children", array);
		outArray.put(json);
		return outArray.toString();
	}
	@RequestMapping(value="/list",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String list(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Resource> list=new ArrayList<Resource>();
		list=menuService.findMenus();
		JSONArray parentArray=new JSONArray();
		parentArray=MenuUtil.getMenuTreeGrid(list, "00");
		String json = parentArray.toString();  
		System.out.println(json);  
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
