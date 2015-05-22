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

public class OrderAction extends ActionSupport {
	private int id = 0;
	private int status = -2;
	
	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	public String search() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(status == -2) {
			data.put("result", -1);
		} else {
			try {
				Map order = jt.queryForMap("select orders.*, users.username as user_name from orders, users where status=? and users.id=orders.user_id limit 1", new Object[]{status});
				data.put("result", 0);
				data.put("order", order);
			} catch (Exception e) {
				e.printStackTrace();
				data.put("result", -2);
				data.put("message", "没有符合条件的订单！");
			}
		}
		json = JSONObject.fromObject(data);
		return "json";
	}
	
	public String modify() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		if(id != 0 && status == 0) {
			try {
				data.put("order", jt.update("update orders set status=1 where id=? and status=0", new Object[]{id}));
				data.put("result", 0);
				data.put("message", "订单更改成功！");
			} catch (Exception e) {
				data.put("result", -1);
				data.put("message", "写入数据库失败！");
			}
		} else if(id != 0 && status == 3) {
			try {
				data.put("order", jt.update("update orders set status=4 where id=? and status=3", new Object[]{id}));
				data.put("result", 0);
				data.put("message", "订单更改成功！");
			} catch (Exception e) {
				data.put("result", -1);
				data.put("message", "写入数据库失败！");
			}
		} else {
			data.put("result", -2);
			data.put("message", "参数输入有误！");
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
