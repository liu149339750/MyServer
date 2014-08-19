package com.lw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lw.dao.AdminDao;
import com.lw.entity.AdminRequest;
import com.lw.entity.CheatEntity;
import com.lw.entity.CoastEntity;
import com.lw.entity.DealEntity;
import com.lw.entity.ExchangeEntity;
import com.lw.entity.OrderInfo;
import com.lw.util.Util;

public class AdminServlet extends HttpServlet{

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		InputStream in = req.getInputStream();
		InputStreamReader ir = new InputStreamReader(in, "utf-8");
		Gson gson = new Gson();
		AdminRequest ar = gson.fromJson(ir, AdminRequest.class);
		ir.close();
		if(ar == null)
			return;
		
		AdminDao dao = new AdminDao();
		String json  = null;
		switch (ar.getFlag()) {
		case AdminRequest.FLAG_QUERY_IS_CHEAT:
			CheatEntity ce = dao.getCheatEntity(ar);
			json = gson.toJson(ce);
			break;
		case AdminRequest.FLAG_QUERY_DEAL_BY_DEVICE:
			List<DealEntity> des = dao.getDealEntityByDevice(ar);
			json = gson.toJson(des);
			break;
		case AdminRequest.FLAG_QUERY_ORFER_BY_DEVICE:
			List<OrderInfo> infos = dao.getOrderInfoByDevice(ar);
			json = gson.toJson(infos);
			break;
		case AdminRequest.FLAG_QUERY_PAY_BY_DEVICE:
			List<ExchangeEntity> pay = dao.getPayExchangeByDevice(ar);
			json = gson.toJson(pay);
			break;
		case AdminRequest.FLAG_QUERY_UNPAY:
			List<ExchangeEntity> data = dao.getUnPayExchange();
			json = gson.toJson(data);
			break;
		case AdminRequest.FLAG_DEAL:
			System.out.println("deal zuanqian = " + ar.getNumber());
			dao.dealPay(ar);
			break;
		case AdminRequest.FLAG_QUERY_COSAT:
//			int days = Integer.parseInt(req.getParameter("days"));
			int days = Integer.parseInt(req.getHeader("days"));
			int coast = dao.getCoast(days);
			CoastEntity coastEntity = new CoastEntity();
			coastEntity.setMoney(coast);
			coastEntity.setDays(days);
			json = gson.toJson(coastEntity);
			break;
		default:
			break;
		}
		System.out.println("admin deal! json = " + json);
		if(!Util.isEmpty(json)){
			OutputStream out = resp.getOutputStream();
			out.write(json.getBytes("utf-8"));
			out.flush();
			out.close();
		}
	}
}
