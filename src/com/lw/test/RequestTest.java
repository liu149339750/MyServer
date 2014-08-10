package com.lw.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestTest {
	
	public static void main(String[] args) throws IOException {
		URL url  = new URL("http://localhost:8080/MyServer/request");
		HttpURLConnection http =  (HttpURLConnection) url.openConnection();
		http.getInputStream().close();
	}
}
