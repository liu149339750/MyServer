package com.lw.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.lw.entity.Device;

public class LoginTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		Device device = new Device();
		device.setAndroid_id("test");
		device.setDevice("HTI928");
		device.setMac("sdasdasdas");
		device.setMac("dsadsadas");
		Gson gson = new Gson();
		
		URL url  = new URL("http://sunsonfly.synology.me:7070/zuanqian/login");
		HttpURLConnection http =  (HttpURLConnection) url.openConnection();
		http.setDoOutput(true);
		OutputStream out = http.getOutputStream();
		out.write(gson.toJson(device).getBytes("utf-8"));
		out.close();
		
		String id = http.getHeaderField("id");
		
		System.out.println("id = " + id);
	}

}
