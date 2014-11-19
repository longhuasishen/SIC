package com.sand.ibsmis.dao.inf;

import java.util.HashMap;
import java.util.List;

import com.sand.ibsmis.bean.User;

public interface UserMapper {
	public User getUser(String username);  
	public List<User> getAllUser(int currentPage, int limitNum);  
	public int getUserCount();
    public int insertUser(User user);  
    public int updateUser(User user);  
    public int deleteUser(String username); 
    public List<HashMap<String, Object>> getParentMenu(String username);
    public List<HashMap<String, Object>> getSubMenu(String username);
    public List<HashMap<String, Object>> getPermissions(String roleId);
	public List<HashMap<String, Object>> getUrlList(String roleId);
}
