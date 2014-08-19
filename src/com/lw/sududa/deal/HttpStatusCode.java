package com.lw.sududa.deal;

public class HttpStatusCode {

	public static final int ERROR = -9;
	public static final int SUBMMIT = -1;
	public static final int SUCESS = 1;
	public static final int PROGRESING = 0;
	public static final int MAY_SUCESS = 5;
	public static final int RETURN_ALL = 10;
	public static final int RETURN_PART = 9;
	
	
	public static String getStatusString(int code){
		String result = null;
		switch (code) {
		case ERROR:
			result = "异常,订单未提交，未扣款";
			break;
		case SUBMMIT:
			result = "已扣款，将提交供货商";
			break;
		case SUCESS:
			result = "成功";
			break;
		case PROGRESING:
			result = "处理中";
			break;
		case MAY_SUCESS:
			result = "疑似成功";
			break;
		case RETURN_PART:
			result = "部分退款";
			break;
		case RETURN_ALL:
			result = "全额退款";
			break;
		default:
			break;
		}
		return result;
	}
}
