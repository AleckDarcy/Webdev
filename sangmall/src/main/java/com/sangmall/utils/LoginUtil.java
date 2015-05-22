package com.sangmall.utils;

import java.util.Map;

public class LoginUtil {
	public static void login(String username, Map session) {
		session.put("username", username);
	}
	
	public static void logout(Map session) {
		session.remove("username");
	}
	
	public static String getLoginUsername(Map session) {
		if(session.containsKey("username")) {
			return session.get("username") + "";
		} else {
			return "";
		}
	}
}
