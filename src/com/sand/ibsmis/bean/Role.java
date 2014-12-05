package com.sand.ibsmis.bean;

import java.util.List;

public class Role {
	private String role_id;
	private String role_name;
	private String role_comment;
	private String rstate;
	/*private List<User> userList;
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}*/
	
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String roleId) {
		role_id = roleId;
	}
	public String getRstate() {
		return rstate;
	}
	public void setRstate(String rstate) {
		this.rstate = rstate;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String roleName) {
		role_name = roleName;
	}
	public String getRole_comment() {
		return role_comment;
	}
	public void setRole_comment(String roleComment) {
		role_comment = roleComment;
	}
	
}
