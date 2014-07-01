package com.lw.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import com.lw.db.DBUtil;
import com.lw.entity.OrderInfo;

public class OrderDao {
	private Connection mConnection;
	private static final String insert = "insert into points_order_info (device_id,order_id,status,message,point,time,server_time) value (?,?,?,?,?,?,now())";
	
	public void addOrderInfo(List<OrderInfo> list){
		try{
			mConnection = DBUtil.getConn();
			for(int i=0;i<list.size();i++){
				OrderInfo order = list.get(i);
				PreparedStatement ps = mConnection.prepareStatement(insert);
				ps.setInt(1, order.getDeviceId());
				ps.setString(2, order.getOrderId());
				ps.setInt(3, order.getStatus());
				ps.setString(4, order.getMessage());
				ps.setInt(5, order.getPoint());
//				java.util.Date d = new java.util.Date(order.getTime());
//				System.out.println(d.toLocaleString());
//				Date date = new Date(order.getTime());
//				ps.setDate(6, date);
//				ps.setString(6, d.toLocaleString());
				ps.setTimestamp(6, new Timestamp(order.getTime()));
				ps.execute();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close();
		}
	}
}
