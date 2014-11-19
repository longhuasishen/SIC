package com.sand.ibsmis.dao.inf;

import java.util.List;

import com.sand.ibsmis.bean.Button;
import com.sand.ibsmis.bean.Resource;

public interface ButtonMapper {
	public Button getButtonById(String buttonId);  
	public List<Button> findButtons();
	public int queryButtonCount();
}