package com.lw.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lw.util.EmailSend;

public class test {
	public static void main(String[] args) throws SQLException {
//		Connection con = 
//			DBUtil.getConn();
//		System.out.println(con);
//		 con = 
//			DBUtil.getConn();
//		System.out.println(con);
//		long t = System.currentTimeMillis();
//		PreparedStatement ps = con.prepareStatement("insert into device (first_login,last_login) value (now(),now())");
//		Date date = new Date(System.currentTimeMillis());
////		ps.setDate(1, date);
////		ps.setDate(2, date);
//		int r = ps.executeUpdate();
//		System.out.println(r);
		
		
/*		PreparedStatement ps = con.prepareStatement("select * from lw");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
			System.out.println(rs.getString(2));*/
		EmailSend.sendEmail("sdjj", "message");
	}

}
