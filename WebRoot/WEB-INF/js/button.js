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