package com.sand.ibsmis.dbutil;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBConnectionManager {

	private static final Log logger = LogFactory.getLog(DBConnectionManager.class);
	
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void DatabaseManager() {

	}
	
	/**
	 * 获得一个数据库连接
	 * @return Connection对象 
	 */
	public synchronized Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("获得连接失败!"+e.getMessage());
		}
		if (conn != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Connected");
			}
		}
		return conn;
	}

	/**
	 * 释放从连接池取得的连接
	 * @param conn Connection连接对象
	 */
	public synchronized void releaseConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				if (logger.isDebugEnabled()) {
					logger.debug("Connection released");
				}
			} catch (SQLException sqlex) {
				if (conn != null)
					try {
						conn.rollback();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				logger.error("Release connection failed");
			} finally {
				conn = null;
			}
		}
	}
}
