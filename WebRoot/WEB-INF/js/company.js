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
            $('#fm').form('clear');
        	$('#w').attr('title','添加新的商户');
        	$('#companyId').attr("readonly","");
        	$('#channelId').attr("readonly","");
        	$('#cc').attr("disabled","true");
        	$('#operAmt').val("0");
        	$('#operAmt').attr("disabled","true");
            //$('#w').dialog('open').dialog('setTitle','New Company');
            $('#w').window('open');
            $('#currAmt').val("0");
            $('#warnAmt').val("500");
            $("#cc").combobox('setValue','add');
            $("#state").combobox('setValue','yes');
            $('#currAmt').attr("readonly","");
            url = 'com_save.action';
        }
        function editCompany(){
        	$('.ftitle').text('编辑商户');
            var row = $('#dg').datagrid('getSelected');
            if (row){
	        	$('#fm').form('clear');
                //$('#dlg').dialog('open').dialog('setTitle','Edit Company');
                //$('#fm').form('load',row);
                $('#w').window('open');
	            $('#fm').form('load',row);
	        	$('#companyId').attr("readonly","true");
	        	$('#channelId').attr("readonly","true");
	        	$('#currAmt').attr("readonly","true");
	        	$('#cc').attr("disabled","");
	        	$("#cc").combobox('setValue','add');
	        	$('#operAmt').val("0");
	        	$('#operAmt').attr("disabled","");
                url = 'com_update.action';
            }else{
            	$('#dlg').dialog('close');
            	$.messager.alert('提示','请先选择一个商户!','info');
            }
        }
        function saveCompany(){
        	var oper=$('#cc').combobox('getValue');
        	var currAmt=$('#currAmt').val().replace(',','');
        	var operAmt=$('#operAmt').val().replace(',','');
        	numObj1 = new Number(currAmt);
			numObj2 = new Number(operAmt);
        	if(oper==='mul'){
        		if(numObj1<numObj2){
        			$.messager.alert('提示','商户当前余额不足!','info');
        			return;
        		}
        	}
        	if($('#operAmt').val()=='undefined'||$('#operAmt').val()==''){
        		$.messager.alert('提示','请填写操作金额!','info');
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
                        if(result.oper=='add'){
	                    	$.messager.alert('提示','商户信息添加成功，商户用户名:'+result.companyId+',系统密码:'+result.pass,'info');
	                        $('#w').window('close');        // close the dialog
                        	$('#dg').datagrid('reload');    // reload the user data
                        }else if(result.oper=='update'){
                        	$.messager.alert('提示','商户信息更新成功','info');
	                        $('#w').window('close');        // close the dialog
                        	$('#dg').datagrid('reload');    // reload the user data
                        }
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
        function forbiddenCompany(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	if(row.status=='不可用'){
            		$.messager.alert('提示','商户状态不可用，无需重复禁用!','info');
            		return;
            	}
                $.messager.confirm('提示','你确定要禁用这个商户么?',function(r){
                    if (r){
                        $.post('com_forbidden.action',{companyId:row.companyId},function(result){
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
        function allowCompany(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	if(row.status=='可用'){
            		$.messager.alert('提示','商户状态可用，无需重复启用!','info');
            		return;
            	}
                $.messager.confirm('提示','你确定要启用这个商户么?',function(r){
                    if (r){
                        $.post('com_allow.action',{companyId:row.companyId},function(result){
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