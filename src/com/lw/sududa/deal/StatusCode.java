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
			result = "�ɹ�";
			break;
		case UNKOWN_NUMBER:
			result = "���벻��";
			break;
		case CHARGER_FAIL:
			result = "��ֵʧ��";
			break;
		case ERROR:
			result = "�쳣���";
			break;
		case UNKOWN_TYPE:
			result = "������Ӫ��δ֪";
			break;
		case UNSURPPORT:
			result = "���ݿ��Ҳ���֧��";
			break;
		default:
			result = HttpStatusCode.getStatusString(code);
			break;
		}
		return result;
	}
}
