package com.sangmall.admin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sangmall.util.Constants;

public class AdminAction extends ActionSupport {
	private String render = "";
	private String name = "";
	private String password = "";
	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	public String login() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		try {
			jt.queryForMap("select * from admins where name=? and password=?", new Object[]{name, password});
			Map session = (Map) ActionContext.getContext().getSession();
			session.put("name", name);
			data.put("result", 0);
			data.put("err_code", 0);
		} catch (EmptyResultDataAccessException e) {
			data.put("result", 0);
			data.put("err_code", -1);
		} finally {
			json = JSONObject.fromObject(data);
		}
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}
	
	public String home() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		
		data.put("result", 0);
		data.put("title", "Sang Mall");
		data.put("brands", jt.queryForList("select * from brands"));
		data.put("categories", jt.queryForList("select * from categories"));
		data.put("message", "欢迎登陆Sang Bear管理员后台！");
		data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "home";
		}
	}
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
