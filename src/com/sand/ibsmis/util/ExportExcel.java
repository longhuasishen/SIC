package com.sand.ibsmis.util;


import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.record.formula.functions.Cell;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import com.sun.rowset.internal.Row;

/**
 * 
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 * 
 */
public class ExportExcel<T> {
	public void exportExcel(Collection<T> dataset, OutputStream out) {
		exportExcel("Sheet1", null, dataset, out, "yyyy-MM-dd");
	}
	public void exportExcel(String[] headers, Collection<T> dataset,
	OutputStream out) {
		exportExcel("Sheet1", headers, dataset, out, "yyyy-MM-dd");
	}
	public void exportExcel(String[] headers, Collection<T> dataset,
	OutputStream out, String pattern) {
		exportExcel("Sheet1", headers, dataset, out, pattern);
	}
	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * @param title
	 * 表格标题名 
	 * @param headers
	 *  表格属性列名数组
	 * @param dataset
	 * 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 * javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据) 
	 * @param out
	 * 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 * 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */

	@SuppressWarnings("unchecked")
	public void exportExcel(String title, String[] headers,
	Collection<T> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 25);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		//font2.setColor(HSSFColor.BLACK.index);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		/*HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("商户交易记录信息"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("sand");*/
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
				+ fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
					new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// if (value instanceof Integer) {
					// int intValue = (Integer) value;
					// cell.setCellValue(intValue);
					// } else if (value instanceof Float) {
					// float fValue = (Float) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(fValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Double) {
					// double dValue = (Double) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(dValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Long) {
					// long longValue = (Long) value;
					// cell.setCellValue(longValue);
					// }
					/* if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
						1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
						bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {*/
						// 其它数据类型都当作字符串简单处理
					    if(value==null){
					    	textValue="";
					    }else{
					    	textValue = value.toString();
					    }
//					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						/*Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);*/
						/*if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {*/
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							/*HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLACK.index);
							richString.applyFont(font3);*/
							cell.setCellValue(richString);
//						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getExcel(String filename){
		try {
			//1、创建工作簿
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(filename));
			//2、循环sheet
			for(int i=0;i<workbook.getNumberOfSheets();i++){
				if(workbook.getSheetAt(i).getPhysicalNumberOfRows()>0){
					HSSFSheet childSheet=workbook.getSheetAt(i);
					//3、循环sheet的行
					for(Iterator rit=childSheet.rowIterator();rit.hasNext();){
						HSSFRow row=(HSSFRow)rit.next();
						//4、循环每一行的每一列
						if(row.getRowNum()>0){
							Vector datas = new Vector();
							HashMap<Integer, Object> paramMap=new HashMap<Integer, Object>();
							for(Iterator cit=row.cellIterator();cit.hasNext();){
								HSSFCell cell=(HSSFCell) cit.next();
								if(cell.getCellNum()>0){
									String valueStr=this.getValue2007(cell);
									System.out.println(valueStr);
									datas.add(valueStr);
									paramMap.put(paramMap.size()+1, valueStr);
								}
							}
							insertToTable("airport", paramMap);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getValue2007(HSSFCell cell){
		String value="";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING://字符串型
			value=cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC://数字型，日期和纯数字
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				value=sdf.format(cell.getDateCellValue());
			}else{
				value=String.valueOf(cell.getNumericCellValue());
			}
		case HSSFCell.CELL_TYPE_BOOLEAN://布尔型
			value=" "+cell.getBooleanCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:// 公式型
			value=cell.getCellFormula();
		default:
			break;
		}
		return value;
	}
	public boolean insertToTable(String tableName,HashMap<Integer, Object> paramMap){
		boolean r=false;
		String insertSql="insert into "+tableName+" values(?,?,?,?)";
		Connection conn = this.conn();
		int i=-1;
		try{
			i=save(conn, insertSql, paramMap);
			if(i>0){
				r=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return r;
	}
	/**
	 * 用于连接oracle数据库的方法
	 * 只需修改中的参数getConnection("url","用户名","密码");
	 */
	public Connection conn(){
		try {
		//第一步：加载JDBC驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//第二步：创建数据库连接
		Connection con =DriverManager.getConnection("jdbc:oracle:thin:@172.28.250.91:1521:oraibs", "sandibs", "sandibs1234");
		return con;
		}catch(ClassNotFoundException cnf){
		  System.out.println("driver not find:"+cnf);
		  return null;
		}catch(SQLException sqle){
		  System.out.println("can't connection db:"+sqle);
		  return null;
		}
		  catch (Exception e) {
		System.out.println("Failed to load JDBC/ODBC driver.");
		return null;
		}
	}
	public int save(Connection connection, String insert,
			Map<Integer, Object> paramMap) throws SQLException {
		PreparedStatement ps = null;
		int res = 0;
		try{
			ps = connection.prepareStatement(insert);
			if(paramMap != null){
				Iterator<Integer> ci = paramMap.keySet().iterator();
				for (int i = 1; ci.hasNext(); i++) {
					int index = ci.next();
					ps.setObject(index, paramMap.get(index));
				}
			}
			res = ps.executeUpdate();
		}catch(Exception e){
			throw new SQLException(e.getMessage());
		}finally{
			try {
				if(ps != null) ps.close();
			} catch (SQLException e) {
				throw new SQLException(e.getMessage());
			}
		}
		return res;
	}
}

