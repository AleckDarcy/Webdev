<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	Map data = (Map)request.getAttribute("data");
	List users = (List) ((Map) request.getAttribute("data")).get("users");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>正在登录</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>

	<body style="text-align: center;">
<%
		for(int i = 0; i < users.size(); i++) {
			Map user = (Map) users.get(i);
			Set<String> set = user.keySet();
			for (String key : set) {
				out.println(key + " = " + user.get(key));
			}
		}
%>
	</body>
</html>