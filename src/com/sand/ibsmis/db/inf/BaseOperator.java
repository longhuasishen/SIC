package com.sand.ibsmis.db.inf;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sand.ibsmis.dbutil.DBConnectionManager;


/**
 * @author he.y
 * 
 * 
 * 数据库操作父类
 */
public abstract class BaseOperator{
   
	public static final Log logger = LogFactory.getLog(BaseOperator.class);
	
	private DBConnectionManager session;
	
	private ISQLoperator  operator;

	public DBConnectionManager getSession() {
		return session;
	}

	public void setSession(DBConnectionManager session) {
		this.session = session;
	}

	public ISQLoperator getOperator() {
		return operator;
	}

	public void setOperator(ISQLoperator operator) {
		this.operator = operator;
	}
	
	public Connection getConnection() throws SQLException{
		Connection c = null;
		try{
			c = session.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("获得连接时出错：" + e.getMessage());
		}
		return c;
	}
	
	public void releaseConection(Connection conn) throws SQLException{
		try{
			session.releaseConnection(conn);
		}catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("释放连接时出错："+e.getMessage());
		}
	}
	
	public void releaseSavePoint(Connection conn,Savepoint point) throws SQLException{
		String name = "";
		try {
			name = point.getSavepointName();
			conn.releaseSavepoint(point);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("释放保存点[" + name + "]时出错："+e.getMessage());
		}
	}
	
	public Savepoint savePoint(Connection conn,String name) throws SQLException{
		try {
			return conn.setSavepoint(name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("设置保存点出错："+e.getMessage());
		}
	}
	
	public void commit(Connection conn) throws SQLException{
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("提交数据时出错："+e.getMessage());
		}
	}
	
	public void rollBack(Connection conn)throws SQLException{
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("回滚时出错："+e.getMessage());
		}
	}
	
	public void rollBackSavePoint(Connection conn,Savepoint point) throws SQLException{
		String name = "";
		try {
			name = point.getSavepointName();
			conn.rollback(point);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("回滚到保存点[" + name + "]时出错："+e.getMessage());
		}
	}
	
	public  List<HashMap<String,Object>> getDatas(Connection c,String sql,HashMap<Integer,Object> pm) throws SQLException{
		return operator.query(c, sql, pm);
	}
	
	public HashMap<String,Object> getAloneObject(Connection c,String sql,HashMap<Integer,Object> pm)throws SQLException{
		return operator.queryAlone(c, sql, pm);
	}
	
	@SuppressWarnings("unchecked")
	public boolean callProc(Connection connection, String procName,List<Object> pList, Map<Integer,Integer> omap, List oData)throws SQLException{
		return operator.callProc(connection, procName, pList, omap, oData);
	}
	
}
