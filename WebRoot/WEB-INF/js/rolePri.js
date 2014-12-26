$(function(){
//	roleprivi/selectUser
	$("#roleTree li:eq(0)").find("div").addClass("tree-node-selected");
//	alert($('#roleTree').tree("getSelected"));
	$('#roleTree').tree({
		onClick: function(node){
			$("#dg").datagrid({url:'roleprivi/selectUser?roleId='+node.id});
			$("#dgMenu").treegrid({url:'roleprivi/selectMenu?roleId='+node.id});
			$("#menuTree").tree({
				url:'roleprivi/selectMenuTree?roleId='+node.id,
				onClick:function(menuNode){
					var menuId=menuNode.id;
					if(menuId.length<=2){
/*						$.messager.show({
			                title: "操作提示",
			                msg: "暂无按钮记录！",
			                showType: 'slide',
			                timeout: 5000
			            });*/
						$('#buttonList').html("暂无按钮");
					}else{
						$.post('roleprivi/selectMenuButton?timestamp='+new Date(),{roleId:node.id,menuId:menuId},function(result){
							if(result!=null&&result.length>0){
								var size=result.length;
								var html="";
								for(var i=0;i<size;i++){
									if(result[i].checked==true){
										html+="<input name='butt' type='checkbox' checked='checked' value='' id='"+result[i].menuCode+"' /><label>"+result[i].buttonName+" </label> ";
									}else{
										html+="<input name='butt' type='checkbox' value='' id='"+result[i].menuCode+"' /><label>"+result[i].buttonName+" </label> ";
									}
								}
								$('#buttonList').html(html);
							}else{
								$('#buttonList').html("暂无按钮");
							}
						});
					}
				}
			});
		}
	});
});