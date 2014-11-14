<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String prefix = request.getParameter("prefix");
	if(prefix == null) {
		prefix = "";
	}
%>
<div class="top-header-nav">
	<nav class="top-nav main-menu">
		<ul class="top-nav">
			<li>
				<a href="Product_show.do">首　　页</a><span></span>
			</li>
			<li>
				<a href="Product_search.do">所有商品</a><span></span>
			</li>
			<li>
				<a href="products.html">关于我们</a><span></span>
			</li>
			<div class="clearfix"></div>
		</ul>
		<a href="#" id="pull"><img src="images/nav-icon.png" title="menu" /></a>
	</nav>

	<div class="top-header-search-box">
		<form action="<%= prefix + "Product_search.do" %>">
			<input type="text" placeholder="Search" required / maxlength="22" name="product_name">
			<input type="submit" value=" ">
		</form>
	</div>
	<div class="clearfix"></div>
</div>
<script>
	$(function() {
		var pull = $('#pull');
		menu = $('nav ul');
		menuHeight = menu.height();
		$(pull).on('click', function(e) {
			e.preventDefault();
			menu.slideToggle();
		});
		$(window).resize(function() {
			var w = $(window).width();
			if (w > 320 && menu.is(':hidden')) {
				menu.removeAttr('style');
			}
		});
	});
</script>