prompt PL/SQL Developer import file
prompt Created on 2014年9月26日 by Administrator
set feedback off
set define off
prompt Creating SYSTEM_BUTTON...
create table SYSTEM_BUTTON
(
  BUTTONCODE  VARCHAR2(20),
  BUTTONNAME  VARCHAR2(20),
  BUTTONICON  VARCHAR2(20),
  BUTTONEVENT VARCHAR2(100),
  RESDESC     VARCHAR2(50),
  STATE       INTEGER default (0)
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Creating SYSTEM_COMPANY...
create table SYSTEM_COMPANY
(
  COMPANY_ID   VARCHAR2(15),
  COMPANY_NAME VARCHAR2(50),
  CHANNEL_ID   VARCHAR2(15),
  INSERT_TIME  DATE,
  UPDATE_USER  VARCHAR2(50),
  UPDATE_TIME  DATE,
  BEFORE_AMT   NUMBER(10,2) default (0.00),
  AFTER_AMT    NUMBER(10,2),
  CURR_AMT     NUMBER(10,2),
  WARN_AMT     NUMBER(10,2),
  STATUS       INTEGER default (0)
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Creating SYSTEM_MENU...
create table SYSTEM_MENU
(
  MENUCODE       VARCHAR2(20),
  MENUNAME       VARCHAR2(20),
  PARENTMENUCODE VARCHAR2(20),
  MENUICON       VARCHAR2(20),
  MENUURL        VARCHAR2(100),
  RESDESC        VARCHAR2(50),
  STATE          INTEGER default (0)
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Creating SYSTEM_MENUBUTTON...
create table SYSTEM_MENUBUTTON
(
  MENUCODE   VARCHAR2(20),
  BUTTONCODE VARCHAR2(20)
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Creating SYSTEM_ROLE...
create table SYSTEM_ROLE
(
  ROLE_ID      NUMBER,
  ROLE_NAME    VARCHAR2(50),
  ROLE_COMMENT VARCHAR2(100),
  STATE        INTEGER default (0)
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Creating SYSTEM_ROLEMENU...
create table SYSTEM_ROLEMENU
(
  ROLE_ID  NUMBER,
  MENUCODE VARCHAR2(20)
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Creating SYSTEM_ROLE_MENUBUTTON...
create table SYSTEM_ROLE_MENUBUTTON
(
  ROLE_ID    NUMBER,
  MENUCODE   VARCHAR2(20),
  BUTTONCODE VARCHAR2(20)
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Creating SYSTEM_ROLE_MENUITEMS...
create table SYSTEM_ROLE_MENUITEMS
(
  ROLE_ID  NUMBER,
  MENUCODE VARCHAR2(20),
  ITEMCODE VARCHAR2(20)
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Creating SYSTEM_USER...
create table SYSTEM_USER
(
  USERNAME    VARCHAR2(50),
  USER_PASSWD VARCHAR2(50),
  COMPANY_ID  VARCHAR2(15),
  NAME        VARCHAR2(20),
  EMAIL       VARCHAR2(50),
  PHONENUMBER VARCHAR2(20),
  LAST_LOGIN  VARCHAR2(50),
  LAST_IP     VARCHAR2(20),
  USER_DESC   VARCHAR2(50),
  STATE       INTEGER default (0),
  ROLE_ID     NUMBER
)
tablespace ORAIBS_SPACE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );

prompt Disabling triggers for SYSTEM_BUTTON...
alter table SYSTEM_BUTTON disable all triggers;
prompt Disabling triggers for SYSTEM_COMPANY...
alter table SYSTEM_COMPANY disable all triggers;
prompt Disabling triggers for SYSTEM_MENU...
alter table SYSTEM_MENU disable all triggers;
prompt Disabling triggers for SYSTEM_MENUBUTTON...
alter table SYSTEM_MENUBUTTON disable all triggers;
prompt Disabling triggers for SYSTEM_ROLE...
alter table SYSTEM_ROLE disable all triggers;
prompt Disabling triggers for SYSTEM_ROLEMENU...
alter table SYSTEM_ROLEMENU disable all triggers;
prompt Disabling triggers for SYSTEM_ROLE_MENUBUTTON...
alter table SYSTEM_ROLE_MENUBUTTON disable all triggers;
prompt Disabling triggers for SYSTEM_ROLE_MENUITEMS...
alter table SYSTEM_ROLE_MENUITEMS disable all triggers;
prompt Disabling triggers for SYSTEM_USER...
alter table SYSTEM_USER disable all triggers;
prompt Deleting SYSTEM_USER...
delete from SYSTEM_USER;
commit;
prompt Deleting SYSTEM_ROLE_MENUITEMS...
delete from SYSTEM_ROLE_MENUITEMS;
commit;
prompt Deleting SYSTEM_ROLE_MENUBUTTON...
delete from SYSTEM_ROLE_MENUBUTTON;
commit;
prompt Deleting SYSTEM_ROLEMENU...
delete from SYSTEM_ROLEMENU;
commit;
prompt Deleting SYSTEM_ROLE...
delete from SYSTEM_ROLE;
commit;
prompt Deleting SYSTEM_MENUBUTTON...
delete from SYSTEM_MENUBUTTON;
commit;
prompt Deleting SYSTEM_MENU...
delete from SYSTEM_MENU;
commit;
prompt Deleting SYSTEM_COMPANY...
delete from SYSTEM_COMPANY;
commit;
prompt Deleting SYSTEM_BUTTON...
delete from SYSTEM_BUTTON;
commit;
prompt Loading SYSTEM_BUTTON...
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('VIEW', '查看详细', 'icon-add', 'view', null, 0);
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('ADD', '新增', 'icon-add', 'add', null, 0);
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('DEL', '删除', 'icon-del', 'del', null, 0);
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('EDIT', '编辑', 'icon-edit', 'edit', null, 0);
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('QUERY', '查询', 'icon-query', 'query', null, 0);
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('SAVE', '保存', 'icon-save', 'save', null, 0);
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('CANCEL', '取消', 'icon-cancel', 'cancel', null, 0);
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('EXPORT', '导出', 'icon-export', 'export', null, 0);
insert into SYSTEM_BUTTON (BUTTONCODE, BUTTONNAME, BUTTONICON, BUTTONEVENT, RESDESC, STATE)
values ('RESETPASS', '重置密码', 'icon-resetpass', 'resetpass', null, 0);
commit;
prompt 9 records loaded
prompt Loading SYSTEM_COMPANY...
insert into SYSTEM_COMPANY (COMPANY_ID, COMPANY_NAME, CHANNEL_ID, INSERT_TIME, UPDATE_USER, UPDATE_TIME, BEFORE_AMT, AFTER_AMT, CURR_AMT, WARN_AMT, STATUS)
values ('888002148160009', 'PayMent网站', '00000001', to_date('10-04-2014 11:05:35', 'dd-mm-yyyy hh24:mi:ss'), 'sandadmin', to_date('10-04-2014 11:14:04', 'dd-mm-yyyy hh24:mi:ss'), 1000000, 1100000, 9390.5, 10000, 1);
insert into SYSTEM_COMPANY (COMPANY_ID, COMPANY_NAME, CHANNEL_ID, INSERT_TIME, UPDATE_USER, UPDATE_TIME, BEFORE_AMT, AFTER_AMT, CURR_AMT, WARN_AMT, STATUS)
values ('888002148160011', '金诚通', '00000011', to_date('14-04-2014 10:20:08', 'dd-mm-yyyy hh24:mi:ss'), 'sandadmin', to_date('14-04-2014 10:20:08', 'dd-mm-yyyy hh24:mi:ss'), 100000, 100000, 536528.32, 10000, 0);
insert into SYSTEM_COMPANY (COMPANY_ID, COMPANY_NAME, CHANNEL_ID, INSERT_TIME, UPDATE_USER, UPDATE_TIME, BEFORE_AMT, AFTER_AMT, CURR_AMT, WARN_AMT, STATUS)
values ('888002148160002', '生活杉德网', '00000007', to_date('10-04-2014 15:19:20', 'dd-mm-yyyy hh24:mi:ss'), 'sandyunying', to_date('18-04-2014 13:25:08', 'dd-mm-yyyy hh24:mi:ss'), 100030, 1100030, 200030, 100000, 1);
commit;
prompt 3 records loaded
prompt Loading SYSTEM_MENU...
insert into SYSTEM_MENU (MENUCODE, MENUNAME, PARENTMENUCODE, MENUICON, MENUURL, RESDESC, STATE)
values ('0102', '角色管理', '01', 'icon-users', 'roleManage', null, 0);
insert into SYSTEM_MENU (MENUCODE, MENUNAME, PARENTMENUCODE, MENUICON, MENUURL, RESDESC, STATE)
values ('02', '对账管理', '00', 'icon-sys', null, null, 0);
insert into SYSTEM_MENU (MENUCODE, MENUNAME, PARENTMENUCODE, MENUICON, MENUURL, RESDESC, STATE)
values ('0201', '商户交易记录管理', '02', 'icon-users', 'transManage', null, 0);
insert into SYSTEM_MENU (MENUCODE, MENUNAME, PARENTMENUCODE, MENUICON, MENUURL, RESDESC, STATE)
values ('0104', '菜单管理', '01', 'icon-users', 'menuManage', null, 0);
insert into SYSTEM_MENU (MENUCODE, MENUNAME, PARENTMENUCODE, MENUICON, MENUURL, RESDESC, STATE)
values ('0103', '商户管理', '01', 'icon-users', 'compManage', null, 0);
insert into SYSTEM_MENU (MENUCODE, MENUNAME, PARENTMENUCODE, MENUICON, MENUURL, RESDESC, STATE)
values ('01', '系统管理', '00', 'icon-sys', null, null, 0);
insert into SYSTEM_MENU (MENUCODE, MENUNAME, PARENTMENUCODE, MENUICON, MENUURL, RESDESC, STATE)
values ('0101', '用户管理', '01', 'icon-users', 'userManage', null, 0);
commit;
prompt 7 records loaded
prompt Loading SYSTEM_MENUBUTTON...
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0101', 'ADD');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0101', 'EDIT');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0101', 'DEL');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0101', 'SAVE');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0101', 'CANCEL');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0102', 'ADD');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0102', 'EDIT');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0102', 'DEL');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0102', 'SAVE');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0102', 'CANCEL');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0101', 'RESETPASS');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0101', 'QUERY');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0201', 'QUERY');
insert into SYSTEM_MENUBUTTON (MENUCODE, BUTTONCODE)
values ('0201', 'EXPORT');
commit;
prompt 14 records loaded
prompt Loading SYSTEM_ROLE...
insert into SYSTEM_ROLE (ROLE_ID, ROLE_NAME, ROLE_COMMENT, STATE)
values (1, '超级管理员', '超级管理员角色，无敌权限', 0);
insert into SYSTEM_ROLE (ROLE_ID, ROLE_NAME, ROLE_COMMENT, STATE)
values (2, '普通商户', '系统外接商户，具有查询该商户交易记录和下载对账单权限', 1);
commit;
prompt 2 records loaded
prompt Loading SYSTEM_ROLEMENU...
insert into SYSTEM_ROLEMENU (ROLE_ID, MENUCODE)
values (1, '0101');
insert into SYSTEM_ROLEMENU (ROLE_ID, MENUCODE)
values (1, '0102');
insert into SYSTEM_ROLEMENU (ROLE_ID, MENUCODE)
values (1, '0201');
insert into SYSTEM_ROLEMENU (ROLE_ID, MENUCODE)
values (1, '0103');
insert into SYSTEM_ROLEMENU (ROLE_ID, MENUCODE)
values (2, '0201');
insert into SYSTEM_ROLEMENU (ROLE_ID, MENUCODE)
values (1, '0104');
commit;
prompt 6 records loaded
prompt Loading SYSTEM_ROLE_MENUBUTTON...
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0101', 'ADD');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0101', 'DEL');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0101', 'EDIT');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0101', 'QUERY');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0101', 'VIEW');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0101', 'RESETPASS');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (2, '0201', 'QUERY');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0102', 'ADD');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0102', 'EDIT');
insert into SYSTEM_ROLE_MENUBUTTON (ROLE_ID, MENUCODE, BUTTONCODE)
values (1, '0102', 'DEL');
commit;
prompt 10 records loaded
prompt Loading SYSTEM_ROLE_MENUITEMS...
prompt Table is empty
prompt Loading SYSTEM_USER...
insert into SYSTEM_USER (USERNAME, USER_PASSWD, COMPANY_ID, NAME, EMAIL, PHONENUMBER, LAST_LOGIN, LAST_IP, USER_DESC, STATE, ROLE_ID)
values ('admin', 'TST8VBeOTpV+bm9XrqTl5w==', null, '管理员', 'admin@sand.com.cn', '021-64031111', '2014-09-26 17:33:40', '0:0:0:0:0:0:0:1', '本系统超级管理员', 0, 1);
insert into SYSTEM_USER (USERNAME, USER_PASSWD, COMPANY_ID, NAME, EMAIL, PHONENUMBER, LAST_LOGIN, LAST_IP, USER_DESC, STATE, ROLE_ID)
values ('test', 'TST8VBeOTpV+bm9XrqTl5w==', '888002148160009', 'test', 'test@163.com', '11111111', null, null, 'test', 0, 2);
insert into SYSTEM_USER (USERNAME, USER_PASSWD, COMPANY_ID, NAME, EMAIL, PHONENUMBER, LAST_LOGIN, LAST_IP, USER_DESC, STATE, ROLE_ID)
values ('test2', 'TST8VBeOTpV+bm9XrqTl5w==', '888002148160002', 'test22', 'test2@163.com', '22222222', null, null, 'test222222222', 0, 2);
insert into SYSTEM_USER (USERNAME, USER_PASSWD, COMPANY_ID, NAME, EMAIL, PHONENUMBER, LAST_LOGIN, LAST_IP, USER_DESC, STATE, ROLE_ID)
values ('longhuasishen', 'TST8VBeOTpV+bm9XrqTl5w==', null, '李守勇', 'lishouyong.920@163.com', '15000596141', '2014-08-07 17:13:03', '0:0:0:0:0:0:0:1', '管理员哦', 0, 1);
insert into SYSTEM_USER (USERNAME, USER_PASSWD, COMPANY_ID, NAME, EMAIL, PHONENUMBER, LAST_LOGIN, LAST_IP, USER_DESC, STATE, ROLE_ID)
values ('sandlife', 'TST8VBeOTpV+bm9XrqTl5w==', '888002148160002', '1111', 'sandlife@163.com', '11111111', null, null, 'sandlife操作人员', 0, 2);
commit;
prompt 5 records loaded
prompt Enabling triggers for SYSTEM_BUTTON...
alter table SYSTEM_BUTTON enable all triggers;
prompt Enabling triggers for SYSTEM_COMPANY...
alter table SYSTEM_COMPANY enable all triggers;
prompt Enabling triggers for SYSTEM_MENU...
alter table SYSTEM_MENU enable all triggers;
prompt Enabling triggers for SYSTEM_MENUBUTTON...
alter table SYSTEM_MENUBUTTON enable all triggers;
prompt Enabling triggers for SYSTEM_ROLE...
alter table SYSTEM_ROLE enable all triggers;
prompt Enabling triggers for SYSTEM_ROLEMENU...
alter table SYSTEM_ROLEMENU enable all triggers;
prompt Enabling triggers for SYSTEM_ROLE_MENUBUTTON...
alter table SYSTEM_ROLE_MENUBUTTON enable all triggers;
prompt Enabling triggers for SYSTEM_ROLE_MENUITEMS...
alter table SYSTEM_ROLE_MENUITEMS enable all triggers;
prompt Enabling triggers for SYSTEM_USER...
alter table SYSTEM_USER enable all triggers;
set feedback on
set define on
prompt Done.
