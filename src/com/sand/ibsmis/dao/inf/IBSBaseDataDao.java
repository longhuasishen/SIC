package com.sand.ibsmis.dao.inf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Resource;
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.Rule;
import com.sand.ibsmis.bean.TransJnl;
import com.sand.ibsmis.bean.TransType;
import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.util.Page;




public interface IBSBaseDataDao {
	
	public boolean ceshi() throws Exception;
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User getUser(String username) throws Exception;
	/**
	 * 查询商户信息列表，分页显示
	 * @param currentPage
	 * @param limitNum
	 * @param paramMap
	 * @return
	 */
	public Page<Company> getCompanyList( int currentPage, int limitNum,Map<String, Object> paramMap);
	/**
	 * 查询商户交易信息列表，分页显示
	 * @param currentPage
	 * @param limitNum
	 * @param paramMap
	 * @return
	 */
	public Page<TransJnl> getTransList( int currentPage, int limitNum,Map<String, Object> paramMap);
	/**
	 * 查询用户信息列表，分页显示
	 * @param currentPage
	 * @param limitNum
	 * @param paramMap
	 * @return
	 */
	public Page<User> getUserList( int currentPage, int limitNum,Map<String, Object> paramMap);
	/**
	 * 更新用户登录信息
	 * @param username
	 * @param loginTime
	 * @param loginIp
	 * @return
	 */
	public boolean updateLoginTime(String username, String loginTime,String loginIp);
	/**
	 * 根据登录用户获取该用户权限信息
	 * @param username
	 * @return
	 */
	public List<HashMap<String, Object>> getPrivilege(String username);
	/**
	 * 更新商户信息
	 * @param companyId
	 * @param companyName
	 * @param username
	 * @param afterAmt
	 * @param warnAmt
	 * @param operIp
	 * @param operAmt
	 * @return
	 */
	public boolean updateCompany(String companyId, String companyName,String username, double afterAmt,double warnAmt,String operIp,String operAmt);
	/**
	 * 添加新商户信息
	 * @param companyId
	 * @param channelId
	 * @param companyName
	 * @param username
	 * @param afterAmt
	 * @param warnAmt
	 * @return
	 */
	public String addCompany(String companyId, String channelId,String companyName,String username, double afterAmt,double warnAmt,String state);
	/**
	 * 删除商户和该商户用户
	 * @param companyId
	 * @param username
	 * @return
	 */
	public boolean delCompany(String companyId, String username);
	/**
	 * 修改用户密码
	 * @param username
	 * @param newpass
	 * @return
	 */
	public boolean updatePassword(String username,String newpass,String type);
	/**
	 * 检测某个表某字段数据信息是否存在
	 * @param colName
	 * @param tableName
	 * @param valueStr
	 * @return
	 */
	public boolean checkData(String colName, String tableName, String valueStr);
	/**
	 * 获取所有角色信息
	 * @param start
	 * @param number
	 * @param paramMap
	 * @return
	 */
	public Page<Role> getRoleList(int start, int number,Map<String, Object> paramMap);
	/**
	 * 获取IBS所有交易类型
	 * @param start
	 * @param number
	 * @param paramMap
	 * @return
	 */
	public Page<TransType> getTypeList(int start, int number,Map<String, Object> paramMap);
	/**
	 * 获取本系统所有菜单项信息
	 * @return
	 */
	public Page<Resource> getAllResource();
	/**
	 * 保存角色信息
	 * @param roleId
	 * @param roleName
	 * @param roleComment
	 * @param priId
	 * @return
	 */
	public boolean saveRoleInfo(String roleId, String roleName,String roleComment, String priId);
	/**
	 * 删除角色
	 * @param roleName
	 * @return
	 */
	public boolean delRole(String roleName);
	/**
	 * 获取系统所有角色信息
	 * @param currentPage
	 * @param limitNum
	 * @param paramMap
	 * @return
	 */
	public Page<Rule> getRuleList( int currentPage, int limitNum,Map<String, Object> paramMap);
	public Page<HashMap<String, Object>> getRuleList();
	/**
	 * 保存商户业务类型对账规则
	 * @param paramMap
	 * @return
	 */
	public boolean saveRuleInfo(HashMap<String, Object> paramMap);
	/**
	 * 保存用户
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean saveUser(String userId, String roleId);
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	public boolean deleteUser(String userId);
	/**
	 * 根据商户号获取商户信息
	 * @param companyId
	 * @return
	 */
	public HashMap<String, Object> getInfoByComId(String companyId);
	public List<String> getPrivilegeUri(String username);
	/**
	 * 根据商户号减少商户金额   ---------外接接口用
	 * @param companyId
	 * @param operAmt
	 * @param orderDate 
	 * @param orderId 
	 * @param mid 
	 * @return
	 */
	public String updateMoneyByCompanyId(String companyId, double operAmt, String mid, String orderId, String orderDate);
	/**
	 * 根据渠道号获取所有trans_type
	 * @return
	 */
	public List<HashMap<String, Object>> getAllTransType(String companyId);
	/**
	 * 禁用商户
	 * @param companyId
	 * @param username
	 * @param status 
	 * @return
	 */
	public boolean forbiddenCompany(String companyId, String username, String status);
	/**
	 * 禁用商户某业务
	 * @param companyId
	 * @param typeIds
	 * @param state
	 * @return
	 */
	public boolean updateRuleState(String companyId, String typeIds,String state);
	/**
	 * 删除某个商户的某个业务对账规则
	 * @param companyId
	 * @param typeIds
	 * @return
	 */
	public boolean deleteRule(String companyId, String typeIds);
	/**
	 * 查询上游商户信息
	 * @return
	 */
	public List<HashMap<String, Object>> getAllCompanyInfoList();
	/**
	 * 根据用户名取得该用户对应商户的渠道号
	 * @param username
	 * @return
	 */
	public HashMap<String, Object> getChannelByComId(String username);
}
