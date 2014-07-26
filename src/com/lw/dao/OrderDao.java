package com.lw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lw.db.DBUtil;
import com.lw.entity.OrderInfo;
import com.lw.entity.OrderRespon;

public class OrderDao {
	private Connection mConnection;
	private static final String insert = "insert into points_order_info (device_id,order_id,status,message,point,time,totalPoint,cid,server_time) " +
			 "select ?,?,?,?,?,?,?,?,now() from dual where not exists (select * from points_order_info where cid = ? and device_id = ? and message = ?)";

	private static final String SELECT = "select Id  from points_order_info where device_id = ? and cid = ? and message = ?";
	public List<OrderRespon> addOrderInfo(List<OrderInfo> list){
		List<OrderRespon> results = new ArrayList<OrderRespon>();
		try{
			mConnection = DBUtil.getConn();
			for(int i=0;i<list.size();i++){
				OrderInfo order = list.get(i);
				PreparedStatement ps = mConnection.prepareStatement(insert);
				ps.setInt(1, order.getDeviceId());
				ps.setString(2, order.getOrderId() + "");
				ps.setInt(3, order.getStatus());
				ps.setString(4, order.getMessage());
				ps.setInt(5, order.getPoint());
				ps.setTimestamp(6, new Timestamp(order.getTime()));
				ps.setInt(7, order.getTotalPoint());
				ps.setInt(8, order.getId());
				ps.setInt(9, order.getId());
				ps.setInt(10, order.getDeviceId());
				ps.setString(11, order.getMessage());
				ps.execute();
				
				if (order.getId() != 0) {
					ps = mConnection.prepareStatement(SELECT);
					ps.setInt(1, order.getDeviceId());
					ps.setInt(2, order.getId());
					ps.setString(3, order.getMessage());
					ResultSet rs = ps.executeQuery();
					int id = -1;
					if (rs.last())
						id = rs.getInt(1);
					OrderRespon or = new OrderRespon();
					or.setCid(order.getId());
					or.setSid(id);
					results.add(or);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close();
		}
		return results;
	}
}
