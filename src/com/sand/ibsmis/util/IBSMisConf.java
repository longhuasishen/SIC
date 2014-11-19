package com.sand.ibsmis.util;




/**
 * IBSMIS 工具类
 * 
 * @author he.y
 * 
 */
public class IBSMisConf {
	
	//系统接口返回信息
	public static final String IBSMIS_SERVICE_RESP_MSG="respResult";
	public static final String IBSMIS_SERVICE_RESP_CODE="respCode";
	public static final String IBSMIS_SERVICE_RESPCODE_NOMOREMONEY="P002";
	public static final String IBSMIS_SERVICE_RESPSTR_NOMOREMONEY="余额不足";
	public static final String IBSMIS_SERVICE_RESPSTR_SUCCESS="操作成功";
	public static final String IBSMIS_SERVICE_RESPCODE_SUCCESS="0000";
	public static final String IBSMIS_SERVICE_RESPSTR_FAIL="操作失败";
	public static final String IBSMIS_SERVICE_RESPCODE_FAIL="P001";
	public static final String IBSMIS_SERVICE_RESPCODE_QUERYFAIL="0001";
	public static final String IBSMIS_SERVICE_RESPCODE_QUERYNOMOREMONEY="0002";
	
	//系统版本：测试版本TEST或者生产版本PRODUCT
	public static final String IBSMIS_DATASOURCE = "IBSBASEDATADAO";
	public static final String IBSMIS_FILEDIR = IBSMisCommUtils.commPros.getProperty("filedir");
	public static final String SICKEY = IBSMisCommUtils.commPros.getProperty("SICKey");
	
	public static final String PATTERN_YYYYMMDDHHMMSS="yyyy-MM-dd HH:mm:ss";
	public static final String SQL_GET_REGINFO_CESHI = "select * from reg_info where local_serial=?";
	public static final String SQL_GET_USER_BYUSERNAME = "select username,password,last_login,last_ip,state,u.role_id,r.role_name from sand_user u join sand_role r on u.role_id=r.role_id where u.username=?";
//	public static final String SQL_GET_COMPANY_LIST = "select company_id,company_name,update_user,to_char(update_time,'YYYY-MM-DD hh24:mi:ss') as update_time,before_amt,after_amt,curr_amt from sand_company_money where order by company_id";
	public static final String SQL_GET_COMPANY_LIST = "SELECT company_id,company_name,update_user,to_char(update_time, 'YYYY-MM-DD hh24:mi:ss') as update_time,before_amt,after_amt,curr_amt  FROM (SELECT e.*, ROWNUM rn FROM (SELECT * FROM sand_company_money) e WHERE ROWNUM <= ?) WHERE rn >= ? order by company_id";
	public static final String SQL_UPDATE_LOGINTIME="UPDATE sand_user set last_login=?,last_ip=? where username=?";
	public static final String SQL_GET_PRIVILEGE="select role_id,menuid,menuname,parentmenuid,menuicon,menuurl from sand_privilege sp where role_id=?";
	public static final String SQL_GET_PRIVILEGEURL="select menuurl from sand_privilege sp where role_id=?";
	public static final String SQL_UPDATE_SANDMONEY="update sand_company_money t set t.company_name=?,update_user=?,update_time=sysdate,before_amt=curr_amt,after_amt=?,curr_amt=?,warn_amt=? where company_id=?";
	public static final String SQL_ADD_MONEYOPERATE="insert into sand_operate_money(company_id,company_name,update_user,update_time,update_ip,operate,oper_amt) values(?,?,?,sysdate,?,?,?)";
	public static final String SQL_INSERT_SANDMONEY="insert into sand_company_money(company_id,company_name,channel_id,insert_time,update_user,update_time,before_amt,after_amt,curr_amt,warn_amt,status) values (?,?,?,sysdate,?,sysdate,?,?,?,?,?)";
	public static final String SQL_DELETE_SANDMONEY="delete from sand_company_money where company_id=?";
	public static final String SQL_FORBIDDEN_SANDMONEY="update sand_company_money set status=? where company_id=?";
	public static final String SQL_DELETE_SANDUSER="delete from sand_user where username=?";
	public static final String SQL_DELETE_SANDPRIO="delete from sand_privilege where username=?";
	public static final String SQL_UPDATEPASS="update sand_user set password=? where username=?";
	public static final String SQL_RESETPASS="update sand_user set password=?,state='1' where username=?";
	public static final String SQL_INSERT_SANDUSER="insert into sand_user(username,password,last_login,last_ip,role_id) values(?,?,'','',3)";
	public static final String SQL_INSERT_SANDROLE="insert into sand_role values(?,?,?)";
	public static final String SQL_SELECT_MAXROLEID="select max(role_id) roleid from sand_role";
	public static final String SQL_UPDATE_ROLE="update sand_role set role_name=?,role_comment=? where role_id=?";
	public static final String SQL_DELETE_ROLEPRI="delete from sand_privilege where role_id=?";
	public static final String SQL_DELETE_ROLE="delete from sand_role where role_id=?";
	public static final String SQL_DELETE_ROLEUESER="delete from sand_user where role_id=?";
	public static final String SQL_GETALLCOMPANYINFO="select company_id,company_name from company_info t";

