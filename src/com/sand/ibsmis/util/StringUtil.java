package com.sand.ibsmis.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


public class StringUtil {
	
	//Spring Id 标识配置
	public static final String SPRING_BEAN_ID_FASTHREADPOOL = "ibsComThreadPool";
	public static final String SPRING_BEAN_ID_UPSASYNHANDLER = "upsAsynHandler";
	
	
	public static final String COMPANY_SERVICE = "companyServiceHandler";//商户业务对象名称
	public static final String QUERYMONEY_SERVICE = "queryMoneyServiceHandler";//商户业务对象名称
	/**
	 * 获取指定日期相对日期的字符串
	 * 
	 * @param patten
	 *            格式化字符串
	 * @return
	 */
	public static String getBeforeSomeDate(Date date, String patten,int delay) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + delay);
		SimpleDateFormat sf = new SimpleDateFormat(patten);
		return sf.format(c.getTime());
	}
	/**
	 * 左边填充
	 */
	public static int FILL_TYPE_LEFT = 0;

	/**
	 * 右边填充
	 */
	public static int FILL_TYPE_RIGHT = 1;
	
	/**
	 * 两头填充
	 */
	public static int FILL_TYPE_BOTH = 2;
	/**
	 * 字符串填充方法
	 * 
	 * @param source
	 *            原始字符串
	 * @param fillChar
	 *            填充字符
	 * @param length
	 *            填充后的长度
	 * @param fillType
	 *            填充类型
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String fillString(String source, char fillChar, int length,
			int fillType) throws UnsupportedEncodingException {
		byte[] bss = source.getBytes("GBK");
		int len = bss.length;
		if (len >= length)
			return fillType == FILL_TYPE_RIGHT ? new String(bss, 0, length)
					: new String(bss, len - length, length);
		for (int i = 0, n = length - len; i < n; i++)
			source = (fillType == FILL_TYPE_LEFT) ? fillChar + source : source
					+ fillChar;
		return source;
	}
	public static String currentDay(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	/**
	 * 返回解析后的Date对象
	 * 
	 * @param dateStr
	 * @param patten
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateStr, String patten) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(patten);
		return sf.parse(dateStr);
	}
	public static String dateToString(Date date, String patten) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(patten);
		return sf.format(date);
	}
	/**
	 * 获取下一天日期
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getAfterDay(String date,int n) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTime(sf.parse(date));
		ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH) + n);
		return sf.format(ca.getTime());

	}
	/**
	 * 获取下个月日期
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getNextMonth(String date) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTime(sf.parse(date));
		ca.add(Calendar.MONTH,1); //将当前日期加一个月
		return sf.format(ca.getTime());
		
	}
	public static String[] nextToken(String s) {
	    String[] result = new String[2];
	    if ('"' == s.charAt(1)) {
	      String[] str = s.split("\"");
	      result[0] = str[1];
	      result[1] = s.substring(s.indexOf("\"", 2) + 1);
	    } else {
	      result[0] = "";
	      result[1] = s.substring(1);
	    }
	    return result;
	  }
	public static double myParseDouble(String s) {
	    String t = s.replaceAll(",", "");
	    return Double.parseDouble(t);
	  }
	public static void main(String[] args) throws Exception {
//		System.out.println(StringUtil.getBeforeSomeDate(new Date(), "yyyyMMdd", -1));
//		System.out.println("20121116".substring(2,"20121116".length()));
//		String status = "20121211";
//		System.out.println(status.substring(0, 4)+"-"+status.substring(4, 6)+"-"+status.substring(6, 8));
//		float a = (float) 12.34;
//		float b = (float) 3.454;
//		String a = "12.34";
//		String b= "3.454";
//		System.out.println(Float.valueOf(a)-Float.valueOf(b));
		/*char r = '0';
		try {
			System.out.println(fillString("1", r, 5, 1));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			System.out.println("20140319160828".substring(4,6));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getIp(HttpServletRequest request){
		String loginIP="";
		if(request.getHeader("X-Real-IP")!=null){
			loginIP=request.getHeader("X-Real-IP");
		}else{
			loginIP=request.getRemoteAddr();
		}
		return loginIP;
	}
}