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
import com.sangmall.utils.CartUtil;
import com.sangmall.utils.LoginUtil;

public class UserAction extends ActionSupport {
	private int page = 0;
	private int limit = 10;
	private int id;
	private String username;
	private String password;
	private String render = "";
	private String redirect = "";
	@Resource
	private ApplicationContext applicationContext;
	
	Map data = new HashMap();
	JSONObject json;

	public String login() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		List result = jt.queryForList("select * from users where username=? and password=?", new Object[]{username, password});
		System.out.println("================================================");
		System.out.println(result);
		System.out.println("================================================");
		if(result.size() == 0) {
			data.put("result", -1);
			data.put("error_code", "Everything is ruined!");
		} else if(result.size() == 1){
			LoginUtil.login(username, ActionContext.getContext().getSession());
			data.put("result", 0);
			data.put("error_code", "OK");
			data.put("redirect", redirect);
		} else {
			data.put("result", -2);
			data.put("error_code", "Internal error!");
		}
		data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {	
			return "json";
		} else {
			return "success";
		}
	}
	
	public String logout() {
		LoginUtil.logout(ActionContext.getContext().getSession());
		data.put("result", 0);
		data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
		data.put("login_username", "");
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {	
			return "json";
		} else {
			return "success";
		}
	}
	
	public String register() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		int user = jt.update("insert into users (username, password) values (?, ?)", new Object[]{username, password});
		System.out.println("================================================");
		System.out.println(user);
		System.out.println("================================================");
		if(user == 0) {
			data.put("result", -1);
			data.put("error_code", "Everything is ruined!");
		} else if(user == 1){
			LoginUtil.login(username, ActionContext.getContext().getSession());
			data.put("result", 0);
			data.put("error_code", "OK");
			data.put("redirect", redirect);
			data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
		} else {
			data.put("result", -2);
			data.put("error_code", "Internal error!");
		}
		json = JSONObject.fromObject(data);
		if(render.equals("json")) {
			return "json";
		} else {
			return "success";
		}
	}
	
	public String home() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		String username = LoginUtil.getLoginUsername(ActionContext.getContext().getSession());
		if(username.length() == 0) {
			data.put("result", -1);
			data.put("redirect", "User_home.do");
			json = JSONObject.fromObject(data);
			return "login";
		}
		Map result = jt.queryForMap("select * from users where username=?", new Object[]{username});
		if(result == null) {
			data.put("result", -1);
			data.put("redirect", "User_home.do");
			json = JSONObject.fromObject(data);
			return "login";
		} else {
			int user_id = Integer.parseInt(result.get("id") + "");
			List orders = jt.queryForList("select * from orders where user_id=? order by create_time desc", new Object[]{user_id});
			data.put("result", 0);
			data.put("orders", orders);
			data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
			data.put("login_username", LoginUtil.getLoginUsername(ActionContext.getContext().getSession()));
			json = JSONObject.fromObject(data);
			return "success";
		}
	}
	
	public String check() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		List result = jt.queryForList("select * from users where username=?", new Object[]{username});
		if(username == null || username.length() == 0) {
			data.put("result", -2);
		} else if(result.size() == 0) {
			data.put("result", 0);
		} else {
			data.put("result", -1);
		}
		json = JSONObject.fromObject(data);
		return "json";
	}
	
	public Map getData() {
		return data;
	}
	
	public void setData(Map data) {
		this.data = data;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRender(String render) {
		this.render = render;
	}

	public String getRender() {
		return render;
	}
	
	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getRedirect() {
		return redirect;
	}
}
