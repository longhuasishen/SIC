<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
	<script type="text/javascript">
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
    </script>

  </head>
  
  <body  class="easyui-layout">
  	<div data-options="region:'center',title:''">
    <table id="dg" title="商户交易记录信息列表" class="easyui-datagrid" style="display:block;"
            url="trans_list.action" loadMsg="正在加载数据..." rownumbers="true"
            toolbar="#toolbar" pagination="true" pageSize="15"  pageList="[5,15,25,35,45,55]" checkbox="true" 
            rownumbers="true" fitColumns="true" fit="true" singleSelect="true">
        <thead>
            <tr>
            	<th data-options="field:'ck',checkbox:true"></th>
                <th field="channel_serial" width="50">商户订单号</th>
                <th field="trans_time" width="50">交易日期时间</th>
                <th field="trans_amt" width="50">交易金额</th>
                <th field="order_back" width="50">缴费条码</th>
                <th field="status" width="50">交易状态</th>
            </tr>
        </thead>
    </table>
    </div>
    <div id="toolbar">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">请输入待查询的订单号:</a>
    	<input name="queryOrderId" id='queryOrderId' class="easyui-validatebox" type="text">
    	<input name="orderId" id='orderId' type="hidden">
    	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">交易日期:</a>
    	<input name="startDate" id='startDate' class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></input>&nbsp;&nbsp;至
    	<input name="endDate" id='endDate' class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></input>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryJnl()">查询</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="exportExcel()">导出为excel</a>
       
    </div>
    <script type="text/javascript">
    	function queryJnl(){
        	var queryOrderId=$('#queryOrderId').val();
        	var startDate=$('#startDate').datebox('getValue');
        	var endDate=$('#endDate').datebox('getValue');
            $('#dg').datagrid('load',{
            	queryOrderId:queryOrderId,
            	startDate:startDate,
            	endDate:endDate
            });
            $('#orderId').val($('#queryOrderId').val());
            $('#queryOrderId').val('');
        }
        function exportExcel(){
        	var queryOrderId=$('#orderId').val();
        	var startDate=$('#startDate').datebox('getValue');
        	var endDate=$('#endDate').datebox('getValue');
        	var url='Export_excel.action?';
        	if(queryOrderId!=null&&queryOrderId!=''){
        		url+='queryOrderId='+queryOrderId+'&';
        	}
        	if(startDate!=null&&startDate!=''){
        		url+='startDate='+startDate+'&';
        	}
        	if(endDate!=null&&endDate!=''){
        		url+='endDate='+endDate;
        	}
        	//url='Export_excel.action?queryOrderId='+queryOrderId+'&startDate='+startDate+'endDate='+endDate;
        	window.location.href=url;
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
