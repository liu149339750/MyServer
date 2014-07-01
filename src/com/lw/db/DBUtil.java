package com.lw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	static ThreadLocal<Connection> td =
		new ThreadLocal<Connection>();
	
	public static Connection getConn(){
		Connection conn = 
			td.get();
		if(conn == null){
			td.set(getLocalConn());
			conn = td.get();
		}else{
			return conn;
		}
		
		return conn;
		
	}

	private static Connection getLocalConn() {
		Connection con = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/liuwei?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
//			con = DriverManager.getConnection("jdbc:mariadb://sunsonfly.synology.me:3306/liuwei?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/liuwei?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
//			con = DriverManager.getConnection("jdbc:mysql://sunsonfly.synology.me:3306/liuwei?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static void close(){
		Connection con = 
			td.get();
		if(con!=null){
			try {
				con.close();
				td.set(null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
