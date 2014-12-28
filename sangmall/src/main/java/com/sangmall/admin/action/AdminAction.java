package com.sangmall.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sangmall.util.Constants;

public class AdminAction extends ActionSupport {
	private String render = "";
	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	public String login() {
		data.put("result", 0);
		data.put("err_code", 0);
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}
	
	public String home() {
		data.put("result", 0);
		data.put("title", "Sang Mall");
		data.put("message", "wawawawawawawawa");
		data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
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
}
