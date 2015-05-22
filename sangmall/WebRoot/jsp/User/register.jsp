<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%
	JSONObject json = (JSONObject)request.getAttribute("json");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户注册</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
		<script src="js/jquery.min.js"></script>
		<link href="css/style.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="js/move-top.js"></script>
		<script type="text/javascript" src="js/easing.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(function($) {
				$(".scroll").click(function(event){		
					event.preventDefault();
					$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
				});
			});
		</script>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script type="application/x-javascript">
			addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); }
		</script>
	</head>
	<body>
		<div class="container">
			<jsp:include page="../header.jsp">
				<jsp:param name="prefix" value=""/>
			</jsp:include>
			<jsp:include page="../navigation.jsp">
				<jsp:param name="prefix" value=""/>
			</jsp:include>
		</div>
		
		<div class="container">
			<div class="filters">
				<form action="" class="search" method="get">
					<div class="filters-head">
						<div class="filters-head-left">
							<h3>用户<span>注册</span></h3>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="filters-grids">
						<div class="filters-container">
							<div class="filters-tag">
								<label>用户名：</label>
							</div>
							<div class="col-xs-2">
								<input type="text" name="username" class="form control" onchange="check(this.value);" placeholder="请输入用户名" value="">
								<a class="info"></a>
							</div>
						</div>
						<div class="clearfix"> </div>
						<div class="filters-container">
							<div class="filters-tag">
								<label>密　码：</label>
							</div>
							<div class="col-xs-2">
								<input type="password" name="password" class="form control" placeholder="请输入密码" value=""><br>
							</div>
						</div>
						<div class="clearfix"> </div>
						<div class="filters-container">
							<div class="filters-tag">
								
							</div>
							<div class="col-xs-2">
								<button type="button" onclick="regist();" class="btn btn-primary">　注　　册　</button>
							</div>
						</div>
						<div class="clearfix"> </div>
					</div>
				</form>
			</div>
		</div>
		
		<jsp:include page="../footer.jsp">
			<jsp:param name="prefix" value=""/>
		</jsp:include>
	</body>
</html>
<script type="text/javascript"">
	function check(username) {
		if(username.length == 0) {
			$("a.info").html("");
		} else {
			$.post("User_check.do?render=json", $("form").serialize(), function(data) {
				if(data.result == 0) {
					$("a.info").html("用户名可以使用");
					$("a.info").css({color:"black"});
				} else if(data.result == -1) {
					$("a.info").html("用户名已被使用");
					$("a.info").css({color:"red"});
				}
			}, "json");
		}
	}
	
	function regist() {
		$.post("User_register.do?render=json", $("form").serialize(), function(data) {
			if(data.result == 0) {
				if(data.redirect.length > 0) {
					location.href = data.redirect;
				} else {
					location.href = "Product_show.do";
				}
			} else {
				alert("用户名或密码错误！");
			}
		}, "json");
	}
</script>