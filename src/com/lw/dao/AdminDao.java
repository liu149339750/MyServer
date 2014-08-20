package com.lw.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.lw.db.DBUtil;
import com.lw.entity.AdminRequest;
import com.lw.entity.CheatEntity;
import com.lw.entity.DealEntity;
import com.lw.entity.ExchangeEntity;
import com.lw.entity.OrderInfo;
import com.lw.util.EmailSend;
import com.lw.util.Type;

public class AdminDao {

	private  final String GETUNPAY = "select Id,device_id,spend_point,number,total_points,time,message " +
			 " from pay where deal != 1" ;
	private  final String GETPAY = "select Id,device_id,spend_point,number,total_points,time,message from pay where device_id = ? or number = ?";
	private  final String GET_DEALB_YDEVICE = "select pay_id,device_id,message,time,feedback from deal where device_id = ?";
	private  final String GET_ORDERINFO = "select device_id,cid,message,point,time from points_order_info where device_id = ?";
	
	private final String GET_APP_POINTS = "select sum(point) from points_order_info where device_id = ?";
	private final String GET_SPEND_POINTS = "select sum(spend_point) from pay where device_id = ?";
	private final String GET_TOTAL_POINTS = "select point from device where id = ?";
	
	private final String UPDATA_PAY_DEAL = "update pay set deal = 1 where device_id = ? and id = ?";
	private final String INSERT_FEEDBACK = "insert into deal set time = now(),message = ?,device_id=?,pay_id=? ";
	
	private final String GET_COAST = "select sum(money) from pay where time > ?";
	
	private final String GET_UNDEAL_PHONE = "select device_id,Id,money,number from pay where deal = 0 and type = ? and orderId is NULL";
	private final String UPDATA_PAY_STATUS = "update pay set deal = ?,orderId = ? where device_id = ? and Id = ?";
	private final String GET_EXCHANGE  = "select money ,number,type from pay where device_id = ? and Id = ?";
	
