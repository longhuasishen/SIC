package com.sand.ibsmis.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Resource;
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.Rule;
import com.sand.ibsmis.bean.TransJnl;
import com.sand.ibsmis.bean.TransType;
import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.dao.inf.IBSBaseDataDao;
import com.sand.ibsmis.db.inf.BaseOperator;
import com.sand.ibsmis.util.IBSMisConf;
import com.sand.ibsmis.util.MD5Util;
import com.sand.ibsmis.util.Page;
import com.sand.ibsmis.util.StringUtil;

public class IBSBaseDataDaoImpl extends BaseOperator implements IBSBaseDataDao {
	public boolean ceshi() throws Exception {
		
		boolean r = false;
		return r;
	}

	public User getUser(String username) throws Exception {
		User user=new User();
		Connection c = null;
		HashMap<String, Object> userMap = null;
		try{
			
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, username);
            c = getConnection();
            userMap = getAloneObject(c, IBSMisConf.SQL_GET_USER_BYUSERNAME, paramMap);
            if(userMap!=null&&!userMap.isEmpty() && userMap.size() > 0){
            	user.setPassword(userMap.get("password").toString());
            	user.setUsername(userMap.get("username").toString());
            	if(userMap.get("last_login")==null){
            		user.setLast_login("");
            	}else{
            		user.setLast_login(userMap.get("last_login").toString());
            	}
            	user.setRole_id(userMap.get("role_id").toString());
            	user.setRole_name(userMap.get("role_name").toString());
            	user.setState(userMap.get("state").toString());
            }else{
            	user=null;
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
		return user;
	}

	public Page<Company> getCompanyList( int start, int number,Map<String, Object> map) {
		Connection c = null;
		int count=0;
		Page<Company> page=new Page<Company>();
		List<Company> result=new ArrayList<Company>();
		List<HashMap<String, Object>> comList = null;
		HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
		HashMap<String, Object> countMap=new HashMap<String, Object>();
		String SQL_GET_COMPANY_LIST ="";
		String SQL_GET_COUNT="";
		try{
			c = getConnection();
			if(start==-1&&number==-1){
				SQL_GET_COMPANY_LIST="select * from sand_company_money order by company_id";
				comList = getDatas(c, SQL_GET_COMPANY_LIST, null);
			}else{
				if(map!=null){
					paramMap.put(1, "%"+map.get("companyId")+"%");
					SQL_GET_COMPANY_LIST="SELECT company_id,company_name,channel_id,to_char(insert_time, 'YYYY-MM-DD hh24:mi:ss') as insert_time,update_user,to_char(update_time, 'YYYY-MM-DD hh24:mi:ss') as update_time,before_amt,after_amt,curr_amt,warn_amt,status,rn FROM (SELECT e.*, ROWNUM rn FROM (SELECT * FROM sand_company_money order by company_id) e WHERE company_id like ? and ROWNUM <= "+(start+number)+" ) WHERE rn > "+start+" order by company_id asc";
					SQL_GET_COUNT="SELECT count(*) as count from sand_company_money where company_id like ?";
					countMap=getAloneObject(c, SQL_GET_COUNT, paramMap);
					comList = getDatas(c, SQL_GET_COMPANY_LIST, paramMap);
				}else{
					SQL_GET_COMPANY_LIST = "SELECT company_id,company_name,channel_id,to_char(insert_time, 'YYYY-MM-DD hh24:mi:ss') as insert_time,update_user,to_char(update_time, 'YYYY-MM-DD hh24:mi:ss') as update_time,before_amt,after_amt,curr_amt,warn_amt,status,rn FROM (SELECT e.*, ROWNUM rn FROM (SELECT * FROM sand_company_money order by company_id) e WHERE ROWNUM <= "+(start+number)+" ) WHERE rn > "+start+" order by company_id asc";
					SQL_GET_COUNT="SELECT count(*) as count from sand_company_money";
					countMap=getAloneObject(c, SQL_GET_COUNT, null);
					comList = getDatas(c, SQL_GET_COMPANY_LIST, null);
				}
			}
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
			if(comList!=null&&!comList.isEmpty() && comList.size() > 0){
				page.setTotalCount(count);
				for (int i = 0; i < comList.size(); i++) {
					HashMap<String, Object> comMap=comList.get(i);
					Company company=new Company();
					company.setCompanyId(comMap.get("company_id").toString());
					company.setCompanyName(comMap.get("company_name").toString());
					company.setChannelId(comMap.get("channel_id").toString());
					company.setUpdateUser(comMap.get("update_user").toString());
//            		company.setUpdateTime(StringUtil.getDate(StringUtil.dateToString((Date)comMap.get("update_time"),IBSMisConf.PATTERN_YYYYMMDDHHMMSS),IBSMisConf.PATTERN_YYYYMMDDHHMMSS));
					company.setInsertTime(comMap.get("insert_time").toString());
					company.setUpdateTime(comMap.get("update_time").toString());
					company.setBeforeAmt(Double.parseDouble(comMap.get("before_amt").toString()));
					company.setAfterAmt(Double.parseDouble(comMap.get("after_amt").toString()));
					company.setCurrAmt(Double.parseDouble(comMap.get("curr_amt").toString()));
					company.setWarnAmt(Double.parseDouble(comMap.get("warn_amt").toString()));
					if(Integer.parseInt(comMap.get("status").toString())==1){
						company.setStatus("可用");
					}else{
						company.setStatus("不可用");
					}
					result.add(company);
				}
				page.setResult(result);
			}else{
				comList=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
	public Page<TransJnl> getTransList( int start, int number,Map<String, Object> map) {
		Connection c = null;
		int count=0;
		Page<TransJnl> page=new Page<TransJnl>();
		List<TransJnl> result=new ArrayList<TransJnl>();
		List<HashMap<String, Object>> jnlList = null;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			String SQL_GET_JNL_LIST ="SELECT local_serial,local_serialtime,trans_kind,trans_type,channel_id,channel_serial,mid,tid,settle_bat,term_serial,issue_plant,issue_no,card_no,plan_amt,trans_amt,purchase_amt,trans_time,product_no,deposit_no,area_code,company_id,app_code,order_no,order_back,status,third_resp,buf1,buf2,buf3,trace_num,third_order_no,rn FROM (SELECT e.*, ROWNUM rn FROM (";
			String TRANS_JNL_LIST=" SELECT local_serial,local_serialtime,trans_kind,trans_type,t.channel_id,channel_serial,mid,tid,settle_bat,term_serial,issue_plant,issue_no,card_no,plan_amt,trans_amt,purchase_amt,trans_time,product_no,deposit_no,area_code,t.company_id,app_code,order_no,order_back,t.status,third_resp,buf1,buf2,buf3,trace_num,third_order_no FROM trans_jnl t, sand_company_money u where 1 = 1 and u.channel_id = (select outer_channel from sand_company_channel c where c.inner_channel=t.channel_id) ";
			String BATCH_JNL_LIST=" SELECT local_serial,local_serialtime,trans_kind,trans_type,b.channel_id,channel_serial,mid,tid,settle_bat,term_serial,issue_plant,issue_no,card_no,plan_amt,trans_amt,purchase_amt,trans_time,product_no,deposit_no,area_code,b.company_id,app_code,order_no,order_back,b.status,third_resp,buf1,buf2,buf3,trace_num,third_order_no FROM batch_jnl b, sand_company_money u where 1 = 1 and u.channel_id = (select outer_channel from sand_company_channel c where c.inner_channel=b.channel_id) ";
			String HIS_JNL_LIST=" SELECT local_serial,local_serialtime,trans_kind,trans_type,h.channel_id,channel_serial,mid,tid,settle_bat,term_serial,issue_plant,issue_no,card_no,plan_amt,trans_amt,purchase_amt,trans_time,product_no,deposit_no,area_code,h.company_id,app_code,order_no,order_back,h.status,third_resp,buf1,buf2,buf3,trace_num,third_order_no FROM his_jnl h, sand_company_money u where 1 = 1 and u.channel_id = (select outer_channel from sand_company_channel c where c.inner_channel=h.channel_id) ";
			String SQL_GET_COUNT="select sum(count) as count from( ";
			String TRANS_GET_COUNT=" SELECT count(*) as count FROM trans_jnl t, sand_company_money u where 1 = 1 and u.channel_id = (select outer_channel from sand_company_channel c where c.inner_channel=t.channel_id) ";
			String BATCH_GET_COUNT=" SELECT count(*) as count FROM batch_jnl b, sand_company_money u where 1 = 1 and u.channel_id = (select outer_channel from sand_company_channel c where c.inner_channel=b.channel_id) ";
			String HIS_GET_COUNT=" SELECT count(*) as count FROM his_jnl h, sand_company_money u where 1 = 1 and u.channel_id = (select outer_channel from sand_company_channel c where c.inner_channel=h.channel_id) ";
			
			if(map.containsKey("companyId")){
				TRANS_JNL_LIST +=" and u.company_id=? ";
				BATCH_JNL_LIST +=" and u.company_id=? ";
				HIS_JNL_LIST +=" and u.company_id=? ";
				TRANS_GET_COUNT +=" and u.company_id=? ";
				BATCH_GET_COUNT +=" and u.company_id=? ";
				HIS_GET_COUNT +=" and u.company_id=? ";
				paramMap.put((map.size()*0)+1, map.get("companyId"));
				paramMap.put((map.size()*1)+1, map.get("companyId"));
				paramMap.put((map.size()*2)+1, map.get("companyId"));
			}
			if(map.containsKey("queryOrderId")){
				TRANS_JNL_LIST +=" and channel_serial like ? ";
				BATCH_JNL_LIST +=" and channel_serial like ? ";
				HIS_JNL_LIST +=" and channel_serial like ? ";
				TRANS_GET_COUNT +=" and t.channel_serial like ? ";
				BATCH_GET_COUNT +=" and b.channel_serial like ? ";
				HIS_GET_COUNT +=" and h.channel_serial like ? ";
				if(map.containsKey("companyId")){
					paramMap.put((map.size()*0)+2, "%"+map.get("queryOrderId")+"%");
					paramMap.put((map.size()*1)+2, "%"+map.get("queryOrderId")+"%");
					paramMap.put((map.size()*2)+2, "%"+map.get("queryOrderId")+"%");
				}else{
					paramMap.put((map.size()*0)+1, "%"+map.get("queryOrderId")+"%");
					paramMap.put((map.size()*1)+1, "%"+map.get("queryOrderId")+"%");
					paramMap.put((map.size()*2)+1, "%"+map.get("queryOrderId")+"%");
				}
			}
			if(map.containsKey("startDate")){
				TRANS_JNL_LIST +=" and to_date(substr(t.local_serialtime,1,8),'yyyymmdd')>= to_date(?,'yyyymmdd') ";
				BATCH_JNL_LIST +=" and to_date(substr(b.local_serialtime,1,8),'yyyymmdd')>= to_date(?,'yyyymmdd') ";
				HIS_JNL_LIST +=" and to_date(substr(h.local_serialtime,1,8),'yyyymmdd')>= to_date(?,'yyyymmdd') ";
				TRANS_GET_COUNT +=" and to_date(substr(t.local_serialtime,1,8),'yyyymmdd')>= to_date(?,'yyyymmdd') ";
				BATCH_GET_COUNT +=" and to_date(substr(b.local_serialtime,1,8),'yyyymmdd')>= to_date(?,'yyyymmdd') ";
				HIS_GET_COUNT +=" and to_date(substr(h.local_serialtime,1,8),'yyyymmdd')>= to_date(?,'yyyymmdd') ";
				if(map.containsKey("companyId")&&map.containsKey("queryOrderId")){
						paramMap.put((map.size()*0)+3, map.get("startDate").toString());
						paramMap.put((map.size()*1)+3, map.get("startDate").toString());
						paramMap.put((map.size()*2)+3, map.get("startDate").toString());
				}else if(map.containsKey("companyId")||map.containsKey("queryOrderId")){
						paramMap.put((map.size()*0)+2, map.get("startDate").toString());
						paramMap.put((map.size()*1)+2, map.get("startDate").toString());
						paramMap.put((map.size()*2)+2, map.get("startDate").toString());
				}else if(!map.containsKey("companyId")&&!map.containsKey("queryOrderId")){
						paramMap.put((map.size()*0)+1, map.get("startDate").toString());
						paramMap.put((map.size()*1)+1, map.get("startDate").toString());
						paramMap.put((map.size()*2)+1, map.get("startDate").toString());
				}
			}
			if(map.containsKey("endDate")){
				TRANS_JNL_LIST +=" and to_date(substr(t.local_serialtime,1,8),'yyyymmdd')<= to_date(?,'yyyymmdd') ";
				BATCH_JNL_LIST +=" and to_date(substr(b.local_serialtime,1,8),'yyyymmdd')<= to_date(?,'yyyymmdd') ";
				HIS_JNL_LIST +=" and to_date(substr(h.local_serialtime,1,8),'yyyymmdd')<= to_date(?,'yyyymmdd') ";
				TRANS_GET_COUNT +=" and to_date(substr(t.local_serialtime,1,8),'yyyymmdd')<= to_date(?,'yyyymmdd') ";
				BATCH_GET_COUNT +=" and to_date(substr(b.local_serialtime,1,8),'yyyymmdd')<= to_date(?,'yyyymmdd') ";
				HIS_GET_COUNT +=" and to_date(substr(h.local_serialtime,1,8),'yyyymmdd')<= to_date(?,'yyyymmdd') ";
				if(map.containsKey("companyId")&&map.containsKey("queryOrderId")&&map.containsKey("startDate")){
						paramMap.put((map.size()*0)+4, map.get("endDate").toString());
						paramMap.put((map.size()*1)+4, map.get("endDate").toString());
						paramMap.put((map.size()*2)+4, map.get("endDate").toString());
				}else if((map.size()==3)&&(!map.containsKey("companyId")||!map.containsKey("queryOrderId")||!map.containsKey("startDate"))){
						paramMap.put((map.size()*0)+3, map.get("endDate").toString());
						paramMap.put((map.size()*1)+3, map.get("endDate").toString());
						paramMap.put((map.size()*2)+3, map.get("endDate").toString());
				}else if(map.size()==2){
						paramMap.put((map.size()*0)+2, map.get("endDate").toString());
						paramMap.put((map.size()*1)+2, map.get("endDate").toString());
						paramMap.put((map.size()*2)+2, map.get("endDate").toString());
				}else{
						paramMap.put((map.size()*0)+1, map.get("endDate").toString());
						paramMap.put((map.size()*1)+1, map.get("endDate").toString());
						paramMap.put((map.size()*2)+1, map.get("endDate").toString());
				}
			}
			SQL_GET_JNL_LIST+=TRANS_JNL_LIST;
			SQL_GET_JNL_LIST+=" union ";
			SQL_GET_JNL_LIST+=BATCH_JNL_LIST;
			SQL_GET_JNL_LIST+=" union ";
			SQL_GET_JNL_LIST+=HIS_JNL_LIST;
			SQL_GET_COUNT+=TRANS_GET_COUNT;
			SQL_GET_COUNT+=" union all";
			SQL_GET_COUNT+=BATCH_GET_COUNT;
			SQL_GET_COUNT+=" union all";
			SQL_GET_COUNT+=HIS_GET_COUNT;
			SQL_GET_COUNT+=")";
			if(start==0&&number==0){
				SQL_GET_JNL_LIST += ") e ) order by local_serial";
			}else{
				SQL_GET_JNL_LIST += ") e WHERE ROWNUM <= "+(start+number)+" ) WHERE rn > "+start+" order by local_serial";
			}
			logger.info(SQL_GET_JNL_LIST);
			logger.info(SQL_GET_COUNT);
			c = getConnection();
			HashMap<String, Object> countMap=getAloneObject(c, SQL_GET_COUNT, paramMap);
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
			jnlList = getDatas(c, SQL_GET_JNL_LIST, paramMap);
			if(jnlList!=null&&!jnlList.isEmpty() && jnlList.size() > 0){
				page.setTotalCount(count);
				for (int i = 0; i < jnlList.size(); i++) {
					HashMap<String, Object> jnlMap=jnlList.get(i);
					TransJnl jnl=new TransJnl();
					jnl.setChannel_serial(jnlMap.get("channel_serial").toString());
					jnl.setTrans_amt(Double.parseDouble(jnlMap.get("trans_amt").toString()));
					if(jnlMap.get("local_serialtime")!=null){
						String dateStr=jnlMap.get("local_serialtime").toString();
						String year=dateStr.substring(0,4);
						String month=dateStr.substring(4,6);
						String day=dateStr.substring(6,8);
						String hour=dateStr.substring(8,10);
						String minute=dateStr.substring(10,12);
						String second=dateStr.substring(12,14);
						jnl.setTrans_time(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
					}else{
						jnl.setTrans_time("");
					}
					if(jnlMap.get("deposit_no")!=null){
						jnl.setOrder_back(jnlMap.get("deposit_no").toString());
					}else{
						jnl.setOrder_back("");
					}
					if(jnlMap.get("status")!=null){
						if(jnlMap.get("status").toString().equals("0000")){
							jnl.setStatus("成功");
						}else{
							jnl.setStatus("失败");
						}
					}else{
						jnl.setStatus("失败");
					}
					result.add(jnl);
				}
				page.setResult(result);
			}else{
				jnlList=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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

	public boolean updateLoginTime(String username,String loginTime,String loginIp) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			if(loginIp.equalsIgnoreCase("0:0:0:0:0:0:0:1")){
				loginIp="127.0.0.1";
			}
			paramMap.put(1, loginTime);
			paramMap.put(2, loginIp);
			paramMap.put(3, username);
            c = getConnection();
            i=getOperator().update(c, IBSMisConf.SQL_UPDATE_LOGINTIME, paramMap);
            if(i>0){
            	r=true;
            }
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public List<HashMap<String, Object>> getPrivilege(String username) {
		Connection c = null;
		List<HashMap<String, Object>> list = null;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, username);
            c = getConnection();
            list = getDatas(c, IBSMisConf.SQL_GET_PRIVILEGE, paramMap);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return list;
	}
	public List<String> getPrivilegeUri(String username) {
		Connection c = null;
		List<HashMap<String, Object>> list = null;
		List<String> urlList=new ArrayList<String>();
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, username);
			c = getConnection();
			list = getDatas(c, IBSMisConf.SQL_GET_PRIVILEGEURL, paramMap);
			if(list!=null&&list.size()>0){
				for(HashMap<String, Object> map : list){
					if(map.get("menuurl")!=null&&map.get("menuurl")!=""){
						urlList.add(map.get("menuurl").toString());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return urlList;
	}

	public boolean updateCompany(String companyId, String companyName,
			String username, double afterAmt,double warnAmt,String operIp,String operAmt) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, companyName);
			paramMap.put(2, username);
			paramMap.put(3, afterAmt);
			paramMap.put(4, afterAmt);
			paramMap.put(5, warnAmt);
			paramMap.put(6, companyId);
            c = getConnection();
            i = getOperator().update(c, IBSMisConf.SQL_UPDATE_SANDMONEY, paramMap);
            logger.info("SQL_UPDATE_SANDMONEY,i=:"+i);
            if(i>0){
            	paramMap.clear();
            	if(operIp.equalsIgnoreCase("0:0:0:0:0:0:0:1")){
            		operIp="127.0.0.1";
    			}
            	paramMap.put(1, companyId);
    			paramMap.put(2, companyName);
    			paramMap.put(3, username);
    			paramMap.put(4, operIp);
    			paramMap.put(5, "add");
    			paramMap.put(6, operAmt);
            	i=getOperator().save(c, IBSMisConf.SQL_ADD_MONEYOPERATE, paramMap);
            	logger.info("SQL_ADD_MONEYOPERATE,i=:"+i);
            	if(i>0){
            		r=true;
            	}
            }
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public String addCompany(String companyId, String channelId,String companyName,
			String username, double afterAmt, double warnAmt,String state) {
		String passwordStr="";
		boolean r =false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, companyId);
			paramMap.put(2, companyName);
			paramMap.put(3, channelId);
			paramMap.put(4, username);
			paramMap.put(5, afterAmt);
			paramMap.put(6, afterAmt);
			paramMap.put(7, afterAmt);
			paramMap.put(8, warnAmt);
			paramMap.put(9, Integer.parseInt(state));
            c = getConnection();
            i = getOperator().save(c, IBSMisConf.SQL_INSERT_SANDMONEY, paramMap);
            logger.info("SQL_INSERT_SANDMONEY,i=:"+i);
            if(i>0){
            	paramMap.clear();
            	paramMap.put(1, companyId.toLowerCase());
            	String doubleStr=Math.random()*(999999-100000)+100000+"";
            	passwordStr=doubleStr.substring(0,doubleStr.indexOf("."));
            	logger.info(passwordStr);
            	String password=MD5Util.MD5(passwordStr);
            	paramMap.put(2, password);
            	i=getOperator().save(c, IBSMisConf.SQL_INSERT_SANDUSER, paramMap);
            	if(i>0){
            		logger.info("SQL_INSERT_SANDUSER=:"+i);
            		r=true;
            	}
            }
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return passwordStr;
	}

	public boolean delCompany(String companyId, String username) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, companyId);
            c = getConnection();
            i = getOperator().delete(c, IBSMisConf.SQL_DELETE_SANDMONEY, paramMap);
            logger.info("SQL_DELETE_SANDMONEY,i=:"+i);
            if(i>0){
            	i = getOperator().delete(c, IBSMisConf.SQL_DELETE_SANDUSER, paramMap);
                logger.info("SQL_DELETE_SANDUSER,i=:"+i);
            	r=true;
            }
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}
	public boolean forbiddenCompany(String companyId, String username,String status) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, status);
			paramMap.put(2, companyId);
			c = getConnection();
			i = getOperator().update(c, IBSMisConf.SQL_FORBIDDEN_SANDMONEY, paramMap);
			logger.info("SQL_FORBIDDEN_SANDMONEY,i=:"+i);
			if(i>0){
				r=true;
			}
			if(r){
				commit(c);
			}else{
				rollBack(c);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public boolean updatePassword(String username,String newpass,String type) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, newpass);
			paramMap.put(2, username);
			if(type.equalsIgnoreCase("change")){
	            c = getConnection();
	            i = getOperator().update(c, IBSMisConf.SQL_UPDATEPASS, paramMap);
	            logger.info("SQL_UPDATEPASS,i=:"+i);
			}else{
				c = getConnection();
	            i = getOperator().update(c, IBSMisConf.SQL_RESETPASS, paramMap);
	            logger.info("SQL_RESETPASS,i=:"+i);
			}
            if(i>0){
            	r=true;
            }
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public Page<User> getUserList(int start, int number,Map<String, Object> paramMap) {
		Connection c = null;
		int count=0;
		Page<User> page=new Page<User>();
		List<User> result=new ArrayList<User>();
		List<HashMap<String, Object>> userList = null;
		try{
			
			String SQL_GET_USER_LIST ="SELECT username,password,last_login,last_ip,role_name,rn FROM (SELECT e.*, ROWNUM rn FROM (select username,password,last_login,last_ip,u.role_id,r.role_name from sand_user u join sand_role r on u.role_id=r.role_id";
			String SQL_GET_COUNT="select count(*) as count from sand_user t join sand_role r on r.role_id=t.role_id";
			
			SQL_GET_USER_LIST += ") e WHERE ROWNUM <= "+(start+number)+" ) WHERE rn > "+start+" order by username";
			c = getConnection();
			HashMap<String, Object> countMap=getAloneObject(c, SQL_GET_COUNT, null);
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
			userList = getDatas(c, SQL_GET_USER_LIST, null);
			if(userList!=null&&!userList.isEmpty() && userList.size() > 0){
				page.setTotalCount(count);
				for (int i = 0; i < userList.size(); i++) {
					HashMap<String, Object> userMap=userList.get(i);
					User user=new User();
					user.setUsername(userMap.get("username").toString());
					user.setPassword(userMap.get("password").toString());
					if(userMap.get("last_login")==null){
						user.setLast_login("");
					}else{
						user.setLast_login(userMap.get("last_login").toString());
					}
					if(userMap.get("last_ip")==null){
						user.setLast_ip("");
					}else{
						user.setLast_ip(userMap.get("last_ip").toString());
					}
					user.setRole_name(userMap.get("role_name").toString());
					result.add(user);
				}
				page.setResult(result);
			}else{
				userList=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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

	public boolean checkData(String colName, String tableName, String valueStr) {
		Connection c = null;
		boolean r=false;
		
		List<HashMap<String, Object>> userList = null;
		try{
			String sql="select "+colName+" from "+tableName +" where "+colName+"=?";
			c = getConnection();
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, valueStr);
			
			userList = getDatas(c, sql, paramMap);
			if(userList!=null&&!userList.isEmpty() && userList.size() > 0){
				r=false;
			}else{
				r=true;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public Page<Role> getRoleList(int start, int number,Map<String, Object> paramMap) {
		Connection c = null;
		int count=0;
		Page<Role> page=new Page<Role>();
		List<Role> result=new ArrayList<Role>();
		List<HashMap<String, Object>> roleList = null;
		String SQL_GET_ROLE_LIST ="";
		String SQL_GET_COUNT="";
		try{
			if(start==0&&number==0){
				SQL_GET_ROLE_LIST ="SELECT role_id,role_name,role_comment,rn FROM (SELECT e.*, ROWNUM rn FROM (select * from sand_role where role_id !=3 order by role_id) e )";
				SQL_GET_COUNT="select count(*) as count from sand_role t";
			}else{
				SQL_GET_ROLE_LIST ="SELECT role_id,role_name,role_comment,rn FROM (SELECT e.*, ROWNUM rn FROM (select * from sand_role";
				SQL_GET_COUNT="select count(*) as count from sand_role t";
				SQL_GET_ROLE_LIST += ") e WHERE ROWNUM <= "+(start+number)+" ) WHERE rn > "+start+" order by role_id";
			}
			c = getConnection();
			HashMap<String, Object> countMap=getAloneObject(c, SQL_GET_COUNT, null);
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
			roleList = getDatas(c, SQL_GET_ROLE_LIST, null);
			if(roleList!=null&&!roleList.isEmpty() && roleList.size() > 0){
				page.setTotalCount(count);
				for (int i = 0; i < roleList.size(); i++) {
					HashMap<String, Object> roleMap=roleList.get(i);
					Role role=new Role();
					role.setRole_id(roleMap.get("role_id").toString());
					role.setRole_name(roleMap.get("role_name").toString());
					if(roleMap.get("role_comment")==null){
						role.setRole_comment("");
					}else{
						role.setRole_comment(roleMap.get("role_comment").toString());
					}
					result.add(role);
				}
				page.setResult(result);
			}else{
				roleList=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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

	public Page<Resource> getAllResource() {
		Connection c = null;
		int count=0;
		Page<Resource> page=new Page<Resource>();
		List<Resource> result=new ArrayList<Resource>();
		List<HashMap<String, Object>> resList = null;
		try{
			
			String SQL_GET_RESOURCE_LIST ="SELECT menuid,menuname,parentmenuid,menuicon,menuurl,rn FROM (SELECT e.*, ROWNUM rn FROM (select * from sand_resource order by parentmenuid asc";
			String SQL_GET_COUNT="select count(*) as count from sand_resource t";
			
			SQL_GET_RESOURCE_LIST += ") e )";
			c = getConnection();
			HashMap<String, Object> countMap=getAloneObject(c, SQL_GET_COUNT, null);
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
			resList = getDatas(c, SQL_GET_RESOURCE_LIST, null);
			if(resList!=null&&!resList.isEmpty() && resList.size() > 0){
				page.setTotalCount(count);
				for (int i = 0; i < resList.size(); i++) {
					HashMap<String, Object> resMap=resList.get(i);
					Resource resource=new Resource();
					resource.setMenuid(resMap.get("menuid").toString());
					resource.setMenuname(resMap.get("menuname").toString());
					resource.setParentmenuid(resMap.get("parentmenuid").toString());
					resource.setMenuicon(resMap.get("menuicon").toString());
					if(resMap.get("menuurl")==null){
						resource.setMenuurl("");
					}else{
						resource.setMenuurl(resMap.get("menuurl").toString());
					}
					result.add(resource);
				}
				page.setResult(result);
			}else{
				resList=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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

	public boolean saveRoleInfo(String roleId, String roleName,String roleComment, String priId) {
		Connection c = null;
		int i=0;
		boolean r=false;
		String sql="";
		try{
			if(roleId.equalsIgnoreCase("none")){
				//添加新的角色操作
				c=getConnection();
				HashMap<String,Object> map=getAloneObject(c, IBSMisConf.SQL_SELECT_MAXROLEID, null);
				if(map.size()>0){
					roleId=map.get("roleid").toString();
					roleId=(Integer.parseInt(roleId)+1)+"";
				}
				HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
				paramMap.put(1, roleId);
				paramMap.put(2, roleName);
				paramMap.put(3, roleComment);
				i=getOperator().save(c,IBSMisConf.SQL_INSERT_SANDROLE, paramMap);
				if(i>0){
					if(!priId.equalsIgnoreCase("")){
						String[] menuids=priId.split(",");
						sql="insert into sand_privilege (select ";
						sql+=roleId;
						sql+=",s.* from sand_resource s where s.menuid in (select distinct r.parentmenuid from sand_resource r where r.menuid in (";
						for (int j = 0; j < menuids.length; j++) {
							int menuid=Integer.parseInt(menuids[j]);
							sql+=menuid;
							sql+=",";
						}
						sql=sql.substring(0, sql.length()-1);
						sql+=")) or menuid in (";
						for (int j = 0; j < menuids.length; j++) {
							int menuid=Integer.parseInt(menuids[j]);
							sql+=menuid;
							sql+=",";
						}
						sql=sql.substring(0, sql.length()-1);
						sql+="))";
						logger.info("sql=:"+sql);
						i=getOperator().save(c, sql,null);
						if(i>0){
							r=true;
						}
					}else{
						r=true;
					}
				}
			}else{
				//编辑已有角色信息操作
				HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
				paramMap.put(1, roleName);
				paramMap.put(2, roleComment);
				paramMap.put(3, roleId);
				c=getConnection();
				i=getOperator().update(c, IBSMisConf.SQL_UPDATE_ROLE, paramMap);
				paramMap.clear();
				paramMap.put(1, roleId);
				i=getOperator().delete(c, IBSMisConf.SQL_DELETE_ROLEPRI, paramMap);
				if(!priId.equalsIgnoreCase("")){
					String[] menuids=priId.split(",");
					sql="insert into sand_privilege (select ";
					sql+=roleId;
					sql+=",s.* from sand_resource s where s.menuid in (select distinct r.parentmenuid from sand_resource r where r.menuid in (";
					for (int j = 0; j < menuids.length; j++) {
						int menuid=Integer.parseInt(menuids[j]);
						sql+=menuid;
						sql+=",";
					}
					sql=sql.substring(0, sql.length()-1);
					sql+=")) or menuid in (";
					for (int j = 0; j < menuids.length; j++) {
						int menuid=Integer.parseInt(menuids[j]);
						sql+=menuid;
						sql+=",";
					}
					sql=sql.substring(0, sql.length()-1);
					sql+="))";
					logger.info("sql=:"+sql);
					i=getOperator().save(c, sql,null);
					/*sql="insert into sand_privilege (select ";
					sql+=roleId;
					sql+=",r.* from sand_resource r where r.menuid in (";
					for (int j = 0; j < menuids.length; j++) {
						int menuid=Integer.parseInt(menuids[j]);
						sql+=menuid;
						sql+=",";
					}
					sql=sql.substring(0, sql.length()-1);
					sql+="))";
					logger.info("sql=:"+sql);
					i=getOperator().save(c, sql,null);*/
					if(i>0){
						r=true;
					}
				}else{
					r=true;
				}
			}
			if(r){
				commit(c);
			}else{
				rollBack(c);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public boolean delRole(String roleName) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, roleName);
            c = getConnection();
            i = getOperator().delete(c, IBSMisConf.SQL_DELETE_ROLEPRI, paramMap);
            logger.info("SQL_DELETE_ROLEPRI,i=:"+i);
            i = getOperator().delete(c, IBSMisConf.SQL_DELETE_ROLEUESER, paramMap);
            logger.info("SQL_DELETE_ROLEUESER,i=:"+i);
            i = getOperator().delete(c, IBSMisConf.SQL_DELETE_ROLE, paramMap);
            logger.info("SQL_DELETE_ROLE,i=:"+i);
            r=true;
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}


	public Page<Rule> getRuleList(int currentPage, int limitNum,Map<String, Object> map) {
		Connection c = null;
		int count=0;
		Page<Rule> page=new Page<Rule>();
		List<Rule> result=new ArrayList<Rule>();
		List<HashMap<String, Object>> resList = null;
		HashMap<String, Object> countMap=new HashMap<String, Object>();
		HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
		try{
			c = getConnection();
			if(map!=null){
				String SQL_SELECT_TYPE_RULE="select s.company_id,s.company_name,t.typeid,t.typename,c.rule_id,c.rule_name,r.startdate,r.enddate,r.cleardate,r.typeState from sand_company_money s,sand_ibs_transtype t,sand_company_rule r,sand_check_rule c where r.company_id=s.company_id and r.tran_type=t.typeid and r.rule_id=c.rule_id  order by s.company_id,t.typeid";
				String SQL_SELECT_COUNT_TYPE_RULE="select count(*) count from sand_company_money s,sand_ibs_transtype t,sand_company_rule r,sand_check_rule c where r.company_id=s.company_id and r.tran_type=t.typeid and r.rule_id=c.rule_id ";
				if(map.containsKey("companyId")){
					paramMap.put(paramMap.size()+1, "%"+map.get("companyId")+"%");
					SQL_SELECT_TYPE_RULE+=" and s.company_id like ?";
					SQL_SELECT_COUNT_TYPE_RULE+=" and s.company_id like ?";
				}
				if(map.containsKey("typeName")){
					paramMap.put(paramMap.size()+1, "%"+map.get("typeName")+"%");
					SQL_SELECT_TYPE_RULE+=" and t.typeid like ?";
					SQL_SELECT_COUNT_TYPE_RULE+=" and t.typeid like ?";
				}
				SQL_SELECT_TYPE_RULE+=" order by s.company_id,t.typeid";
				countMap=getAloneObject(c, SQL_SELECT_COUNT_TYPE_RULE, paramMap);
				resList = getDatas(c, SQL_SELECT_TYPE_RULE, paramMap);
			}else{
				countMap=getAloneObject(c, IBSMisConf.SQL_SELECT_COUNT_TYPE_RULE, null);
				resList = getDatas(c, IBSMisConf.SQL_SELECT_TYPE_RULE, null);
			}
			if(countMap!=null&&!countMap.isEmpty() && countMap.size() > 0){
				count=Integer.parseInt(countMap.get("count").toString());
			}
			if(resList!=null&&!resList.isEmpty() && resList.size() > 0){
				page.setTotalCount(count);
				for (int i = 0; i < resList.size(); i++) {
					HashMap<String, Object> resMap=resList.get(i);
					Rule rule=new Rule();
					rule.setCompany_id(resMap.get("company_id").toString());
					rule.setCompany_name(resMap.get("company_name").toString());
					rule.setTran_type(resMap.get("typeid").toString());
					rule.setType_name(resMap.get("typename").toString());
					rule.setRule_id(resMap.get("rule_id").toString());
					rule.setRule_name(resMap.get("rule_name").toString());
					rule.setStartDate(resMap.get("startdate").toString());
					rule.setEndDate(resMap.get("enddate").toString());
					rule.setClearDate(resMap.get("cleardate").toString());
					if("1".equalsIgnoreCase(resMap.get("typestate").toString())){
						rule.setTypeState("可用");
					}else{
						rule.setTypeState("不可用");
					}
					result.add(rule);
				}
				page.setResult(result);
			}else{
				resList=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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

	public Page<HashMap<String, Object>> getRuleList() {
		Connection c = null;
		int count=0;
		Page<HashMap<String, Object>> page=new Page<HashMap<String, Object>>();
		List<HashMap<String, Object>> result=new ArrayList<HashMap<String, Object>>();
		try{
			c = getConnection();
			result = getDatas(c, IBSMisConf.SQL_SELECT_ALLRULE, null);
			if(result!=null&&!result.isEmpty() && result.size() > 0){
				page.setResult(result);
				page.setTotalCount(result.size());
			}else{
				result=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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

	public Page<TransType> getTypeList(int start, int number,Map<String, Object> paramMap) {
		Connection c = null;
		int count=0;
		Page<TransType> page=new Page<TransType>();
		List<TransType> result=new ArrayList<TransType>();
		List<HashMap<String, Object>> typeList = null;
		try{
			c = getConnection();
			typeList = getDatas(c, IBSMisConf.SQL_SELECT_ALLTYPE, null);
			if(typeList!=null&&!typeList.isEmpty() && typeList.size() > 0){
				page.setTotalCount(count);
				for (int i = 0; i < typeList.size(); i++) {
					HashMap<String, Object> typeMap=typeList.get(i);
					TransType type=new TransType();
					type.setTrans_kind("");
					type.setTrans_type(typeMap.get("typeid").toString());
					type.setType_name(typeMap.get("typename").toString());
					type.setStatus(0);
					result.add(type);
				}
				page.setResult(result);
			}else{
				typeList=null;
				count=0;
				page.setTotalCount(count);
				page.setResult(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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

	public boolean saveRuleInfo(HashMap<String, Object> map) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			String typeIds=map.get("typeIds").toString();
			String[] typeArr=typeIds.split("#");
			String companyId=map.get("companyId").toString();
			String ruleId=map.get("ruleId").toString();
			String startDate="";
			String endDate="";
			String tStr="";
			if(map.containsKey("tStr")){
				tStr=map.get("tStr").toString();
			}
			if(ruleId.equalsIgnoreCase("0001")){
				startDate=map.get("startDate").toString();
				endDate=StringUtil.getAfterDay(startDate,1);
			}else if(ruleId.equalsIgnoreCase("0002")){
				startDate=map.get("startDate").toString();
				endDate=StringUtil.getNextMonth(startDate);
			}else{
				startDate=map.get("startDate").toString();
				endDate=StringUtil.getAfterDay(startDate,Integer.parseInt(tStr));
			}
			c = getConnection();
			for(String typeStr : typeArr){
				paramMap.clear();
				paramMap.put(1, companyId);
				paramMap.put(2, typeStr);
				getOperator().delete(c, IBSMisConf.SQL_DELETE_COMPANYRULE, paramMap);
				paramMap.clear();
				paramMap.put(1, companyId);
				paramMap.put(2, typeStr);
				paramMap.put(3, ruleId);
				paramMap.put(4, startDate);
				paramMap.put(5, endDate);
				paramMap.put(6, endDate);
				i = getOperator().save(c, IBSMisConf.SQL_INSERT_COMPANYRULE, paramMap);
				logger.info("SQL_INSERT_COMPANYRULE,i=:"+i);
				//此处将该商户对应的业务功能所对应的bussid放入缓存
				
			}
            if(i>0){
            	r=true;
            }
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public boolean saveUser(String userId, String roleId) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			String password=MD5Util.MD5("sand123456");
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, userId);
			paramMap.put(2, password);
			paramMap.put(3, roleId);
            c = getConnection();
            i=getOperator().save(c, IBSMisConf.SQL_INSERT_SAND_USER, paramMap);
            logger.info("SQL_INSERT_SAND_USER,i=:"+i);
            r=true;
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public boolean deleteUser(String userId) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, userId);
            c = getConnection();
            i=getOperator().delete(c, IBSMisConf.SQL_DELETE_SANDUSER, paramMap);
            logger.info("SQL_DELETE_SANDUSER,i=:"+i);
            r=true;
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public synchronized HashMap<String, Object> getInfoByComId(String companyId) {
		Connection c = null;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, companyId);
            c = getConnection();
            HashMap<String, Object> map=getAloneObject(c, IBSMisConf.SQL_GETCOMBYID, paramMap);
            if(map!=null&&map.size()>0){
            	return map;
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return null;
	}

	public synchronized String updateMoneyByCompanyId(String channelId, double operAmt , String mid, String orderId, String orderDate) {
			boolean r=false;
			String result=IBSMisConf.IBSMIS_SERVICE_RESPCODE_FAIL;
			Connection c = null;
			int i=-1;
			try{
				HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
				paramMap.put(1, channelId);
	            c = getConnection();
	            HashMap<String, Object> map=new HashMap<String, Object>();
	            map=getAloneObject(c, IBSMisConf.SQL_QUERYMONEYBEFOREUPDATE, paramMap);
	            if(map!=null&&map.containsKey("leftamt")){
	            	double leftAmt=Double.parseDouble(map.get("leftamt").toString());
	            	if(leftAmt>operAmt){
	            		paramMap.put(1, operAmt);
	    				paramMap.put(2, channelId);
	    	            i=getOperator().update(c, IBSMisConf.SQL_UPDATEMONEYBYCOMPANY, paramMap);
	    	            logger.info("SQL_UPDATEMONEYBYCOMPANY,i=:"+i);
	    	            if(i>0){
	    	            	paramMap.clear();
	    	            	paramMap.put(1, channelId);
	    	            	paramMap.put(2, mid);
	    	            	paramMap.put(3, orderId);
		    				paramMap.put(4, orderDate);
		    				paramMap.put(5, operAmt);
		    				i=getOperator().save(c, IBSMisConf.SQL_ADD_TO_IBSMINUS_MONEY, paramMap);
		    				logger.info("SQL_ADD_TO_IBSMINUS_MONEY,i=:"+i);
		    				if(i>0){
		    					r=true;
		    				}
	    	            }
	    	            if(r){
	    	            	commit(c);
	    	            	result=IBSMisConf.IBSMIS_SERVICE_RESPCODE_SUCCESS;
	    	            	logger.info("修改商户余额成功");
	    	            }else{
	    	            	rollBack(c);
	    	            }
	            	}else{
	            		logger.info("商户余额不足");
	            		result=IBSMisConf.IBSMIS_SERVICE_RESPCODE_NOMOREMONEY;
	            	}
	            }
			}catch(Exception e){
				e.printStackTrace();
				logger.error("修改商户余额失败"+e.getMessage());
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
			return result;
	}
	public List<HashMap<String, Object>> getAllTransType(String channelId) {
		List<HashMap<String, Object>> listData = null;
		Connection c = null;
		try {
			HashMap<Integer, Object> paramMap=new HashMap<Integer, Object>();
			paramMap.put(1, channelId);
			c=getConnection();
			listData =getDatas(c, IBSMisConf.OUTER_GET_ALLTRANTYPE, paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取所有交易类型trans_type信息出错");
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
		return listData;
	}

	public boolean updateRuleState(String companyId, String typeIds,String state) {
			boolean r=false;
			Connection c = null;
			int i=-1;
			try{
				HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
				paramMap.put(1, state);
				paramMap.put(2, companyId);
				paramMap.put(3, typeIds);
	            c = getConnection();
	            i=getOperator().update(c, IBSMisConf.SQL_UPDATESTATEBYTYPE, paramMap);
	            logger.info("SQL_UPDATESTATEBYTYPE,i=:"+i);
	            r=true;
	            if(r){
	            	commit(c);
	            }else{
	            	rollBack(c);
	            }
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
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
		return r;
	}

	public boolean deleteRule(String companyId, String typeIds) {
		boolean r=false;
		Connection c = null;
		int i=-1;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, companyId);
			paramMap.put(2, typeIds);
            c = getConnection();
            i=getOperator().delete(c, IBSMisConf.SQL_DELETE_COMPANYRULE, paramMap);
            logger.info("SQL_DELETE_COMPANYRULE,i=:"+i);
            r=true;
            if(r){
            	commit(c);
            }else{
            	rollBack(c);
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return r;
	}

	public List<HashMap<String, Object>> getAllCompanyInfoList() {
		List<HashMap<String, Object>> listData = null;
		Connection c = null;
		try {
			HashMap<Integer, Object> paramMap=new HashMap<Integer, Object>();
			c=getConnection();
			listData =getDatas(c, IBSMisConf.SQL_GETALLCOMPANYINFO, paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取所有上游商户信息company_info出错");
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
		return listData;
	}

	public HashMap<String, Object> getChannelByComId(String username) {
		Connection c = null;
		try{
			HashMap<Integer,Object> paramMap = new HashMap<Integer,Object>();
			paramMap.put(1, username);
            c = getConnection();
            HashMap<String, Object> map=getAloneObject(c, IBSMisConf.SQL_GETCHANNELBYCOMID, paramMap);
            if(map!=null&&map.size()>0){
            	return map;
            }
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return null;
	}
}