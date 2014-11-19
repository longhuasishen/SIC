package com.sand.ibsmis.bean;


public class TokenCompany {
	/**
	 * 企业编号
	 */
	private String eid;
	/**
	 * 企业名
	 */
	private String companyName;
	/**
	 * 已用令牌数
	 */
	private int tokenQty;
	public int getTokenQty() {
		return tokenQty;
	}
	public void setTokenQty(int tokenQty) {
		this.tokenQty = tokenQty;
	}
	public int getMaxQty() {
		return maxQty;
	}
	public void setMaxQty(int maxQty) {
		this.maxQty = maxQty;
	}
	/**
	 *可用令牌数
	 */
	private int maxQty; 
	/**
	 * 用户创建时间
	 */
	private String createTime; 
	/**
	 * 过期日期
	 */
	private String expireTime;
	/**
	 *密钥
	 */
	private String tokenId;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getEid() {
		return eid;
	}
}
