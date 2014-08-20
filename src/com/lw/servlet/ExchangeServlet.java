package com.lw.servlet;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lw.dao.ExchangeDao;
import com.lw.entity.ExchangeEntity;
import com.lw.util.Util;

public class ExchangeServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(Util.getFormmaterTime() + "    ExchangeServlet ");
		ServletInputStream in = req.getInputStream();
		Gson gson = new Gson();
		ExchangeEntity exchange = gson.fromJson(new InputStreamReader(in), ExchangeEntity.class);
		in.close();
		if(exchange == null){
			System.out.println("ExchangeServlet exchange == null,return!");
			return;
		}
		ExchangeDao ed = new ExchangeDao();
		boolean sucess = ed.addRecord(exchange);
		System.out.println("ExchangeServlet sucess="+sucess);
		resp.setHeader("result", sucess?"sucess":"fail");
		
		if(sucess){
			
		}
	}
}
