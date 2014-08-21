package com.lw.entity;

public class AdminRequest {

	public static final int FLAG_QUERY_UNPAY = 1;
	public static final int FLAG_QUERY_PAY_BY_DEVICE = 2;
	public static final int FLAG_QUERY_ORFER_BY_DEVICE = 3;
	public static final int FLAG_QUERY_DEAL_BY_DEVICE = 4;
//	public static final int FLAG_QUERY_BY_SQL = 5;
	public static final int FLAG_QUERY_IS_CHEAT = 5;
	public static final int FLAG_DEAL          = 6;
	
	public static final int FLAG_QUERY_COSAT = 7;
	
	public static final int FLAG_CHARGE_UNPAY_PHONE = 8;
	public static final int FLAG_CHARGE_NUMBER = 9;
	
	private int flag;
	private int device_id;
	private String number ;
	private int pay_id;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getPay_id() {
		return pay_id;
	}
	public void setPay_id(int pay_id) {
		this.pay_id = pay_id;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int deviceId) {
		device_id = deviceId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
	
}
