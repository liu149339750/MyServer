package com.lw.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.lw.entity.ExchangeEntity;

public class ExchangeTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ExchangeEntity ee = new ExchangeEntity();
		ee.setDeviceId(123);
		ee.setMoney("5");
		ee.setNumber("13222526711");
		ee.setType(1);
		ee.setSpendPoint(500);
		
		Gson gson = new Gson();
//		URL url  = new URL("http://localhost:8080/MyServer/exchange");
		URL url  = new URL("http://sunsonfly.synology.me:7070/MyServer/exchange");
		HttpURLConnection http =  (HttpURLConnection) url.openConnection();
		http.setDoOutput(true);
		OutputStream out = http.getOutputStream();
		out.write(gson.toJson(ee).getBytes("utf-8"));
		out.close();
		System.out.println(http.getHeaderField("result"));
	}

}
