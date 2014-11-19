package com.sand.ibsmis.bean;

import java.util.Date;

public class Company {
	private String companyId;
	private String companyName;
	private String channelId;
	private String insertTime;
	private String updateUser;
	private String updateTime;
	private double beforeAmt;
	private double afterAmt;
	private double currAmt;
	private double warnAmt;
	private String status;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public double getBeforeAmt() {
		return beforeAmt;
	}
	public void setBeforeAmt(double beforeAmt) {
		this.beforeAmt = beforeAmt;
	}
	public double getAfterAmt() {
		return afterAmt;
	}
	public void setAfterAmt(double afterAmt) {
		this.afterAmt = afterAmt;
	}
	public double getCurrAmt() {
		return currAmt;
	}
	public void setCurrAmt(double currAmt) {
		this.currAmt = currAmt;
	}
	public double getWarnAmt() {
		return warnAmt;
	}
	public void setWarnAmt(double warnAmt) {
		this.warnAmt = warnAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
