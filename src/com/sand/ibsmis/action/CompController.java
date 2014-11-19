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
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.service.inf.CompanyService;

@Controller
@RequestMapping("/comp")
public class CompController {
	private static final Log logger = LogFactory.getLog(CompController.class);
	private CompanyService companyService;
	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	@RequestMapping(value="/select",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String select(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Company> list=new ArrayList<Company>();
		list=companyService.findCompanys();
		JSONArray array=new JSONArray();
		JSONObject json=new JSONObject();
		json.put("comp_id", "0");
		json.put("comp_name", "请选择");
		json.put("selected", true);
		array.put(json);
		if(list!=null&&list.size()>0){
			for (Company company : list) {
				JSONObject object=new JSONObject();
				object.put("comp_id", company.getCompanyId().toString());
				object.put("comp_name", company.getCompanyName());
				array.put(object);
			}
		}
		return array.toString();
	}
	@RequestMapping(value="/list",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String list(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setCharacterEncoding("UTF-8");
		List<Company> list=new ArrayList<Company>();
		list=companyService.findCompanys();
		int companyCount=companyService.queryCompanyCount();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", companyCount);
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
	
}
