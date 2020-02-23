package com.qfedu.estore.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qfedu.estore.entity.Category;
import com.qfedu.estore.service.CategoryService;
import com.qfedu.estore.utils.BeanFactory;
import com.qfedu.estore.utils.JedisUtils;

import redis.clients.jedis.Jedis;

@SuppressWarnings("all")
@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
	CategoryService service = BeanFactory.getBean(CategoryService.class);
	
	public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// JedisUtils工具类，获取Jedis对象
		Jedis jedis = JedisUtils.getJedis();
		String json = jedis.get("category");
		if ( json == null ) {
			List<Category> cList = service.query();
			Gson gson = new Gson();
			json = gson.toJson(cList);
			jedis.set("category", json);
		}
		JedisUtils.closeJedis(jedis);
		// 将结果返回到客户端
		resp.getWriter().write(json);
		
		/*
		第一次实现：
		List<Category> cList = service.query();
		// 将java中的集合或者是对象转换为json字符串
		Gson gson = new Gson();
		// "[{id:11, name: 北京},{id: 12, name: 天津}]"
		String json = gson.toJson(cList);
		resp.getWriter().write(json);
		*/
	}
	
	public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
