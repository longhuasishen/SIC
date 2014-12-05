package com.sand.ibsmis.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.dao.inf.RoleMapper;
import com.sand.ibsmis.service.inf.RolePriService;
import com.sand.ibsmis.service.inf.RoleService;

@Service("rolePriService")
public class RolePriServiceImpl implements RolePriService {
	private static Log logger = LogFactory.getLog(RolePriServiceImpl.class);
	private RoleMapper roleMapper;
	@Autowired
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}
	
	public List<Role> findRoles() {
		return roleMapper.findRoles();
	}
	
	public int queryRoleCount(){
		return roleMapper.queryRoleCount();
	}

	public String insertRole(Role role) {
		
		return null;
	}
}
