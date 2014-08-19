package com.lw.sududa.deal;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.lw.sududa.entity.ChargeResutEntity;
import com.lw.sududa.entity.PhoneEntity;
import com.lw.util.EmailSend;
import com.lw.util.Util;


public class DataLayer extends SududaRequest{

	
	public static PhoneEntity getPhoneInfo(String number) throws IOException{
		String data = getPhoninfoData(number);
		System.out.println(data);
		JsonParser jp = new JsonParser();
		PhoneEntity pe = jp.getPhoneEntity(data);
		return pe;
	}
	
	public static int chargeNumber(int money,String orderId,String to){
		try {
			PhoneEntity pe = getPhoneInfo(to);
			if(pe.getStatus()!=1){
				return StatusCode.UNKOWN_NUMBER;
			}
			String type = pe.getType();
			String productId = null;
			if(type == null)
				return StatusCode.ERROR;
			if(type.contains("联通")){
				if(money == 1){
					productId = "1089";
				}else if(money == 5){
					productId = "1093";
				}else {
					String city = pe.getCity();
					String like = city.trim().substring(0, 2);
					productId = SududaDao.getProductId(101, like, money);
				}
			}else if(type.contains("移动")){
				if(money == 1){
					productId = "18";
				}else if(money == 5){
					productId = "24";
				}else {
					String city = pe.getCity();
					String like = city.trim().substring(0, 2);
					productId = SududaDao.getProductId(100, like, money);
				}
			}else if(type.contains("电信")){
				if(money == 1){
					productId = "3301";
				}else if(money == 5){
					productId = "3401";
				}else {
					String city = pe.getCity();
					String like = city.trim().substring(0, 2);
					productId = SududaDao.getProductId(102, like, money);
				}
			}else {
				return StatusCode.UNKOWN_TYPE;
			}
			if("0".equals(productId))
				return StatusCode.UNSURPPORT;
			System.out.println("productId = " +productId+ ",to = " + to);
			ChargeResutEntity cr = getRechageResult(productId, orderId, to);
			if(cr.getStatus() != HttpStatusCode.SUCESS)
				EmailSend.sendEmail("charge fail", "status = " + cr.getStatus() + ",  tips = " + cr.getTips());
			return cr.getStatus();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return StatusCode.UNKOWN_TYPE;
	}
	
	public static ChargeResutEntity getRechageResult(String productId,String orderId,String to) {
		ChargeResutEntity cr = null;
		try {
			String charge = getRechargeResult(productId, orderId, to);
			System.out.println(charge);
			JsonParser jp = new JsonParser();
			cr = jp.getChargeEntity(charge);
			int status = cr.getStatus();
			long now = System.currentTimeMillis();
			while(status != HttpStatusCode.SUCESS&&status != HttpStatusCode.ERROR && status != HttpStatusCode.RETURN_ALL && status != HttpStatusCode.RETURN_PART){
				try {
					Thread.sleep(5000);
					cr = getStatus(orderId);
					status = cr.getStatus();
					if(now - System.currentTimeMillis() > 30*1000*60)
						break;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Util.sendErrorEmail(e, "charge meet eorr,please check");
		} 
		return cr;
	}
	
	public static ChargeResutEntity getStatus(String orderId) throws MalformedURLException, UnsupportedEncodingException, IOException{
		String json = getStatusResult(orderId);
		System.out.println(json);
		JsonParser jp = new JsonParser();
		ChargeResutEntity cr = jp.getChargeEntity(json);
		return cr;
	}
}
