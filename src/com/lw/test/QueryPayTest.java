package com.lw.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lw.entity.PayEntity;
import com.lw.entity.QueryPayEntity;

public class QueryPayTest {

	
	public static void main(String[] args) throws IOException {
		QueryPayEntity query = new QueryPayEntity();
		query.setFlag(QueryPayEntity.FLAG_QUERY_TODAY_PAYS);
		
		Gson gson = new Gson();
//		URL url  = new URL("http://localhost:8080/MyServer/exchange");
		URL url  = new URL("http://192.168.1.109:8080/MyServer/querypay");
		HttpURLConnection http =  (HttpURLConnection) url.openConnection();
		http.setDoOutput(true);
		OutputStream out = http.getOutputStream();
		out.write(gson.toJson(query).getBytes("utf-8"));
		out.close();
		InputStream in = http.getInputStream();
		Type type = new TypeToken<List<PayEntity>>(){}.getType();
		List<PayEntity> data = gson.fromJson(new InputStreamReader(in,"utf-8"), type);
		System.out.println(gson.toJson(data));
	}
}
