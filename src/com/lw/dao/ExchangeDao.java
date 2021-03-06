package com.lw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.lw.db.DBUtil;
import com.lw.entity.ExchangeEntity;

public class ExchangeDao {
	Connection mConnection;
	private static final String insert = "insert into pay (device_id,spend_point,money,number,total_points,local_phone,uuid,message,version,type,time) value (?,?,?,?,?,?,?,?,?,?,now())";
	
	public int addRecord(ExchangeEntity entity){
		boolean sucess = false;
		int r = -1;
		try{
			mConnection = DBUtil.getConn();
			PreparedStatement ps = mConnection.prepareStatement(insert,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, entity.getDeviceId());
			ps.setInt(2, entity.getSpendPoint());
			ps.setString(3, entity.getMoney());
			ps.setString(4, entity.getNumber());
			ps.setInt(5, entity.getTotalPoints());
			ps.setString(6, entity.getLocalPhoneNumber());
			ps.setString(7, entity.getUuid());
			ps.setString(8, entity.getMessage());
			ps.setString(9, entity.getVersion());
			ps.setInt(10, entity.getType());
			sucess = ps.executeUpdate() == 1;
			if(!sucess)
				return -1;
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next())
				r = rs.getInt(1);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close();
		}
		return r;
	}
	
}
