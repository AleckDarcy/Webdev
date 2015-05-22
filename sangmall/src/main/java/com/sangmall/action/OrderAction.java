package com.sangmall.action;

import java.io.UnsupportedEncodingException;
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

@SuppressWarnings("serial")
public class OrderAction extends ActionSupport {
	private String render = "";
	private int id = 0;
	private String address = "";

	@Resource
	private ApplicationContext applicationContext;
	
	Map<String, Object> data = new HashMap<String, Object>();
	JSONObject json;
	
	public String create() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		Map session = ActionContext.getContext().getSession();
		if(session.get("username") == null) {
			data.put("redirect", "Cart_show.do");
			json = JSONObject.fromObject(data);
			return "login";
		} else {
			String username = session.get("username") + "";
			Map user_id = jt.queryForMap("select * from users where username=?", new Object[]{username});
			Map cart = CartUtil.getCart(session);
			try {
				address = new String(address.getBytes("ISO8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				address = "";
				e.printStackTrace();
			}
			System.out.println("address : " + address);
			int order = jt.update("insert into orders (user_id, items, price, address) values (?, ?, ?, ?)", new Object[]{Integer.parseInt(user_id.get("id") + ""), JSONObject.fromObject(cart.get("items")).toString(), Float.parseFloat(cart.get("total_price") + ""), address});
			CartUtil.clearCart(session);
			data.put("result", 0);
			data.put("order", order);
			data.put("cart", CartUtil.getCart(ActionContext.getContext().getSession()));
			data.put("image_server", Constants.SANG_MALL_IMAGE_SERVER);
			data.put("login_username", LoginUtil.getLoginUsername(ActionContext.getContext().getSession()));
			json = JSONObject.fromObject(data);
			return "success";
		}
	}

	public String canceled() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		int order = jt.update("update orders set status=-1 where id=? and status=0", new Object[]{id});
		data.put("result", order - 1);
		json = JSONObject.fromObject(data);
		return "json";
	}
	
	public String received() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		int order = jt.update("update orders set status=2 where id=? and status=1", new Object[]{id});
		data.put("result", order - 1);
		json = JSONObject.fromObject(data);
		return "json";
	}
	
	public String returned() {
		JdbcTemplate jt = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		int order = jt.update("update orders set status=3 where id=? and status=2", new Object[]{id});
		data.put("result", order - 1);
		json = JSONObject.fromObject(data);
		return "json";
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
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
