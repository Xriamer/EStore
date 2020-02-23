package com.qfedu.estore.service;

import java.util.List;

import com.qfedu.estore.entity.Cart;

public interface CartService {

	List<Cart> query(Cart cart);
	
	void add(Cart cart);

	List<Cart> queryWithGoods(Cart cart);

	int goodsNum(String uid);

	void changeNum(Cart cart);

	void del(Integer uid, int gid);

}
