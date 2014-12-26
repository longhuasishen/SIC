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
  	<div data-options="region:'west',split:true,collapsible:false" title="角色列表" style="width:200px;">
  		<ul id="roleTree" class="easyui-tree" data-options="url:'roleprivi/selectRole',method:'get',animate:true"></ul>
  	</div>
  	<div data-options="region:'center',title:''">
	    <div class="easyui-tabs" style="height:auto">
	    	<div title="角色成员" >
	    		<div class="easyui-layout" style="height:93%;" data-options="fit:true" >
		            <table id="dg" title="角色成员列表" class="easyui-datagrid" style="display:block;"
				           toolbar="#toolbar" url="" loadMsg="正在加载数据..." rownumbers="true"
				           checkbox="true" rownumbers="true" fit="true" singleSelect="true">
				        <thead data-options="frozen:true">
				            <tr>
				                <th data-options="field:'username',width:80">用户名</th>
				                <th data-options="field:'name',width:70">姓名</th>
				                <th data-options="field:'email',width:150">邮箱</th>
				                <th data-options="field:'phonenumber',width:90">联系电话</th>
				                <th data-options="field:'company_id',width:120">所属商户</th>
				            </tr>
				        </thead>
				        <thead>
				        	<tr>
				                <th data-options="field:'last_login',width:140">最近登录时间</th>
				                <th data-options="field:'last_ip',width:120">最近登录IP</th>
				                <th data-options="field:'desc',width:140">说明</th>
				            </tr>
				        </thead>
				    </table>
			    </div>
	        </div>
	        <div title="模块权限" data-options="closable:true">
	            <table id="dgMenu" title="菜单信息列表" class="easyui-treegrid" fitColumns="true" checkbox="true"  
	    		data-options="url: 'roleprivi/selectMenu',
	                		  method: 'post',
	                	      rownumbers: true,
	                		  idField: 'menucode',
	                		  singleSelect:false,
	                		  treeField: 'menuname'">
			        <thead>
			            <tr>
			            	<th data-options="field:'ck',checkbox:true"></th>
			                <th data-options="field:'menuname',width:120">模块名称</th>
			                <th data-options="field:'menucode',width:50">模块编码</th>
			                <th data-options="field:'parentmenucode',width:50">上级模块编码</th>
			                <th data-options="field:'menuurl',width:90">链接地址</th>
			                <th data-options="field:'menuicon',width:90">链接图标</th>
			                <th data-options="field:'menudesc',width:90">模块说明</th>
			            </tr>
			        </thead>
			    </table>
	        </div>
	        <div title="按钮权限" data-options="closable:true">
	            <div class="easyui-layout" style="height:93%" data-options="fit:true" data-options="fit:true">
	            	<div data-options="region:'west',title:'模块列表',split:true,collapsible:false" style="width:200px;">
	            		<ul id="menuTree" class="easyui-tree" data-options="url:'',method:'get',animate:true"></ul>
	            	</div>
	            	<div data-options="region:'center',title:'选择按钮',split:true,collapsible:false">
	            		<div id="alloctTools" style="background-color:#F7F7F7">
			    			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-resetpass" plain="true" onclick="add()">授权</a>
			    		</div>
	            		<div id="buttonList">暂无按钮</div>
	            	</div>
	            </div>
	        </div>
	    </div>
    
    </div>
    <div id="toolbar">
       <span style="margin-left:15px;">
	        <shiro:hasPermission name="menuManage:ADD">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="menuManage:EDIT">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="menuManage:DEL">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="menuManage:ALLOT">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-resetpass" plain="true" onclick="resetpass()">分配按钮</a>
	        </shiro:hasPermission>
	    </span>
	    <span style="margin-left:15px;">
	    	<shiro:hasPermission name="menuManage:QUERY">
		    	<span>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">查询条件:</a>
			    	<select id="queryOption">
			    		<option value="username">用户名</option>
			    		<option value="name">姓名</option>
			    		<option value="phonenumber">联系电话</option>
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
    
    <div id="w" class="easyui-window" title="菜单信息" data-options="modal:true,resizable:false,closed:true,maximizable:false,minimizable:false,collapsible:false" style="width:420px;height:280px;">
		<div class="easyui-layout" data-options="fit:true">
		  <div region="center" style="text-align:left;padding:5px;">
	        <form id="fm" method="post">
	            <div class="fitem">
	                <label>模块名称[*]:</label>
	                <input name="menuname" id='menuname' class="easyui-textbox">
	            </div>
	            <div class="fitem">
	                <label>模块编码[*]:</label>
	                <input name="menucode" id='menucode' class="easyui-textbox">
	            </div>
	            <div class="fitem">
	                <label>上级模块[*]:</label>
	                <input name="parentmenucode" id='parentmenucode' class="easyui-combotree"  style="width:133px;" data-options="url:'menu/select'"/>
	            </div>
	            <div class="fitem">
	            	<label>模块图标[*]:</label>
	                <input name="menuicon" id='menuicon' class="easyui-textbox" >
	            </div>
	            <div class="fitem">
	                <label>模块链接[*]:</label>
	                <input name="menuurl" id='menuurl' class="easyui-textbox" >
	            </div>
	            <div class="fitem">
	            	<label>模块说明[*]:</label>
	                <input name="menudesc" id='menudesc' class="easyui-textbox" >
	            </div>
	          </form>
	        </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:2px 0 0;height:30px;">
                <a id="btnOk" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="save()">保存</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')">取消</a>
            </div>
        </div>
    </div>
    
    <script type="text/javascript" src="js/rolePri.js"></script>
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
