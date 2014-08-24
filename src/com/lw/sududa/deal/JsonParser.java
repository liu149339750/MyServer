package com.lw.sududa.deal;
import java.io.IOException;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;
import com.lw.sududa.entity.ChargeResutEntity;
import com.lw.sududa.entity.PhoneEntity;

public class JsonParser {

	public PhoneEntity getPhoneEntity(String json) {
		JsonReader reader = new JsonReader(new StringReader(json));
		PhoneEntity pe = new PhoneEntity();
		try {
			reader.beginObject();
			if (reader.hasNext()){
				String v = reader.nextName();
				if (v.equals("sududa")) {
					reader.beginObject();
					while (reader.hasNext()) {
						String name = reader.nextName();
						if (name.equals("type")) {
							pe.setType(reader.nextString());
						} else if (name.equals("city")) {
							pe.setCity(reader.nextString());
						} else if (name.equals("status")) {
							pe.setStatus(reader.nextInt());
						}else if(name.equals("areacode")){
							pe.setAreacode(reader.nextString());
						} else if(name.equals("zipcode")){
							pe.setZipcode(reader.nextString());
						} else
							reader.skipValue();
					}
					reader.endObject();
				}
			}
			reader.endObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pe;
	}

	public ChargeResutEntity getChargeEntity(String json) {
		JsonReader reader = new JsonReader(new StringReader(json));
		ChargeResutEntity cr = new ChargeResutEntity();
		try {
			reader.beginObject();
			if (reader.hasNext()){
				String v = reader.nextName();
				if (v.equals("sududa")) {
					reader.beginObject();
					while (reader.hasNext()) {
						String name = reader.nextName();
						if (name.equals("balance")) {
							cr.setBalance((float)reader.nextDouble());
						} else if (name.equals("tips")) {
							cr.setTips(reader.nextString());
						} else if (name.equals("status")) {
							cr.setStatus(reader.nextInt());
						} else
							reader.skipValue();
					}
					reader.endObject();
				}
			}
			reader.endObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cr;
	}

}
