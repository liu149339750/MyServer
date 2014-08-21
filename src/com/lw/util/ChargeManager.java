package com.lw.util;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lw.dao.AdminDao;
import com.lw.entity.AdminRequest;
import com.lw.entity.ExchangeEntity;
import com.lw.sududa.deal.DataLayer;
import com.lw.sududa.deal.StatusCode;

public class ChargeManager {

	private static ChargeManager mChargeManager;
	private Executor mExecutor = Executors.newFixedThreadPool(3);
	private AdminDao mAdminDao = new AdminDao();
	
	public static ChargeManager getInstance(){
		if(mChargeManager == null)
			mChargeManager = new ChargeManager();
		return mChargeManager;
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
				
			}
			mAdminDao.updateDeal(mEntity.getDeviceId(), mEntity.getPay_id(), orderId, result);
			if(result == StatusCode.SUCESS){
				AdminRequest ar = new AdminRequest();
				ar.setDevice_id(mEntity.getDeviceId());
				ar.setPay_id(mEntity.getPay_id());
				ar.setMessage("您的" + mEntity.getMoney() + "元话费请求已又系统自动处理，请注意查收");
				mAdminDao.dealPay(ar);
			}
		}
	}
}
