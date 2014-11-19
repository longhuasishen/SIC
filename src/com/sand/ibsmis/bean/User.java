package com.sand.ibsmis.bean;

public class User {
	private String username;
	private String password;
	private String company_id;
	private String name;
	private String email;
	private String phonenumber;
	private String last_login;
	private String last_ip;
	private String desc;
	private String state;
	private Role role;
	
	
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String companyId) {
		company_id = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getLast_ip() {
		return last_ip;
	}
	public void setLast_ip(String lastIp) {
		last_ip = lastIp;
	}
	public String getLast_login() {
		return last_login;
	}
	public void setLast_login(String lastLogin) {
		last_login = lastLogin;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
