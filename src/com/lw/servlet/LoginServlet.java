package com.lw.servlet;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lw.dao.DealDao;
import com.lw.dao.RecommandDao;
import com.lw.dao.DealDao.PayDealMessage;
import com.lw.dao.DeviceDao;
import com.lw.entity.Device;
import com.lw.util.Util;

public class LoginServlet extends HttpServlet{
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletInputStream in = req.getInputStream();
		String json = Util.readStream(in);
		Gson gson = new Gson();
		Device device = gson.fromJson(json, Device.class);
		System.out.println(Util.getFormmaterTime() +  "     device = " + json);
		in.close();
		if(device == null || device.isEmpty()){
			System.out.println("device == null,return!");
			return;
		}
		int resultId = 0;
		DeviceDao dd = new DeviceDao();
		int cid = device.getId();
		int id = dd.getDeviceId(device);
		System.out.println("login cid = " + cid + ",id = " + id);
		if(id <=0 && cid <= 0)  //第一次登陆
			resultId = dd.addDevice(device);
		else if(cid <= 0){     //之前安装过
//			dd.updataFirstLogin(id);
//			resultId = id;
			resultId = dd.addDevice(device, id);
			updataRecommand(id,resultId);
		}else if (cid != id){   //数据被串改或者数据库出问题
			System.out.println("error!数据被串改或者数据库出问题 ,cid = " + cid + ",id = " + id +"\n"+device);
			if(id == 0)
				resultId = dd.addDevice(device,cid);
			else
				resultId = id;
		}else if(cid == id){ //sucess
			dd.updataLastLogin(id,device.getPoint());
			resultId = id;
		}else{
			resultId = id;
		}
		resp.setIntHeader("id", resultId);
		
		DealDao deal = new DealDao();
		List<PayDealMessage> data = deal.getDealMessage(resultId);
		if(data.size() == 0 )
			return;
		List<String> messages = new ArrayList<String>();
		int payIds[] = new int[data.size()];
		for(int i =0;i<data.size();i++){
			messages.add(data.get(i).message);
			payIds[i] = data.get(i).id;
		}
		Type type = new TypeToken<List<String>>(){}.getType();
		resp.setHeader("exchangeMessage", gson.toJson(messages, type));
		deal.updataDealStatus(payIds);
	}

	private void updataRecommand(int oldId, int newId) {
		RecommandDao rd = new RecommandDao();
		rd.updataRelation(oldId, newId);
	}
}
