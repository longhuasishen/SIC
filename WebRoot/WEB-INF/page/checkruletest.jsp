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
            	<th data-options="field:'ck',checkbox:true"><br></th>
                <th field="company_id" width="35">商户编号</th>
                <th field="company_name" width="50">商户名称</th>
                <th field="tran_type" width="20">业务类型</th>
                <th field="type_name" width="40">业务名称</th>
                <th field="rule_name" width="50">清算规则</th>
                <th field="startDate" width="40">开始日期</th>
                <th field="endDate" width="40">结束日期</th>
                <th field="clearDate">清算日期</th>
                <th field="typeState">业务状态</th>
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRule()">添加新业务规则</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRule()">删除业务规则</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="forbiddenRule()">禁用业务</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="allowRule()">启用业务</a>
    </div>
    
    <div id="w" class="easyui-window" title="商户对账规则编辑" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:700px;height:300px;">
		<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="text-align:left;padding-left:25px;padding-top:2px;height:30px;">
                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true">请选择商户:</a>
    			<input id="addCompanyId" name="addCompanyId" class="easyui-combobox" name="rule_id" style="width:155px;" data-options="editable:false,panelHeight:'100',valueField:'company_id',textField:'company_name',url:'com_getCompany.action'"/>
            </div>
            <!--<div data-options="region:'west',split:true,collapsible:false" style="width:300px">
            	<div class="fitem">
	                <ul id="tt" class="easyui-tree" data-options="url:'trans_getTransType.action',method:'get',animate:true,checkbox:true,onlyLeafCheck:true"></ul>
	            </div>
            </div>
            --><div data-options="region:'center'"  style="padding:5px;">
               <form id="fm" method="post">
                 <div class="fitem">
					<label>请选择业务类型:</label>
					<div class="fitem" id="typelist"></div>
	             </div>
	            <script type="text/javascript">
	            	var types="";
	            	$.ajax({
						url: "trans_getTransType.action",
				        data: "",
				        type:"POST",
				        contentType: "application/json; charset=utf-8",
				        dataType: "json",
				        success: function (data) {
				           types=eval(data);
				           //_menus=JSON.jsonify(data);
				           $.each(types, function(i) {
				               var object=types[i];
				               $('#typelist').html($('#typelist').html()+'<span style="width:200px;display:block;float:left;"><input type="checkbox" class="checktype" id="'+object.trans_type+'" value="'+object.type_name+'"/>'+object.type_name+'</span>');
				               if((i+1)%3==0){
				               	  $('#typelist').html($('#typelist').html()+'<br/>');
				               }
				           });
				        },
				        error: function (msg) {
				            alert('页面初始化错误,强制退出');
				            $('#w').window('close');
				        }
					});
	            </script>
	            <div class="fitem" style="margin-top:40px;">
	                <label>对账规则:</label>
		            <input id="rule" class="easyui-combobox" name="rule_id" style="width:155px;" data-options="editable:false,panelHeight:'auto',valueField:'rule_id',textField:'rule_name',url:'rule_getRule.action'"/>
	            	<input id="t" class="easyui-numberbox" name="t" style="visibility:hidden"/>
	            </div>
	            <div class="fitem">
	                <label>开始日期:</label>
	                <input name="startDate" id='startDate' class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></input>
	            </div>
	           </form>	
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:2px 0 0;height:30px;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="saveCheckRule()">保存</a>
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
        function addRule(){
        	$('#w').window('open');
        	$(".checktype").attr("checked","");
        	$('#addCompanyId').combobox('setValue','888002148160002');
        	$('#rule').combobox('setValue','0001');
        	$('#startDate').datebox('setValue',myformatter(new Date()));
            url = 'rule_save.action';
        }
        function deleteRule(){
		       var row = $('#dg').datagrid('getSelected');  
		       if (row){  
			      comId=row.company_id;  
			      typeId=row.tran_type;
				  $.messager.confirm('提示','你确定要删除该商户的这个业务规则么?',function(r){
                    if (r){
                        $.post('rule_delete.action',{companyId:comId,typeIds:typeId},function(result){
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
			 }  else{
			    	$.messager.alert('提示','请先选择一个商户的某个业务');
			 }
        }
        $('#rule').combobox({
            onSelect:function(rec){
	        	var ruleId=$('#rule').combobox('getValue');
	        	if(ruleId=='0003'){
	        		$('#t').css("visibility","visible");
	        	}else{
	        		$('#t').css("visibility","hidden");
	        	}
	        }
        })
        function saveCheckRule(){
        	var comId=$('#addCompanyId').combobox('getValue');
        	var ruleId=$('#rule').combobox('getValue');
        	var startDate="";
        	startDate=$('#startDate').datebox('getValue');
        	
        	if(ruleId==='0003'){
        		ruleId+='#';
        		ruleId+=$('#t').val();
        	}
        	var relateIds = "";
            $("#typelist").find("input:checked").each(function(){  
                  relateIds += $(this).attr("id")+"#";
            });
            if(relateIds=='#'||relateIds==''){
            	$.messager.alert('提示','尚未选择任何业务类型，无法保存','info');
            	return;
            }
            $.post('rule_save.action',{companyId:comId,ruleId:ruleId,typeIds:relateIds,startDate:startDate},function(result){
                    if (result.success==false){
                        $.messager.show({
                            title: 'Error',
                            msg: result.message
                        });
                    } else {
                    	$.messager.alert('提示','保存成功','info');
                        $('#w').window('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the user data
                    }
             },'json');
        }
        function forbiddenRule(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	if(row.typeState=='不可用'){
            		$.messager.alert('提示','业务状态不可用，无需重复禁用!','info');
            		return;
            	}
                $.messager.confirm('提示','你确定要禁用这个业务么?',function(r){
                    if (r){
                        $.post('rule_updateRuleState.action',{companyId:row.company_id,typeIds:row.tran_type,state:'forbidden'},function(result){
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
        function allowRule(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	if(row.typeState=='可用'){
            		$.messager.alert('提示','业务状态可用，无需重复启用!','info');
            		return;
            	}
                $.messager.confirm('提示','你确定要启用这个业务么?',function(r){
                    if (r){
                        $.post('rule_updateRuleState.action',{companyId:row.company_id,typeIds:row.tran_type,state:'allow'},function(result){
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
            width:90px;
        }
    </style>
</body>
</html>
