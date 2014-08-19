package com.lw.sududa.deal;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class SududaUtil {

	
	/**
	 * 根据签名方式获取请求URL
	 * @author ac
	 * @param para     请求参数map集合
	 * @param path	       请求接口名称 如：/api/recharge
	 * @param key      密匙
	 * **/
	public static String getRequestURL(String path, Map<String, String> para, String key) throws UnsupportedEncodingException
	{

		String url = "http://www.chongzhi.com";// 该参数必须按实际请求路径修改
		String param = "";
		String signType = "";
		if (path.indexOf("/") != 0)
		{
			path = "/" + path;
		}
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(para.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			// ----------------------按key排序----------------------
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)
			{
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		// ----------------------排序后获得签名串----------------------
		for (int i = 0; i < list.size(); i++)
		{
			Map.Entry<String, String> m = list.get(i);
			String k = m.getKey();
			param += k + "=" + m.getValue() + "&";
		}
		param = path + "?" + param;
		url += param;
		if (!"".equals(key) && key != null)
		{
			// signkey时需追加密匙
			param += key;
			if ("".equals(key))
			{
				signType = "sign";
			}
			else
			{
				signType = "signkey";
			}
		}
		else
		{
			// sign时需去掉最后一个&
			param = param.substring(0, param.length() - 1);
			signType = "sign";
		}
//		System.out.println(URLEncoder.encode(param, "UTF-8"));
		String sign = md5(URLEncoder.encode(param, "UTF-8"));// 签名串 URL编码
		url += signType + "=" + sign;
		return url;
	}

	/**
	 * md5加密
	 * @author ac
	 * **/
	public static String md5(String str)
	{
		MessageDigest md5;
		StringBuffer md5StrBuff = new StringBuffer();
		try
		{
			md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			byte[] domain = md5.digest();
			for (int i = 0; i < domain.length; i++)
			{
				if (Integer.toHexString(0xFF & domain[i]).length() == 1)
				{
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & domain[i]));
				}
				else
					md5StrBuff.append(Integer.toHexString(0xFF & domain[i]));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return md5StrBuff.toString().toLowerCase();
	}
}
