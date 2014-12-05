function checkFormatter(val,row){
			if(val=='0'){
				return '<input type="checkbox" checked="checked" disabled="disabled"/>有效';
			}else{
				return '<input type="checkbox" disabled="disabled"/>无效';
			}
		}
function add(){
	$('#w').window({title:'菜单信息-添加'});
	$('#fm').form('clear');
	$('#parentmenucode').combotree('reload');
	$('#w').window('open');
	url='menu/save';
}
function edit(){
    var row = $('#dg').treegrid('getSelected');  
    if (row){  
	    roleId=row.buttonCode;  
 	    $('#w').window({title:'菜单信息-编辑'});
        $('#w').window('open');
        $('#parentmenucode').combotree('reload');
        $('#fm').form('load',row);
        url='menu/edit';
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
        dataType:'json',
        success: function(result){
            var result = eval('('+result+')');
            if (result.success==false){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
            	$.messager.alert('提示',result.message,'info',function(){
            		$('#w').window('close');        // close the dialog
                    $('#dg').treegrid('reload');    // reload the user data
            	});
            }
        }
    });
}
function del(){
	var row = $('#dg').treegrid('getSelected');
    if (row){
        $.messager.confirm('提示','你确定要删除这个菜单信息么?',function(r){
            if (r){
                $.post('menu/delete',{menucode:row.menucode},function(result){
                    if (result.success){
                        $('#dg').treegrid('reload');    // reload the user data
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