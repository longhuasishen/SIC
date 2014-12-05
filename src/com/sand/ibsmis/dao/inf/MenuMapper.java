package com.sand.ibsmis.dao.inf;

import java.util.List;

import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Resource;

public interface MenuMapper {
	public Resource getResourceById(String resourceId);  
	public List<Resource> findResources();
	public int queryResourceCount();
	public int updateMenu(Resource resource);
	public int insertMenu(Resource resource);
	public int deleteMenu(String menucode);
	public List<Resource> findResourcesByRoleId(String roleId);
}
