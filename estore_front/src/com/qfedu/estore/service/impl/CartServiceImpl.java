package com.qfedu.estore.service.impl;

import java.util.List;

import com.qfedu.estore.dao.CartDAO;
import com.qfedu.estore.dao.GoodsDAO;
import com.qfedu.estore.entity.Cart;
import com.qfedu.estore.service.CartService;
import com.qfedu.estore.utils.BeanFactory;

public class CartServiceImpl implements CartService {
	CartDAO dao = BeanFactory.getBean(CartDAO.class);
	GoodsDAO goodsDao = BeanFactory.getBean(GoodsDAO.class);
	
	public List<Cart> query(Cart cart) {
		return dao.query(cart);
	}
	
	public void add(Cart cart) {
		// 1、查询购买记录
		List<Cart> cList = dao.query(cart);
		
		// 没有购买记录，新增
		if ( cList == null || cList.size() == 0 ) {
			dao.add(cart);
		}
		// 有购买记录，修改购买数量
		else {
			int oldNum = cList.get(0).getBuynum();
			int newNum = oldNum + cart.getBuynum();
			
			cart.setBuynum(newNum);
			
			dao.update(cart);
		}

	}

	@Override
	public List<Cart> queryWithGoods(Cart cart) {
		return dao.queryWithGoods(cart);
	}

	@Override
	public int goodsNum(String uid) {
		return dao.goodsNum(uid);
	}

	@Override
	public void changeNum(Cart cart) {
		dao.changeNum(cart);
	}

	@Override
	public void del(Integer uid, int gid) {
		dao.del(uid, gid);
	}

}
