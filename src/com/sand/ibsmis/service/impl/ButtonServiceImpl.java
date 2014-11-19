package com.sand.ibsmis.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sand.ibsmis.bean.Button;
import com.sand.ibsmis.bean.Resource;
import com.sand.ibsmis.dao.inf.ButtonMapper;
import com.sand.ibsmis.dao.inf.CompanyMapper;
import com.sand.ibsmis.dao.inf.MenuMapper;
import com.sand.ibsmis.service.inf.ButtonService;
import com.sand.ibsmis.service.inf.MenuService;

@Service("buttonService")
public class ButtonServiceImpl implements ButtonService {
	private static Log logger = LogFactory.getLog(ButtonServiceImpl.class);
	private ButtonMapper buttonMapper;
	@Autowired
	public void setMenuMapper(ButtonMapper buttonMapper) {
		this.buttonMapper = buttonMapper;
	}

	public List<Button> findButtonList() {
		return buttonMapper.findButtons();
	}

	public int queryButtonCount() {
		return buttonMapper.queryButtonCount();
	}

}
