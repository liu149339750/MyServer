package com.lw.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lw.entity.AdminRequest;
import com.lw.entity.CheatEntity;
import com.lw.entity.CoastEntity;
import com.lw.entity.DealEntity;
import com.lw.entity.ExchangeEntity;
import com.lw.entity.OrderInfo;

public class AdminTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		AdminRequest ar = new AdminRequest();
//		URL url  = new URL("http://sunsonfly.synology.me:7070/zuanqian/admin");
		URL url  = new URL("http://localhost:8080/MyServer/admin");
		HttpURLConnection http =  (HttpURLConnection) url.openConnection();
		http.setDoOutput(true);
//		testOrderInfo(ar,http);
//		testUnPay(ar, http);
//		testPay(ar, http);
//		testdeal(ar, http);
		
//		testCheat(ar, http);
		
		testCoastQuery(ar, http);
	}

	private static void testCoastQuery(AdminRequest ar, HttpURLConnection http)
			throws IOException, UnsupportedEncodingException {
		ar.setFlag(AdminRequest.FLAG_QUERY_COSAT);
		http.setRequestProperty("days", "0");
		OutputStream out = http.getOutputStream();
		Gson gson = new Gson();
		String json = gson.toJson(ar);
		out.write(json.getBytes("utf-8"));
		out.close();
		InputStream in = http.getInputStream();
		CoastEntity data = gson.fromJson(new InputStreamReader(in,"utf-8"), CoastEntity.class);
		System.out.println(data.getMoney());
	}

	private static void testCheat(AdminRequest ar, HttpURLConnection http)
			throws IOException, UnsupportedEncodingException {
		ar.setFlag(AdminRequest.FLAG_QUERY_IS_CHEAT);
		ar.setDevice_id(612);
		Gson gson = new Gson();
		String json = gson.toJson(ar);
		OutputStream out = http.getOutputStream();
		out.write(json.getBytes("utf-8"));
		out.close();
		InputStream in = http.getInputStream();
		CheatEntity data = gson.fromJson(new InputStreamReader(in,"utf-8"), CheatEntity.class);
		System.out.println(data.getEarnPoint() + ":" + data.getSpendPoint() + " >>" +data.getTotalPoint());
	}

	private static void testdeal(AdminRequest ar, HttpURLConnection http)
			throws IOException, UnsupportedEncodingException {
		ar.setFlag(AdminRequest.FLAG_QUERY_DEAL_BY_DEVICE);
		ar.setDevice_id(256);
		OutputStream out = http.getOutputStream();
		Gson gson = new Gson();
		String json = gson.toJson(ar);
		out.write(json.getBytes("utf-8"));
		out.close();
		InputStream in = http.getInputStream();
		Type type = new TypeToken<List<DealEntity>>(){}.getType();
		List<DealEntity> data = gson.fromJson(new InputStreamReader(in,"utf-8"), type);
		for(DealEntity oi : data){
			System.out.println(oi.getTime() + "  " + oi.getMessage());
		}
	}

	private static void testPay(AdminRequest ar, HttpURLConnection http)
			throws IOException, UnsupportedEncodingException {
		ar.setFlag(AdminRequest.FLAG_QUERY_PAY_BY_DEVICE);
		ar.setDevice_id(256);
		OutputStream out = http.getOutputStream();
		Gson gson = new Gson();
		String json = gson.toJson(ar);
		out.write(json.getBytes("utf-8"));
		out.close();
		InputStream in = http.getInputStream();
		Type type = new TypeToken<List<ExchangeEntity>>(){}.getType();
		List<ExchangeEntity> data = gson.fromJson(new InputStreamReader(in,"utf-8"), type);
		for(ExchangeEntity oi : data){
			System.out.println(oi.getTime() + "  " + oi.getNumber());
		}
	}

	private static void testUnPay(AdminRequest ar, HttpURLConnection http)
			throws IOException, UnsupportedEncodingException {
		ar.setFlag(AdminRequest.FLAG_QUERY_UNPAY);
		OutputStream out = http.getOutputStream();
		Gson gson = new Gson();
		String json = gson.toJson(ar);
		out.write(json.getBytes("utf-8"));
		out.close();
		InputStream in = http.getInputStream();
		Type type = new TypeToken<List<ExchangeEntity>>(){}.getType();
		List<ExchangeEntity> data = gson.fromJson(new InputStreamReader(in,"utf-8"), type);
		for(ExchangeEntity oi : data){
			System.out.println(oi.getTime() + "  " + oi.getNumber());
		}
	}

	private static void testOrderInfo(AdminRequest ar,HttpURLConnection http)
			throws MalformedURLException, IOException,
			UnsupportedEncodingException {
		ar.setFlag(AdminRequest.FLAG_QUERY_ORFER_BY_DEVICE);
		ar.setDevice_id(569);
		
		OutputStream out = http.getOutputStream();
		Gson gson = new Gson();
		String json = gson.toJson(ar);
		out.write(json.getBytes("utf-8"));
		out.close();
		InputStream in = http.getInputStream();
		Type type = new TypeToken<List<OrderInfo>>(){}.getType();
		List<OrderInfo> data = gson.fromJson(new InputStreamReader(in,"utf-8"), type);
		for(OrderInfo oi : data){
			System.out.println(oi.getStime() + "  " + oi.getMessage());
		}
	}

}
