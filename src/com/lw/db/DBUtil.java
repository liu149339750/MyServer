package com.lw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	static ThreadLocal<Connection> td = new ThreadLocal<Connection>();

	private static final String CREATE_DEAL = "CREATE TABLE IF NOT EXISTS deal ( Id int(11) NOT NULL AUTO_INCREMENT,"
			+ "pay_id int(11) DEFAULT NULL, "
			+ "device_id int(11) DEFAULT NULL,"
			+ "message varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '',"
			+ "time datetime DEFAULT NULL,"
			+ "feedback int(11) DEFAULT '0', "
			+ "PRIMARY KEY (Id))";
	private static final String CREATE_DEVICE = "CREATE TABLE IF NOT EXISTS device  ("
			+ " Id  int(11) NOT NULL AUTO_INCREMENT,"
			+ " uuid  varchar(255) DEFAULT '',"
			+ " imei  varchar(255) DEFAULT '',"
			+ " mac  varchar(255) DEFAULT '',"
			+ " android_id  varchar(255) DEFAULT '',"
			+ " model  varchar(255) DEFAULT NULL, "
			+ " device  varchar(255) DEFAULT '',"
			+ " point  int(11) DEFAULT NULL ,"
			+ " first_login  datetime DEFAULT NULL,"
			+ " last_login  datetime DEFAULT NULL,"
			+ " ever_id  int(11) DEFAULT NULL," + " PRIMARY KEY ( Id ))";

	private static final String CREATE_PAY = "CREATE TABLE IF NOT EXISTS pay ("
			+ "Id int(11) NOT NULL AUTO_INCREMENT,"
			+ "device_id int(11) DEFAULT NULL,"
			+ "spend_point int(11) DEFAULT NULL,"
			+ "money varchar(255) DEFAULT NULL,"
			+ "number varchar(255) DEFAULT '',"
			+ "total_points int(11) DEFAULT NULL,"
			+ "time datetime DEFAULT NULL,"
			+ "local_phone varchar(255) DEFAULT NULL,"
			+ "uuid varchar(255) DEFAULT NULL,"
			+ "message varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,"
			+ "type varchar(100),"
			+ "version varchar(100),"
			+ "orderId varchar(100),"
			+ "deal int(11) DEFAULT 0 ," + "PRIMARY KEY (Id))";

	private static final String CREATE_ORDER_INFO = "CREATE TABLE IF NOT EXISTS points_order_info ("
			+ "Id int(11) NOT NULL AUTO_INCREMENT,"
			+ "device_id int(11) DEFAULT NULL,"
			+ "cid int(11) DEFAULT 0," 
			+ "order_id varchar(255) DEFAULT '' COMMENT '��ȡ������(����Ψһ��)',"
			+ "status int(11) DEFAULT NULL COMMENT '��ȡ���ֶ�����״̬��1. ��ʾ�����߻�������벢���û�����˻��֡� 2. ��ʾ������û�л�����뵫�û�����˻��֣�δͨ������Լ�����ģʽ�½�����Ч���������',"
			+ "message varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '���λ�ȡ���ֵ�������硰�ɹ���װ��--����ȡ��100��ҡ�',"
			+ "point int(11) DEFAULT NULL COMMENT '���λ�õĻ���',"
			+ "time datetime DEFAULT NULL COMMENT '���λ�û��ֵĽ���ʱ��',"
			+ "server_time datetime DEFAULT NULL COMMENT '������ʱ��',"
			+ "totalPoint int(11) DEFAULT 0,"
			+ "PRIMARY KEY (Id)) ";
	
	private static final String CREATE_PHONE_INFO = "create table if NOT EXISTS phone (" +
			"Id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
			"pay_id int(11) default 0," +
			"type varchar(50)," +
			"city varchar(50)," +
			"areacode varchar(50)," +
			"zipcode varchar(50)) CHARSET=utf8";
	
	private static final String CREATE_RECOMMAND = "CREATE TABLE IF NOT EXISTS recommand ("
		+ "Id int(11) NOT NULL primary key AUTO_INCREMENT,"
		+ "device_id int(11) DEFAULT NULL,"
		+ "recommand_id int(11)," 
		+ "point int(11) default 0,"
		+ "time datetime ,"
		+ "device varchar(100))"; //the model and os version
	

/*	static {
		Connection con = getLocalConn();
		try {
			Statement statement = con.createStatement();
			statement.executeUpdate(CREATE_DEAL);
			
			con.prepareStatement(CREATE_DEVICE).executeUpdate();
			con.prepareStatement(CREATE_PAY).executeUpdate();
			con.prepareStatement(CREATE_ORDER_INFO).execute();
			con.prepareStatement(CREATE_RECOMMAND).execute();
			con.prepareStatement(CREATE_PHONE_INFO).execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public static Connection getConn() {
		Connection conn = td.get();
		if (conn == null) {
			td.set(getLocalConn());
			conn = td.get();
		} else {
			try {
				if(conn.isClosed()){
					td.set(getLocalConn());
					conn = td.get();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return conn;
		}

		return conn;

	}

	private static Connection getLocalConn() {
		Connection con = null;
		try {
			
			Class.forName("org.mariadb.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/zuanqianbao?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
//			 con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/liuwei?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
//			con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/liuwei?user=root&password=coco5200&useUnicode=true&characterEncoding=utf8");
//			 con =DriverManager.getConnection("jdbc:mariadb://sunsonfly.synology.me:3306/liuwei?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
			// con =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/liuwei?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
			// con =
//			 DriverManager.getConnection("jdbc:mysql://sunsonfly.synology.me:3306/liuwei?user=liuwei&password=coco5200&useUnicode=true&characterEncoding=utf8");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void close() {
		Connection con = td.get();
		if (con != null) {
			try {
				con.close();
				td.set(null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
