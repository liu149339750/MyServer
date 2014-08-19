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
			result = "�쳣,����δ�ύ��δ�ۿ�";
			break;
		case SUBMMIT:
			result = "�ѿۿ���ύ������";
			break;
		case SUCESS:
			result = "�ɹ�";
			break;
		case PROGRESING:
			result = "������";
			break;
		case MAY_SUCESS:
			result = "���Ƴɹ�";
			break;
		case RETURN_PART:
			result = "�����˿�";
			break;
		case RETURN_ALL:
			result = "ȫ���˿�";
			break;
		default:
			break;
		}
		return result;
	}
}
