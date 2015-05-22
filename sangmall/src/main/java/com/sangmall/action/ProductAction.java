package com.sangmall.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sangmall.util.Constants;
import com.sangmall.utils.CartUtil;
import com.sangmall.utils.LoginUtil;

@SuppressWarnings("serial")
public class ProductAction extends ActionSupport {
	private int page = 0;
	private int limit = 12;
	private int product_id;
	private int[] category_ids = {};
	private String product_name = "";
	private int[] brand_ids = {};
	private int current_price = 0;
	private String render = "";
	private int count;
	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	@SuppressWarnings("rawtypes")
	public String show() {		
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		List banners = jt.queryForList("select banners.header as header, banners.label as label, products.price * products.discount_rate as price, products.icon_graph as icon_graph from banners left join products on banners.product_id = products.id order by banners.id");
		List recommended_categories = jt.queryForList("select * from categories where is_recommended=1 order by id desc limit 3");
		List special_products = jt.queryForList("select products.*, products.price * products.discount_rate as price, brands.icon_graph as brand_icon_graph from products, brands where is_special=1 and brands.id=products.brand_id order by id desc");
		for(int i = 0; i < recommended_categories.size(); i++) {
			Map category = (Map)recommended_categories.get(i);
			Object id = category.get("id");
			Map products = jt.queryForMap("select count(*) as count from products where category_id=" + id);
			category.put("count", products.get("count"));
		}
		
		data.put("result", 0);
		data.put("banners", banners);
		data.put("recommended_categories", recommended_categories);
		data.put("special_products", special_products);
		data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
		data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
		data.put("login_username", LoginUtil.getLoginUsername(ActionContext.getContext().getSession()));
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}
	
	public String search() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		String sql = "from products, brands ";
		String ids;
		boolean need_and = false;
		if(brand_ids.length != 0) {
			ids = brand_ids[0] + "";
			for(int i = 1; i < brand_ids.length; i++) {
				ids += ", " + brand_ids[i];
			}
			sql += "where products.brand_id in (" + ids + ") ";
			need_and = true;
		}
		if(product_name.length() != 0) {
			if(need_and) {
				sql += "and products.product_name like '%" + product_name + "%' ";
			} else {
				sql += "where products.product_name like '%" + product_name + "%' ";
				need_and = true;
			}
		}
		if(category_ids.length != 0) {
			ids = category_ids[0] + "";
			for(int i = 1; i < category_ids.length; i++) {
				ids += ", " + category_ids[i];
			}
			if(need_and) {
				sql += "and products.category_id in (" + ids + ") ";
			} else {
				sql += "where products.category_id in (" + ids + ") ";
				need_and = true;
			}
		}
		Map count = new HashMap();
		List products = new ArrayList();
		if(need_and) {
			count = jt.queryForMap("select count(*) as count " + sql + "and brands.id=products.brand_id");
			products = jt.queryForList("select products.*, brands.icon_graph as brand_icon_graph " + sql + "and brands.id=products.brand_id limit " + page * limit + ", " + limit);
		} else {
			count = jt.queryForMap("select count(*) as count " + sql + "where brands.id=products.brand_id");
			products = jt.queryForList("select products.*, brands.icon_graph as brand_icon_graph " + sql + "where brands.id=products.brand_id limit " + page * limit + ", " + limit);
		}
		List categories = jt.queryForList("select * from categories");
		for(int i = 0; i < categories.size(); i++) {
			Map category = (Map)categories.get(i);
			category.put("selected", false);
			for(int j = 0; j < category_ids.length; j++) {
				if((category.get("id") + "").equalsIgnoreCase(category_ids[j] + "")) {
					category.put("selected", true);
				}
			}
		}
		List brands = jt.queryForList("select * from brands");
		for(int i = 0; i < brands.size(); i++) {
			Map brand = (Map)brands.get(i);
			brand.put("selected", false);
			for(int j = 0; j < brand_ids.length; j++) {
				if((brand.get("id") + "").equalsIgnoreCase(brand_ids[j] + "")) {
					brand.put("selected", true);
				}
			}
		}
		data.put("result", 0);
		data.put("count", count.get("count"));
		data.put("products", products);
		data.put("categories", categories);
		data.put("brands", brands);
		data.put("page", page);
		data.put("limit", limit);
		data.put("category_ids", category_ids);
		data.put("product_name", product_name);
		data.put("brand_ids", brand_ids);
		data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
		data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
		data.put("login_username", LoginUtil.getLoginUsername(ActionContext.getContext().getSession()));
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}
	
	public String details() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		List product = jt.queryForList("select *, price * discount_rate as current_price from products where id=?", new Object[]{product_id});
		List category = jt.queryForList("select categories.* from categories, products where categories.id=products.category_id and products.id=?", new Object[]{product_id});
		if(product.size() > 0) {
			data.put("result", 0);
			data.put("product", product.get(0));
			data.put("category", category);
			data.put("product_id", product_id);
			data.put("brand_ids", brand_ids);
		} else {
			data.put("result", -1);
		}
		data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
		data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
		data.put("login_username", LoginUtil.getLoginUsername(ActionContext.getContext().getSession()));
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}
	
	public String addToCart() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		Map product = jt.queryForMap("select * from products where id=?", new Object[]{product_id});
		if(product != null) {
			data.put("result", CartUtil.putToCart(product, ActionContext.getContext().getSession()));
		} else {
			data.put("result", -2);
		}
		json = JSONObject.fromObject(data);
		return "json";
	}
	
	public String removeFromCart() {
		CartUtil.removeFromCart(product_id, count, ActionContext.getContext().getSession());
		data.put("result", 0);
		json = JSONObject.fromObject(data);
		return "json";
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int[] getCategory_ids() {
		return category_ids;
	}

	public void setCategory_ids(int[] category_ids) {
		this.category_ids = category_ids;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int[] getBrand_ids() {
		return brand_ids;
	}

	public void setBrand_ids(int[] brand_ids) {
		this.brand_ids = brand_ids;
	}
	
	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public void setRender(String render) {
		this.render = render;
	}

	public String getRender() {
		return render;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setCurrent_price(int current_price) {
		this.current_price = current_price;
	}

	public int getCurrent_price() {
		return current_price;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}
}
