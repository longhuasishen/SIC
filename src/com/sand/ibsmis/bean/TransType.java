package com.sand.ibsmis.bean;

public class TransType {
	private String trans_kind;
	private String trans_type;
	private String type_name;
	private int status;
	public String getTrans_kind() {
		return trans_kind;
	}
	public void setTrans_kind(String transKind) {
		trans_kind = transKind;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String transType) {
		trans_type = transType;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String typeName) {
		type_name = typeName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
