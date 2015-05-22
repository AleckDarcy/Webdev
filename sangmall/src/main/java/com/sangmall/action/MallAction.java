package com.sangmall.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sangmall.util.Constants;
import com.sangmall.utils.CartUtil;
import com.sangmall.utils.LoginUtil;

public class MallAction extends ActionSupport {
	private String render = "";
	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	public String about_us() {
		data.put("result", 0);
		data.put("title", "Sang Mall");
		data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
		data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
		data.put("login_username", LoginUtil.getLoginUsername(ActionContext.getContext().getSession()));
		data.put("message", "blah blah blah ...");
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}

	public String getRender() {
		return render;
	}

	public void setRender(String render) {
		this.render = render;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
}
