<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cart_count = request.getParameter("cart_count");
	String login_username = request.getParameter("login_username");
	if(login_username == null) {
		login_username = "";
	}
	String prefix = request.getParameter("prefix");
	if(prefix == null) {
		prefix = "";
	}
%>
<div class="top-header">
	<div class="logo">
		<a href="Product_show.do"><img src="images/logo.png" title="barndlogo" />
		</a>
	</div>
	<div class="top-header-info">
		<div class="top-contact-info">
			<ul class="unstyled-list list-inline">
				<li>
					<span class="phone"> </span>110
				</li>
				<li>
					<span class="mail"> </span><a href="#">110@qq.com</a>
				</li>
				<div class="clearfix"></div>
			</ul>
		</div>
		<div class="cart-details">
			<div class="add-to-cart">
				<ul class="unstyled-list list-inline">
					<li>
						<span class="cart" onclick="location.href='Cart_show.do'"></span>
						<ul class="cart-sub">
							<li>
								<p>
									<%= cart_count %>件商品
								</p>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<div class="login-rigister">
				<ul class="unstyled-list list-inline">
<%
					if(login_username.length() == 0) {
%>
						<li>
							<a class="login" href="User_login.do">登　录</a>
						</li>
						<li>
							<a class="rigister" href="User_register.do">注　册<span> </span>
							</a>
						</li>
<%
					} else {
%>
						<li>
							<a class="login" href="User_home.do"><%= login_username %></a>
						</li>
						<li>
							<a class="rigister" href="User_logout.do">退　出<span> </span>
							</a>
						</li>
<%
					}
%>
					<div class="clearfix"></div>
				</ul>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<div class="clearfix">
	</div>
</div>