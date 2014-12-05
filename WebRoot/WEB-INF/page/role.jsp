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
    <table id="dg" title="角色信息列表" class="easyui-datagrid" style="display:block;"
           toolbar="#toolbar" url="role/list" loadMsg="正在加载数据..." rownumbers="true"
           pagination="true" pageSize="15" pageList="[5,15,25,35,45,55]" checkbox="true" 
            rownumbers="true" fitColumns="true" fit="true" singleSelect="true">
        <thead>
            <tr>
            	<th data-options="field:'ck',checkbox:true"></th>
                <th data-options="field:'role_id', width:20">角色ID</th>
                <th data-options="field:'role_name' ,width:30">角色名称</th>
                <th data-options="field:'rstate', width:50, formatter:checkFormatter">状态</th>
                <th data-options="field:'role_comment', width:50">角色说明</th>
            </tr>
        </thead>
    </table>
    </div>
    <div id="toolbar">
    	<shiro:hasPermission name="roleManage:ADD">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRole()">添加角色</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="roleManage:EDIT">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPrivi()">编辑角色</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="roleManage:DEL">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyRole()">删除角色</a>
        </shiro:hasPermission>
   </div>
    
    <div id="w" class="easyui-window" title="角色信息" data-options="modal:true,resizable:false,closed:true,maximizable:false,minimizable:false,collapsible:false" style="width:480px;height:290px;">
		<div class="easyui-layout" data-options="fit:true">
		  <div data-options="region:'center'" style="padding:5px;">
	        <form id="fm" method="post">
	            <div class="fitem">
	                <label>角色编号[*]:</label>
	                <input name="role_id" id='role_id' class="easyui-textbox" style="width:315px;" required="true">
	            </div>
	            <div class="fitem">
	                <label>角色名称[*]:</label>
	                <input name="role_name" id='role_name' class="easyui-textbox" style="width:315px;" required="true">
	            </div>
	            <div class="fitem">
	                <label>角色状态[*]:</label>
	                <input id="state" name="state" type="checkbox"/>
	                <label>有效</label>
	            </div>
	            <div class="fitem">
	            	<label>描述[*]:</label>
	                <textarea style="height:100px;width:315px;vertical-align:top" id="role_comment" name="role_comment"></textarea>
	            </div>
	          </form>
	        </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:2px 0 0;height:30px;">
                <a id="btnOk" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="save()">保存</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')">取消</a>
            </div>
        </div>
    </div>
    
    <script type="text/javascript" src="js/role.js"></script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 15px;
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
            width:80px;
        }
    </style>
</body>
</html>
