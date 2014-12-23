<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%
	JSONObject json = (JSONObject)request.getAttribute("json");
	String image_server = json.get("image_server").toString();
	Map product = (Map)json.get("product");
	DecimalFormat df = new DecimalFormat("######0.00");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>商品详情</title>
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
		<%= product %>
		<div class="container">
			<jsp:include page="../header.jsp">
				<jsp:param name="prefix" value=""/>
			</jsp:include>
			<jsp:include page="../navigation.jsp">
				<jsp:param name="prefix" value=""/>
			</jsp:include>
		</div>
<%	
		if(product != null) {
%>
		<div class="product-details">
			<div class="container">
				<div class="product-details-row1">
					<div class="product-details-row1-head">
						<h2><%= product.get("product_name") %></h2>
						<p><%= product.get("description") %></p>
					</div>
					<div class="col-md-4 product-details-row1-col1">
						<link rel="stylesheet" href="css/etalage.css">
						<script src="js/jquery.etalage.min.js"></script>
						<script>
							jQuery(document).ready(function($){
								$('#etalage').etalage({
									thumb_image_width: 300,
									thumb_image_height: 400,
									source_image_width: 900,
									source_image_height: 1000,
									show_hint: true,
									click_callback: function(image_anchor, instance_id){
										alert('Callback example:\nYou clicked on an image with the anchor: "'+image_anchor+'"\n(in Etalage instance: "'+instance_id+'")');
									}
								});

								$('.dropdownlist').change(function(){
									etalage_show( $(this).find('option:selected').attr('class') );
								});
							});
						</script>

						<div class="details-left">
							<div class="details-left-slider">
								<ul id="etalage">
									<li>
										<img class="etalage_thumb_image" src="images/product-slide/image1_thumb.jpg" />
										<img class="etalage_source_image" src="images/product-slide/image1_large.jpg" />
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-md-8 product-details-row1-col2">
						<div class="product-rating">
							<a class="rate" href="#"><span> </span></a>
							<label><a href="#">Read <b>%%</b> Reviews</a></label>
						</div>
						<div class="product-price">
							<div class="product-price-left text-right">
								<span><%= df.format(Float.parseFloat(product.get("price").toString())) %></span>
								<h5><%= df.format(Float.parseFloat(product.get("current_price").toString())) %></h5>
							</div>
							<div class="product-price-right">
								<a href="#"><span> </span></a>
								<label>节省<b><%= (int)(100 - 100 * Float.parseFloat(product.get("discount_rate").toString())) %>%</b></label>
							</div>
							<div class="clearfix"> </div>
						</div>
						<div class="product-price-details">
							<p class="text-right">%%</p>
							<a class="shipping" href="#"><span> </span>Free shipping</a>
							<div class="clearfix"> </div>
							<div class="product-cart-share">
								<div class="add-cart-btn">
									<input type="button" value="Add to cart" />
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"> </div>
				</div>
				<div class="product-tabs">
				    <div id="horizontalTab">
				        <ul>
				            <li><a href="#tab-1">Product overview</a></li>
				            <li><a href="#tab-2">Features</a></li>
				            <li><a href="#tab-3">Customer reviews</a></li>
				        </ul>
				        <div id="tab-1" class="product-complete-info">
				        	<h3>DESCRIPTION:</h3>
				        	<p>With its beautiful premium leather, lace-up oxford styling, recycled rubber outsoles and 9-inch height, this Earthkeepers City Premium style is an undeniably handsome boot. To complement its rustic, commanding outer appearance, we've paid attention to the inside as well - by adding SmartWool庐 faux shearling to the linings and crafting the footbed using our exclusive anti-fatigue comfort foam technology to absorb shock. Imported.</p>
				       		<span>DETAILS:</span>
				       		<div class="product-fea">
				       			<p>Premium burnished full-grain leather and suede upper</p>
				       			<p>Leather is from a tannery rated Silver for its water, energy and waste-management practices</p>
				       			<p>Leather lining and footbed for a premium feel and optimal comfort</p>
				       			<p>SmartWool庐 faux shearling lining is made with 60% merino wool and 40% PET</p>
				       			<p>Reflective insole board for additional warmth under foot</p>
				       			<p>100% organic cotton laces</p>
				       			<p>SmartWool庐 fabric-lined anti-fatigue footbed provides superior, all-day comfort and climate control</p>
				       			<p>Timberland庐 exclusive Gripstick鈩?and Green Rubber鈩?outsole is made with 42% recycled rubber</p>
				       		</div>
				        </div>
				        <div id="tab-2" class="product-complete-info">
				        	<h3>DESCRIPTION:</h3>
				        	<p>With its beautiful premium leather, lace-up oxford styling, recycled rubber outsoles and 9-inch height, this Earthkeepers City Premium style is an undeniably handsome boot. To complement its rustic, commanding outer appearance, we've paid attention to the inside as well - by adding SmartWool庐 faux shearling to the linings and crafting the footbed using our exclusive anti-fatigue comfort foam technology to absorb shock. Imported.</p>
				       		<p>lace-up oxford styling, recycled rubber outsoles and 9-inch height, this Earthkeepers City Premium style is an undeniably handsome boot. To complement its rustic, commanding outer appearance,</p>
				       		<span>DETAILS:</span>
				       		<div class="product-fea">
				       			<p>Premium burnished full-grain leather and suede upper</p>
				       			<p>Leather is from a tannery rated Silver for its water, energy and waste-management practices</p>
				       			<p>Leather lining and footbed for a premium feel and optimal comfort</p>
				       			<p>SmartWool庐 faux shearling lining is made with 60% merino wool and 40% PET</p>
				       			<p>Reflective insole board for additional warmth under foot</p>
				       		</div>
				        </div>
				        <div id="tab-3"  class="product-complete-info">
				        	<h3>DESCRIPTION:</h3>
				        	<p>With its beautiful premium leather, lace-up oxford styling, recycled rubber outsoles and 9-inch height, this Earthkeepers City Premium style is an undeniably handsome boot. To complement its rustic, commanding outer appearance, we've paid attention to the inside as well - by adding SmartWool庐 faux shearling to the linings and crafting the footbed using our exclusive anti-fatigue comfort foam technology to absorb shock. Imported.</p>
				       		<span>DETAILS:</span>
				       		<div class="product-fea">
				       			<p>100% organic cotton laces</p>
				       			<p>SmartWool庐 fabric-lined anti-fatigue footbed provides superior, all-day comfort and climate control</p>
				       			<p>Timberland庐 exclusive Gripstick鈩?and Green Rubber鈩?outsole is made with 42% recycled rubber</p>
				       			<p>Premium burnished full-grain leather and suede upper</p>
				       			<p>Leather is from a tannery rated Silver for its water, energy and waste-management practices</p>
				       			<p>Leather lining and footbed for a premium feel and optimal comfort</p>
				       			<p>SmartWool庐 faux shearling lining is made with 60% merino wool and 40% PET</p>
				       			<p>Reflective insole board for additional warmth under foot</p>
				       			<p>100% organic cotton laces</p>
				       			<p>SmartWool庐 fabric-lined anti-fatigue footbed provides superior, all-day comfort and climate control</p>
				       			<p>Timberland庐 exclusive Gripstick鈩?and Green Rubber鈩?outsole is made with 42% recycled rubber</p>
				       		</div>
				        </div>
				    </div>
				    <script src="js/jquery.responsiveTabs.js" type="text/javascript"></script>
				    <script type="text/javascript">
				        $(document).ready(function () {
				            $('#horizontalTab').responsiveTabs({
				                rotate: false,
				                startCollapsed: 'accordion',
				                collapsible: 'accordion',
				                setHash: true,
				                disabled: [3,4],
				                activate: function(e, tab) {
				                    $('.info').html('Tab <strong>' + tab.id + '</strong> activated!');
				                }
				            });
				
				            $('#start-rotation').on('click', function() {
				                $('#horizontalTab').responsiveTabs('active');
				            });
				            $('#stop-rotation').on('click', function() {
				                $('#horizontalTab').responsiveTabs('stopRotation');
				            });
				            $('#start-rotation').on('click', function() {
				                $('#horizontalTab').responsiveTabs('active');
				            });
				            $('.select-tab').on('click', function() {
				                $('#horizontalTab').responsiveTabs('activate', $(this).val());
				            });
				
				        });
				    </script>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
<%	
		}
%>			
		<jsp:include page="../footer.jsp">
			<jsp:param name="prefix" value=""/>
		</jsp:include>
	</body>
</html>