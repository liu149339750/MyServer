package com.lw.entity;

public class ResultEntity {

	public final static int RESULT_SUCESS = 0;
	public final static int RESULT_FAIL   = 1;
	
	private int id;
	private int result;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	
}
