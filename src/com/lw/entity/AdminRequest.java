package com.lw.entity;

public class AdminRequest {

	public static final int FLAG_QUERY_UNPAY = 1;
	public static final int FLAG_QUERY_PAY_BY_DEVICE = 2;
	public static final int FLAG_QUERY_ORFER_BY_DEVICE = 3;
	public static final int FLAG_QUERY_DEAL_BY_DEVICE = 4;
//	public static final int FLAG_QUERY_BY_SQL = 5;
	public static final int FLAG_QUERY_IS_CHEAT = 5;
	
	private int flag;
	private int device_id;
	private String number ;
	private String sql;
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
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
	
}
