package com.lw.sududa.deal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lw.db.DBUtil;


public class SududaDao {
	private static String SQL = "SELECT i,p17 from product where b like ? and t = ? and f = ?";
	
	public static String getProductId(int t,String contains,int money){
		Connection con = DBUtil.getConn();
		String productId = "0";
		try {
			PreparedStatement ps = con.prepareStatement(SQL);
			ps.setString(1, "%" + contains + "%");
			ps.setInt(2, t);
			ps.setInt(3, money);
			ResultSet rs = ps.executeQuery();
			float coast = 0;
			while(rs.next()){
				if(coast == 0){
					coast = rs.getFloat(2);
					productId = rs.getString(1);
				}else{
					float temp = rs.getFloat(2);
					if(coast > temp){
						coast = temp;
						productId = rs.getString(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productId;
	}
	
	public static void main(String[] args) {
		System.out.println(getProductId(100, "Ω≠À’", 10));
	}
}
