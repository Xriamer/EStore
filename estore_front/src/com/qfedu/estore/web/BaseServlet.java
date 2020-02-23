package com.qfedu.estore.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qfedu.estore.entity.User;

@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {
	
	public User isLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = (User)req.getSession().getAttribute("loginUser");
		// 如果是ajax请求，则请求头中会携带X-Requested-With，值为XMLHttpRequest
		String xrw = req.getHeader("X-Requested-With");
		
		if ( user == null && xrw == null ) {
			// 登录成功之后，跳转到之前所在的页面
			// 从哪个页面跳转过来的
			String referer = req.getHeader("Referer");
			if ( referer != null ) {
				referer = URLEncoder.encode(referer, "UTF-8");
				resp.sendRedirect(req.getContextPath() + "/login.jsp?redirect=" + referer);
			} else {
				resp.sendRedirect(req.getContextPath() + "/login.jsp");
			}
			
		}
		return user;
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// servlet的路径?method=serlvet中的方法名
		// user?method=login
		String methodName = request.getParameter("method");
		Class clazz = this.getClass();
		try {
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, 
					HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
