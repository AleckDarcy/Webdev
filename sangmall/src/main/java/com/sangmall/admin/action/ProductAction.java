package com.sangmall.admin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opensymphony.xwork2.ActionSupport;

public class ProductAction extends ActionSupport {
	private int id;
	private String product_name = "";
	private int price = 0;
	private float discount_rate = 1.0f;
	private String description = "";
	private int brand_id = 0;
	private int category_id = 0;
	private int is_special = 0;
	private String materials = "";
	private String details = "";
	private File icon;
    private String iconFileName;
    private String iconContentType;

	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	public String search() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(id == 0 && product_name.equals("")) {
			data.put("result", -1);
		} else {
			try {
				Map product = jt.queryForMap("select * from products where id=? or product_name=? limit 1", new Object[]{id, product_name});
				data.put("result", 0);
				data.put("product", product);
			} catch (Exception e) {
				data.put("result", -1);
			}
		}
		json = JSONObject.fromObject(data);
		return "json";
	}
	
	public String add() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(product_name.equals("")) {
			data.put("result", -1);
			data.put("message", "缺少商品名！");
		} else if(product_name.equals("default")) {
			data.put("result", -2);
			data.put("message", "商品名错误！");
		} else {
			String message = "";
			try {
				String icon_graph = "products/default.png";
				String path = ServletActionContext.getServletContext().getRealPath("/");
				if(new File("products/" + product_name + ".jpg").exists() || new File("products/" + product_name + ".png").exists()) {
					data.put("result", -3);
					data.put("message", message + "商品添加失败！");
				} else {
					if (icon != null) {
						if(iconContentType.equals("image/jpeg")) {
							icon_graph = "products/" + product_name + ".jpg";
							File savedFile = new File(path + "/images/" + icon_graph);
							icon.renameTo(savedFile);
						} else if(iconContentType.equals("image/png")) {
							icon_graph = "products/" + product_name + ".png";
							File savedFile = new File(path + "/images/" + icon_graph);
							icon.renameTo(savedFile);
						} else {
							icon_graph = "products/" + product_name + ".png";
							icon = new File(path + "/images/products/default.png");
							File savedFile = new File(path + "/images/" + icon_graph);
							FileInputStream fi = new FileInputStream(icon);
							FileOutputStream fo = new FileOutputStream(savedFile);
							FileChannel in = fi.getChannel();
							FileChannel out = fo.getChannel();
							in.transferTo(0, in.size(), out);
							message = "不支持的图片格式！";
						}
					} else {
						icon_graph = "products/" + product_name + ".png";
			        	icon = new File(path + "/images/products/default.png");
			        	File savedFile = new File(path + "/images/" + icon_graph);
						FileInputStream fi = new FileInputStream(icon);
						FileOutputStream fo = new FileOutputStream(savedFile);
						FileChannel in = fi.getChannel();
						FileChannel out = fo.getChannel();
						in.transferTo(0, in.size(), out);
			        	message = "没有上传文件！";
			        }
					int product = jt.update("insert into products (product_name, price, discount_rate, description, brand_id, category_id, is_special, icon_graph, materials, details) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{product_name, price, discount_rate, description, brand_id, category_id, is_special, icon_graph, materials, details});
					data.put("result", 0);
					data.put("product", product);
					data.put("message", message + "商品添加成功！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				data.put("result", -4);
				data.put("message", message + "未知错误！");
			}
		}
		json = JSONObject.fromObject(data);
		return "success";
	}
	
	public String modify() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(id == 0 || product_name.equals("")) {
			data.put("result", -1);
			data.put("message", "缺少ID或商品名！");
		}
		else {
			try {
				int product = jt.update("update products set price=?, discount_rate=?, description=?, is_special=?, materials=?, details=? where id=? and product_name=?", new Object[]{price, discount_rate, description, is_special, materials, details, id, product_name});
				data.put("result", 0);
				data.put("message", "商品修改成功！");
				data.put("product", product);
			} catch (Exception e) {
				data.put("result", -2);
				data.put("message", "商品修改失败！");
			}
		}
		json = JSONObject.fromObject(data);
		return "success";
	}
	
	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public float getDiscount_rate() {
		return discount_rate;
	}

	public void setDiscount_rate(float discount_rate) {
		this.discount_rate = discount_rate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public int getIs_special() {
		return is_special;
	}

	public void setIs_special(int is_special) {
		this.is_special = is_special;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	public File getIcon() {
		return icon;
	}

	public void setIcon(File icon) {
		this.icon = icon;
	}

	public String getIconFileName() {
		return iconFileName;
	}

	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	public String getIconContentType() {
		return iconContentType;
	}

	public void setIconContentType(String iconContentType) {
		this.iconContentType = iconContentType;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getCategory_id() {
		return category_id;
	}
}
