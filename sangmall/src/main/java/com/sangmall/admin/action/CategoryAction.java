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

public class CategoryAction extends ActionSupport {
	private int id;
	private String category_name = "";
	private int is_recommended = 0;

	private File graph;
    private String graphFileName;
    private String graphContentType;
    private File shadow_graph;
    private String shadow_graphFileName;
    private String shadow_graphContentType;

	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	public String search() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(id == 0 && category_name.equals("")) {
			data.put("result", -1);
		} else {
			try {
				Map category = jt.queryForMap("select * from categories where id=? or category_name=?", new Object[]{id, category_name});
				data.put("result", 0);
				data.put("category", category);
			} catch (Exception e) {
				data.put("result", -2);
			}
		}
		json = JSONObject.fromObject(data);
		return "json";
	}
	
	public String add() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(category_name.equals("")) {
			data.put("result", -1);
			data.put("message", "缺少类别名！");
		} else if(category_name.equals("default")) {
			data.put("result", -2);
			data.put("message", "类别名错误！");
		} else {
			String message = "";
			try {
				String image = "categories/default.png";
				String shadow_image = "categories/default_shadow.png";
				String path = ServletActionContext.getServletContext().getRealPath("/");
				if (graph != null) {
					if(graphContentType.equals("image/jpeg")) {
						image = "categories/" + category_name + ".jpg";
						File savedFile = new File(path + "/images/" + image);
						graph.renameTo(savedFile);
					} else if(graphContentType.equals("image/png")) {
						image = "categories/" + category_name + ".png";
						File savedFile = new File(path + "/images/" + image);
						graph.renameTo(savedFile);
					} else {
						image = "categories/" + category_name + ".png";
						graph = new File(path + "/images/categories/default.png");
						File savedFile = new File(path + "/images/" + image);
						FileInputStream fi = new FileInputStream(graph);
						FileOutputStream fo = new FileOutputStream(savedFile);
						FileChannel in = fi.getChannel();
						FileChannel out = fo.getChannel();
						in.transferTo(0, in.size(), out);
						message = "“图　片”格式错误！";
					}
				} else {
					image = "categories/" + category_name + ".png";
					graph = new File(path + "/images/categories/default.png");
					File savedFile = new File(path + "/images/" + image);
					FileInputStream fi = new FileInputStream(graph);
					FileOutputStream fo = new FileOutputStream(savedFile);
					FileChannel in = fi.getChannel();
					FileChannel out = fo.getChannel();
					in.transferTo(0, in.size(), out);
		        	message = "没有上传“图　片”文件！";
		        }
				
				if (shadow_graph != null) {
					if(shadow_graphContentType.equals("image/jpeg")) {
						shadow_image = "categories/" + category_name + "_shadow.jpg";
						File savedFile = new File(path + "/images/" + shadow_image);
						shadow_graph.renameTo(savedFile);
					} else if(shadow_graphContentType.equals("image/png")) {
						shadow_image = "categories/" + category_name + "_shadow.png";
						File savedFile = new File(path + "/images/" + shadow_image);
						shadow_graph.renameTo(savedFile);
					} else {
						shadow_image = "categories/" + category_name + "_shadow.png";
						shadow_graph = new File(path + "/images/categories/default_shadow.png");
						File savedFile = new File(path + "/images/" + shadow_image);
						FileInputStream fi = new FileInputStream(shadow_graph);
						FileOutputStream fo = new FileOutputStream(savedFile);
						FileChannel in = fi.getChannel();
						FileChannel out = fo.getChannel();
						in.transferTo(0, in.size(), out);
						message += "“阴影图”格式错误！";
					}
				} else {
					shadow_image = "categories/" + category_name + "_shadow.png";
					shadow_graph = new File(path + "/images/categories/default_shadow.png");
					File savedFile = new File(path + "/images/" + shadow_image);
					FileInputStream fi = new FileInputStream(shadow_graph);
					FileOutputStream fo = new FileOutputStream(savedFile);
					FileChannel in = fi.getChannel();
					FileChannel out = fo.getChannel();
					in.transferTo(0, in.size(), out);
		        	message += "没有上传“阴影图”文件！";
		        }
				
				int category = jt.update("insert into categories (category_name, is_recommended, image, shadow_image) values (?, ?, ?, ?)", new Object[]{category_name, is_recommended, image, shadow_image});
				data.put("result", 0);
				data.put("category", category);
				data.put("message", message + "类别添加成功！");
			} catch (Exception e) {
				e.printStackTrace();
				data.put("result", -3);
				data.put("message", message + "类别添加失败！");
			}
		}
		json = JSONObject.fromObject(data);
		return "success";
	}
	
	public String modify() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(id == 0 || category_name.equals("")) {
			data.put("result", -1);
			data.put("message", "缺少ID或类别名！");
		}
		else {
			try {
				int product = jt.update("update categories set is_recommended=? where id=? and category_name=?", new Object[]{is_recommended, id, category_name});
				data.put("result", 0);
				data.put("message", "类别修改成功！");
				data.put("product", product);
			} catch (Exception e) {
				data.put("result", -2);
				data.put("message", "类别修改失败！");
			}
		}
		json = JSONObject.fromObject(data);
		return "success";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public int getIs_recommended() {
		return is_recommended;
	}

	public void setIs_recommended(int is_recommended) {
		this.is_recommended = is_recommended;
	}

	public File getGraph() {
		return graph;
	}

	public void setGraph(File graph) {
		this.graph = graph;
	}

	public String getGraphFileName() {
		return graphFileName;
	}

	public void setGraphFileName(String graphFileName) {
		this.graphFileName = graphFileName;
	}

	public String getGraphContentType() {
		return graphContentType;
	}

	public void setGraphContentType(String graphContentType) {
		this.graphContentType = graphContentType;
	}

	public File getShadow_graph() {
		return shadow_graph;
	}

	public void setShadow_graph(File shadow_graph) {
		this.shadow_graph = shadow_graph;
	}

	public String getShadow_graphFileName() {
		return shadow_graphFileName;
	}

	public void setShadow_graphFileName(String shadow_graphFileName) {
		this.shadow_graphFileName = shadow_graphFileName;
	}

	public String getShadow_graphContentType() {
		return shadow_graphContentType;
	}

	public void setShadow_graphContentType(String shadow_graphContentType) {
		this.shadow_graphContentType = shadow_graphContentType;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
