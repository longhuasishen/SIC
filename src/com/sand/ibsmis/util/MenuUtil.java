package com.sand.ibsmis.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sand.ibsmis.bean.Resource;

public class MenuUtil {
	public static JSONArray getMenuTree(List<Resource> list,String rootCode) throws JSONException{
		JSONArray parentArray=new JSONArray();
		for(Resource resource : list){
			if(rootCode.equalsIgnoreCase(resource.getParentmenucode())){
				JSONObject object=new JSONObject();
				object.put("menucode", resource.getMenucode());
				object.put("menuname", resource.getMenuname());
				object.put("parentmenucode", resource.getParentmenucode());
				object.put("menuicon", resource.getMenuicon());
				object.put("menuurl", resource.getMenuurl());
				object.put("resdesc", resource.getResdesc());
				object.put("menustate", resource.getState());
				JSONArray childrenArray=new JSONArray();
				childrenArray=getMenuTree(list, resource.getMenucode());
				if(childrenArray!=null&&childrenArray.length()>0){
					object.put("children", childrenArray);
				}
				parentArray.put(object);
			}
		}
		return parentArray;
	}
}