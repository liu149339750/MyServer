package com.lw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lw.db.DBUtil;
import com.lw.entity.PayEntity;
import com.lw.entity.QueryPayEntity;
import com.lw.util.Util;

public class QueryPayServlet extends HttpServlet{
	private static final String GET_MY_EXCHANGE = "select money,number,time,deal,version from pay where device_id = ?";
	private static final String GET_TODAY_PAYS = "select money,number,time,deal,version,device_id from pay where time > ?";
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		InputStream in = req.getInputStream();
		Gson gson = new Gson();
		QueryPayEntity payEntity = gson.fromJson(new InputStreamReader(in), QueryPayEntity.class);
		in.close();
		if(payEntity == null){
//			return ;
			payEntity = new QueryPayEntity();
			payEntity.setFlag(QueryPayEntity.FLAG_QUERY_TODAY_PAYS);
		}
		
		String json = null;
		switch (payEntity.getFlag()) {
		case QueryPayEntity.FLAG_QUERY_MY_PAY:
			List<PayEntity> data = getMyExchanges(payEntity.getDeviceId());
			json = gson.toJson(data);
			break;
		case QueryPayEntity.FLAG_QUERY_TODAY_PAYS:
			List<PayEntity> datas = getTodayPays();
			json = gson.toJson(datas);
			break;
		default:
			break;
		}
		
		if(!Util.isEmpty(json)){
			OutputStream out = resp.getOutputStream();
			out.write(json.getBytes("utf-8"));
			out.flush();
			out.close();
		}
	}
	
	private List<PayEntity> getTodayPays(){
		Connection con = DBUtil.getConn();
		List<PayEntity> result = new ArrayList<PayEntity>();
		try {
			Calendar calendar = Calendar.getInstance();
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			Date date = new Date(year - 1900, month, day);
			
			PreparedStatement ps = con.prepareStatement(GET_TODAY_PAYS);
			ps.setDate(1, date);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				PayEntity entity = new PayEntity();
				entity.setMoney(rs.getInt(1));
				entity.setNumber(rs.getString(2));
				entity.setTime(rs.getString(3));
				entity.setDeal(rs.getInt(4));
				entity.setVersion(rs.getString(5));
				entity.setDeviceId(rs.getInt(6));
				result.add(entity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return result;
	}
	
	private List<PayEntity> getMyExchanges(int deviceId){
		Connection con = DBUtil.getConn();
		List<PayEntity> result = new ArrayList<PayEntity>();
		try {
			PreparedStatement ps = con.prepareStatement(GET_MY_EXCHANGE);
			ps.setInt(1, deviceId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				PayEntity entity = new PayEntity();
				entity.setMoney(rs.getInt(1));
				entity.setNumber(rs.getString(2));
				entity.setTime(rs.getString(3));
				entity.setDeal(rs.getInt(4));
				entity.setVersion(rs.getString(5));
				result.add(entity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return result;
	}
}
