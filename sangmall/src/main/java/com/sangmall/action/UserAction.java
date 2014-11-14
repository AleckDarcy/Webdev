package com.sangmall.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	private int page = 0;
	private int limit = 10;
	private int id;
	private String username;
	private String password;
	private int privilege;

	@Resource
	private ApplicationContext applicationContext;
	
	Map data = new HashMap();
	
	public String login() {
//		TemplateDao templateDao = (TemplateDao)applicationContext.getBean("templateDao");
//		User user = new User();
//		user.setUsername(username);
//		user.setPassword(password);
//		user.setPrivilege(privilege);
//		Object result = templateDao.selectModel(user);
		
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		List result = jt.queryForList("select * from users where username=? and password=? and privilege=?", new Object[]{username, password, privilege});
		System.out.println("================================================");
		System.out.println(result);
		System.out.println("================================================");
		if(result.size() == 0) {
			data.put("result", -1);
			data.put("error_code", "Everything is ruined!");
		} else if(result.size() == 1){
			data.put("result", 0);
			data.put("privilege", privilege);
			data.put("error_code", "OK");
		} else {
			data.put("result", -2);
			data.put("error_code", "Internal error!");
		}
		return "success";
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

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
}
