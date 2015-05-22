<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%
	JSONObject json = (JSONObject)request.getAttribute("json");
	Map cart = json.getJSONObject("cart");
	String login_username = json.getString("login_username");
	int cart_count = 0;
	if(!cart.isEmpty()) {
		cart_count = ((Map)cart.get("items")).size();
	}
	String image_server = json.get("image_server").toString();
	DecimalFormat df = new DecimalFormat("######0.00");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>首页</title>
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
			/*addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); }*/
		</script>
	</head>
	<body>
		<div class="container">
			<jsp:include page="../header.jsp">
				<jsp:param name="prefix" value=""/>
				<jsp:param name="cart_count" value="<%= cart_count %>"/>
				<jsp:param name="login_username" value="<%= login_username %>"/>
			</jsp:include>
			<jsp:include page="../navigation.jsp">
				<jsp:param name="prefix" value=""/>
			</jsp:include>
		</div>
		<div  id="top" class="callbacks_container">
			<ul class="rslides" id="slider4">
<%
				JSONArray banners = json.getJSONArray("banners");
				for(int i = 0; i < banners.size(); i++) {
					Map banner = (Map)banners.get(i);
%>
					<li>
						<img src="<%= image_server + "/" + banner.get("icon_graph") %>" alt="">
						<div class="caption">
							<div class="slide-text-info">
								<h1><%= banner.get("header") %></h1>
								<label><%= banner.get("label") %></label>
								<a class="slide-btn" href="#"><span><%= df.format(Float.parseFloat(banner.get("price").toString())) %></span> <small>GET NOW</small><label> </label></a>
							</div>
						</div>
					</li>
<%
				}
%>
			</ul>
		</div>
		<div class="clearfix"> </div>
			    
		<div class="content">
			<div class="container">
				<div class="top-row">
<%
					JSONArray recommended_categories = json.getJSONArray("recommended_categories");
						for(int i = 0; i < recommended_categories.size(); i++) {
							Map category = (Map)recommended_categories.get(i);
%>
								<div class="col-xs-4">
									<div class="top-row-col1 text-center">
										<a class="r-list-w" href="Product_search.do?category_ids=<%= category.get("id") %>">
											<h2><%= category.get("category_name") %></h2>
											<img class="r-img text-center" src="<%= image_server + "/" + category.get("image") %>" title="name" />
											<span><img src="<%= image_server + "/" + category.get("shadow_image") %>" title="name" /></span>
											<h4>总共有</h4>
											<label><%= category.get("count") %>件商品</label>
											<img src="images/list-icon.png" title="list" />
										</a>
									</div>
								</div>
<%
							}
%>
						</div>
					<div class="clearfix"> </div>
				</div>
				<div class="container"> 
				<div class="special-products">
					<div class="s-products-head">
						<div class="s-products-head-left">
							<h3>SPECIAL <span>PRODUCTS</span></h3>
						</div>
						<div class="s-products-head-right">
							<a href="Product_search.do"><span></span>view all products</a>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="special-products-grids">
<%
						JSONArray special_products = json.getJSONArray("special_products");
						for(int i = 0; i < special_products.size(); i++) {
							Map product = (Map)special_products.get(i);
%>
							<div class="col-md-3 special-products-grid text-center">
								<a class="brand-name" href="Product_search.do?brand_ids=<%= product.get("brand_id") %>"><img src="<%= image_server + "/" + product.get("brand_icon_graph") %>" title="name" /></a>
								<a class="product-here" href="Product_details.do?product_id=<%= product.get("id") %>"><img src="<%= image_server + "/" + product.get("icon_graph") %>" title="<%= product.get("product_name") %>" /></a>
								<h4><a href="Product_details.do?product_id=<%= product.get("id") %>"><%= product.get("product_name") %></a></h4>
								<a class="product-btn" href="Product_details.do?product_id=<%= product.get("id") %>"><span><%= df.format(Float.parseFloat(product.get("price").toString())) %>$</span><small>GET NOW</small><label> </label></a>
							</div>
<%
						}
%>
						<div class="clearfix"> </div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../footer.jsp">
			<jsp:param name="prefix" value=""/>
		</jsp:include>
	</body>
</html>
<script src="js/responsiveslides.min.js"></script>
<script>
	$(function() {
		$("#slider4").responsiveSlides({
			auto : true,
			pager : true,
			nav : true,
			speed : 500,
			namespace : "callbacks",
			before : function() {
				$('.events').append("<li>before event fired.</li>");
			},
			after : function() {
				$('.events').append("<li>after event fired.</li>");
			}
		});
	});
</script>