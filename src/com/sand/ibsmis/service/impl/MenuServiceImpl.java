package com.sand.ibsmis.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sand.ibsmis.bean.Resource;
import com.sand.ibsmis.dao.inf.CompanyMapper;
import com.sand.ibsmis.dao.inf.MenuMapper;
import com.sand.ibsmis.service.inf.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	private static Log logger = LogFactory.getLog(MenuServiceImpl.class);
	private MenuMapper menuMapper;
	@Autowired
	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}

	public List<Resource> findMenus() {
		
		return menuMapper.findResources();
	}
	public Resource getMenuByCode(String menucode){
		return menuMapper.getResourceById(menucode);
	}
	public int queryMenCount() {
		
		return menuMapper.queryResourceCount();
	}

	public int deleteMenu(String menucode) {
		return menuMapper.deleteMenu(menucode);
	}

	public int saveMenu(Resource resource) {
		resource.setMenustate("0");
		return menuMapper.insertMenu(resource);
	}

	public int updateMenu(Resource resource) {
		resource.setMenustate("0");
		return menuMapper.updateMenu(resource);
	}

	public List<Resource> findMenusByRoleId(String roleId) {
		
		return menuMapper.findResourcesByRoleId(roleId);
	}

}
