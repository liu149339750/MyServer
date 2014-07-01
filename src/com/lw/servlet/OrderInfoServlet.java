package com.lw.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lw.dao.OrderDao;
import com.lw.entity.OrderInfo;
import com.lw.util.Util;

public class OrderInfoServlet extends HttpServlet{
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ServletInputStream in = req.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer sb = new StringBuffer();
		String temp = null;
		while((temp = br.readLine()) != null){
			sb.append(temp);
		}
		br.close();
		System.out.println(Util.getFormmaterTime() + "     OrderInfo = " + sb.toString());
		Gson gson = new Gson();
		Type type = new TypeToken<List<OrderInfo>>(){}.getType();
		List<OrderInfo> orders = gson.fromJson(sb.toString(), type);
		if(orders == null || orders.size() == 0){
			System.out.println("orders == null,return!");
			return;
		}
		OrderDao od = new OrderDao();
		od.addOrderInfo(orders);
		
		resp.setStatus(200);
	}
}
