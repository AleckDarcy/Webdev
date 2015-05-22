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
		<div class="container"> 
			<div class="special-products">
				<div class="s-products-head">
					<div class="s-products-head-left">
						<h3>我的<span>购物车</span></h3>
					</div>
					<div class="clearfix"> </div>
				</div>
				<div class="special-products-grids">
<%
					Map items = (Map)cart.get("items");
					if(items.size() == 0) {
%>
						购物车内没有商品！
<%							
					} else {
						Set<String> product_ids = items.keySet();
						if(product_ids.size() == 0) {
%>
							购物车内没有商品！
<%						
						} else {
%>
							<table style="border:1px solid gray;width:100%;margin:auto;">
								<tbody>
									<tr>
										<td style="width:25%;border:1px solid gray;">图片</td>
										<td style="width:30%;border:1px solid gray;">商品名</td>
										<td style="width:25%;border:1px solid gray;">数量</td>
										<td style="width:10%;border:1px solid gray;">单价</td>
										<td style="width:10%;border:1px solid gray;">总价</td>
									</tr>
<%
								for(String product_id: product_ids) {
									Map item = (Map)items.get(product_id);
									Map product = item;
%>
									<tr>
										<td style="border:1px solid gray;">
											<img style="width:100%;height:auto;" src="<%= image_server %>/<%= item.get("icon_graph") %>">
										</td>
										<td style="border:1px solid gray;">
											<a href="http://localhost:8080/sangmall/Product_details.do?product_id=<%= item.get("id") %>"><%= item.get("product_name") %></a>
										</td>
										<td style="border:1px solid gray;">
											<label><%= item.get("count") %></label>　<a onclick="add_to_cart(<%= item.get("id") %>, '<%=item.get("product_name") %>', <%= item.get("current_price") %>);">添加</a>　<a onclick="remove_from_cart(<%= item.get("id") %>, 1);">减少</a>　<a onclick="remove_from_cart(<%= item.get("id") %>, 0);">删除</a>
										</td>
										<td style="border:1px solid gray;">
											<%= item.get("current_price") %>
										</td>
										<td style="border:1px solid gray;">
											<%= Float.parseFloat(item.get("current_price") + "") * Integer.parseInt(item.get("count") + "") %>
										</td>
									</tr>
<%
								}
%>
								<tr>
									<td>送货地址：</td>
									<td><input type="text" class="address" value="" placeholder="请输入送货地址"></td>
									<td></td>
									<td>总价：<%= cart.get("total_price") %></td>
									<td><button type="button" class="btn btn-primary" onclick="if($('input.address').val().length == 0) { alert('请输入送货地址！'); } else { location.href='Order_create.do?address=' + $('input.address').val();}">提交订单</button></td>
								</tr>
							</tbody>
						</table>
						<div class="clearfix"> </div>
<%
						}
					}
%>						
				</div>
			</div>
		</div>
		<jsp:include page="../footer.jsp">
			<jsp:param name="prefix" value=""/>
		</jsp:include>
	</body>
</html>
<script>
	function add_to_cart(product_id, product_name, current_price) {
		$.post("Product_addToCart.do?product_id=" + product_id, null, function(data) {
			if(data.result == 0) {
				window.location.reload();
			} else {
				alert("添加失败！");
			}
		}, "json");
	}
	
	function remove_from_cart(product_id, count) {
		$.post("Product_removeFromCart.do?product_id=" + product_id + "&count=" + count, null, function(data) {
			if(data.result == 0) {
				window.location.reload();
			} else {
				alert("删除失败！");
			}
		}, "json");
	}
</script>