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
    <table id="dg" title="用户信息列表" class="easyui-datagrid" style="display:block;"
           toolbar="#toolbar" url="tokenUser_list.action" loadMsg="正在加载数据..." rownumbers="true"
           pagination="true" pageSize="15" pageList="[5,15,25,35,45,55]" checkbox="true" 
            rownumbers="true" fitColumns="true" fit="true" singleSelect="true" >
        <thead>
            <tr>
            	<th data-options="field:'ck',checkbox:true"></th>
                <th field="userName" width="30">用户名</th>
                <th field="eid" width="20">企业编号</th>
                <th field="associate" width="20">关联令牌</th>
                <th field="tokenId" width="80">SN</th>
                <th field="lock" width="20" data-options="formatter:setLock">锁定状态</th>
                <th field="lockTime" width="20">锁定时间</th>
                <th field="failedCount" width="20">失败次数</th>
                <th field="urent" width="20">注册状态</th>
                <th field="createTime" width="50">创建时间</th>
                <th data-options="field:'_operateA',width:50,align:'center',formatter:resetOper">操作按钮</th> 
            </tr>
        </thead>
    </table>
    </div>
    <div id="toolbar">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">企业编号:</a>
    	<input name="queryCompanyId" id='queryCompanyId' class="easyui-validatebox" type="text">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">用户名:</a>
    	<input name="userName" id='userName' class="easyui-validatebox" type="text">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryCompany()">查询</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="resetPass()">修改密码</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delUser()">删除用户</a>
    </div>
    <div id="w" class="easyui-window" title="密码修改" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:420px;height:320px;">
		<div class="easyui-layout" data-options="fit:true">
		  <div data-options="region:'center'" style="padding:5px;">
	        <form id="fm" method="post">
	            <div class="fitem">
	                <label>企业编号:</label>
	                <input name="eid" id='eid' class="easyui-validatebox" required="true" readonly="readonly">
	            </div>
	            <div class="fitem">
	                <label>用户名:</label>
	                <input name="userName" id="userName" class="easyui-validatebox"  required="true" readonly="readonly">
	            </div>
	            <div class="fitem">
	                <label>密码:</label>
	                <input name="pwd" id="pwd" class="easyui-validatebox" required="true" validType="length[4,10]" >
	            </div>
	            <div class="fitem">
	                <label>确认密码:</label>
	                <input name="pwd2" id="pwd2" class="easyui-validatebox"  required="true" validType="length[4-10]">
	            </div>
	           </form>
	        </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:2px 0 0;height:30px;">
                <a id="btnOk" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="savePwd()">保存</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')">取消</a>
            </div>
        </div>
    </div>
    
    
    <script type="text/javascript"><!--
		var lock;
		function setLock(value){
			if(value == 0){
				lock = 1;
				return "未锁定";
			}else if(value == 1){
				lock =0;
				return "已锁定";
			}
		}
    	function recoverToken(index){
    		$('#dg').datagrid('selectRow',index);// 关键在这里  
		    var row = $('#dg').datagrid('getSelected');  
		    if (row){  
		        //$('#dlg').window('open');  
    			//$.messager.alert('upload','upload'+index+'--'+row.clearDate+'--'+row.companyId);
    			$.messager.confirm('提示','你确定要回收令牌吗?',function(r){
                    if (r){
                        $.post('tokenUser_recoverToken.action?eid='+row.eid+'&userName='+row.userName,function(result){
                            if (result.success){
                                $.messager.alert('提示','回收令牌成功');    // reload the user data
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.alert('提示',result.errorMsg);    // reload the user data
                            }
                        },'json');
                    }
                });
		    }  
    	}

    	function lockToken(index){
    		$('#dg').datagrid('selectRow',index);// 关键在这里  
		    var row = $('#dg').datagrid('getSelected');  
		    if (row){  
		        //$('#dlg').window('open');  
    			//$.messager.alert('upload','upload'+index+'--'+row.clearDate+'--'+row.companyId);
    			$.messager.confirm('提示',lock==1?'你确定要锁定令牌?':'你确定要解锁令牌?',function(r){
                    if (r){
                        $.post('tokenUser_lock.action?eid='+row.eid+'&userName='+row.userName+'&lock='+lock,function(result){
                            if (result.success){
                                $.messager.alert('提示',result.errorMsg);    // reload the user data
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.alert('提示',result.errorMsg);    // reload the user data
                            }
                        },'json');
                    }
                });
		    }  
        }

		function resetPass(){
			  var row = $('#dg').datagrid('getSelected');
			  if(row){
				  $('#fm').form('clear');
	                $('#w').window('open');
		            $('#fm').form('load',row);
	                url = 'tokenUser_editPwd.action';
			 }else{
				 $.messager.alert('提示','请先选择一个用户!','info');
			}
		}
		function savePwd(){
        	var pwd=$('#pwd').val().replace(',','');
        	var pwd2=$('#pwd2').val().replace(',','');
        	
        	if($('#pwd').val()=='undefined'||$('#pwd').val()==''){
        		$.messager.alert('提示','请填写修改密码!','info');
        		return;
        	}
        	if($('#pwd2').val()=='undefined'||$('#pwd2').val()==''){
        		$.messager.alert('提示','请填写确认密码!','info');
        		return;
        	}
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.success==false){
                    	$.messager.alert('提示',result.errorMsg,'info');
                    } else {
                        	$.messager.alert('提示',result.errorMsg,'info');
	                        $('#w').window('close');        // close the dialog
                        	$('#dg').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
    	function resetOper(val,row,index){
        	var a ='';
        	if(row.tokenId ==null || "" == row.tokenId){
            	a= '<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="associateToken('+index+')">分配令牌</a>';
            }else{
        		a= '<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="recoverToken('+index+')">回收令牌</a>';
            }
            if(row.lock == 0){
				a += '&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="lockToken('+index+')">锁定</a>';
              }else if(row.lock == 1){
				a+= '&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="lockToken('+index+')">解锁</a>';
              }
            return a;
		}
        var url;
        function operation_add(){
				$.messager.alert('add','add');
			}
		function queryCompany(){
        	var companyId=$('#queryCompanyId').val();
        	var userName = $("#userName").val();
            $('#dg').datagrid('load',{
            	eid:companyId,
            	userName:userName
            });
        }
        function newUser(){
        	$('#w').window('open');
            $('#fm').form('clear');
            $('#role_id').combobox('setValue','2');
            url = 'user_save.action';
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
        function saveUser(){
        	var roleId=$('#role_id').combobox('getValue');
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
                        $('#w').window('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
        function delUser(){
		    var row = $('#dg').datagrid('getSelected'); 
            if (row){
                $.messager.confirm('提示','你确定要删除这个用户么?',function(r){
                    if (r){
                        $.post('tokenCompany_deleteCompany.action',{eid:row.eid,userName:row.userName},function(result){
                            if (result.success){
                            	 $.messager.alert('提示',result.errorMsg);    // reload the user data
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
            	$.messager.alert('提示','请先选择一个用户!','info');
            }
        }
        
        function associateToken(index){
        	$('#dg').datagrid('selectRow',index);
		    var row = $('#dg').datagrid('getSelected'); 
            if (row){
                $.messager.confirm('提示','确定要给该用户分配令牌吗?',function(r){
                    if (r){
                        $.post('tokenUser_associateToken.action',{eid:row.eid,userName:row.userName},function(result){
                            if (result.success){
                            	 $.messager.alert('提示',result.errorMsg);    // reload the user data
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
                            	 $.messager.alert('提示',result.errorMsg);    // reload the user data
                            }
                        },'json');
                    }
                });
            }else{
            	$.messager.alert('提示','请先选择一个用户!','info');
            }
        }
    --></script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:30px 30px;
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
