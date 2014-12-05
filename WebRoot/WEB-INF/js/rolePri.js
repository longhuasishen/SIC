$(function(){
//	roleprivi/selectUser
	$("#roleTree li:eq(0)").find("div").addClass("tree-node-selected");
//	alert($('#roleTree').tree("getSelected"));
	$('#roleTree').tree({
		onClick: function(node){
			$("#dg").datagrid({url:'roleprivi/selectUser?roleId='+node.id});
		}
	});
});