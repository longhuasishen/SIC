<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>杉德久彰商户管理平台首页</title>
    <link href="<%=base%>css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<%=base%>easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=base%>easyui/themes/icon.css" />
    <script type="text/javascript" src="<%=base%>js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=base%>easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=base%>js/StringBuffer.js"></script>
	<script type="text/javascript" src='<%=base%>js/outlook2.js'> </script>
    <!--<script type="text/javascript">
    	window.onbeforeunload = function(){  
		    var n = window.event.screenX - window.screenLeft;      
		    var b = n > document.documentElement.scrollWidth-20;  
    		alert(n+","+b);
		    if(b && window.event.clientY < 0 || window.event.altKey)      
		    {      
		           alert("关闭而非刷新");  
		           //window.location.href = 'logoff.html';  
		    }      
		}  
    --></script>
    

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 50px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 40px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">欢迎您, ${sessionScope.username} <a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 24px; "><img src="images/blocks.gif" width="20" height="20" align="absmiddle" /> 杉德久彰商户管理操作平台</span>
    </div>
    <div region="south" split="true" style="height: 30px;line-height:30px; background: #D2E0F2; ">
        <div class="footer">杉德久彰电子商务服务有限公司2013-2014</div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
				
	</div>

    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
			</div>
		</div>
    </div>
    
    
    <!--修改密码窗口-->
    <div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="password" class="txt01" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
	<script type="text/javascript"><!--
    	var _menus ={};
    	var menuStr='{"menus":[';
		$.ajax({
			url: "getPrivilegeAction?timestamp=" + new Date().getTime(),
	        data: "",
	        type:"POST",
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function (data) {
	           _menus=eval(data);
	           //_menus=JSON.jsonify(data);
	           $.each(_menus, function(i) {
	                //alert("ID:" + _menus[i].menuid + ",Name:" + _menus[i].menuname);
	                menuStr+='{"menucode":"';
	                menuStr+=_menus[i].menucode;
	                menuStr+='","icon":"';
	                menuStr+=_menus[i].icon;
	                menuStr+='","menuname":"';
	                menuStr+=_menus[i].menuname;
	                menuStr+='","menus":[';
	                var submenus=eval(_menus[i].menus);
	                $.each(submenus,function(i){
	                	if(typeof(submenus[i][0])!='undefined'){
	                		menuStr+='{"menucode":"';
	                		menuStr+=submenus[i][0].menucode;
	                		menuStr+='","menuname":"';
	                		menuStr+=submenus[i][0].menuname;
	                		menuStr+='","icon":"';
	                		menuStr+=submenus[i][0].icon;
	                		menuStr+='","url":"';
	                		menuStr+=submenus[i][0].menuurl;
	                		menuStr+='"},';
	                	}
	                });
	                menuStr=menuStr.substr(0,menuStr.length-1);
	                menuStr+=']},';
	           });
	           menuStr=menuStr.substr(0,menuStr.length-1);
	           menuStr+=']};';
	           //alert(menuStr);
	           _menus=JSON.jsonify(menuStr);
	           initWin();
	        },
	        error: function (msg) {
	            alert('页面初始化错误,强制退出');
	            window.opener=null;//此设置是为不弹出提示框
    			window.open('','_self');
    			window.close();
	        }
		});
		/*var _menus = {"menus":[
							{"menuid":"1","icon":"icon-sys","menuname":"系统管理",
								"menus":[
										{"menuid":"12","menuname":"运营商金额管理","icon":"icon-users","url":"page/company.jsp"}
									]
							}
					]};*/
					  
        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }

        

        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('loginAction_changePassword.action?newpass=' + $newpass.val(), function(result) {
            	if (result.success){
	                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + result.newpass, 'info');
	                $newpass.val('');
	                $rePass.val('');
	                closePwd();
	             }else{
	             	$.messager.show({    // show error message
                          title: 'Error',
                          msg: result.errorMsg
                    });
	             }
            },'json');
            
        }

        $(function() {
            openPwd();
	
            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
            })

			$('#btnCancel').click(function(){closePwd();})

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = 'loginAction_logout.action';
                    }
                });
            })
        });
		
		

    
    --></script>

</body>
</html>
