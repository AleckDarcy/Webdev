<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	JSONObject json = (JSONObject)request.getAttribute("json");
	JSONArray brands = json.getJSONArray("brands");
	JSONArray categories = json.getJSONArray("categories");
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
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
		<script src="js/jquery.min.js"></script>
	</head>
	<body>
<%
		if(json.has("message")) {
%>
			<div style="border:1px solid red;width:400px;margin:auto;">
				<%= json.get("message") %>
			</div>
<%
		}
%>
		<br>
		
		<div style="border:1px solid;width:400px;margin:auto;">
			<h5>横幅增加：</h5>
			<form action="admin_Banner_add.do" class="banner_add_form" method="post">
				<label style="width:80px;">横幅名：</label><input type="text" name="header"><br>
				<label style="width:80px;">标　签：</label><input type="text" name="label"><br>
				<label style="width:80px;">商品ＩＤ：</label><input type="text" name="product_id" id="banner_product_id"><br>
				<button type="button" class="btn btn-primary" onclick="post_banner_add($('#banner_product_id').val());">增加横幅</button>
			</form>
		</div>
		
		<div style="border:1px solid;width:400px;margin:auto;">
			<h5>类别增加：</h5>
			<form action="admin_Category_add.do" method="post">
				<label style="width:80px;">类别名：</label><input type="text" name="category_name"><br>
				<label style="width:80px;">推　荐：</label>
				<select name="is_recommended">
					<option value ="0">否</option>
					<option value ="1">是</option>
				</select><br>
				<label style="width:80px;">图　片：</label><s:file name="graph"></s:file><br>
				<label style="width:80px;">阴影图：</label><s:file name="shadow_graph"></s:file><br>
				<input type="submit" class="btn btn-primary" value="提交">
			</form>
		</div>
		
		<div style="border:1px solid;width:400px;margin:auto;">
			<h5>类别修改：</h5>
			<form action="admin_Category_modify.do" method="post">
				<label style="width:80px;">类别名：</label><input type="text" name="category_name" id="category_modify_name"><br><button type="button" onclick="search_category_by_name($('#category_modify_name').val());">搜　索</button><br>
				<div class="category_details" style="display:none;">
					<label style="width:80px;">ＩＤ　：</label><input type="text" class="category_id" name="id" readonly><br>
					<label style="width:80px;">推　荐：</label>
					<select name="is_recommended" class="category_is_recommended">
						<option value ="0">否</option>
						<option value ="1">是</option>
					</select><br>
					<br>
					<input type="submit" class="btn btn-primary" value="提交修改">
				</div>
			</form>
		</div>
		
		<div style="border:1px solid;width:400px;margin:auto;">
			<h5>品牌增加：</h5>
			<form action="admin_Brand_add.do" enctype="multipart/form-data" method="post">
				<label style="width:80px;">品牌名：</label><input type="text" name="brand_name"><br>
				<label style="width:80px;">图　片：</label><s:file name="icon"></s:file>
				<input type="submit" class="btn btn-primary" value="增加品牌">
			</form>
		</div>
		
		<!--div style="border:1px solid;width:400px;margin:auto;">
			<h5>品牌修改：</h5>
			<form action="" method="post">
				<label style="width:80px;">品牌名：</label><input type="text" name="id" id="brand_modify_name"><button type="button" onclick="search_brand_by_name($('#brand_modify_name').val());">搜　索</button><br>	
				<div class="brand_details" style="display:none;">
					<label style="width:80px;">ＩＤ　：</label><input type="text" class="brand_brand_id" name="" disabled><br>
					<label style="width:80px;">图　片：</label><input type="text" name="icon"><br>
					<input type="submit" class="btn btn-primary" value="提交">
				</div>
			</form>
		</div-->
		
		<div style="border:1px solid;width:400px;margin:auto;">
			<h5>商品增加：</h5>
			<form action="admin_Product_add.do" enctype="multipart/form-data" method="post">
				<label style="width:80px;">商品名：</label><input type="text" name="product_name"><br>
				<label style="width:80px;">价　格：</label><input type="text" name="price"><br>
				<label style="width:80px;">折　率：</label><input type="text" name="discount_rate"><br>
				<label style="width:80px;">简　介：</label><input type="text" name="description"><br>
				<label style="width:80px;">品　牌：</label>
				<select name="brand_id">
<%
					for(int i = 0; i< brands.size(); i++) {
						Map brand = (Map)brands.get(i);
%>
						<option value ="<%= brand.get("id") %>"><%= brand.get("brand_name")%></option>
<%
					}
%>
				</select><br>
				<label style="width:80px;">类　别：</label>
				<select name="category_id">
<%
					for(int i = 0; i< categories.size(); i++) {
						Map category = (Map)categories.get(i);
%>
						<option value ="<%= category.get("id") %>"><%= category.get("category_name")%></option>
<%
					}
