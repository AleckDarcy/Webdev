<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%
	JSONObject json = (JSONObject)request.getAttribute("json");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>管理员登录</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="js/jquery.min.js"></script>
	</head>

	<body style="margin:auto;">
		<form action="Admin_login.do?render=json" style="text-align:center;"method="post">
			<label>用户名：</label>
			<input type="text" name="username" placeholder="请输入用户名"/><br>
			<label>密　码：</label>
			<input type="password" name="password" placeholder="请输入密码"/><br>
			<input type="submit" style="display:none;" value="登　录"/>
			<button type="button" onclick="submit_login_form();">登　录</button>
		</form>
	</body>
</html>
<script type="text/javascript"">
	function submit_login_form() {
		$.post("Admin_login.do?render=json", $("form").serialize(), function(data) {
			alert(data.err_code);
		}, "json");
	}
</script>
