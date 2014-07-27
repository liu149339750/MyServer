package com.lw.entity;

public class ExchangeEntity {
	
	private int deviceId;
	private int spendPoint;
	private String number;
	private int totalPoints;
	private String money;
	
	private String localPhoneNumber;
	private String uuid;
	private String message;
	
	private String time;
	private int pay_id;
	
	
	public int getPay_id() {
		return pay_id;
	}
	public void setPay_id(int payId) {
		pay_id = payId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocalPhoneNumber() {
		return localPhoneNumber;
	}
	public void setLocalPhoneNumber(String localPhoneNumber) {
		this.localPhoneNumber = localPhoneNumber;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getSpendPoint() {
		return spendPoint;
	}
	public void setSpendPoint(int spendPoint) {
		this.spendPoint = spendPoint;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
}
