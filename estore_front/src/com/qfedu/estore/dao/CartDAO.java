package com.qfedu.estore.dao;

import java.util.List;

import com.qfedu.estore.entity.Cart;

public interface CartDAO {

	List<Cart> query(Cart cart);
	List<Cart> queryWithGoods(Cart cart);

	void add(Cart cart);

	void update(Cart cart);

	int goodsNum(String uid);

	void changeNum(Cart cart);

	void del(Integer uid, int gid);
	void clear(Integer uid);

}
