package com.sand.ibsmis.service.inf;

import java.util.List;

import org.json.JSONArray;

import com.sand.ibsmis.bean.Button;

public interface RolePriService {
	
	JSONArray findButtonByMenuRole(String roleId, String menuId);
	
}
