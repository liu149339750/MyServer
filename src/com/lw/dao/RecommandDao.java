package com.lw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lw.db.DBUtil;
import com.lw.entity.RecommandDeviceEntity;

public class RecommandDao {

	private final String GET_RECOMMAND_DEVICE = "select recommand_id,point,device from recommand where device_id = ?";
	private final String SPEND_POINT = "updata recommand set point = 0,time = now() where recommand_id = ?";
	private final String UPDATA_DEVICEID_RELATION = "updata recommand set device_id = ? where device_id = ?";
	private final String UPDATA_RECOMMANDID_RELATION = "updata recommand set recommand_id = ? where recommand_id = ?";
	
	public List<RecommandDeviceEntity> getRecommandDevice(int deviceId){
		Connection connection = DBUtil.getConn();
		List<RecommandDeviceEntity> data = new ArrayList<RecommandDeviceEntity>();
		try {
			PreparedStatement ps = connection.prepareStatement(GET_RECOMMAND_DEVICE);
			ps.setInt(1, deviceId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				int rId = rs.getInt(1);
				int point = rs.getInt(2);
				String device = rs.getString(3);
				RecommandDeviceEntity re = new RecommandDeviceEntity();
				re.setPoint(point);
				re.setRecommand_id(rId);
				int d = device.lastIndexOf("_");
				if(d != -1){
					re.setDevice(device.substring(0, d));
					re.setVersion(device.substring(d+1));
				}else
					re.setDevice(device);
				data.add(re);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return data;
	}
	
	public boolean PickPoint(int recommandId){
		Connection con = DBUtil.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement(SPEND_POINT);
			ps.setInt(1, recommandId);
			int count = ps.executeUpdate();
			if(count > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return false;
	}
	
	public void updataRelation(int oldId,int newId){
		Connection con = DBUtil.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement(UPDATA_DEVICEID_RELATION);
			ps.setInt(1, newId);
			ps.setInt(2, oldId);
			ps.executeUpdate();
			
			ps = con.prepareStatement(UPDATA_RECOMMANDID_RELATION);
			ps.setInt(1, newId);
			ps.setInt(2, oldId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		
	}
}
