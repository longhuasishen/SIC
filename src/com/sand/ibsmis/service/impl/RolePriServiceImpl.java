package com.sand.ibsmis.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sand.ibsmis.bean.Button;
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.dao.inf.ButtonMapper;
import com.sand.ibsmis.dao.inf.RoleMapper;
import com.sand.ibsmis.service.inf.RolePriService;
import com.sand.ibsmis.service.inf.RoleService;

@Service("rolePriService")
public class RolePriServiceImpl implements RolePriService {
	private static Log logger = LogFactory.getLog(RolePriServiceImpl.class);
	private ButtonMapper buttonMapper;
	@Autowired
	public void setButtonMapper(ButtonMapper buttonMapper) {
		this.buttonMapper = buttonMapper;
	}
	public ButtonMapper getButtonMapper() {
		return buttonMapper;
	}
	
	public JSONArray findButtonByMenuRole(String roleId, String menuId) {
		List<Button> checkList=buttonMapper.findButtonByMenuRole(roleId,menuId);
		List<Button> allList=buttonMapper.findButtonByMenu(menuId);
		JSONArray array=new JSONArray();
		try {
			for (Button button : allList) {
				JSONObject object = new JSONObject(button);
				for (Button temp : checkList) {
					if (button.getButtonCode().equalsIgnoreCase(temp.getButtonCode())) {
						object.put("checked", true);
						break;
					}
				}
				array.put(object);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array;
	}
}
