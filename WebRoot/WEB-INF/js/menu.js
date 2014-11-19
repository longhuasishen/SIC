function checkFormatter(val,row){
			if(val=='0'){
				return '<input type="checkbox" checked="checked" disabled="disabled"/>有效';
			}else{
				return '<input type="checkbox" disabled="disabled"/>无效';
			}
		}