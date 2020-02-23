package com.qfedu.estore.service;

import java.util.List;

import com.qfedu.estore.entity.Orders;

public interface OrdersService {

	void addOrders(Integer uid, String address);

	List<Orders> query(Integer id);

	void cancel(String oid, Integer uid);

	Orders get(String oid, Integer uid);

	void changeStatus(String oid, Integer uid, int pay);

}
