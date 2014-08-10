package com.lw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lw.dao.RecommandDao;
import com.lw.entity.RecommandDeviceEntity;
import com.lw.entity.RecommandEntity;
import com.lw.entity.ResultEntity;
import com.lw.util.Util;

public class RecommandServlet extends HttpServlet{

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		InputStream in = req.getInputStream();
		InputStreamReader ir = new InputStreamReader(in, "utf-8");
		Gson gson = new Gson();
		RecommandEntity re = gson.fromJson(ir, RecommandEntity.class);
		
		RecommandDao rd = new RecommandDao();
		int deviceId = re.getDevice_id();
		String json = null;
		int flag = re.getFlag();
		if(flag == RecommandEntity.FLAG_QUERY){  //≤È—Ø
			List<RecommandDeviceEntity> data = rd.getRecommandDevice(deviceId);
			json = gson.toJson(data);
		}else if(flag == RecommandEntity.FLAG_DEAL){ //Ã·»°
			boolean r = rd.PickPoint(deviceId);
			ResultEntity result = new ResultEntity();
			result.setResult(r?ResultEntity.RESULT_SUCESS:ResultEntity.RESULT_FAIL);
			result.setId(deviceId);
			json = gson.toJson(result);
		}
		
		if(!Util.isEmpty(json)){
			OutputStream out = resp.getOutputStream();
			out.write(json.getBytes("utf-8"));
			out.flush();
			out.close();
		}
	}
}
