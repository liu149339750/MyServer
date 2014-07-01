package com.lw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
