<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String prefix = request.getParameter("prefix");
	if(prefix == null) {
		prefix = "";
	}
%>
<div class="bottom">
<div class="footer">
	<div class="container">
		<div class="col-md-3 footer-logo">
			<a href="index.html"><img src="images/flogo.png" title="brand-logo" /></a>
		</div>
		<div class="col-md-7 footer-links">
			<ul class="unstyled-list list-inline">
				<li><a href="#"> Faq</a> <span> </span></li>
				<li><a href="#"> Terms and Conditions</a> <span> </span></li>
				<li><a href="#"> Secure Payments</a> <span> </span></li>
				<li><a href="#"> Shipping</a> <span> </span></li>
				<li><a href="contact.html"> Contact</a> </li>
				<div class="clearfix"> </div>
			</ul>
		</div>
		<div class="col-md-2 footer-social">
			<ul class="unstyled-list list-inline">
				<li><a class="pin" href="#"><span> </span></a></li>
				<li><a class="twitter" href="#"><span> </span></a></li>
				<li><a class="facebook" href="#"><span> </span></a></li>
				<div class="clearfix"> </div>
			</ul>
		</div>
		<div class="clearfix"> </div>
	</div>
</div>
<div class="clearfix"> </div>
			 
<div class="copy-right">
	<div class="container">
		<p>Copyright &copy; 2014 Sang Bear Co.,Ltd. All rights reserved.</a></p>
		<script type="text/javascript">
			$(document).ready(function() {
				$().UItoTop({ easingType: 'easeOutQuart' });
			});
		</script>
		<a href="#" id="toTop" style="display: block;"><span id="toTopHover" style="opacity: 1;"> </span></a>
	</div>
</div>
</div>
<style type="text/css">
.bottom {
	position: fixed;
	left: 0px;
	bottom: 0px;
	width: 100%;
	z-index: 99;
}
</style>