package com.sand.ibsmis.util;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.sand.ibsmis.bean.User;

public class PoiUtil {

	public static void main(String[] args) throws Exception {
		ExportExcel<Object> excel=new ExportExcel<Object>();
		/*List list=new ArrayList<User>();
		User user=new User();
		user.setUsername("sandadmin");
		user.setPassword("sandadmin");
		user.setLast_ip("127.0.0.1");
		user.setLast_login("sandadmin");
		user.setRole_id("3");
	//	user.setRole_name("第三方商户");
		list.add(user);
		String[] hearders = new String[] {"交易流水号", "交易流水时间", "商城订单号", "交易金额", "交易时间","第三方订单号", "交易状态"};//表头数组
	    FileOutputStream os = new FileOutputStream("D:\\workbook.xls");
	    excel.exportExcel(hearders, list, os);
	    os.close();*/
		excel.getExcel("E:\\sand\\东航对账查询语句\\airport.csv");
	}

}



