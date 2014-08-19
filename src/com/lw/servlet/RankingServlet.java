package com.lw.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lw.dao.RankDao;
import com.lw.entity.RankEntity;

public class RankingServlet extends HttpServlet{

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		RankDao rd = new RankDao();
		List<RankEntity>ranks = rd.getRanks();
		Gson gson = new Gson();
		String json = gson.toJson(ranks);
		
		OutputStream out = resp.getOutputStream();
		out.write(json.getBytes("utf-8"));
		out.close();
		
//		System.out.println("rank json = " + json);
	}
}
