<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%
	JSONObject json = (JSONObject)request.getAttribute("json");
	String image_server = json.get("image_server").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>所有商品</title>
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
		
		<div class="container">
			<div class="filters">
				<div class="filters-head">
					<div class="filters-head-left">
						<h3>高级<span>搜索</span></h3>
					</div>
					<div class="clearfix"> </div>
				</div>
				<div class="filters-grids">
					<label>品名：</label><input type="text" name="product_name" value="<%= json.get("product_name") %>"><br>
					<label>品牌：</label>
<%
						JSONArray brands = json.getJSONArray("brands");
						for(int i = 0; i < brands.size(); i++) {
							Map brand = (Map)brands.get(i);
%>
							<div>
								<%= brand.get("brand_name") %>
							</div>
<%
						}
%>
					<br>
					
					<div class="filters-container">
						<div class="filters-tag">
							<label>类别：</label>
						</div>
<%
						JSONArray categories = json.getJSONArray("categories");
						for(int i = 0; i < categories.size(); i++) {
							Map category = (Map)categories.get(i);
%>
							<div class="filters-choice">
								<%= category.get("category_name") %>
							</div>
<%
						}
%>					
						</div>
					<br>
					<label></label>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
		
		<div class="container"> 
			<div class="special-products">
				<div class="s-products-head">
					<div class="s-products-head-left">
						<h3>搜索<span>结果</span></h3>
					</div>
					<div class="s-products-head-right">
						<a href="Product_search.do"><span></span>查看所有商品</a>
					</div>
					<div class="clearfix"> </div>
				</div>
				<div class="special-products-grids">
<%
					JSONArray products = json.getJSONArray("products");
					if(products.size() == 0) {
%>
						没有找到符合条件的商品！
<%							
					}
					for(int i = 0; i < products.size(); i++) {
						Map product = (Map)products.get(i);
%>
						<div class="col-md-3 special-products-grid text-center">
							<a class="brand-name" href="<%= product.get("brand_id") %>"><img src="images/b1.jpg" title="name" /></a>
							<a class="product-here" href="<%= product.get("id") %>"><img src="images/p1.jpg" title="product-name" /></a>
							<h4><a href="single-page.html"><%= product.get("product_name") %></a></h4>
							<a class="product-btn" href="single-page.html"><span><%= product.get("price") %>$</span><small>GET NOW</small><label> </label></a>
						</div>
<%
					}
%>
					<div class="clearfix"> </div>
				</div>
			</div>
		</div>
		<jsp:include page="../footer.jsp">
			<jsp:param name="prefix" value=""/>
		</jsp:include>
	</body>
</html>