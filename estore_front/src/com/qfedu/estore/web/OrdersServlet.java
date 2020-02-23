package com.qfedu.estore.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;

import com.github.wxpay.sdk.WXPay;
import com.google.gson.Gson;
import com.qfedu.estore.constants.Constants;
import com.qfedu.estore.entity.Cart;
import com.qfedu.estore.entity.Orders;
import com.qfedu.estore.entity.User;
import com.qfedu.estore.service.CartService;
import com.qfedu.estore.service.OrdersService;
import com.qfedu.estore.utils.BeanFactory;
import com.qfedu.estore.utils.JDBCUtils;
import com.qfedu.estore.utils.JedisUtils;

import redis.clients.jedis.Jedis;

@SuppressWarnings("all")
@WebServlet("/orders")
public class OrdersServlet extends BaseServlet {
	CartService cartService = BeanFactory.getBean(CartService.class);
	OrdersService service = BeanFactory.getBean(OrdersService.class);
	public void getStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 判断是否登录
		User user = isLogin(req, resp);
		if ( user != null ) {
	        WXPay wxpay = null;
			try {
				wxpay = new WXPay();
				Map<String, String> data = new HashMap<String, String>();
		        String oid = req.getParameter("id");
		        data.put("out_trade_no", oid);
		        Map<String, String> result = wxpay.orderQuery(data);
		        // 获取交易状态
		        String trade_state = result.get("trade_state");
		        if ( "SUCCESS".equalsIgnoreCase(trade_state) ) {
		        	// 修改订单状态为已支付
		        	Integer uid = user.getId();
		        	service.changeStatus(oid, uid, Constants.STATUS_ORDER.PAY);
		        }
	            resp.getWriter().write(trade_state);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			resp.getWriter().write("nologin");
		}
	}
	
	public void getPayUrl(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 判断是否登录
		User user = isLogin(req, resp);
		if ( user != null ) {
			// 订单id
			String id = req.getParameter("id");
	        try {
	        	Orders orders = service.get(id, user.getId());
	        	// 计算价格总共多少分
	        	Double totalprice = orders.getTotalprice() * 100;
	        	String total_fee = totalprice.intValue() + "";
	        	
				WXPay wxpay = new WXPay();
				Map<String, String> data = new HashMap<String, String>();
		        data.put("body", "千锋商城-扫码支付中心");
		        data.put("out_trade_no", id);
		        //data.put("device_info", "");
		        //data.put("fee_type", "CNY");
		        data.put("total_fee", total_fee);
		        data.put("spbill_create_ip", "127.0.0.1");
		        data.put("notify_url", "http://www.example.com/wxpay/notify");
		        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
		        //data.put("product_id", "12");
		        
		        Map<String, String> result = wxpay.unifiedOrder(data);
		        String code_url = result.get("code_url");
	            resp.getWriter().write(code_url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 判断是否登录
		User user = isLogin(req, resp);
		if ( user != null ) {
			// 订单号
			String oid = req.getParameter("id");
			Integer uid = user.getId();
			// 为了保证安全性，在对用户数据进行操作时，需要把用户的id也作为sql的条件
			Orders orders = service.get(oid, uid);
			req.setAttribute("orders", orders);
			req.getRequestDispatcher("/orders_detail.jsp").forward(req, resp);
		}
	}
	
	public void cancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 判断是否登录
		User user = isLogin(req, resp);
		if ( user != null ) {
			// 订单号
			String oid = req.getParameter("id");
			Integer uid = user.getId();
			// 为了保证安全性，在对用户数据进行操作时，需要把用户的id也作为sql的条件
			service.cancel(oid, uid);
		}
	}
	
	public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 判断是否登录
		User user = isLogin(req, resp);
		if ( user != null ) {
			List<Orders> oList = service.query(user.getId());
			req.setAttribute("oList", oList);
			req.getRequestDispatcher("/orders.jsp").forward(req, resp);
		}
	}
	
	public void addOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 判断是否登录
		User user = isLogin(req, resp);
		if ( user != null ) {
			
			// 获取收货地址信息
			String pp = req.getParameter("pp");
			String cc = req.getParameter("cc");
			String dd = req.getParameter("dd");
			String detailAddress = req.getParameter("detailAddress");
			String zipcode = req.getParameter("zipcode");
			String name = req.getParameter("name");
			String tel = req.getParameter("tel");
			
			String address = pp + cc + dd + " " + detailAddress + " 收件人：" + name + " 手机号：" + tel;
			Integer uid = user.getId();
			
			service.addOrders(uid, address);
			
			// 跳转到查询订单列表页面
			resp.sendRedirect(req.getContextPath() + "/orders?method=query");
		}
	}
	
	public void loadSSX(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 将省市县的数据保存到redis缓存中，请求地址作为key
			// /estore_front/orders?method=loadSSX&pid=-1
			String key = req.getRequestURI() + "?" + req.getQueryString();
			// 从redis中获取区域信息
			Jedis j = JedisUtils.getJedis();
			String json = j.get(key);
			if ( StringUtils.isBlank(json) ) {
				String pid = req.getParameter("pid");
				QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
				String sql = "select * from `province_city_district` where pid=?";
				List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), pid);
				Gson gson = new Gson();
				json = gson.toJson(listMap);
				
				j.set(key, json);
			}
			
			resp.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}
	
	public void toOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = isLogin(req, resp);
				
		if ( user != null ) {
			Integer uid = user.getId();
			
			Cart cart = new Cart();
			cart.setUid(uid);
			
			List<Cart> cList = cartService.queryWithGoods(cart);
			req.setAttribute("cList", cList);
			req.getRequestDispatcher("/orders_submit.jsp").forward(req, resp);
		}
	}
}
