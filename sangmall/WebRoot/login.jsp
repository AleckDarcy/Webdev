<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"	+ request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="js/jquery.js"></script>
	</head>

	<body style="margin:auto;">
		<form action="User_login.do" style="text-align:center;"method="post">
			<label>用户名：</label>
			<input type="text" name="username" placeholder="请输入用户名"/><br>
			<label>密　码：</label>
			<input type="password" name="password" placeholder="请输入密码"/><br>
			<input type="radio" value="0" checked="checked" name="privilege"/>买家登录
			<input type="radio" value="1" name="privilege"/>管理员登录<br>
			<input type="submit" value="登　录"/>
			<button type="button" onclick="submit_login_form();">dadas</button>
			<a href="jsp/User/register.jsp"><button type="button">注　册</button></a>
		</form>
	</body>
</html>
<script type="text/javascript"">
	function submit_login_form() {
		$.post("User_login.do", $("form").serialize(), function(data) {
			alert(data.error_code);
		}, "json");
	}
</script>
