		function resetpass(index){
    		$('#dg').datagrid('selectRow',index);// 关键在这里  
		    var row = $('#dg').datagrid('getSelected');  
		    if (row){  
    			$.messager.confirm('提示','你确定要重置这个用户的密码么?',function(r){
                    if (r){
                        $.post('user/resetpass',{username:row.username},function(result){
                            if (result.success){
                                $.messager.alert('提示',result.message);    // reload the user data
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
			return '<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="resetPass('+index+')">权限编辑</a>';
		}
    	function resetOper(val,row,index){
			return '<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="resetPass('+index+')">密码重置</a>';
		}
		function roleFormatter(val,row){
			return row.role.role_name;
		}
		function checkFormatter(val,row){
			if(val=='0'){
				return '<input type="checkbox" checked="checked" disabled="disabled"/>有效';
			}else{
				return '<input type="checkbox" disabled="disabled"/>无效';
			}
		}
        var url;
		function query(){
        	var companyId=$('#queryCompanyId').val();
            $('#dg').datagrid('load',{
            	companyId:companyId
            });
        }
		
		$(function(){
			$('#roleid').combobox({
				onSelect:function(){
					var roleid=$('#roleid').combobox('getValue');
					if(roleid=='1'){
						$('#mid').attr("disabled",'disabled');
					}else{
						$('#mid').attr("disabled",'');
					}
				}
			});
		});
        function add(){
        	$('#fm').form('clear');
        	$('#password').attr("readonly","");
        	$('#w').window('open');
            $('#state').attr("checked",true);
            $('#roleid').combobox('setValue','0');
            $('#company_id').combobox('setValue','0');
            url = 'user/save';
        }
        function edit(){
        	//$('#w').text('用户信息-编辑');
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#fm').form('load',row);
                if(row.state=='0'){
                	$('#state').attr("checked","checked");
                }else{
                	$('#state').attr("checked","");
                }
                $('#password').val("******");
            	$('#password').attr("readonly","true");
            	$('#roleid').combobox('setValue',row.role.role_id);
            	$('#company_id').combobox('setValue',row.company_id==null?'0':row.company_id);
                $('#w').window('open');
                url = 'user/update';
            }else{
            	$.messager.alert('提示','请先选择一个用户!','info');
            }
        }
        function save(){
        	var roleId=$('#roleid').combobox('getValue');
        	var company_id=$('#company_id').combobox('getValue');
        	if("0"==roleId){
        		$.messager.alert('提示','请先选择一个角色!','info');
        		return;
        	}
        	if("2"==roleId&&("0"==company_id||""==company_id)){
        		$.messager.alert('提示','请先选择一个商户!','info');
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
                        $.messager.show({
                            title: 'Error',
                            msg: result.message
                        });
                    } else {
                    	$.messager.alert('提示',result.message,'info');
                        $('#w').window('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
        function del(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('提示','你确定要删除这个用户信息么?',function(r){
                    if (r){
                    	alert('123');
                        $.post('user/delete',{username:row.username},function(result){
                        	alert(result);
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
            	$.messager.alert('提示','请先选择一个用户!','info');
            }
        }
        