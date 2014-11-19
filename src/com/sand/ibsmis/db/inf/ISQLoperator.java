package com.sand.ibsmis.db.inf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author he.y
 * 
 * 数据库操作的接口
 */
public interface ISQLoperator {
	
	public List<HashMap<String,Object>> query(Connection connection,String sql,Map<Integer,Object> paramMap) throws SQLException;

	public int save(Connection connection,  String insert,  Map<Integer,Object> paramMap) throws SQLException;
	
	public int update(Connection connection,String update,Map<Integer,Object> paramMap) throws SQLException;
	
	public int delete(Connection connection,String delete,Map<Integer,Object> paramMap) throws SQLException;
	
	public HashMap<String,Object> queryAlone(Connection connection,String sql,Map<Integer,Object> paramMap) throws SQLException;
	
	public Boolean callProc(Connection connection,String procName,List<Object> pList,Map<Integer,Integer> oList,List<Object>oData) throws SQLException;

	
}
