package com.lw.entity;

public class RecommandDeviceEntity {

	private String device;	
	private String version;
	private int point;
	private int recommand_id;
	
	
	
	public int getRecommand_id() {
		return recommand_id;
	}
	public void setRecommand_id(int recommandId) {
		recommand_id = recommandId;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	
}
