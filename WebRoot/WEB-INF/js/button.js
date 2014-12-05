function checkFormatter(val,row){
			if(val=='0'){
				return '<input type="checkbox" checked="checked" disabled="disabled"/>有效';
			}else{
				return '<input type="checkbox" disabled="disabled"/>无效';
			}
		}
function iconFormatter(val,row){
	return "<img class='"+val+"' src='' alt='图片出不来啦' style='height:20px;width:20px;' border='0' />"+val;
}
function add(){
	$('#w').window({title:'操作按钮-添加'});
	$('#fm').form('clear');
	$('#w').window('open');
	url='button/save';
}
function edit(){
    var row = $('#dg').datagrid('getSelected');  
    if (row){  
	    roleId=row.buttonCode;  
 	    $('#w').window({title:'操作按钮-编辑'});
        $('#w').window('open');
        $('#fm').form('load',row);
        url='button/edit';
	}else{
	    $.messager.alert('提示','请先选择一条记录');
	}
}
function save(){
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
        $.messager.confirm('提示','你确定要删除这个按钮信息么?',function(r){
            if (r){
                $.post('button/delete',{buttonCode:row.buttonCode},function(result){
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
    	$.messager.alert('提示','请先选择一条记录!','info');
    }
}