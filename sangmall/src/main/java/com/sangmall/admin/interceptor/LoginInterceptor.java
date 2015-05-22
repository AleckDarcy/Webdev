package com.sangmall.admin.interceptor;

import java.util.Map;  

import com.opensymphony.xwork2.ActionContext;  
import com.opensymphony.xwork2.ActionInvocation;  
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {
	@Override  
	public String intercept(ActionInvocation invocation) throws Exception {  
		ActionContext ctx = invocation.getInvocationContext();  
		Map session = ctx.getSession();  
		String user = (String) session.get("name");  
		if (user != null) {
			System.out.println("test");
			return invocation.invoke();
		}
		ctx.put("tip", "你还没有登录");
		return "login";
	}
}
