package com.sangmall.utils;

import java.util.HashMap;
import java.util.Map;

public class CartUtil {
	public static Map getCart(Map session) {
		if(!session.containsKey("cart")) {
			Map cart = new HashMap();
			cart.put("items", new HashMap<String, Map>());
			cart.put("total_price", 0.0);
			session.put("cart", cart);
		}
		return (Map)session.get("cart");
	}
	
	public static int putToCart(Map product, Map session) {
		Map cart = getCart(session);
		Map<String, Map> items = (Map<String, Map>)cart.get("items");
		Map item = product;
		int id = Integer.parseInt(product.get("id") + "");
		if(items.containsKey(id + "")) {
			item = (Map)items.get(id + "");
			item.put("count", Integer.parseInt(item.get("count") + "") + 1);
			items.put(id + "", item);
		} else {
			item.put("current_price", Integer.parseInt(item.get("price") + "") * Float.parseFloat(item.get("discount_rate") + ""));
			item.put("count", 1);
			if(items.toString().length() + item.toString().length() + (id + "").length() + 100 > 1000) {
				return -1;
			} else {
				items.put(id + "", item);
			}
		}
		cart.put("total_price", Float.parseFloat(cart.get("total_price") + "") + Float.parseFloat(item.get("current_price") + ""));
		return 0;
	}
	
	public static Map removeFromCart(int id, int count, Map session) {
		Map cart = getCart(session);
		Map<String, Map> items = (Map<String, Map>)cart.get("items");
		if(count == 0) {
			Map item = items.get(id + "");
			cart.put("total_price", Float.parseFloat(cart.get("total_price") + "") - Float.parseFloat(item.get("current_price") + "") * Integer.parseInt(item.get("count") + ""));
			items.remove(id + "");
		} else {
			Map item = items.get(id + "");
			System.err.println(Integer.parseInt(item.get("count") + ""));
			if(Integer.parseInt(item.get("count") + "") == 1) {
				items.remove(id + "");
			} else {
				item.put("count", Integer.parseInt(item.get("count") + "") - 1);
				items.put(id + "", item);
			}
			cart.put("total_price", Float.parseFloat(cart.get("total_price") + "") - Float.parseFloat(item.get("current_price") + ""));
		}
		return (Map)session.get("cart");
	}
	
	public static void clearCart(Map session) {
		if(session.containsKey("cart")) {
			session.remove("cart");
		}
	}
}