	public static final String SQL_SELECT_TYPE_RULE="select s.company_id,s.company_name,t.typeid,t.typename,c.rule_id,c.rule_name,r.startdate,r.enddate,r.cleardate,r.typeState from sand_company_money s,sand_ibs_transtype t,sand_company_rule r,sand_check_rule c where r.company_id=s.company_id and r.tran_type=t.typeid and r.rule_id=c.rule_id order by s.company_id,t.typeid";
	public static final String SQL_SELECT_COUNT_TYPE_RULE="select count(*) count from sand_company_money s,sand_ibs_transtype t,sand_company_rule r,sand_check_rule c where r.company_id=s.company_id and r.tran_type=t.typeid and r.rule_id=c.rule_id ";
	public static final String SQL_SELECT_ALLRULE="select * from sand_check_rule";
	public static final String SQL_SELECT_ALLTYPE="select * from sand_ibs_transtype t order by typeid";
	public static final String SQL_INSERT_COMPANYRULE="insert into sand_company_rule values(?,?,?,?,?,?,'1')";
	public static final String SQL_DELETE_COMPANYRULE="delete from sand_company_rule where company_id=? and tran_type=?";
	public static final String SQL_INSERT_SAND_USER="insert into sand_user(username,password,role_id,state) values(?,?,?,0)";
	public static final String SQL_GETCOMBYID="SELECT t.curr_amt-t.warn_amt leftAmt from sand_company_money t,sand_company_channel c where t.channel_id=? and t.channel_id=c.outer_channel";
	public static final String SQL_GETCHANNELBYCOMID="SELECT s.channel_id channel FROM sand_user u join sand_company_money s on u.username=s.company_id and u.username=?";
	//修改余额前查询是否余额足够
	public static final String SQL_QUERYMONEYBEFOREUPDATE="select (t.curr_amt-t.warn_amt) leftAmt from sand_company_money t,sand_company_channel c where t.channel_id=? and t.channel_id=c.outer_channel";
	//修改余额
	public static final String SQL_UPDATEMONEYBYCOMPANY="update sand_company_money t set curr_amt=curr_amt-? where t.channel_id=? and t.curr_amt-t.warn_amt>0";
	//将修改余额记录，订单号，订单日期，商户号等信息写入记录表
	public static final String SQL_ADD_TO_IBSMINUS_MONEY="insert into sand_ibsminus_money(channelId,mid,orderId,orderDate,oper_amt) values(?,?,?,?,?)";
	//根据商户号和业务类型更改某业务状态
	public static final String SQL_UPDATESTATEBYTYPE="update sand_company_rule t set typestate=? where t.company_id=? and t.tran_type=?";
	//查找所有trans_type
	public static final String OUTER_GET_ALLTRANTYPE="select distinct * from sand_company_rule t where t.company_id=(select company_id from sand_company_money s where s.channel_id=?) order by t.tran_type";
	
	
}

