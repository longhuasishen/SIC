var toolbar = [{
			text:'添加'+obj,
			iconCls:'icon-add',
			handler:function(){operation_add();}
		},'-',{
			text:'编辑'+obj,
			iconCls:'icon-edit',
			handler:function(){alert('cut')}
		},'-',{
			text:'删除'+obj,
			iconCls:'icon-cut',
			handler:function(){alert('save')}
		}];
