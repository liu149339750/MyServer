package com.lw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lw.db.DBUtil;

public class DealDao {
	
	private static final String QUERY = "select pay_id,message from deal where device_id = ? and feedback = 0";
	private static final String INSERT = "insert into deal (device_id,pay_id,message,time) values (?,?,?,now())";
	private static final String UPDATA = "update deal set feedback = 1 where pay_id = ?";
	
	public List<PayDealMessage> getDealMessage(int deviceId){
		Connection connection = DBUtil.getConn();
		List<PayDealMessage> data = new ArrayList<PayDealMessage>();
		try {
			PreparedStatement ps = connection.prepareStatement(QUERY);
			ps.setInt(1, deviceId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				data.add(new PayDealMessage(rs.getInt(1),rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close();
		}
		return data;
	}
	
	public void addDealMessage(int deviceId,int payId,String message){
		Connection connection = DBUtil.getConn();
		try {
			PreparedStatement ps = connection.prepareStatement(INSERT);
			ps.setInt(1, deviceId);
			ps.setInt(2, payId);
			ps.setString(3, message);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
	}
	
	public void updataDealStatus(int ...payId){
		Connection connection = DBUtil.getConn();
		try {
			for(int i=0;i<payId.length;i++){
				PreparedStatement ps = connection.prepareStatement(UPDATA);
				ps.setInt(1, payId[i]);
				int count = ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
	}
	
	public class PayDealMessage{
		public int id;
		public String message;
		
		public PayDealMessage(int id,String message){
			this.id = id;
			this.message = message;
		}
	}
}
