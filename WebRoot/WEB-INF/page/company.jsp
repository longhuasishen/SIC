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
    <table id="dg" title="运营商金额信息列表" class="easyui-datagrid" style="display:block;"
            url="comp/list.action" loadMsg="正在加载数据..." rownumbers="true"
            toolbar="#toolbar" checkbox="true" 
            rownumbers="true" fit="true" singleSelect="true">
        <thead data-options="frozen:true">
            <tr>
            	<th data-options="field:'ck',checkbox:true"></th>
                <th field="companyId" width="120" sortable="true">运营商编号</th>
                <th field="companyName" width="100">运营商名称</th>
                <th field="channelId" width="100">运营商渠道号</th>
                <th field="insertTime" width="150">商户添加日期</th>
            </tr>
        </thead>
        <thead>
        	<tr>
                <th field="updateUser" width="100">最近更新人员</th>
                <th field="updateTime" width="150">最近更新日期</th>
                <th field="beforeAmt" width="120">上一次操作前金额</th>
                <th field="afterAmt" width="120">上一次操作后金额</th>
                <th field="currAmt" width="100">账户当前余额</th>
                <th field="warnAmt" width="100">账户预警余额</th>
                <th field="status" width="50" sortable="true">状态</th>
            </tr>
        </thead>
    </table>
    </div>
    <div id="toolbar">
       <shiro:hasPermission name="compManage:QUERY">
    		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">请输入待查询的运营商编号:</a>
    		<input name="queryCompanyId" id='queryCompanyId' class="easyui-validatebox" type="text">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryCompany()">查询</a>
       </shiro:hasPermission>
       <shiro:hasPermission name="compManage:ADD">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newCompany()">添加新商户</a>
       </shiro:hasPermission>
       <shiro:hasPermission name="compManage:EDIT">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCompany()">编辑商户</a>
       </shiro:hasPermission>
       <shiro:hasPermission name="compManage:DEL"> 
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyCompany()">删除商户</a>
       </shiro:hasPermission>
        <!--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="forbiddenCompany()">禁用商户</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="allowCompany()">启用商户</a>
    --></div>
    <div id="w" class="easyui-window" title="商户编辑" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:430px;height:350px;">
		<div class="easyui-layout" data-options="fit:true">
		  <div data-options="region:'center'" style="padding:5px;">
	        <form id="fm" method="post">
	            <div class="fitem">
	                <label>运营商编号:</label>
	                <input name="companyId" id='companyId' class="easyui-validatebox" required="true">
	            </div>
	            <div class="fitem">
	                <label>运营商名称:</label>
	                <input name="companyName" class="easyui-validatebox" required="true">
	            </div>
	            <div class="fitem">
	                <label>运营商渠道号:</label>
	                <input name="channelId" id="channelId" class="easyui-validatebox" required="true">
	            </div>
	            <div class="fitem">
	                <label>当前余额:</label>
	                <input name="currAmt" id='currAmt' class="easyui-numberbox" data-options="precision:2,groupSeparator:','"  value="0">
	            </div>
	            <div class="fitem">
	                <label>操作类型:</label>
	               <select id="cc" class="easyui-combobox" name="oper" style="width:155px;" data-options="editable:false,panelHeight:'auto'">
					    <option value="add" selected="selected">增加</option>
					    <!--<option value="mul">减少</option>-->
					</select>
	            </div>
	            <div class="fitem">
	                <label>操作金额:</label>
	                <input name="operAmt" id="operAmt" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"  value="0">
	            </div>
	            <div class="fitem">
	                <label>预警金额:</label>
	                <input name="warnAmt" id="warnAmt" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"  value="0">
	            </div>
	            <div class="fitem">
	                <label>商户状态:</label>
	               <select id="state" class="easyui-combobox" name="state" style="width:155px;" data-options="editable:false,panelHeight:'auto'">
					    <option value="yes" selected="selected">可用</option>
					    <option value="no" >不可用</option>
					    <!--<option value="mul">减少</option>-->
					</select>
	            </div>
	            <!--<div class="fitem">
	                <label>用户角色:</label>
	               <input id="role" class="easyui-combobox" name="role_id" style="width:155px;" data-options="editable:false,panelHeight:'auto',valueField:'role_id',textField:'role_name',url:'com_getRole.action'"/>
	            </div>
	        --></form>
	        </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:2px 0 0;height:30px;">
                <a id="btnOk" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="saveCompany()">保存</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')">取消</a>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="js/company.js"></script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:15px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>
</body>
</html>
