package com.lw.test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.lw.entity.OrderInfo;

public class OrderTest {
//	private static final String ORDER = "http://sunsonfly.synology.me:7070/zuanqian/order";
	private static final String ORDER = "http://localhost:8080/MyServer/order";
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		OrderInfo order = new OrderInfo();
		order.setDeviceId(12);
		order.setMessage("my name is liuwei");
		order.setOrderId(32);
		order.setPoint(342);
		order.setStatus(1);
		order.setTime(System.currentTimeMillis());
		Gson gson = new Gson();
		List<OrderInfo>list = new ArrayList<OrderInfo>();
		list.add(order);
		String json = gson.toJson(list);
		System.out.println("json = " +json);
		URL url = new URL(ORDER);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setDoOutput(true);
		OutputStream out = http.getOutputStream();
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
		br.write(json);
		br.flush();
		br.close();
		System.out.println(http.getResponseCode());
//		System.out.println(http.getHeaderField("name"));
		System.out.println("send Order over");
	}

}
