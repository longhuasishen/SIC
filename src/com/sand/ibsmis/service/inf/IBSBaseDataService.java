package com.sand.ibsmis.service.inf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.util.Page;


public interface IBSBaseDataService{
	public boolean checkLogin(String username,String password,String code);
	public boolean updateLoginTime(String username,String loginIp);
	public JSONObject getPrivilege(String username);
	public Page findUserList(int currentPage, int limitNum,Map<String, Object> paramMap);
	/**
	 * 根据用户名获取用户信息
	 */
	public User getUser(String username);
	/**
	 * 根据角色id查找对应的Permission
	 * @param roleId
	 * @return
	 */
	public List<String> findPermissions(String roleId);
	/**
	 * 插入新的用户
	 * @param user
	 * @return
	 */
	public String insertUser(User user);
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public String updateUser(User user);
	/**
	 * 删除用户
	 * @param user
	 * @return
	 * @throws JSONException 
	 */
	public String deleteUser(String username) throws JSONException;
	/**
	 * 根据角色id查找对应的Url
	 * @param roleId
	 * @return
	 */
	public List<String> findUrlList(String roleId);
	/**
	 * 根据roleid角色号查找该角色对应的所有用户
	 * @param roleId
	 * @return
	 */
	public List<User> findUsersByRoleId(String roleId);
}
