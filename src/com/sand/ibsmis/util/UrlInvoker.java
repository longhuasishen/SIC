package com.sand.ibsmis.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class UrlInvoker {
	private static Logger log = Logger.getLogger(UrlInvoker.class);
	public static String urlConnectionReaderThrowEx(String urlStr,String charset) throws Exception {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null ;
		URLConnection uc = null;
		try {
			log.info("1.将进行HttpPost请求");
			URL url = new URL(urlStr);
			uc = url.openConnection();
			uc.setConnectTimeout(20000);  // 20秒连接超时
			uc.setReadTimeout(20000); //20秒读取超时
			log.info("2.成功创建连接到["+url+"]连接");
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(), charset));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine + System.getProperty("line.separator"));
			}
			log.info("3.成功获得数据长度["+sb.toString().length()+"],并断开连接");
		} catch (Exception e) {
			log.info("连接异常"+e.getMessage());
			throw e;
		}finally{
			if(in != null) in.close();
		}
		return sb.toString().trim();
	}
}
