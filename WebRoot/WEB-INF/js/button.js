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
 	    $('#w').window({title:'编辑角色'});
        $('#w').window('open');
        $('#fm').form('load',row);
        url='role_add.action?roleId='+roleId;
	    }  else{
	    	$.messager.alert('提示','请先选择一个角色');
	    }
}