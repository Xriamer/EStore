package com.qfedu.estore.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qfedu.estore.constants.Constants;
import com.qfedu.estore.dao.CartDAO;
import com.qfedu.estore.dao.GoodsDAO;
import com.qfedu.estore.dao.OrdersDAO;
import com.qfedu.estore.entity.Cart;
import com.qfedu.estore.entity.Goods;
import com.qfedu.estore.entity.Orders;
import com.qfedu.estore.entity.OrdersItem;
import com.qfedu.estore.service.OrdersService;
import com.qfedu.estore.utils.BeanFactory;
import com.qfedu.estore.utils.JDBCUtils;
import com.qfedu.estore.utils.UUIDUtils;

public class OrdersServiceImpl implements OrdersService {
	OrdersDAO dao = BeanFactory.getBean(OrdersDAO.class);
	CartDAO cartDAO = BeanFactory.getBean(CartDAO.class);
	GoodsDAO goodsDAO = BeanFactory.getBean(GoodsDAO.class);
	
	public void addOrders(Integer uid, String address) {
		// 根据已有信息，构造出订单对象
		Orders orders = new Orders();
		orders.setUid(uid);
		orders.setAddress(address);
		
		// 生成随机32位订单编号
		String id = UUIDUtils.getUUID();
		orders.setId(id);
		
		// 计算总金额
		// 查询购物车信息，包含商品信息
		Cart cart = new Cart();
		cart.setUid(uid);
		List<Cart> cList = cartDAO.queryWithGoods(cart);
		
		// 计算总金额，并构建订单明细数据
		double price = 0; // 总金额
		// 订单明细
		List<OrdersItem> items = new ArrayList<>();
		// 在循环中做2件事：  计算总金额、构造订单明细数据
		for (Cart c : cList) {
			price += c.getGoods().getEstoreprice() * c.getBuynum();
			OrdersItem item = new OrdersItem(id, c.getGid(), c.getBuynum());
			items.add(item);
		}
		orders.setTotalprice(price);
		
		// 订单状态：1待付款  2已付款 3已过期   4已取消
		orders.setStatus(Constants.STATUS_ORDER.UNPAY);
		orders.setCreatetime(new Date());
		
		// 事务处理
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 开启事务
			conn.setAutoCommit(false);
			
			// 添加订单数据
			dao.addOrders(orders);
			// 添加订单明细
			dao.addOrdersItem(items);
			// 清空购物车
			cartDAO.clear(uid);
			
		}
		// 在进行数据库操作时，发生异常
		catch (Exception e) {
			e.printStackTrace();
			try {
				// 事务的回滚
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				// 提交事务
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public List<Orders> query(Integer id) {
		
		return dao.query(id);
	}
	@Override
	public void cancel(String oid, Integer uid) {
		dao.cancel(oid, uid);
		
	}
	public Orders get(String oid, Integer uid) {
		// 查询订单
		Orders orders = dao.get(oid, uid);
		
		// 查询订单明细List
		List<OrdersItem> list = dao.getItems(oid);
		orders.setList(list);
		
		// 遍历List,查询商品信息
		Goods goods = new Goods();
		for (OrdersItem item : list) {
			goods.setId(item.getGid());
			Goods goods2 = goodsDAO.query(goods).get(0);
			item.setGoods(goods2);
		}
		
		return orders;
	}
	
	public void changeStatus(String oid, Integer uid, int status) {
		dao.changeStatus(oid, uid, status);
	}
}
