<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String prefix = request.getParameter("prefix");
	if(prefix == null) {
		prefix = "";
	}
%>
<div class="top-header">
	<div class="logo">
		<a href="index.html"><img src="images/logo.png" title="barndlogo" />
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
						<span class="cart" onclick="alert('wa');"></span>
						<ul class="cart-sub">
							<li>
								<p>
									0件商品
								</p>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<div class="login-rigister">
				<ul class="unstyled-list list-inline">
					<li>
						<a class="login" href="#">登　录</a>
					</li>
					<li>
						<a class="rigister" href="#">注　册<span> </span>
						</a>
					</li>
					<div class="clearfix"></div>
				</ul>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<div class="clearfix">
	</div>
</div>