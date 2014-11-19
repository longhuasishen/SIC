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

	public int queryMenCount() {
		
		return 0;
	}

}