%>
				</select><br>
				<label style="width:80px;">推　荐：</label>
				<select name="is_special">
					<option value ="0">否</option>
					<option value ="1">是</option>
				</select><br>
				<label style="width:80px;">图　片：</label><s:file name="icon"></s:file>
				<label style="width:80px;">材　料：</label><input type="text" name="materials"><br>
				<label style="width:80px;">详　细：</label><input type="text" name="details"><br>
				<input type="submit" class="btn btn-primary" value="增加商品">
			</form>
		</div>
		
		<div style="border:1px solid;width:400px;margin:auto;">
			<h5>商品修改：</h5>
			<form action="admin_Product_modify.do" method="post">
				<label style="width:80px;">商品名：</label><input type="text" name="product_name" id="product_modify_name"><button type="button" onclick="search_product_by_name($('#product_modify_name').val());">搜　索</button><br>	
				<div class="product_details" style="display:none;">
					<label style="width:80px;">ＩＤ　：</label><input type="text" class="product_id" name="id" readonly><br>
					<label style="width:80px;">价　格：</label><input type="text" class="product_price" name="price"><br>
					<label style="width:80px;">折　率：</label><input type="text" class="product_discount_rate" name="discount_rate"><br>
					<label style="width:80px;">简　介：</label><input type="text" class="product_description" name="description"><br>
					<label style="width:80px;">推　荐：</label><input type="text" class="product_is_special" name="is_special"><br>
					<label style="width:80px;">材　料：</label><input type="text" class="product_materials" name="materials"><br>
					<label style="width:80px;">详　细：</label><input type="text" class="product_details" name="details"><br>
					<input type="submit" class="btn btn-primary" value="提交修改">
				</div>
			</form>
		</div>
		
		<div style="border:1px solid;width:400px;margin:auto;">
			<label>订单管理：</label>
			<form action="admin_Order_modify.do" method="post">
				<label style="width:80px;">状　态：</label>
				<select name="status" id="order_modify_status" onchange="$('button.order_search').click();">
					<option value="0">已下单</option>
					<option value="3">申请退货</option>
				</select>
				<button type="button" class="order_search" onclick="search_order_by_status($('#order_modify_status').val());">搜　索</button><br>	
				<div class="order_details" style="display:none;">
					<label style="width:80px;">ＩＤ　：</label><input type="text" class="order_id" name="id" readonly><br>
					<label style="width:80px;">用　户：</label><input type="text" class="order_user_name" name="" readonly><br>
					<label style="width:80px;">价　格：</label><input type="text" class="order_price" name="" readonly><br>
					<label style="width:80px;">地　址：</label><input type="text" class="order_address" name="" readonly><br>
					<label style="width:80px;">时　间：</label><input type="text" class="order_create_time" name="" readonly><br>
					<input type="submit" class="btn btn-primary" id="order_submit" value="提交修改">
				</div>
			</form>
		</div>
		<br>
	</body>
</html>
<script type="text/javascript">
	function post_banner_add(product_id) {
		$.post("admin_Product_search.do?id=" + product_id, null, function(data) {
			if(data.result == 0) {
				alert("wa！");
				$.post("admin_Banner_add.do", $("form.banner_add_form").serialize(), function(data) {
					if(data.result == 0) {
						alert("横幅添加成功！");
					} else {
						alert("横幅添加失败！");
					}
				}, "json");
			} else {
				alert("未搜索到商品！");
			}
		}, "json");
	}
	
	function search_category_by_name(category_name) {
		$.post("admin_Category_search.do?category_name=" + category_name, null, function(data) {
			if(data.result == 0) {
				$("div.category_details").css("display", "");
				var category = data.category;
				for(key in category) {
					$("input.category_" + key).val(category[key]);
				}
				$("select.category_is_recommended option:selected").attr("selected", "");
				$("select.category_is_recommended").children("option[value='" + category["is_recommended"] + "']").attr("selected", "selected")  ;
			} else {
				alert("未搜索到类别！");
				$("div.category_details").css("display", "none");
			}
		}, "json");
	}
	
	function search_product_by_name(product_name) {
		$.post("admin_Product_search.do?product_name=" + product_name, null, function(data) {
			if(data.result == 0) {
				$("div.product_details").css("display", "");
				var product = data.product;
				for(key in product) {
					$("input.product_" + key).val(product[key]);
				}
			} else {
				alert("未搜索到商品！");
				$("div.product_details").css("display", "none");
			}
		}, "json");
	}
	
	function search_order_by_status(status) {
		$.post("admin_Order_search.do?status=" + status, null, function(data) {
			if(data.result == 0) {
				$("div.order_details").css("display", "");
				var order = data.order;
				for(key in order) {
					if(key == "create_time") {
						var time = order[key];
						$("input.order_create_time").val((time["year"] + 1900) + "年" + (time["month"] + 1) + "月" + time["date"] + "日");
					} else {
						$("input.order_" + key).val(order[key]);
					}
				}
				if(order["status"] == 0) {
					$("#order_submit").val("发　货");
				} else if(order["status"] == 3) {
					$("#order_submit").val("退　货");
				}
			} else {
				alert("未搜索到订单！");
				$("div.order_details").css("display", "none");
			}
		}, "json");
	}
</script>