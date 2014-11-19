package com.sand.ibsmis.service.inf;

import java.util.List;

import com.sand.ibsmis.bean.Role;

public interface RoleService {
	public List<Role> findRoles();
	public int queryRoleCount();
	public String insertRole(Role role);
}