	public List<ExchangeEntity> getUnPayExchange(){
		Connection connection = DBUtil.getConn();
		List<ExchangeEntity> data = new ArrayList<ExchangeEntity>();
		try {
			PreparedStatement ps = connection.prepareStatement(GETUNPAY);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ExchangeEntity ee = new ExchangeEntity();
				ee.setDeviceId(rs.getInt(2));
				ee.setPay_id(rs.getInt(1));
				ee.setSpendPoint(rs.getInt(3));
				ee.setNumber(rs.getString(4));
				ee.setTotalPoints(rs.getInt(5));
				ee.setTime(rs.getString(6));
				ee.setMessage(rs.getString(7));
				data.add(ee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close();
		}
		return data;
	}
	
	public List<ExchangeEntity> getPayExchangeByDevice(AdminRequest ar){
		Connection connection = DBUtil.getConn();
		List<ExchangeEntity> data = new ArrayList<ExchangeEntity>();
		try {
			PreparedStatement ps = connection.prepareStatement(GETPAY);
			ps.setInt(1, ar.getDevice_id());
			ps.setString(2, ar.getNumber());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ExchangeEntity ee = new ExchangeEntity();
				ee.setDeviceId(rs.getInt(2));
				ee.setPay_id(1);
				ee.setSpendPoint(rs.getInt(3));
				ee.setNumber(rs.getString(4));
				ee.setTotalPoints(rs.getInt(5));
				ee.setTime(rs.getString(6));
				ee.setMessage(rs.getString(7));
				data.add(ee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close();
		}
		return data;
	}
	
	public List<DealEntity> getDealEntityByDevice(AdminRequest ar){
		Connection connection = DBUtil.getConn();
		List<DealEntity> data = new ArrayList<DealEntity>();
		try {
			PreparedStatement ps = connection.prepareStatement(GET_DEALB_YDEVICE);
			ps.setInt(1, ar.getDevice_id());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				DealEntity de = new DealEntity();
				de.setPay_id(rs.getInt(1));
				de.setDevice_id(rs.getInt(2));
				de.setMessage(rs.getString(3));
				de.setTime(rs.getString(4));
				de.setFeedback(rs.getInt(5));
				data.add(de);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close();
		}
		return data;
	}
	
	public List<OrderInfo> getOrderInfoByDevice(AdminRequest ar){
		Connection connection = DBUtil.getConn();
		List<OrderInfo> data = new ArrayList<OrderInfo>();
		try {
			PreparedStatement ps = connection.prepareStatement(GET_ORDERINFO);
			ps.setInt(1, ar.getDevice_id());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				OrderInfo de = new OrderInfo();
				de.setDeviceId(rs.getInt(1));
				de.setId(rs.getInt(2));
				de.setMessage(rs.getString(3));
				de.setPoint(rs.getInt(4));
				de.setStime(rs.getString(5));
				data.add(de);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close();
		}
		return data;
	}
	
	public CheatEntity getCheatEntity(AdminRequest ar){
		Connection con = DBUtil.getConn();
		CheatEntity ce = new CheatEntity();
		try {
			PreparedStatement ps = con.prepareStatement(GET_APP_POINTS);
			ps.setInt(1, ar.getDevice_id());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				ce.setEarnPoint(rs.getInt(1));
			
			ps = con.prepareStatement(GET_SPEND_POINTS);
			ps.setInt(1, ar.getDevice_id());
			rs = ps.executeQuery();
			if(rs.next())
				ce.setSpendPoint(rs.getInt(1));
			
			ps = con.prepareStatement(GET_TOTAL_POINTS);
			ps.setInt(1, ar.getDevice_id());
			rs = ps.executeQuery();
			if(rs.next())
				ce.setTotalPoint(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close();
		}
		
		return ce;
	}
	
	public void dealPay(AdminRequest ar){
		Connection con = DBUtil.getConn();
		try {
			PreparedStatement ps = con.prepareStatement(UPDATA_PAY_DEAL);
			ps.setInt(1, ar.getDevice_id());
			ps.setInt(2, ar.getPay_id());
			ps.executeUpdate();
			
			ps = con.prepareStatement(INSERT_FEEDBACK);
			ps.setString(1, ar.getMessage());
			ps.setInt(2, ar.getDevice_id());
			ps.setInt(3, ar.getPay_id());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
	}
	
	public int getCoast(int days){
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		
		Connection con = DBUtil.getConn();
		Date date = new Date(year - 1900, month, day - days);
//		Timestamp timestamp = new Timestamp(year - 1900, month, day - days, 0, 0, 0, 0);
//		System.out.println(date);
//		System.out.println(timestamp);
		try {
			PreparedStatement ps = con.prepareStatement(GET_COAST);
			ps.setDate(1, date);
//			ps.setTimestamp(1, timestamp);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return 0;
	}
	
	public List<ExchangeEntity> getUnDealPhone(){
		Connection con = DBUtil.getConn();
		List<ExchangeEntity> data = new ArrayList<ExchangeEntity>();
		try {
			PreparedStatement ps = con.prepareStatement(GET_UNDEAL_PHONE);
			ps.setInt(1, Type.TYPE_PHONE);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				ExchangeEntity ee = new ExchangeEntity();
				ee.setDeviceId(rs.getInt(1));
				ee.setPay_id(rs.getInt(2));
				ee.setMoney(rs.getString(3));
				ee.setNumber(rs.getString(4));
				ee.setType(Type.TYPE_PHONE);
				data.add(ee);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return data;
	}
	
	public synchronized void updateDeal(int deviceId,int payid,String orderId,int result){
		Connection con = DBUtil.getConn();
		try {
			PreparedStatement ps = con.prepareStatement(UPDATA_PAY_STATUS);
			ps.setInt(1, result);
			ps.setString(2, orderId);
			ps.setInt(3, deviceId);
			ps.setInt(4, payid);
			int count = ps.executeUpdate();
			if(count == 0)
				EmailSend.sendEmail("Server Db result 0", "Updata the OrderId fail. orderId =" + orderId +",PayId = " + payid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EmailSend.sendEmail("Server Db error", "Updata the OrderId fail. orderId =" + orderId +",PayId = " + payid);
		}finally{
			DBUtil.close();
		}
	}
	
	public synchronized ExchangeEntity getExchangeEntity(AdminRequest ar){
		Connection con = DBUtil.getConn();
		ExchangeEntity ee = new ExchangeEntity();
		try {
			PreparedStatement ps = con.prepareStatement(GET_EXCHANGE);
			ps.setInt(1, ar.getDevice_id());
			ps.setInt(2, ar.getPay_id());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				ee.setDeviceId(ar.getDevice_id());
				ee.setPay_id(ar.getPay_id());
				ee.setMoney(rs.getString(1));
				ee.setNumber(rs.getString(2));
				ee.setType(rs.getInt(3));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return ee;
	}
}
