package com.lw.servlet;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lw.dao.AdminDao;
import com.lw.dao.ExchangeDao;
import com.lw.entity.CheatEntity;
import com.lw.entity.ExchangeEntity;
import com.lw.util.ChargeManager;
import com.lw.util.EmailSend;
import com.lw.util.Type;
import com.lw.util.Util;

public class ExchangeServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(Util.getFormmaterTime() + "    ExchangeServlet ");
		ServletInputStream in = req.getInputStream();
		Gson gson = new Gson();
		ExchangeEntity exchange = gson.fromJson(new InputStreamReader(in),
				ExchangeEntity.class);
		in.close();
		if (exchange == null) {
			System.out.println("ExchangeServlet exchange == null,return!");
			return;
		}
		ExchangeDao ed = new ExchangeDao();
		int id = ed.addRecord(exchange);
		System.out.println("ExchangeServlet id = " + id);
		resp.setHeader("result", id != -1 ? "sucess" : "fail");
		exchange.setPay_id(id);
		try{
		if (id != -1) {
			int type = exchange.getType();
			if (type == Type.TYPE_PHONE || type == Type.TYPE_QQ) {
				AdminDao adminDao = new AdminDao();
				if(type == Type.TYPE_PHONE)
					ChargeManager.getInstance().insertPhoneInfo(id, exchange.getNumber());
				CheatEntity ce = adminDao
						.getCheatEntity(exchange.getDeviceId());
				if (ce.getEarnPoint() - ce.getSpendPoint() < -100) {
					EmailSend.sendEmail("Auto Cheat check",
							"a charge not auto deal," + gson.toJson(ce)
									+ gson.toJson(exchange));
					return;
				}
				if (adminDao.getChargeTodayCount(exchange.getDeviceId()) > 3) {
					EmailSend.sendEmail("Charge Over Count", "message = "
							+ gson.toJson(exchange));
					return;
				}
				try {
					if (Util.auto(getServletContext())) {
						ChargeManager.getInstance().chargeNumber(exchange);
					}
				} catch (Exception e) {
					e.printStackTrace();
					EmailSend.sendEmail("error", "charge error!please check");
				}
			}
		} else {
			EmailSend.sendEmail("insert pay fail!",
					"return false to the client," + gson.toJson(exchange));
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
