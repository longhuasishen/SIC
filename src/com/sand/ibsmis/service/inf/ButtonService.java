package com.sand.ibsmis.service.inf;

import java.util.List;

import com.sand.ibsmis.bean.Button;

public interface ButtonService {
	public List<Button> findButtonList();
	public int queryButtonCount();
}
