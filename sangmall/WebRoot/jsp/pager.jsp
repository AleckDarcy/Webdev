<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*" %>
<%
	JSONObject json = JSONObject.fromObject(request.getParameter("params"));
	int count = Integer.parseInt(json.get("count") + "");
	int pagee = Integer.parseInt(json.get("page") + "");
	int limit = Integer.parseInt(json.get("limit") + "");
	String params = "";
	boolean add_and = false;
	String product_name = json.get("product_name") + "";
	if(product_name.length() > 0) {
		params += product_name;
		add_and = true;
	}
	JSONArray brand_ids_array = json.getJSONArray("brand_ids");
	String brand_ids = "";
	if(brand_ids_array.size() >= 1) {
		brand_ids = "brand_ids=" + brand_ids_array.get(0);
	}
	for(int i = 1; i < brand_ids_array.size(); i++) {
		brand_ids += "&brand_ids=" + brand_ids_array.get(i);
	}
	if(brand_ids.length() > 0) {
		if(add_and) {
			params += "&" + brand_ids;
		} else {
			params = brand_ids;
			add_and = true;
		}
	}
	JSONArray category_ids_array = json.getJSONArray("category_ids");
	String category_ids = "";
	if(category_ids_array.size() >= 1) {
		category_ids = "category_ids=" + category_ids_array.get(0);
	}
	for(int i = 1; i < category_ids_array.size(); i++) {
		category_ids += "&category_ids=" + category_ids_array.get(i);
	}
	if(category_ids.length() > 0) {
		if(add_and) {
			params += "&" + category_ids;
		} else {
			params = category_ids;
			add_and = true;
		}
	}
	String action = request.getParameter("action");

	if(action != null && (count - 1) / limit > 0 && pagee < (count - 1) / limit + 1) {
%>	
		<div class="filters">
			<select class="pager" onchange="pager('<%=action %>', '<%= params %>', $('.pager').val() - 1, '<%= limit %>');">		
<%
				for(int i = 1; i <= (count - 1) / limit + 1; i++) {
					if(pagee + 1 == i) {
%>
						<option value="<%= i %>" selected><%= i %></option>
<%
					} else {
%>
						<option value="<%= i %>"><%= i %></option>
<%
					}
				}
%>
			</select>
		</div>
<%	
	}
%>
<script>
	function pager(action, params, page, limit) {
		if(params.length > 0) {
			location.href = action + "?" + params + "&page=" + page + "&limit=" + limit;
		} else {
			location.href = action + "?page=" + page + "&limit=" + limit;
		}
	}
</script>