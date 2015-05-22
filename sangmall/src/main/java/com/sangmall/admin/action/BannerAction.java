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

public class BannerAction extends ActionSupport {
	private String header = "";
	private String label = "";
	private int product_id = 0;

	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	public String add() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(header.equals("")) {
			data.put("result", -1);
			data.put("message", "缺少横幅名！");
		}
		else {
			try {
				int banner = jt.update("insert into banners (header, label, product_id) values (?, ?, ?)", new Object[]{header, label, product_id});
				data.put("result", 0);
				data.put("banner", banner);
				data.put("message", "横幅添加成功！");
			} catch (Exception e) {
				e.printStackTrace();
				data.put("result", -2);
				data.put("message", "横幅添加失败！");
			}
		}
		json = JSONObject.fromObject(data);
		return "json";
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
