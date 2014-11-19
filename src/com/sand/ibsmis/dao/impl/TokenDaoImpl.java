package com.sand.ibsmis.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sand.ibsmis.bean.Token;
import com.sand.ibsmis.bean.TokenCompany;
import com.sand.ibsmis.bean.TokenUser;
import com.sand.ibsmis.dao.inf.ITokenDao;
import com.sand.ibsmis.db.inf.BaseOperator;
import com.sand.ibsmis.dbutil.DataSourceContextHolder;
import com.sand.ibsmis.dbutil.DataSourceType;
import com.sand.ibsmis.util.Page;

public class TokenDaoImpl  extends BaseOperator implements ITokenDao{
	public Page<TokenUser> getUsers(int start, int end, Map<String, Object> queryCondition) throws SQLException{
		DataSourceContextHolder.setDataSourceType(DataSourceType.TOKEN);
		Connection c = null;
		int count = 0;
		Page<TokenUser> page = new Page<TokenUser>();
		List<HashMap<String, Object>> listData = null;
		String sql = "select user_name,eid,mobile, case  when token_id is not null and token_id !='' then '1' else '0' end as associate, token_id,  date_format(create_dtm,'%Y-%m-%d %H:%i:%s') as create_time,urgent from users ";
		String sqlCount = "select count(*)  as count from users ";
		List<TokenUser> lists = null;
		try{
			if(queryCondition != null && queryCondition.size() > 0){
				sql += " where ";
				sqlCount += " where ";
				for(Map.Entry<String, Object> entry : queryCondition.entrySet()){
					sql +=entry.getKey()+" like '%"+entry.getValue() +"%' and ";
					sqlCount += entry.getKey()+" like '%"+entry.getValue() +"%' and ";
				}
				if(sql.lastIndexOf("and") > -1){
					sql = sql.substring(0 ,sql.lastIndexOf("and"));
				}
				if(sqlCount.lastIndexOf("and") > -1){
					sqlCount = sqlCount.substring(0 ,sqlCount.lastIndexOf("and"));
				}
			}
			sql = sql+" limit "+start+", "+end;
			logger.info("调用Token的查询用户列表接口的sql为:"+sql);
			logger.info("调用Token查询总用户数sql为:"+sqlCount);
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
            c = getConnection();
            listData = getDatas(c, sql, paramMap); 
            HashMap<String, Object> countMap=getAloneObject(c, sqlCount, null);
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
            if(listData != null && listData.size() > 0){
            	lists = new ArrayList<TokenUser>();
            	page.setTotalCount(count);
            	for(int i = 0; i < listData.size(); i++){
            		TokenUser tokenUser = new TokenUser();
            		HashMap<String, Object> map = listData.get(i);
            		tokenUser.setUserName((String)map.get("user_name"));
            		tokenUser.setEid((String)map.get("eid"));
            		tokenUser.setTokenId((String)map.get("token_id"));
            		tokenUser.setMobile((String)map.get("mobile"));
            		tokenUser.setUrent((String)map.get("urgent"));
            		tokenUser.setCreateTime((String)map.get("create_time"));
            		tokenUser.setAssociate((String)map.get("associate"));
            		lists.add(tokenUser);
            	}
            	page.setResult(lists);
            }else{
            	lists=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
            }
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
			if(null!=c){
				try {
					releaseConection(c);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("释放连接出错");
				}
			}
		}
		
		return page;
	}
	
