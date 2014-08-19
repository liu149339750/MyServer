package com.lw.sududa.deal;

public class StatusCode {

	
	public static final int UNKOWN_NUMBER = -2;
	public static final int CHARGER_FAIL = -3;
	public static final int ERROR = -4;
	public static final int UNKOWN_TYPE = -5;
	public static final int UNSURPPORT  = -6;
	public static final int SUCESS       = 1;
	
	
	public static String getCodeString(int code){
		String result = null;
		switch (code) {
		case SUCESS:
			result = "成功";
			break;
		case UNKOWN_NUMBER:
			result = "号码不详";
			break;
		case CHARGER_FAIL:
			result = "充值失败";
			break;
		case ERROR:
			result = "异常情况";
			break;
		case UNKOWN_TYPE:
			result = "号码运营商未知";
			break;
		case UNSURPPORT:
			result = "数据库找不到支持";
			break;
		default:
			result = HttpStatusCode.getStatusString(code);
			break;
		}
		return result;
	}
}
