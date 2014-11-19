package com.sand.ibsmis.bean;

public class TransJnl {
	private String channel_serial;
	private double trans_amt;
	private String trans_time;
	private String order_back;
	private String status;
	public String getChannel_serial() {
		return channel_serial;
	}
	public void setChannel_serial(String channelSerial) {
		channel_serial = channelSerial;
	}
	public double getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(double transAmt) {
		trans_amt = transAmt;
	}
	public String getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(String transTime) {
		trans_time = transTime;
	}
	public String getOrder_back() {
		return order_back;
	}
	public void setOrder_back(String orderBack) {
		order_back = orderBack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
