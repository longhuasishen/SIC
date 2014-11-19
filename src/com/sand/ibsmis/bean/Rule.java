package com.sand.ibsmis.bean;

public class Rule {
	 private String company_id;
	 private String company_name;
	 private String tran_type;
	 private String type_name;
	 private String rule_id;
	 private String rule_name;
	 private String typeState;
	 public String getTypeState() {
		return typeState;
	}
	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String companyId) {
		company_id = companyId;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String companyName) {
		company_name = companyName;
	}
	public String getTran_type() {
		return tran_type;
	}
	public void setTran_type(String tranType) {
		tran_type = tranType;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String typeName) {
		type_name = typeName;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String ruleId) {
		rule_id = ruleId;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String ruleName) {
		rule_name = ruleName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getClearDate() {
		return clearDate;
	}
	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}
	private String startDate;
	 private String endDate;
	 private String clearDate;
	
}
