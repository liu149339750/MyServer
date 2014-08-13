package com.lw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lw.db.DBUtil;
import com.lw.entity.RankEntity;

public class RankDao {

	private String RANK_BY_NUMBER = "select sum(money) as money,number,max(time) from pay group by number order by money desc limit 20";
	
	public List<RankEntity> getRanks(){
		Connection con = DBUtil.getConn();
		List<RankEntity> result = new ArrayList<RankEntity>();
		try {
			PreparedStatement ps = con.prepareStatement(RANK_BY_NUMBER);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				RankEntity re = new RankEntity();
				re.setMoney(rs.getInt(1));
				re.setNumber(rs.getString(2));
				re.setTime(rs.getString(3));
				result.add(re);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close();
		}
		return result;
	}
}
