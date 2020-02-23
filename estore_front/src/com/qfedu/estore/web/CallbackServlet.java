package com.qfedu.estore.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qfedu.estore.entity.User;

@SuppressWarnings("all")
@WebServlet("/callback")
// http://ip:端口/estore_front/callback
// http://acrrtp.natappfree.cc/estore_front/callback
public class CallbackServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("ok");
	}
}