	public Page<Token> getTokens(int start, int end, Map<String, Object> queryCondition) throws SQLException{
		DataSourceContextHolder.setDataSourceType(DataSourceType.TOKEN);
		Connection c = null;
		int count = 0;
		Page<Token> page = new Page<Token>();
		List<HashMap<String, Object>> listData = null;
		String sql = "select id, associate,lk,reg,date_format(create_dtm,'%Y-%m-%d %H:%i:%s') as create_time, date_format(reg_dtm,'%Y-%m-%d %H:%i:%s') as reg_time ,failed_count from token";
		String sqlCount = "select count(*)  as count from token";
		List<Token> lists = null;
		try{
			if(queryCondition != null && queryCondition.size() > 0){
				sql += " where ";
				sqlCount += " where ";
				for(Map.Entry<String, Object> entry : queryCondition.entrySet()){
					if("id".equals(entry.getKey())){
						sql +=entry.getKey()+" like '%"+entry.getValue() + "%' and ";
						sqlCount += entry.getKey()+" like '%"+entry.getValue() +"%' and ";
					}else{
						sql +=entry.getKey()+"="+entry.getValue() +" and ";
						sqlCount += entry.getKey()+"="+entry.getValue() +" and ";
					}
				}
				if(sql.lastIndexOf("and") > -1){
					sql = sql.substring(0 ,sql.lastIndexOf("and"));
				}
				if(sqlCount.lastIndexOf("and") > -1){
					sqlCount = sqlCount.substring(0 ,sqlCount.lastIndexOf("and"));
				}
			}
			sql = sql+" limit "+start+", "+end;
			logger.info("调用Token的查询token列表接口的sql为:"+sql);
			logger.info("调用Token查询token列表总数数sql为:"+sqlCount);
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
            c = getConnection();
            listData = getDatas(c, sql, paramMap); 
            HashMap<String, Object> countMap=getAloneObject(c, sqlCount, null);
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
            if(listData != null && listData.size() > 0){
            	lists = new ArrayList<Token>();
            	page.setTotalCount(count);
            	for(int i = 0; i < listData.size(); i++){
            		Token token = new Token();
            		Map<String,Object> map = listData.get(i);
            		token.setTokenId((String)map.get("id"));
            		token.setLock(Integer.parseInt((String) map.get("lk")));
            		token.setReg(Integer.parseInt((String) map.get("reg")));
            		token.setAssociate(Integer.parseInt((String) map.get("associate")));
            		token.setCreateTime((String) map.get("create_time"));
            		token.setRegTime((String) map.get("reg_time"));
            		token.setFailedCount(Integer.parseInt( map.get("failed_count").toString()));
            		lists.add(token);
            	}
            	page.setResult(lists);
            }else{
            	lists=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
            }
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
			if(null!=c){
				try {
					releaseConection(c);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("释放连接出错");
				}
			}
		}
		
		return page;
	}
	public Page<TokenCompany> getTokenCompany(int start, int end, Map<String, Object> queryCondition) throws SQLException{
		DataSourceContextHolder.setDataSourceType(DataSourceType.TOKEN);
		Connection c = null;
		int count = 0;
		Page<TokenCompany> page = new Page<TokenCompany>();
		List<HashMap<String, Object>> listData = null;
		String sql = "select no,company_name,token_qty, max_qty,  sk, date_format(create_dtm,'%Y-%m-%d %H:%i:%s') as create_time,date_format(expire_dtm,'%Y-%m-%d %H:%i:%s') as expire_time from company ";
		String sqlCount = "select count(*)  as count from company ";
		List<TokenCompany> lists = null;
		try{
			if(queryCondition != null && queryCondition.size() > 0){
				sql += "where ";
				sqlCount += "where ";
				for(Map.Entry<String, Object> entry : queryCondition.entrySet()){
					sql +=entry.getKey()+" like '%"+new String(entry.getValue().toString().getBytes("ISO8859-1"),"utf-8") +"%' and ";
					sqlCount += entry.getKey()+" like '%"+entry.getValue() +"%' and ";
				}
				if(sql.lastIndexOf("and") > -1){
					sql = sql.substring(0 ,sql.lastIndexOf("and"));
				}
				if(sqlCount.lastIndexOf("and") > -1){
					sqlCount = sqlCount.substring(0 ,sqlCount.lastIndexOf("and"));
				}
			}
			sql = sql+" limit "+start+", "+end;
			logger.info("调用Token的查询企业列表接口的sql为:"+sql);
			logger.info("调用Token查询总企业数sql为:"+sqlCount);
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
            c = getConnection();
            listData = getDatas(c, sql, paramMap); 
            HashMap<String, Object> countMap=getAloneObject(c, sqlCount, null);
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
            if(listData != null && listData.size() > 0){
            	lists = new ArrayList<TokenCompany>();
            	page.setTotalCount(count);
            	for(int i = 0; i < listData.size(); i++){
            		TokenCompany company = new TokenCompany();
            		HashMap<String, Object> map = listData.get(i);
            		company.setEid((String)map.get("no"));
            		company.setTokenId((String) map.get("sk"));
            		company.setCompanyName((String) map.get("company_name"));
            		company.setTokenQty(Integer.parseInt(map.get("token_qty").toString()));
            		company.setMaxQty(Integer.parseInt(map.get("max_qty").toString()));
            		company.setExpireTime((String) map.get("expire_time"));
            		company.setCreateTime((String) map.get("create_time"));
            		lists.add(company);
            	}
            	page.setResult(lists);
            }else{
            	lists=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
            }
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
			if(null!=c){
				try {
					releaseConection(c);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("释放连接出错");
				}
			}
		}
		
		return page;
	}
	
	public static void main(String[] args) {
		System.out.println("aaaaaa&dd".lastIndexOf("&"));
		String a = "adba&";
		a.substring(0, 3);
		System.out.println(a.lastIndexOf("b"));
		String aa = null;
		try {
			System.out.println((String) aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
