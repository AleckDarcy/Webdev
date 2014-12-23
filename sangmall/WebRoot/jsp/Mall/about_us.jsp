<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%
	JSONObject json = (JSONObject)request.getAttribute("json");
	//String image_server = json.get("image_server").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>关于我们</title>
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
		<%= json %>
		<div class="container">
			<jsp:include page="../header.jsp">
				<jsp:param name="prefix" value=""/>
			</jsp:include>
			<jsp:include page="../navigation.jsp">
				<jsp:param name="prefix" value=""/>
			</jsp:include>
		</div>

		<div class="product-details">
			<div class="container">
				<div class="product-details-row1">
					<div class="product-details-row1-head">
						<h2><%= json.get("title") %></h2>
						<p><%= json.get("message") %></p>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		
		<jsp:include page="../footer.jsp">
			<jsp:param name="prefix" value=""/>
		</jsp:include>
	</body>
</html>