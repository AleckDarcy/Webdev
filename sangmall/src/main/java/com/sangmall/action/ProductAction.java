package com.sangmall.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opensymphony.xwork2.ActionSupport;
import com.sangmall.util.Constants;

@SuppressWarnings("serial")
public class ProductAction extends ActionSupport {
	private int page = 0;
	private int limit = 10;
	private int product_id;
	private int[] category_ids = {};
	private String product_name = "";
	private int[] brand_ids = {};
	private String render = "";
	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	@SuppressWarnings("rawtypes")
	public String show() {		
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		List banners = jt.queryForList("select banners.header as header, banners.label as label, products.price * products.discount_rate as price, products.hd_graph as hd_graph from banners left join products on banners.product_id = products.id order by banners.id");
		List recommended_categories = jt.queryForList("select * from categories where is_recommended=1 order by id desc limit 3");
		List special_products = jt.queryForList("select *, price * discount_rate as price from products where is_special=1 order by id desc");
		
		data.put("result", 0);
		data.put("banners", banners);
		data.put("recommended_categories", recommended_categories);
		data.put("special_products", special_products);
		data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}
	
	public String search() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		String sql = "from products ";
		String ids;
		boolean need_and = false;
		if(brand_ids.length != 0) {
			ids = brand_ids[0] + "";
			for(int i = 1; i < brand_ids.length; i++) {
				ids += ", " + brand_ids[i];
			}
			sql += "where brand_id in (" + ids + ") ";
			need_and = true;
		}
		if(product_name.length() != 0) {
			if(need_and) {
				sql += "and product_name like '%" + product_name + "%' ";
			} else {
				sql += "where product_name like '%" + product_name + "%' ";
			}
		}
		if(category_ids.length != 0) {
			ids = category_ids[0] + "";
			for(int i = 1; i < category_ids.length; i++) {
				ids += ", " + category_ids[i];
			}
			List product_ids = jt.queryForList("select distinct product_id from product_category where category_id in (" + ids + ")");
			for(int i = 0; i < product_ids.size(); i++) {
				Map product_id = (Map)product_ids.get(i);
				if(i == 0) {
					ids = product_id.get("product_id") + "";
				} else {
					ids += ", " + product_id.get("product_id");
				}
			}
			if(need_and) {
				sql += "and id in (" + ids + ") ";
			} else {
				sql += "where id in (" + ids + ") ";
			}
		}
		Map count = jt.queryForMap("select count(*) as count " + sql);
		List products = jt.queryForList("select * " + sql + "limit " + page * limit + ", " + limit);
		List categories = jt.queryForList("select * from categories");
		List brands = jt.queryForList("select * from brands");
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
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}
	
	public String details() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		List product = jt.queryForList("select * from products where id=?", new Object[]{product_id});
		List categories = jt.queryForList("select * from categories where id in (select distinct category_id from product_category where product_id=?)", new Object[]{product_id});
		data.put("result", 0);
		data.put("product", product);
		data.put("page", page);
		data.put("limit", limit);
		data.put("categories", categories);
		data.put("product_name", product_name);
		data.put("brand_ids", brand_ids);
		data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
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
}
