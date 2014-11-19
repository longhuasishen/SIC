<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <div id="cc"  class="easyui-layout" fit="true">  
	     <div region="west" data-options="collapsible:false" title="请选择资源" style="width:200px;">
	     	<ul id="tt" class="easyui-tree" data-options="method:'get',url:'role_privilege.action?roleId=none',animate:true,onlyLeafCheck:true" style="padding:10px;"></ul>
	     </div>  
	     <div region="center" title="资源信息列表" style="padding:5px;background:#eee;">
	     	<table id="dg" class="easyui-datagrid" style="display:block;"
		            url="resource_list.action" loadMsg="正在加载数据..." rownumbers="true"
		           pagination="true" pageSize="15" pageList="[5,15,25,35,45,55]" checkbox="true" 
		            rownumbers="true" fitColumns="true" fit="true" singleSelect="true">
		        <thead>
		            <tr>
		            	<th data-options="field:'ck',checkbox:true"></th>
		                <th field="username" width="50">用户名</th>
		                <th field="menuname" width="50">资源名称</th>
		                <th field="parentmenuid" width="50">用户名</th>
		                <th field="username" width="50">用户名</th>
		                <th field="username" width="50">用户名</th>
		                <th field="username" width="50">用户名</th>
		            </tr>
		        </thead>
		    </table>
	     </div>  
 	</div>  
    </div>
    <div id="toolbar">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">请输入待查询的商户编号:</a>
    	<input name="queryCompanyId" id='queryCompanyId' class="easyui-validatebox" type="text">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryCompany()">查询</a>
        <!--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newCompany()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCompany()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyCompany()">删除</a>
    --></div>
    
    
    <script type="text/javascript"><!--
    	$('#tt').tree({
			onClick: function(node){
				alert(node.text+node.id);  // alert node text property when clicked
				$('#dg').datagrid('load',{
	            	companyId:companyId
	            });
			}
		});
    	function resetPass(index){
    		$('#dg').datagrid('selectRow',index);// 关键在这里  
		    var row = $('#dg').datagrid('getSelected');  
		    if (row){  
		        //$('#dlg').window('open');  
    			//$.messager.alert('upload','upload'+index+'--'+row.clearDate+'--'+row.companyId);
    			$.messager.confirm('提示','你确定要重置这个商户的密码么?',function(r){
                    if (r){
                        $.post('user_resetpass.action?companyId='+row.username,function(result){
                            if (result.success){
                                $.messager.alert('提示',row.username+'密码重置成功，初始化为sand');    // reload the user data
                            } else {
                                $.messager.show({    // show error message
                                    title: 'Error',
                                    msg: result.errorMsg
                                });
                            }
                        },'json');
                    }
                });
		    }  
    	}
    	function formatOper(val,row,index){
			return '<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="resetPass('+index+')">密码重置</a>';
		}
        var url;
        function operation_add(){
				$.messager.alert('add','add');
			}
		function queryCompany(){
        	var companyId=$('#queryCompanyId').val();
            $('#dg').datagrid('load',{
            	companyId:companyId
            });
        }
        function newCompany(){
        	$('.ftitle').text('添加新的商户');
        	$('#companyId').attr("readonly","");
        	$('#cc').attr("disabled","true");
        	$('#operAmt').attr("disabled","true");
            $('#dlg').dialog('open').dialog('setTitle','New Company');
            $('#fm').form('clear');
            $('#currAmt').val("0");
            url = 'com_save.action';
        }
        function editCompany(){
        	$('.ftitle').text('编辑商户');
        	$('#companyId').attr("readonly","true");
        	$('#currAmt').attr("readonly","true");
        	$('#cc').attr("disabled","");
        	$('#operAmt').attr("disabled","");
        	$('#fm').form('clear');
            var row = $('#dg').datagrid('getSelected');
            //$.messager.alert('Info', row.companyId+":"+row.companyName+":"+row.currAmt+":"+row.updateTime);
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','Edit Company');
                $('#fm').form('load',row);
                url = 'com_update.action';
            }else{
            	$.messager.alert('提示','请先选择一个商户!','info');
            }
        }
        function saveCompany(){
        	var oper=$('#cc').combobox('getValue');
        	if(oper==='mul'){
        		if(parseInt($('#currAmt').val())<parseInt($('#operAmt').val())){
        			$.messager.alert('提示','商户当前余额不足!','info');
        			return;
        		}
        	}
        	//document.forms[0].submit();
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.success==false){
                        $.messager.show({
                            title: 'Error',
                            msg: result.message
                        });
                    } else {
                        $('#dlg').dialog('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
        function destroyCompany(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('提示','你确定要删除这个商户信息么?',function(r){
                    if (r){
                        $.post('com_delete.action',{companyId:row.companyId},function(result){
                            if (result.success){
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.show({    // show error message
                                    title: 'Error',
                                    msg: result.errorMsg
                                });
                            }
                        },'json');
                    }
                });
            }else{
            	$.messager.alert('提示','请先选择一个商户!','info');
            }
        }
    --></script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
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
