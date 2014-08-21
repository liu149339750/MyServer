package com.lw.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.lw.db.DBUtil;
import com.lw.entity.Device;
import com.lw.util.Util;

public class DeviceDao {
	
	private Connection mConnection;
	private String insert = "insert into device (uuid,imei,mac,android_id,model,device,first_login,last_login,ever_id,point) value (?,?,?,?,?,?,?,?,?,?)";
	private String queryByMacAndUUid = "select id from device where mac = ? and uuid = ?";
	private String queryByUuIm = "select id from device where uuid = ? and imei = ?";
	private String delete = "delete device where id = ?";
	private String updataLastLogin = "update device set last_login = now(),point = ? where id = ?";
	private String updataFirstLogin = "update device set last_login = ?,first_login = ? where id = ?";
	
	public int addDevice(Device device){
		return addDevice(device, -1);
	}
	
	public int addDevice(Device device,int eid){
		int id = 0;
		try{
			mConnection = DBUtil.getConn();
			PreparedStatement ps = mConnection.prepareStatement(insert);
			ps.setString(1, device.getUuid());
			ps.setString(2, device.getImei());
			ps.setString(3, device.getMac());
			ps.setString(4, device.getAndroid_id());
			ps.setString(5, device.getModel());
			ps.setString(6, device.getDevice());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			ps.setInt(9, eid);
			ps.setInt(10, device.getPoint());
			ps.executeUpdate();
			
			ResultSet rs=ps.getGeneratedKeys();
			while(rs.next())
				id = rs.getInt(1);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close();
		}
		return id;
	}
	
	public void updataLastLogin(int id, int point){
		try{
			mConnection = DBUtil.getConn();
			PreparedStatement ps = mConnection.prepareStatement(updataLastLogin);
			ps.setInt(1, point);
			ps.setInt(2, id);
			ps.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
	}
	
	public void updataFirstLogin(int id){
		try{
			mConnection = DBUtil.getConn();
			PreparedStatement ps = mConnection.prepareStatement(updataFirstLogin);
			Date date = new Date(System.currentTimeMillis());
			ps.setDate(1, date);
			ps.setDate(2, date);
			ps.setInt(3, id);
			ps.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
	}
	
	public void deleteDevice(int id){
		try{
			mConnection = DBUtil.getConn();
			PreparedStatement ps = mConnection.prepareStatement(delete);
			ps.setInt(1, id);
			ps.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
	}
	
	public int getDeviceId(Device device){
		String mac = device.getMac();
		int id = 0;
		try{
			mConnection = DBUtil.getConn();
			if(!Util.isEmpty(mac) && !"00:00:00:00:00:00".equals(mac)){
				PreparedStatement ps = mConnection.prepareStatement(queryByMacAndUUid);
				ps.setString(1, mac);
				ps.setString(2, device.getUuid());
				ResultSet rs = ps.executeQuery();
//				if(rs.first()){
//					id = rs.getInt(1);
//					if(!rs.next())
//						return id;
//				}
				if(rs.last()){
					id = rs.getInt(1);
					return id;
				}
			}
			String uuid = device.getUuid();
			String imei = device.getImei();
			PreparedStatement ps = mConnection.prepareStatement(queryByUuIm);
			ps.setString(1, uuid);
			ps.setString(2, imei);
			ResultSet rs = ps.executeQuery();
//			if(rs.first())
//				id = rs.getInt(1);
//			if(!rs.next())
//				return id;
			if(rs.last())
				id = rs.getInt(1);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return id;
	}
	
	
}
