package com.lw.sududa.deal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;


public class SududaRequest {

	private static String USERNAME = "hnyjliuwei@163.com";
	private static String KEY = "d50848adf19b57dfcc3246f91bc237fe";
	private static String POWER = "17";
	private static String VER = "3";
	
	protected  String getPriceTableUrl(){
		long now = System.currentTimeMillis();
		String time = (now + "").substring(0, (now + "").length() - 3);
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("username", USERNAME);
		map.put("power", POWER);
		map.put("ver", VER);
		map.put("gzip", "true");
		map.put("timestamp", time);
		String rs = null;
		try {
			rs = SududaUtil.getRequestURL("/api/product", map, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	private static  String getPhoneAreaUrl(String number){
		long now = System.currentTimeMillis();
		String time = (now + "").substring(0, (now + "").length() - 3);
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("username", USERNAME);
		map.put("power", POWER);
		map.put("ver", VER);
		map.put("gzip", "true");
		map.put("timestamp", time);
		map.put("format", "json");
		map.put("phone", number);
		String rs = null;
		try {
			rs = SududaUtil.getRequestURL("/api/sys_phone", map, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	protected static String getPhoninfoData(String number) throws IOException{
		String link = getPhoneAreaUrl(number);
		URL url = new URL(link);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		InputStream in = con.getInputStream();
		GZIPInputStream gin = new GZIPInputStream(in);
		BufferedReader br = new BufferedReader(new InputStreamReader(gin,"utf-8"));
		StringBuffer sb = new StringBuffer();
		String r = null;
		while((r = br.readLine()) != null)
			sb.append(r);
		br.close();
		return sb.toString();
	}
	
	private static String getRehargeUrl(String productId,String orderId,String to,int count){
		long now = System.currentTimeMillis();
		String time = (now + "").substring(0, (now + "").length() - 3);
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("username", USERNAME);
		map.put("orderid", orderId);
		map.put("productid", productId);
		map.put("to", to);
		map.put("count", count + "");
		map.put("timestamp", time);
		map.put("ver", VER);
		
		map.put("gzip", "true");
		map.put("power", POWER);
		map.put("format", "json");
		
		String rs = null;
		try {
			rs = SududaUtil.getRequestURL("/api/recharge", map, KEY);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	private static String getStatusUrl(String orderId){
		long now = System.currentTimeMillis();
		String time = (now + "").substring(0, (now + "").length() - 3);
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("username", USERNAME);
		map.put("orderid", orderId);
		map.put("timestamp", time);
		map.put("ver", VER);
		
		map.put("gzip", "true");
		map.put("power", POWER);
		map.put("format", "json");
		
		String rs = null;
		try {
			rs = SududaUtil.getRequestURL("/api/status", map, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	protected static String getStatusResult(String orderId) throws MalformedURLException, UnsupportedEncodingException, IOException{
		String link = getStatusUrl(orderId);
		String result = getHttpResult(link);
		return result;
	}
	
	protected static String getRechargeResult(String productId,String orderId,String to, int count) throws MalformedURLException, UnsupportedEncodingException, IOException{
		String link = getRehargeUrl(productId, orderId, to, count);
		String result = getHttpResult(link);
		return result;
	}

	/**get result from a http request*/
	private static String getHttpResult(String link)
			throws MalformedURLException, IOException,
			UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		URL url = new URL(link);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		InputStream in = con.getInputStream();
		GZIPInputStream gin = new GZIPInputStream(in);
		BufferedReader br = new BufferedReader(new InputStreamReader(gin,"utf-8"));
		String r = null;
		while((r = br.readLine()) != null)
			sb.append(r);
		br.close();
		
		return sb.toString();
	}
	
}
