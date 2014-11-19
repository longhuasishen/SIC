package com.sand.ibsmis.service.inf;

import java.util.List;

import com.sand.ibsmis.bean.Resource;

public interface MenuService {
	public List<Resource> findMenus();
	public int queryMenCount();
}
