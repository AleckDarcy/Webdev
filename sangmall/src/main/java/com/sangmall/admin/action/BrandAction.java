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

public class BrandAction extends ActionSupport {
	private String brand_name = "";
	private String icon_graph = "";
	private File icon;
    private String iconFileName;
    private String iconContentType;

	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
//	public String search() {
//		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
//		if(brand_name.equals("")) {
//			data.put("result", -1);
//		} else {
//			try {
//				Map brand = jt.queryForMap("select * from brands where brand_name=?", new Object[]{brand_name});
//				data.put("result", 0);
//				data.put("brand", brand);
//			} catch (Exception e) {
//				data.put("result", -1);
//			}
//		}
//		json = JSONObject.fromObject(data);
//		return "json";
//	}
	
	public String add() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(brand_name.equals("")) {
			data.put("result", -1);
			data.put("message", "缺少品牌名！");
		}
		else {
			String message = "";
			try {
				String icon_graph = "brands/0.jpg";	
				String path = ServletActionContext.getServletContext().getRealPath("/");
				if (icon != null) {
					if(iconContentType.equals("image/jpeg")) {
						icon_graph = "brands/" + brand_name + ".jpg";
			            File savedFile = new File(path + "/images/" + icon_graph);
			            icon.renameTo(savedFile);
					} else if(iconContentType.equals("image/png")) {
						icon_graph = "brands/" + brand_name + ".png";
			            File savedFile = new File(path + "/images/" + icon_graph);
			            icon.renameTo(savedFile);
					} else {
						icon_graph = "brands/" + brand_name + ".jpg";
						icon = new File(path + "/images/brands/0.jpg");
						File savedFile = new File(path + "/images/" + icon_graph);
						FileInputStream fi = new FileInputStream(icon);
						FileOutputStream fo = new FileOutputStream(savedFile);
						FileChannel in = fi.getChannel();
						FileChannel out = fo.getChannel();
						in.transferTo(0, in.size(), out);
						message = "不支持的图片格式！";
					}
		        } else {
		        	icon_graph = "brands/" + brand_name + ".jpg";
					icon = new File(path + "/images/brands/0.jpg");
					File savedFile = new File(path + "/images/" + icon_graph);
					FileInputStream fi = new FileInputStream(icon);
					FileOutputStream fo = new FileOutputStream(savedFile);
					FileChannel in = fi.getChannel();
					FileChannel out = fo.getChannel();
					in.transferTo(0, in.size(), out);
		        	message = "没有上传文件！";
		        }
				int brand = jt.update("insert into brands (brand_name, icon_graph) values (?, ?)", new Object[]{brand_name, icon_graph});
				data.put("result", 0);
				data.put("brand", brand);
				data.put("message", message + "品牌添加成功！");
			} catch (Exception e) {
				e.printStackTrace();
				data.put("result", -2);
				data.put("message", message + "品牌添加失败！");
			}
		}
		json = JSONObject.fromObject(data);
		return "success";
	}
	
//	public String modify() {
//		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
//		if(id == 0) {
//			data.put("result", -1);
//			data.put("message", "缺少ID！");
//		}
//		else {
//			try {
////				int product = jt.update("update products set price=?, discount_rate=?, description=?, is_special=?, materials=?, details=? where id=?", new Object[]{price, discount_rate, description, is_special, materials, details, id});
//				data.put("result", 0);
//				data.put("message", "商品修改成功！");
////				data.put("product", product);
//			} catch (Exception e) {
//				data.put("result", -2);
//				data.put("message", "商品修改失败！");
//			}
//		}
//		json = JSONObject.fromObject(data);
//		return "success";
//	}
	
	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
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

	public void setIcon_graph(String icon_graph) {
		this.icon_graph = icon_graph;
	}

	public String getIcon_graph() {
		return icon_graph;
	}
}
