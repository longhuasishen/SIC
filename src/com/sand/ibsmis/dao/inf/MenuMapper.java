package com.sand.ibsmis.dao.inf;

import java.util.List;

import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Resource;

public interface MenuMapper {
	public Resource getResourceById(String resourceId);  
	public List<Resource> findResources();
	public int queryResourceCount();
}
