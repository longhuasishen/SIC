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
    <table id="dg" title="按钮信息列表" class="easyui-datagrid" fitColumns="true" toolbar="#toolbar"
    		url="button/list" loadMsg="正在加载数据..." rownumbers="true"
            checkbox="true" 
            rownumbers="true" fit="true" singleSelect="true">
        <thead>
            <tr>
                <th data-options="field:'buttonCode',width:80">编码</th>
                <th data-options="field:'buttonIcon',width:90,formatter:iconFormatter">图标</th>
                <th data-options="field:'buttonName',width:100">名称</th>
                <th data-options="field:'buttonEvent',width:150">事件</th>
                <th data-options="field:'buttonState',width:60,formatter:checkFormatter">是否有效</th>
                <th data-options="field:'buttonDesc',width:90">描述</th>
            </tr>
        </thead>
    </table>
    </div>
    <div id="toolbar">
       <span style="margin-left:15px;">
	        <shiro:hasPermission name="buttonManage:ADD">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="buttonManage:EDIT">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="buttonManage:DEL">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	        </shiro:hasPermission>
	    </span>
	    <span style="margin-left:15px;">
	    	<shiro:hasPermission name="buttonManage:QUERY">
		    	<span>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">查询条件:</a>
			    	<select id="queryOption">
			    		<option value="username">编码</option>
			    		<option value="name">名称</option>
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
    
    <div id="w" class="easyui-window" title="操作按钮" data-options="modal:true,resizable:false,closed:true,maximizable:false,minimizable:false,collapsible:false" style="width:420px;height:250px;">
		<div class="easyui-layout" data-options="fit:true">
		  <div region="center" style="text-align:left;padding:5px;">
	        <form id="fm" method="post">
	            <div class="fitem">
	                <label>按钮编码[*]:</label>
	                <input name="buttonCode" id='buttonCode' class="easyui-textbox">
	            </div>
	            <div class="fitem">
	                <label>按钮名称[*]:</label>
	                <input name="buttonName" id='buttonName' class="easyui-textbox">
	            </div>
	            <div class="fitem">
	            	<label>按钮图标[*]:</label>
	                <input name="buttonIcon" id='buttonIcon' class="easyui-textbox" >
	            </div>
	            <div class="fitem">
	                <label>按钮事件[*]:</label>
	                <input name="buttonEvent" id='buttonEvent' class="easyui-textbox" >
	            </div>
	            <div class="fitem">
	            	<label>描述[*]:</label>
	                <input name="buttonDesc" id='buttonDesc' class="easyui-textbox" >
	            </div>
	          </form>
	        </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:2px 0 0;height:30px;">
                <a id="btnOk" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="save()">保存</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')">取消</a>
            </div>
        </div>
    </div>
    
    <script type="text/javascript" src="js/button.js"></script>
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
            width:80px;
            margin-left:30px;
            margin-top:5px;
        }
        .fitem input{
        	width:200px;
        	height:20px;
        }
    </style>
</body>
</html>
