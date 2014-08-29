package com.lw.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;

import com.lw.sududa.deal.DataLayer;
import com.lw.sududa.entity.PhoneEntity;

public class Test {

	public static void main(String[] args) {
//		Properties p = new Properties();
//		try {
//			p.load(new FileInputStream("src/config.properties"));
//			System.out.println(p.getProperty("auto"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
        DecimalFormat df = new DecimalFormat("0.0");
        System.out.println(df.format(13.2354));
        
        try {
			PhoneEntity pe = DataLayer.getPhoneInfo("18756164155");
			System.out.println(pe.getCity());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
