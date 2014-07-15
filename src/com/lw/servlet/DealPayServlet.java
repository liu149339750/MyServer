package com.lw.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lw.dao.DealDao;
import com.lw.dao.DealDao.PayDealMessage;
import com.lw.util.Util;

public class DealPayServlet extends HttpServlet{

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(Util.getFormmaterTime()   + "DealPayServlet");
		String user = req.getParameter("user");
		DealDao deal = new DealDao();
		if("admin".equals(user)){
			
		}else {
			String cid = req.getHeader("device_id"); //http.setRequestProperty
//			String cid = req.getParameter("device_id");
			int id = -1;
			if(!Util.isEmpty(cid))
				id = Integer.valueOf(cid);
			System.out.println("DealPayServlet device_id = " + id);
			List<PayDealMessage> data = deal.getDealMessage(id);
			OutputStream out = resp.getOutputStream();
			if(data.size() == 0 ){
				out.write(0);
				out.close();
				return;
			}
			List<String> messages = new ArrayList<String>();
			int payIds[] = new int[data.size()];
			for(int i =0;i<data.size();i++){
				messages.add(data.get(i).message);
				payIds[i] = data.get(i).id;
			}
			Type type = new TypeToken<List<String>>(){}.getType();
			Gson gson = new Gson();
			String json = gson.toJson(messages, type);
			System.out.println("message = " + json);
			out.write(1);
			out.write(json.getBytes("utf-8"));
			out.close();
			deal.updataDealStatus(payIds);
		}
		System.out.println("DealPayServlet end");
	}
}
