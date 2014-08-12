package com.lw.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lw.util.DataLifeManager;
import com.lw.util.Util;

public class RequestServlet extends HttpServlet{

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String host = req.getRemoteAddr();
		String device = req.getParameter("device");
		resp.sendRedirect("http://sunsonfly.synology.me:8001/PhoneFare.apk");
		if(!Util.isEmpty(device)){
			System.out.println("device = " + device + ",host = " + host);
			int did = Integer.parseInt(device);
			DataLifeManager.getInstance().addInfo(host,did);
		}
	}
}
