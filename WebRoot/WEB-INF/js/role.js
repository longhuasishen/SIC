function formatOper(val,row,index){
			return '<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="editPrivi('+index+')">权限编辑</a>';
		}
        var url;
        function editPrivi(){
        	   var roleId="1";
		       var row = $('#dg').datagrid('getSelected');  
		       if (row){  
			      roleId=row.role_id;  
	        	   $('#tt').tree({
	        	   		method:'post',
					    url:'role_privilege.action?roleId='+roleId
					});
	        	   $('#w').window({title:'编辑角色'});
	               $('#w').window('open');
	               $('#fm').form('load',row);
	               url='role_add.action?roleId='+roleId;
			    }  else{
			    	$.messager.alert('提示','请先选择一个角色');
			    }
        }
    	function addRole(){
    		$('#w').window({title:'添加角色'});
    		$('#fm').form('clear');
    		$('#w').window('open');
    		url='role/save';
    	}
    	function destroyRole(){
    		var roleId="1";
		       var row = $('#dg').datagrid('getSelected');  
		       if (row){  
		       	  roleId=row.role_id;  
		       	  $.messager.confirm('提示','请谨慎操作，你确定要删除这个角色和属于该角色的所有用户信息吗?',function(r){
                    if (r){
                        $.post('role_delete.action',{role_name:roleId},function(result){
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
			    	$.messager.alert('提示','请先选择一个角色');
			    }
    	}
        function checkFormatter(val,row){
			if(val=='0'){
				return '<input type="checkbox" checked="checked" disabled="disabled"/>有效';
			}else{
				return '<input type="checkbox" disabled="disabled"/>无效';
			}
		}