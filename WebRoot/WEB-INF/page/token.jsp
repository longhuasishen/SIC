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
    <table id="dg" title="令牌列表" class="easyui-datagrid" style="display:block;"
           toolbar="#toolbar" url="token_listToken.action" loadMsg="正在加载数据..." rownumbers="true"
           pagination="true" pageSize="15" pageList="[5,15,25,35,45,55]" checkbox="true" 
            rownumbers="true" fitColumns="true" fit="true" singleSelect="true">
        <thead>
            <tr>
            	<th data-options="field:'ck',checkbox:true"></th>
                <th field="tokenId" width="80">令牌号</th>
                <th field="associate"  width="20" data-options="formatter:setAssociate">关联状态</th>
                <th field="lock" width="20" data-options="formatter:setLock">锁定状态</th>
                <th field="reg"  width="20" data-options="formatter:setReg">注册状态</th>
                <th field="failedCount"  width="20">失败次数</th>
                <th field="regTime" width="60" >注册日期</th>
                <th field="createTime" width="60">创建时间</th>
                <%--<th data-options="field:'_operateA',width:50,align:'center',formatter:resetOper">操作按钮</th> 
            --%></tr>
        </thead>
    </table>
    </div>
    <div id="toolbar">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">令牌:</a>
    	<input name="tokenId" id='tokenId' class="easyui-validatebox"  style="width:250px;"  type="text">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">关联状态:</a>
          <select id="associate" class="easyui-combobox" name="associate" style="width:80px;" data-options="editable:false,panelHeight:'auto'">
			    <option value="0" selected="selected">全部</option>
			    <option value="1">未关联</option>
			    <option value="2">已关联</option>
	     </select>
	     <a href="javascript:void(0)" class="easyui-linkbutton" plain="true">注册状态:</a>
          <select id="reg" class="easyui-combobox" name="reg" style="width:80px;" data-options="editable:false,panelHeight:'auto'">
			    <option value="0" selected="selected">全部</option>
			    <option value="1">未注册</option>
			    <option value="2">已注册</option>
	     </select>
	     <a href="javascript:void(0)" class="easyui-linkbutton" plain="true">锁定状态:</a>
          <select id="lock" class="easyui-combobox" name="lock" style="width:80px;" data-options="editable:false,panelHeight:'auto'">
			    <option value="0" selected="selected">全部</option>
			    <option value="1">未锁定</option>
			    <option value="2">已锁定</option>
	     </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryCompany()">查询</a><%--
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newCompany()">初始化</a>
    --%></div>
    <div id="w" class="easyui-window" title="企业修改" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:420px;height:320px;">
		<div class="easyui-layout" data-options="fit:true">
		  <div data-options="region:'center'" style="padding:5px;">
	        <form id="fm" method="post">
	            <div class="fitem">
	                <label>令牌:</label>
	                <input name="tokenId" id='tokenId' class="easyui-validatebox" required="true">
	            </div>
	            <div class="fitem">
	                <label>关联状态:</label>
               		<select id="associate" class="easyui-combobox" name="associate" style="width:155px;" data-options="editable:false,panelHeight:'auto'">
					    <option value="" selected="selected">全部</option>
					    <option value="1">未关联</option>
					    <option value="2">已关联</option>
					</select>
	            </div>
	            <div class="fitem">
	                <label>可用令牌数:</label>
	                <input name="maxQty" id="maxQty" class="easyui-validatebox" validType="number" >
	            </div>
	            <div class="fitem">
	                <label>过期时间:</label>
	                <input name="expireTime" id='expireTime' class="easyui-datebox" data-options="formatter:myformatter"></input>
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
    		function setReg(value){
				if(value ==0){
					return "未注册"
			     }else if(value == 1){
					return "已注册";
				 }
			}
	    function setAssociate(value){
			if(value ==0){
				return "未关联"
		     }else if(value == 1){
				return "已关联";
			 }
		}
	    function setLock(value){
			if(value ==0){
				return "未锁定"
		     }else if(value == 1){
				return "已锁定";
			 }
		}
	
		   function myformatter(date){
		       var y = date.getFullYear();
		       var m = date.getMonth()+1;
		       var d = date.getDate();
		       return y+(m<10?('0'+m):m)+(d<10?('0'+d):d);
		   }
    	function recoverToken(index){
    	$('#dg').datagrid('selectRow',index);// 关键在这里  
		    var row = $('#dg').datagrid('getSelected');  
		    if (row){  
		        //$('#dlg').window('open');  
    			//$.messager.alert('upload','upload'+index+'--'+row.clearDate+'--'+row.companyId);
    			$.messager.confirm('提示','你确定要回收令牌吗?',function(r){
                    if (r){
                        $.post('recoverToken.action?userName='+row.userName,function(result){
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


    	function newCompany(){
            $('#fm').form('clear');
        	$('#w').attr('title','添加企业');
        	 $('#w').window('open');
            url = 'tokenCompany_saveCompany.action';
        }
    	 function editCompany(index){
         	$('.ftitle').text('编辑企业');
         	$('#dg').datagrid('selectRow',index);// 关键在这里
			  var row = $('#dg').datagrid('getSelected');
             if (row){
 	        	$('#fm').form('clear');
                 //$('#dlg').dialog('open').dialog('setTitle','Edit Company');
                 //$('#fm').form('load',row);
                 $('#w').window('open');
 	            $('#fm').form('load',row);
 	        	$('#no').attr("readonly","true");
                 url = 'tokenCompany_updateCompany.action';
             }else{
             	$('#dlg').dialog('close');
             	$.messager.alert('提示','请先选择一个企业!','info');
             }
         }
    	
		function savePwd(){
        	var pwd=$('#eid').val().replace(',','');
        	var pwd2=$('#companyName').val().replace(',','');
        	
        	if($('#eid').val()=='undefined'||$('#eid').val()==''){
        		$.messager.alert('提示','请填写企业编号!','info');
        		return;
        	}
        	if($('#companyName').val()=='undefined'||$('#companyName').val()==''){
        		$.messager.alert('提示','请填写企业名称!','info');
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
        	if(row.lock ==0 ){//0未锁定
            	return '<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="associateToken('+index+')">锁定</a>';
            }else if(row.lock == 1){
        		return '<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="recoverToken('+index+')">解锁</a>';
            }
		}
        var url;
        function operation_add(){
				$.messager.alert('add','add');
			}
		function queryCompany(){
        	var tokenId=$('#tokenId').val();
        	var reg = $('#reg').combobox('getValue');
        	var lock =  $('#lock').combobox('getValue');
        	var associate =  $('#associate').combobox('getValue');
            $('#dg').datagrid('load',{
            	tokenId:tokenId,
            	reg:reg,
            	lock:lock,
            	associate:associate
            });
        }
        function newUser(){
        	$('#w').window('open');
            $('#fm').form('clear');
            $('#role_id').combobox('setValue','2');
            url = 'user_save.action';
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
        function delUser(index){
        	$('#dg').datagrid('selectRow',index);
		    var row = $('#dg').datagrid('getSelected'); 
            if (row){
                $.messager.confirm('提示','你确定要删除这个用户么?',function(r){
                    if (r){
                        $.post('tokenCompany_deleteCompany.action',{eid:row.no},function(result){
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
            	$.messager.alert('提示','请先选择一个企业!','info');
            }
        }
        
        function associateToken(index){
        	$('#dg').datagrid('selectRow',index);
		    var row = $('#dg').datagrid('getSelected'); 
            if (row){
                $.messager.confirm('提示','确定要给该用户分配令牌吗?',function(r){
                    if (r){
                        $.post('associateToken.action',{userName:row.userName},function(result){
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
