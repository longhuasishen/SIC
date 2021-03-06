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
import org.springframework.web.bind.annotation.RequestParam;
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
	public void setButtonService(ButtonService buttonService) {
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
	@RequestMapping(value="/save",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String saveButton(@ModelAttribute("button") Button button) {
		String result="";
		int i=0;
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("buttonManage:ADD");
		try {
			if (isPermitted) {
				i=buttonService.saveButton(button);
				if(i==1){
					json.put(IBSConstant.RESPSUCCESSCODE, true);
					json.put(IBSConstant.RESPSUCCESSMSG, "保存按钮信息成功");
				}else{
					json.put(IBSConstant.RESPSUCCESSCODE, false);
					json.put(IBSConstant.RESPERRORMSG, "保存按钮信息失败");
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
	public String editButton(@ModelAttribute("button") Button button) {
		String result="";
		int i=0;
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("buttonManage:EDIT");
		try {
			if (isPermitted) {
				i=buttonService.updateButton(button);
				if(i==1){
					json.put(IBSConstant.RESPSUCCESSCODE, true);
					json.put(IBSConstant.RESPSUCCESSMSG, "修改按钮信息成功");
				}else{
					json.put(IBSConstant.RESPSUCCESSCODE, false);
					json.put(IBSConstant.RESPERRORMSG, "修改按钮信息失败");
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
	public String deleteButton(@RequestParam(value="buttonCode") String buttonCode) {
		String result="";
		int i=0;
		JSONObject json=new JSONObject();
		boolean isPermitted=PermissionUtil.isPermitted("buttonManage:DEL");
		try {
			if (isPermitted) {
				i=buttonService.deleteButton(buttonCode);
				if(i==1){
					json.put(IBSConstant.RESPSUCCESSCODE, true);
					json.put(IBSConstant.RESPERRORMSG, "删除按钮信息成功");
				}else{
					json.put(IBSConstant.RESPSUCCESSCODE, false);
					json.put(IBSConstant.RESPSUCCESSMSG, "删除按钮信息失败");
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
