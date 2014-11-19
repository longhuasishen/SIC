package com.sand.ibsmis.service.inf;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sand.ibsmis.bean.Token;
import com.sand.ibsmis.bean.TokenCompany;
import com.sand.ibsmis.bean.TokenUser;
import com.sand.ibsmis.util.Page;

public interface ITokenService {

	public Map<String, Object> removeAssociation(String eid,String userName);

	public Map<String, Object> delUser(String eid, String userName);
	
	public Map<String, Object> associateToken(String eid,String userName);
	
	public Map<String, Object> editPwd(String eid,String userName,String pwd);
	
	public Page<TokenUser> getUsers(int start, int end, Map<String, Object> queryCondition) throws Exception;
	
	public Page<TokenCompany> getTokenCompany(int start, int end, Map<String, Object> queryCondition) throws Exception;
	public Map<String, Object> delTokenCompany(String userName);

	public Map<String, Object> saveCompany(String eid, String companyName, String maxQty,
			String expireTime);
	public Map<String, Object> updateCompany(String eid, String companyName, String maxQty,
			String expireTime) ;

	public Page<Token> getTokens(int start, int number,
			Map<String, Object> queryCondition) throws Exception;

	public Map<String, Object> tokenLock(String eid, String userName,
			Boolean lockBoo);
}
