package com.qfedu.estore.dao;

import java.util.List;

import com.qfedu.estore.entity.Orders;
import com.qfedu.estore.entity.OrdersItem;

public interface OrdersDAO {

	void addOrders(Orders orders);

	void addOrdersItem(List<OrdersItem> items);

	List<Orders> query(Integer id);

	void cancel(String oid, Integer uid);

	Orders get(String oid, Integer uid);

	List<OrdersItem> getItems(String oid);

	void changeStatus(String oid, Integer uid, int status);

}
