package com.lw.entity;

public class RecommandEntity {

	public static final int FLAG_QUERY = 0;
	public static final int FLAG_DEAL = 1;
	
	private int flag;
	private int device_id;
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
	
	
}
