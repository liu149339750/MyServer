package com.lw.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;

public class Util {
	
	
	public static boolean isEmpty(String str){
		if(str == null || str.length() == 0)
			return true;
		return false;
	}
	
	public static String getFormmaterTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		return sdf.format(new Date());
	}
	
	public static String readStream(InputStream in) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer sb = new StringBuffer();
		String temp = null;
		while((temp = br.readLine()) != null){
			sb.append(temp);
		}
		return sb.toString();
	}
	
	public static void sendErrorEmail(Exception e,String title){
		StackTraceElement st[] = e.getStackTrace();
		String message = e.toString();
		for(int i = 0;i<st.length;i++){
			message = message + "\n" + st[i];
		}
		EmailSend.sendEmail(title, message);
	}
	
	public static boolean auto(ServletContext servletContext){
		Properties p = new Properties();
		try {
			
			InputStream input = servletContext.getResourceAsStream("config.txt");
			p.load(input);
			String r = p.getProperty("auto");
			input.close();
			return "true".equals(r);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
