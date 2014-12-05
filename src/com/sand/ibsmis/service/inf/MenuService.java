package com.sand.ibsmis.service.inf;

import java.util.List;

import com.sand.ibsmis.bean.Resource;

public interface MenuService {
	public List<Resource> findMenus();
	public List<Resource> findMenusByRoleId(String roleId);
	public int queryMenCount();
	public int saveMenu(Resource resource);
	public int updateMenu(Resource resource);
	public int deleteMenu(String menucode);
	public Resource getMenuByCode(String menucode);
}
