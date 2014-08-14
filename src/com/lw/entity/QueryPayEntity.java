package com.lw.entity;

public class QueryPayEntity {

	public static final int FLAG_QUERY_TODAY_PAYS = 1;
	public static final int FLAG_QUERY_MY_PAY = 2;
	
	private int flag;
	private int deviceId;
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	
	
	
}
