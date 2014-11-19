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
	<script type="text/javascript">
		function myformatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
        }
        function myparser(s){
            if (!s) return new Date();
            var ss = (s.split('-'));
            var y = parseInt(ss[0],10);
            var m = parseInt(ss[1],10);
            var d = parseInt(ss[2],10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
                return new Date(y,m-1,d);
            } else {
                return new Date();
            }
        }
	</script>
  </head>
  
  <body  class="easyui-layout">
  	<div data-options="region:'center',title:''">
    <table id="dg" title="运营商业务结算规则信息列表" class="easyui-datagrid" style="display:block;"
            url="rule_list.action" loadMsg="正在加载数据..." rownumbers="true"
            toolbar="#toolbar" pagination="true" pageSize="15" pageList="[5,15,25,35,45,55]" checkbox="true" 
            rownumbers="true" fitColumns="true" fit="true" singleSelect="true">
        <thead>
            <tr>
            	<th data-options="field:'ck',checkbox:true"></th>
                <th field="company_id" width="35">商户编号</th>
                <th field="company_name" width="50">商户名称</th>
                <th field="tran_type" width="20">业务类型</th>
                <th field="type_name" width="40">业务名称</th>
                <th field="rule_name" width="50">清算规则</th>
                <th field="startDate" width="40">开始日期</th>
                <th field="endDate" width="40">结束日期</th>
                <th field="clearDate">清算日期</th>
            </tr>
        </thead>
    </table>
    </div>
    <div id="toolbar">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">查询商户编号:</a>
    	<input name="queryCompanyId" id='queryCompanyId' class="easyui-validatebox" type="text">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">查询业务类型:</a>
    	<input name="queryTypeId" id='queryTypeId' class="easyui-validatebox" type="text">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryCompany()">查询</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRule()">编辑</a>
    </div>
    
    <div id="w" class="easyui-window" title="商户对账规则编辑" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:400px;height:280px;">
		<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'"  style="padding:5px;">
               <form id="fm" method="post">
	            <div class="fitem">
	                <label>商户编号:</label>
	                <input name="company_id" id='company_id' class="easyui-validatebox" required="true">
	            </div>
	            <div class="fitem">
	                <label>商户名称:</label>
	                <input name="company_name" class="easyui-validatebox" required="true">
	            </div>
	            <div class="fitem">
	                <label>业务类型:</label>
		            <input id="tran_type" name="tran_type" class="easyui-combobox" name="rule_id" style="width:155px;" data-options="editable:false,panelHeight:'200',valueField:'type_name',textField:'trans_type',url:'trans_getTransType.action',onSelect: function(rec){$('#type_name').val(rec.type_name);}"/>
	            </div>
	            <div class="fitem">
	                <label>业务名称:</label>
	                <input name="type_name" id='type_name'>
	            </div>
	            <div class="fitem">
	                <label>对账规则:</label>
		            <input id="rule" class="easyui-combobox" name="rule_id" style="width:155px;" data-options="editable:false,panelHeight:'auto',valueField:'rule_id',textField:'rule_name',url:'rule_getRule.action'"/>

	            </div>
	            <div class="fitem">
	                <label>开始日期:</label>
	                <input name="startDate" id='startDate' class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></input>
	            </div>
	            <!--<div class="fitem">
	                <label>用户角色:</label>
	               <input id="role" class="easyui-combobox" name="role_id" style="width:155px;" data-options="editable:false,panelHeight:'auto',valueField:'role_id',textField:'role_name',url:'com_getRole.action'"/>
	            </div>
	        --></form>	
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:2px 0 0;height:30px;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="getChecked('ok')">保存</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')">取消</a>
            </div>
        </div>
    </div>
    <script type="text/javascript"><!--
        var url;
        function operation_add(){
				$.messager.alert('add','add');
			}
		function queryCompany(){
        	var companyId=$('#queryCompanyId').val();
        	var typeName=$('#queryTypeId').val();
            $('#dg').datagrid('load',{
            	companyId:companyId,
            	typeName:typeName
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
        function editRule(){
		       var row = $('#dg').datagrid('getSelected');  
		       if (row){  
			      roleId=row.company_id;  
	        	   $('#tt').tree({
					    url:'role_privilege.action?roleId='+roleId
					});
	               $('#w').window('open');
	               $('#fm').form('load',row);
	               url='role_add.action?roleId='+roleId;
			    }  else{
			    	$.messager.alert('提示','请先选择一个商户的交易类别');
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
                    	$.messager.alert('提示','商户信息添加成功，商户用户名:'+result.companyId+',系统密码:'+result.pass,'info');
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
                                $('#dg').datagrid('load',{page:0});    // reload the user data
                                 $.messager.show({    // show error message
                                    title: 'success',
                                    msg: result.message
                                });
                            } else {
                                $.messager.show({    // show error message
                                    title: 'Error',
                                    msg: result.message
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
