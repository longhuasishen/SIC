var depformatter=function(value,rec,index){ 
	var depname='';
	if(value!=null){
		depname= value.depname;
	}
	return depname; 
};
var pathformatter=function(val,row,index){ 
	if(val==null||val==''){
		return '暂无今日对账单';
	}else{
		return '<span style="color:blue;">'+val+'</span>';
	}
}

