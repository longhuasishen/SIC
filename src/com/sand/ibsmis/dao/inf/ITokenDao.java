package com.sand.ibsmis.dao.inf;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sand.ibsmis.bean.Token;
import com.sand.ibsmis.bean.TokenCompany;
import com.sand.ibsmis.bean.TokenUser;
import com.sand.ibsmis.util.Page;

public interface ITokenDao {
	public Page<TokenUser> getUsers(int start, int end, Map<String, Object> queryCondition) throws SQLException;
	public Page<TokenCompany> getTokenCompany(int start, int end, Map<String, Object> queryCondition) throws SQLException;
	public Page<Token> getTokens(int start, int end, Map<String, Object> queryCondition) throws SQLException;
}
