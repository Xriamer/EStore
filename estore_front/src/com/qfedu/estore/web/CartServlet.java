package com.qfedu.estore.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qfedu.estore.entity.Cart;
import com.qfedu.estore.entity.User;
import com.qfedu.estore.service.CartService;
import com.qfedu.estore.utils.BeanFactory;

@SuppressWarnings("all")
@WebServlet("/cart")
public class CartServlet extends BaseServlet {
	CartService service = BeanFactory.getBean(CartService.class);
	public void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = isLogin(req, resp);
				
		if ( user != null ) {
			int gid = Integer.parseInt(req.getParameter("gid"));
			Integer uid = user.getId();
			
			service.del(uid, gid);
			//resp.sendRedirect(req.getContextPath() + "/cart?method=query");
		} else {
			resp.getWriter().write("nologin");
		}
	}
	
	public void changeNum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = isLogin(req, resp);
				
		if ( user != null ) {
			int buynum = Integer.parseInt(req.getParameter("buynum"));
			int gid = Integer.parseInt(req.getParameter("gid"));
			Integer uid = user.getId();
			
			Cart cart = new Cart();
			cart.setUid(uid);
			cart.setGid(gid);
			cart.setBuynum(buynum);
			
			service.changeNum(cart);
			//resp.sendRedirect(req.getContextPath() + "/cart?method=query");
		} else {
			resp.getWriter().write("nologin");
		}
		
		
	}
	
	public void goodsNum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int goodsNum = service.goodsNum(req.getParameter("uid"));
		resp.getWriter().write(goodsNum+"");
	}
	
	
	public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1、判断是否登录
		User user = (User)req.getSession().getAttribute("loginUser");
		if ( user == null ) {
			// 登录成功之后，跳转到之前所在的页面
			
			// 从哪个页面跳转过来的
			String referer = req.getHeader("Referer");
			if ( referer != null ) {
				referer = URLEncoder.encode(referer, "UTF-8");
				resp.sendRedirect(req.getContextPath() + "/login.jsp?redirect=" + referer);
			} else {
				resp.sendRedirect(req.getContextPath() + "/login.jsp");
			}
			
			return ;
		}
		Cart cart = new Cart();
		cart.setUid(user.getId());
		List<Cart> cList = service.queryWithGoods(cart);
		req.setAttribute("cList", cList);
		req.getRequestDispatcher("/cart.jsp").forward(req, resp);
	}
	
	public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1、判断是否登录
		User user = (User)req.getSession().getAttribute("loginUser");
		if ( user == null ) {
			// 从哪个页面跳转过来的
			String referer = req.getHeader("Referer");
			System.out.println(referer);
			// 对地址进行编码：一定要编码
			referer = URLEncoder.encode(referer, "UTF-8");
			// req.getContextPath()  /estore_front
			resp.sendRedirect(req.getContextPath() + "/login.jsp?redirect=" + referer);
			return ;
		}
		
		// 2、获取商品id和购买数量
		String gid = req.getParameter("gid");
		String buynum = req.getParameter("buynum");
		Integer uid = user.getId();
		
		Cart cart = new Cart();
		cart.setGid(Integer.parseInt(gid));
		cart.setUid(uid);
		cart.setBuynum(Integer.parseInt(buynum));
		
		// 3、调用业务层
		service.add(cart);
		
		// 4、跳转到中间页
		resp.sendRedirect(req.getContextPath() + "/buyorcart.jsp");
	}
}
