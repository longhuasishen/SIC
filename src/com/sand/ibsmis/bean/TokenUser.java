package com.sand.ibsmis.bean;


public class TokenUser {
	
	/**
	 * 企业编号
	 */
	private String eid;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 关联的令牌
	 */
	private String tokenId; 
	/**
	 * 用户创建时间
	 */
	private String createTime; 
	/**
	 * 令牌注册状态  0未注册  1 已注册
	 */
	private String urent;
	/**
	 * 是否应关联  0未关联 1已关联
	 */
	private String associate;
	/**
	 * 锁定状态 0未锁 1 已锁
	 */
	private String lock;
	/**
	 * 锁定时间
	 */
	private String lockTime;
	/**
	 * 认证失败次数
	 */
	private String failedCount;
	
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		if(mobile == null){
			mobile = "";
		}
		this.mobile = mobile;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		if(tokenId == null){
			tokenId = "";
		}
		this.tokenId = tokenId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		if(createTime == null){
			createTime = "";
		}
		this.createTime = createTime;
	}
	public String getUrent() {
		if("0".equals(urent)){
			urent = "未注册";
		}else if("1".equals(urent)){
			urent = "已注册";
		}
		return urent;
	}
	public void setUrent(String urent) {
		this.urent = urent;
	}
	public void setAssociate(String associate) {
		if("0".equals(associate)){
			associate  = "未关联";
		}else if("1".equals(associate)){
			associate = "已关联";
		}
		this.associate = associate;
	}
	public String getAssociate() {
		return associate;
	}
	public void setFailedCount(String failedCount) {
		this.failedCount = failedCount;
	}
	public String getFailedCount() {
		return failedCount;
	}
	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}
	public String getLockTime() {
		return lockTime;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public String getLock() {
		return lock;
	}  
}