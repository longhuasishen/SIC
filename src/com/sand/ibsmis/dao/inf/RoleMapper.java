package com.sand.ibsmis.dao.inf;

import java.util.HashMap;
import java.util.List;

import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.User;

public interface RoleMapper {
	public Role getRoleById(String role_id);  
	public List<Role> findRoles();
	public int queryRoleCount();
}
