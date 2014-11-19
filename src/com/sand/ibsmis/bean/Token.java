package com.sand.ibsmis.bean;

/**
 * 令牌类
 * @author wkq723
 *
 */
public class Token {
	
	/**
	 * 令牌号
	 */
	private String tokenId;
	
	/**
	 * 关联状态 0未关联 1 已关联
	 */
	private int associate;
	
	/**
	 * 锁定状态 0为锁定 1 已锁定
	 */
	private int lock;
	
	/**
	 * 注册状态 0 未注册 1 已注册
	 */
	private int reg;
	
	/**
	 *注册时间
	 */
	private String regTime;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 失败次数
	 */
	private int failedCount;
	
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public int getAssociate() {
		return associate;
	}

	public void setAssociate(int associate) {
		this.associate = associate;
	}

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
		this.lock = lock;
	}

	public int getReg() {
		return reg;
	}

	public void setReg(int reg) {
		this.reg = reg;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public int getFailedCount() {
		return failedCount;
	}
}
