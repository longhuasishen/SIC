package com.sand.ibsmis.db.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sand.ibsmis.db.inf.ISQLoperator;



/** 
 * @author  he.y
 *
 * 常用操作实现类
 */
public class SQLoperatorImpl implements ISQLoperator {

	private Log logger = LogFactory.getLog(SQLoperatorImpl.class);
	
	public int delete(Connection connection, String delete,
			Map<Integer, Object> paramMap) throws SQLException {
		PreparedStatement ps = null;
		if(logger.isDebugEnabled()){
			logger.debug("删除时语句为：" + delete);
		}
		int res = 0;
		try{
			ps = connection.prepareStatement(delete);
			if(paramMap != null){
				Iterator<Integer> ci = paramMap.keySet().iterator();
				for (int i = 1; ci.hasNext(); i++) {
					int index = ci.next();
					ps.setObject(index, paramMap.get(index));
				}
			}
			res = ps.executeUpdate();
		}catch(Exception e){
			logger.error(" delete error： " + e.getMessage());
			throw new SQLException(e.getMessage());
		}finally{
			try {
				if(ps != null) ps.close();
			} catch (SQLException e) {
				logger.error(" delete error： " + e.getMessage());
				throw new SQLException(e.getMessage());
			}
		}
		return res;
	}

	public List<HashMap<String, Object>> query(Connection connection,
			String sql, Map<Integer, Object> paramMap) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(logger.isDebugEnabled()){
			logger.debug("条件查询时语句为：" + sql);
		}
		List<HashMap<String, Object>> res = null;
		try{
			ps = connection.prepareStatement(sql);
			if(paramMap != null){
				Iterator<Integer> ci = paramMap.keySet().iterator();
				for (int i = 1; ci.hasNext(); i++) {
					int index = ci.next();
					ps.setObject(index, paramMap.get(index));
				}
			}
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int cc = metaData.getColumnCount();
			String[] cn = new String[cc];
			Integer[] ct = new Integer[cc];
			for (int i = 0; i < cc; i++) {
				cn[i] = metaData.getColumnName(i+1);
				ct[i]= metaData.getColumnType(i+1);
			}
			res = new ArrayList<HashMap<String,Object>>();
			while(rs.next()){
				HashMap<String,Object> hd = new HashMap<String, Object>();
				for (int i = 0; i < cn.length; i++) {
					String columnN = cn[i];
					hd.put(columnN.toLowerCase(), rs.getObject(columnN));
				}
				res.add(hd);
			}
		}catch(Exception e){
			logger.error(" query error： " + e.getMessage());
			throw new SQLException(e.getMessage());
		}finally{
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			} catch (SQLException e) {
				logger.error(" query error： " + e.getMessage());
				throw new SQLException(e.getMessage());
			}
		}
		return res;
	}

	public int save(Connection connection, String insert,
			Map<Integer, Object> paramMap) throws SQLException {
		PreparedStatement ps = null;
		if(logger.isDebugEnabled()){
			logger.debug("保存时语句为：" + insert);
		}
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
			logger.error(" save error： " + e.getMessage());
			throw new SQLException(e.getMessage());
		}finally{
			try {
				if(ps != null) ps.close();
			} catch (SQLException e) {
				logger.error(" save error： " + e.getMessage());
				throw new SQLException(e.getMessage());
			}
		}
		return res;
	}

	public int update(Connection connection, String update,
			Map<Integer, Object> paramMap) throws SQLException {
		PreparedStatement ps = null;
		if(logger.isDebugEnabled()){
			logger.debug("更新时语句为：" + update);
		}
		int res = 0;
		try{
			ps = connection.prepareStatement(update);
			if(paramMap != null){
				Iterator<Integer> ci = paramMap.keySet().iterator();
				for (int i = 1; ci.hasNext(); i++) {
					int index = ci.next();
					ps.setObject(index, paramMap.get(index));
				}
			}
			res = ps.executeUpdate();
		}catch(Exception e){
			logger.error(" update error： " + e.getMessage());
			throw new SQLException(e.getMessage());
		}finally{
			try {
				if(ps != null) ps.close();
			} catch (SQLException e) {
				logger.error(" update error： " + e.getMessage());
				throw new SQLException(e.getMessage());
			}
		}
		return res;
	}

	public HashMap<String, Object> queryAlone(Connection connection,String sql, Map<Integer, Object> paramMap) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(logger.isDebugEnabled()){
			logger.debug("单个查询时语句为：" + sql);
		}
		HashMap<String, Object> res = null;
		try{
			ps = connection.prepareStatement(sql);
			if(paramMap != null){
				Iterator<Integer> ci = paramMap.keySet().iterator();
				for (int i = 1; ci.hasNext(); i++) {
					int index = ci.next();
					ps.setObject(index, paramMap.get(index));
				}
			}
			
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int cc = metaData.getColumnCount();
			String[] cn = new String[cc];
			Integer[] ct = new Integer[cc];
			for (int i = 0; i < cc; i++) {
				cn[i] = metaData.getColumnName(i+1);
				ct[i]= metaData.getColumnType(i+1);
			}
			if(rs.next()){
				res = new HashMap<String,Object>();
				for (int i = 0; i < cn.length; i++) {
					String columnN = cn[i];
					res.put(columnN.toLowerCase(), rs.getObject(columnN));
				}
			}
		}catch(Exception e){
			logger.error(" query error： " + e.getMessage());
			throw new SQLException(e.getMessage());
		}finally{
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			} catch (SQLException e) {
				logger.error(" query error： " + e.getMessage());
				throw new SQLException(e.getMessage());
			}
		}
		return res;
	}

	/**
	 * 调用的存储过程
	 * @param connection 数据连接
	 * @param procName 存储过程名+参数占位符 procName = name(?,?,?,?)
	 * @param pList 输入参数集合
	 * @param omap  输入参数类型集合(不包括输入参数的位置,起始位置从0 开始)和值 类型 omap.put(0,Types.INTEGER);
	 * @param oList 输出参数的值 
	 * @return 执行的结果
	 */
	public Boolean callProc(Connection connection, String procName,List<Object> pList, Map<Integer, Integer> omap,List<Object> oData)throws SQLException {
		if(procName == null || !procName.contains("(") || !procName.contains(")")){
			return false;
		}
		boolean res  = false;
		CallableStatement st = null;
		try {
			st = connection.prepareCall("{call "+procName+"}");		
			int pi = 1;
			if(pList != null){
				for (;pi <= pList.size();){
					Object o = pList.get(pi-1);
					st.setObject(pi, o);
					pi = pi + 1; 
				}
			}
			if(omap != null){
				Set<Entry<Integer,Integer>> se = omap.entrySet();
				for (Entry<Integer, Integer> entry : se) {
				    int inx = pi + entry.getKey();
					st.registerOutParameter(inx, entry.getValue());
				}
			}
			
			st.execute();
			
			if(omap != null && omap.size() > 0){
				Set<Entry<Integer,Integer>> se = omap.entrySet();
				for (Entry<Integer, Integer> entry : se) {
				    int inx = pi + entry.getKey();
					oData.add(entry.getKey(),st.getObject(inx));
				}
			}
			
			res = true;
			
		}catch (SQLException e) {
			logger.error(" execute Proc error ： " + e.getMessage());
			res = false;
			throw new SQLException(e.getMessage());
		}finally{
			try {
				if(st != null) st.close();
			} catch (SQLException e) {
				logger.error(" execute Proc error ： " + e.getMessage());
				res = false;
				throw new SQLException(e.getMessage());
			}
		}
		return res;
	}
		
}
