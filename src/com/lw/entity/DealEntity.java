package com.lw.entity;

public class DealEntity {

	private int pay_id;
	private int device_id;
	private String message;
	private String time;
	private int feedback;
	
	public int getPay_id() {
		return pay_id;
	}
	public void setPay_id(int payId) {
		pay_id = payId;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int deviceId) {
		device_id = deviceId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getFeedback() {
		return feedback;
	}
	public void setFeedback(int feedback) {
		this.feedback = feedback;
	}
	
	
	
}
