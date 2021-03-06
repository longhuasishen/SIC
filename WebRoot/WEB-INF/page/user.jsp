<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
    <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>

  </head>
  
  <body  class="easyui-layout">
  	<div data-options="region:'center',title:''">
    <table id="dg" title="用户信息列表" class="easyui-datagrid" style="display:block;"
           toolbar="#toolbar" url="user/list" loadMsg="正在加载数据..." rownumbers="true"
           pagination="true" pageSize="15" pageList="[5,15,25,35,45,55]" checkbox="true" 
            rownumbers="true" fit="true" singleSelect="true">
        <thead data-options="frozen:true">
            <tr>
                <th data-options="field:'username',width:100">用户名</th>
                <th data-options="field:'name',width:80">姓名</th>
                <th data-options="field:'email',width:150">邮箱</th>
                <th data-options="field:'phonenumber',width:90">联系电话</th>
                <th data-options="field:'company_id',width:120">所属商户</th>
                <th data-options="field:'role.role_name',width:90,formatter:roleFormatter">用户角色</th>
                <th data-options="field:'ustate',width:60,formatter:checkFormatter">是否有效</th>
            </tr>
        </thead>
        <thead>
        	<tr>
                <th data-options="field:'last_login',width:150">最近登录时间</th>
                <th data-options="field:'last_ip',width:120">最近登录IP</th>
                <th data-options="field:'desc',width:150">说明</th>
            </tr>
        </thead>
    </table>
    </div>
    <div id="toolbar">
       <span style="margin-left:15px;">
	        <shiro:hasPermission name="userManage:ADD">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="userManage:EDIT">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="userManage:DEL">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="userManage:RESETPASS">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-resetpass" plain="true" onclick="resetpass()">重置密码</a>
	        </shiro:hasPermission>
	    </span>
	    <span style="margin-left:15px;">
	    	<shiro:hasPermission name="userManage:QUERY">
		    	<span>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">查询条件:</a>
			    	<select id="queryOption">
			    		<option value="username">用户名</option>
			    		<option value="name">姓名</option>
			    		<option value="companyid">所属商户</option>
			    	</select>
		    	</span>
		    	<span style="margin-left:15px;">
		    		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">关键字:</a>
		    		<input name="queryCompanyId" id='queryCompanyId' class="easyui-validatebox" type="text">
		        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="query()">查询</a>
		        </span>
	        </shiro:hasPermission>
	    </span>
    </div>
    
    <div id="w" class="easyui-window" title="用户信息" data-options="modal:true,resizable:false,closed:true,maximizable:false,minimizable:false,collapsible:false" style="width:550px;height:320px;">
		<div class="easyui-layout" data-options="fit:true">
		  <div data-options="region:'center'" style="padding:5px;">
	        <form id="fm" method="post">
	            <div class="fitem">
	                <label>用户名:</label>
	                <input name="username" id='username' class="easyui-validatebox" data-options="validType:'length[1,20]'">
	                <label>密码:</label>
	                <input name='password' id='password' class="easyui-validatebox" type="password"  data-options="validType:'length[1,20]'">
	            </div>
	            <div class="fitem">
	                <label>邮箱:</label>
	                <input name="email" id='email' class="easyui-validatebox" data-options="validType:'email'">
	                <label>联系电话:</label>
	                <input name="phonenumber" id='phonenumber' class="easyui-textbox" required="true">
	            </div>
	            <div class="fitem">
	            	<label>真实姓名:</label>
	                <input name="name" id='name' class="easyui-textbox" required="true">
	                <label>是否有效:</label>
	                <input type="checkbox" id="ustate" name="ustate" checked="checked"><span style="color:red">注意：禁用将不能登录系统</span>
	            </div>
	            <div class="fitem">
	                <label>默认角色:</label>
	                <input id="roleid" name="roleid" class="easyui-combobox" style="width:133px;" data-options="editable:false,panelHeight:'100',valueField:'role_id',textField:'role_name',url:'role/select'"/>
	                <label>商户号:</label>
	                <input name="company_id" id='company_id' class="easyui-combobox"  style="width:133px;" data-options="editable:false,panelHeight:'100',valueField:'comp_id',textField:'comp_name',url:'comp/select'"/>
	            </div>
	            <div class="fitem">
	            	<label>说明:</label>
	                <textarea style="height:100px;width:365px;vertical-align:top" id="desc" name="desc"></textarea>
	            </div>
	          </form>
	        </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:2px 0 0;height:30px;">
                <a id="btnOk" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="save()">保存</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')">取消</a>
            </div>
        </div>
    </div>
    
    <script type="text/javascript" src="js/user.js"></script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 10px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:60px;
            margin-left:30px;
        }
    </style>
</body>
</html>
