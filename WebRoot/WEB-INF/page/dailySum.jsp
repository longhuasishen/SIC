<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
    <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/formatter.js"></script>
	<script type="text/javascript">
	   var selectJson = [];
        function myformatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
        }
        function myparser(s){
            if (!s) return new Date();
            var ss = (s.split('-'));
            var y = parseInt(ss[0],10);
            var m = parseInt(ss[1],10);
            var d = parseInt(ss[2],10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
                return new Date(y,m-1,d);
            } else {
                return new Date();
            }
        }
         function loadDate(){
        	var date = new Date();
            for(var i =0; i < 7; i++){
	            date = new Date(date.valueOf()	 - i*1*24*60*60*1000);
	            var y = date.getFullYear();
	            var m = date.getMonth()+1;
	            var d = date.getDate();
	            if(m<10){
	            	m="0"+m;
	            }
	            if(d<10){
	            	d="0"+d;
	            }
            	var ddd = y+"-"+m+"-"+d;
            	date = new Date();
            	if(i == 0){
            		selectJson.push({"id":ddd,"text":ddd,"selected":true});
            	}else{
            		selectJson.push({"id":ddd,"text":ddd});
            	}
            }
            $('#dateSelect').combobox({
				data:selectJson,
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				onSelect:function(rec){
					var selectDate=$("#dateSelect").combobox('getValue');
                    $('#dg').datagrid('load',{page:0,clearDate:selectDate});    // reload the user data
				}
			}); 
        }
        $(function(){
        	loadDate();
        });
    </script>

  </head>
  
  <body  class="easyui-layout">
  	<div data-options="region:'center',title:''">
    <table id="dg" title="商户对账单信息列表" class="easyui-datagrid" style="display:block;"
            url="com_scan.action" loadMsg="正在加载数据..." rownumbers="true"
            toolbar="#toolbar" pagination="false" pageSize="1000"  checkbox="true" 
            rownumbers="true" fitColumns="true" fit="true" singleSelect="true">
        <thead>
            <tr>
                <th field="company" width="50">商户名称</th>
                <th field="clearDate" width="50">清算日期</th>
                <th field="typeName" width="50">业务类型</th>
                <th field="fileName" width="50">文件名称</th>
                <th data-options="field:'_operate',width:50,align:'center',formatter:formatOper">操作</th>
            </tr>
        </thead>
    </table>
    </div>
    <div id="toolbar" style="padding-top:10px;padding-bottom:7px;">

         <label style="margin-left:30px;padding-right:10px">请选择清算日期:</label>
         <select class="easyui-combobox" name="state" id="dateSelect" style="width:100px;">
                        </select>
    </div>
    <script type="text/javascript">
    	function download(index){
    		var rows=$('#dg').datagrid('getRows');
    		var url=rows[index].url;
    		window.location="download.action?fileName="+url;
    	}
    	function formatOper(val,rec,index){
    		var rows=$('#dg').datagrid('getRows');
    		var url=rows[index].fileName;
			return '<a href="download.action?fileName='+url+'" class="easyui-linkbutton" plain="true" >下载对账单</a>';
		}
    </script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>
</body>
</html>
