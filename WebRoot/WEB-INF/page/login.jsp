<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>请先登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<style type="text/css">

.logindiv {
	top: 30%;
	bottom:50%;
	position: absolute;
	left: 50%;
	width: 500px;
	height: 300px;	
	background-repeat: no-repeat;
}

</style>
<script type="text/javascript"><!--
	function login(){
		document.forms[0].submit();
	};
	if (window != top){
		top.location.href = location.href;
	}
	function keyDown(e) {
		var keycode ="";
		var realkey ="";
        if(navigator.appName == "Microsoft Internet Explorer"){
            keycode = event.keyCode;
            realkey = String.fromCharCode(event.keyCode);
        }else{
            keycode = e.which;
            realkey = String.fromCharCode(e.which);
        }   
        if (keycode == 13) {  
            login();
        }
    }   
	window.onload=function(){
		document.onkeydown = keyDown;
	}
	-->
</script>
</head>

<body style="background-color: #1b2cd4">
<div class="logindiv"  style="background-image:url(images/login.gif);">
  <div align="center"  style="margin-top: 130px">
  <form action="loginAction.do" method="post">
	<table>
		<tr>
			<td>用户名：</td>
			<td><input name="username" style="border: none;height: 21px;" size="20"/></td>
			<td rowspan="4">
				<img style="height: 50px;cursor:pointer;" src="images/loginbut.gif" onclick="login()"/> 
				<!--<input type="submit" value="" style="background:url(images/loginbut.gif);width:83px;height:83px;"/>-->
			</td>
		</tr>
		<tr></tr><tr></tr>
		<tr>
			<td>密&nbsp;&nbsp;码：</td>
			<td><input type="password" name="password" size="20" style="border: none;height: 21px;"/></td>
		</tr>
		<tr>
	      <td>验证码：</td>
	      <td>
	          <input type="text" name="code" id="code" size="4"/>
	      	  <img src="image.jsp" id="123" onclick="this.src='image.jsp?abc='+Math.random()" alt="图片看不清？点击重新得到验证码" style="cursor:hand;margin-bottom:0px;"/>
	      </td>
	    </tr>
		<tr><font color="red">${requestScope.errMsg}</font></tr>
	</table>
  </form>
  </div>
</div>
</body>
</html>
