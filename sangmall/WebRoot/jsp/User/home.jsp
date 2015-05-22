<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%@ page import="java.sql.Timestamp" %>
<%
	JSONObject json = (JSONObject)request.getAttribute("json");
	Map cart = json.getJSONObject("cart");
	String login_username = json.getString("login_username");
	int cart_count = 0;
	if(!cart.isEmpty()) {
		cart_count = ((Map)cart.get("items")).size();
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>我的订单</title>
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
					JSONArray orders = json.getJSONArray("orders");
					if(orders.size() == 0) {
%>
						你没有订单！
<%
					} else {
%>
						<table style="width:100%;">
							<tbody>
								<tr>
									<td style="width:5%;border:1px solid gray;">订单号</td>
									<td style="width:30%;border:1px solid gray;">收货地址</td>
									<td style="width:10%;border:1px solid gray;">总价</td>
									<td style="width:25%;border:1px solid gray;">下单时间</td>
									<td style="width:10%;border:1px solid gray;">状态</td>
									<td style="width:20%;border:1px solid gray;">操作</td>
								</tr>
<%
								for(int i = 0; i < orders.size(); i++) {
									Map order = (Map) orders.get(i);
%>
									<tr>
										<td style="border:1px solid gray;"><%= order.get("id") %></td>
										<td style="border:1px solid gray;"><%= order.get("address") %></td>
										<td style="border:1px solid gray;"><%= order.get("price") %></td>
										<td style="border:1px solid gray;">
<%
											Map<String, Integer> create_time = (Map<String, Integer>) order.get("create_time");
%>
											<%= (create_time.get("year") + 1900) + "年" + (create_time.get("month") + 1) + "月" + create_time.get("date") + "日" + create_time.get("hours") + "时" + create_time.get("minutes") + "分" + create_time.get("seconds") + "秒" %>
										</td>
<%
										if((order.get("status") + "").equals("-1")) {
%>
											<td style="border:1px solid gray;">已取消</td>
											<td style="border:1px solid gray;"></td>
<%
										} else if((order.get("status") + "").equals("0")) {
%>
											<td style="border:1px solid gray;">已下单</td>
											<td style="border:1px solid gray;text-align:center;">
												<button type="button" class="btn btn-primary" onclick="cancel_order(<%= order.get("id") %>)">取消</button>
											</td>
<%
										} else if((order.get("status") + "").equals("1")) {
%>
											<td style="border:1px solid gray;">已发货</td>
											<td style="border:1px solid gray;text-align:center;">
												<button type="button" class="btn btn-primary" onclick="receive_order(<%= order.get("id") %>)">收货</button>
											</td>
<%
										} else if((order.get("status") + "").equals("2")) {
%>
											<td style="border:1px solid gray;">已收货</td>
											<td style="border:1px solid gray;text-align:center;">
												<button type="button" class="btn btn-primary" onclick="return_order(<%= order.get("id") %>)">退货</button>
											</td>
<%
										}  else if((order.get("status") + "").equals("3")) {
%>
											<td style="border:1px solid gray;">已申请退货</td>
											<td style="border:1px solid gray;text-align:center;">
											</td>
<%
										}  else if((order.get("status") + "").equals("4")) {
%>
											<td style="border:1px solid gray;">已完成退货</td>
											<td style="border:1px solid gray;text-align:center;">
											</td>
<%
										}
%>								
									</tr>
<%
								}
%>
							</tbody>
						</table>
<%
					}
%>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>

		<jsp:include page="../footer.jsp">
			<jsp:param name="prefix" value=""/>
		</jsp:include>
	</body>
</html>
<script type="text/javascript"">
	function cancel_order(id) {
		$.post("Order_canceled.do?id=" + id, null, function(data) {
			if(data.result == 0) {
				alert("订单取消成功！");
				window.location.reload();
			} else {
				alert("订单取消失败！");
			}
		}, "json");
	}
	
	function receive_order(id) {
		$.post("Order_received.do?id=" + id, null, function(data) {
			if(data.result == 0) {
				alert("确认收货成功！");
				window.location.reload();
			} else {
				alert("确认收货失败！");
			}
		}, "json");
	}
	
	function return_order(id) {
		$.post("Order_returned.do?id=" + id, null, function(data) {
			if(data.result == 0) {
				alert("退货申请成功！");
				window.location.reload();
			} else {
				alert("退货申请失败！");
			}
		}, "json");
	}
</script>