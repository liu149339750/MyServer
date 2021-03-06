package com.lw.util;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lw.dao.AdminDao;
import com.lw.entity.AdminRequest;
import com.lw.entity.ExchangeEntity;
import com.lw.sududa.deal.DataLayer;
import com.lw.sududa.deal.StatusCode;
import com.lw.sududa.entity.PhoneEntity;

public class ChargeManager {

	private static ChargeManager mChargeManager;
	private ExecutorService mExecutor = Executors.newFixedThreadPool(3);
	private AdminDao mAdminDao = new AdminDao();
	
	public static ChargeManager getInstance(){
		if(mChargeManager == null)
			mChargeManager = new ChargeManager();
		return mChargeManager;
	}
	
	public void insertPhoneInfo(final int payId,final String number){
		mExecutor.execute(new Runnable() {
			
			public void run() {
				try {
					PhoneEntity pe = DataLayer.getPhoneInfo(number);
					AdminDao ad = new AdminDao();
					ad.insertPhoneInfo(payId, pe);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void chargePhones(List<ExchangeEntity> phones) {
		System.out.println("chargePhones");
		for(ExchangeEntity ee : phones){
			mExecutor.execute(new ChargeRunnable(ee));
		}
	}
	
	public  void chargeNumber(ExchangeEntity ee){
		if(ee.getMoney().equals("0")){
			EmailSend.sendEmail("Warning", "the pay request have a 0 money");
			return;
		}
		mExecutor.execute(new ChargeRunnable(ee));
	}
	
	class ChargeRunnable implements Runnable{
		private ExchangeEntity mEntity;
		public ChargeRunnable(ExchangeEntity ee){
			mEntity = ee;
		}
		public void run() {
			String orderId = (System.currentTimeMillis() + "").substring(4) + "v" + mEntity.getDeviceId() + "p" + mEntity.getPay_id();
			int type = mEntity.getType();
			int result = 0;
			if(type == Type.TYPE_PHONE)
				result = DataLayer.chargePhoneNumber(Integer.parseInt(mEntity.getMoney()), orderId, mEntity.getNumber());
			else if(type == Type.TYPE_QQ){
				result = DataLayer.chargeQQ(Integer.parseInt(mEntity.getMoney()), orderId, mEntity.getNumber());
			}
			mAdminDao.updateDeal(mEntity.getDeviceId(), mEntity.getPay_id(), orderId, result);
			if(result == StatusCode.SUCESS){
				AdminRequest ar = new AdminRequest();
				ar.setDevice_id(mEntity.getDeviceId());
				ar.setPay_id(mEntity.getPay_id());
				ar.setMessage("您的" + mEntity.getMoney() + "元" + getString(type) +"请求已由系统自动处理，请注意查收");
				mAdminDao.dealPay(ar);
			}
		}
		private String getString(int type) {
			String str = "";
			if(type == Type.TYPE_PHONE)
				str = "话费";
			else if(type == Type.TYPE_QQ)
				str = "Q币";
			return str;
		}
	}

	public void close() {
		mExecutor.shutdown();
		mExecutor = null;
		mChargeManager = null;
	}
}
