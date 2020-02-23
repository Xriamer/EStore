package com.qfedu.estore.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.qfedu.estore.constants.Constants;
import com.qfedu.estore.dao.OrdersDAO;
import com.qfedu.estore.entity.Orders;
import com.qfedu.estore.entity.OrdersItem;
import com.qfedu.estore.utils.JDBCUtils;

public class OrdersDAOImpl implements OrdersDAO {

	public void addOrders(Orders orders) {
		try {
			//QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			// 后期开发中，所有的非查询操作，都不可以指定数据源，因为每个更新操作，都可能是事务的一部分
			QueryRunner qr = new QueryRunner();
			String sql = "insert into orders values(?,?,?,?,?,?)";
			qr.update(JDBCUtils.getConnection(), sql, orders.getId(), orders.getUid(), orders.getTotalprice(),
					orders.getAddress(), orders.getStatus(), orders.getCreatetime());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	public void addOrdersItem(List<OrdersItem> items) {
		try {
			QueryRunner qr = new QueryRunner();
			String sql = "insert into ordersitem values(?,?,?)";
			/*for (OrdersItem item : items) {
				qr.update(JDBCUtils.getConnection(), sql, item.getOid(), item.getGid(), item.getBuynum());
			}*/
			
			/*
			 * 批量操作，需要接收一个二维数组，其中：
			 * 	高维(1维)：操作次数，一般是集合的size
			 * 	低维(2维)：参数个数，即?的个数
			 * 
			 */
			Object[][] params = new Object[items.size()][3];
			
			// 初始化二维数组数据
			for (int i = 0; i < params.length; i++) {
				params[i][0] = items.get(i).getOid();
				params[i][1] = items.get(i).getGid();
				params[i][2] = items.get(i).getBuynum();
			}
			
			qr.batch(JDBCUtils.getConnection(), sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	@Override
	public List<Orders> query(Integer id) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from orders where uid=? order by createtime desc";
			return qr.query(sql, new BeanListHandler<Orders>(Orders.class), id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	public void cancel(String oid, Integer uid) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "update orders set status=? where id=? and uid=?";
			qr.update(sql, Constants.STATUS_ORDER.CANCEL, oid, uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	@Override
	public Orders get(String oid, Integer uid) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from orders where id=? and uid=?";
			return qr.query(sql, new BeanHandler<Orders>(Orders.class), oid, uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	@Override
	public List<OrdersItem> getItems(String oid) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from ordersitem where oid=?";
			return qr.query(sql, new BeanListHandler<OrdersItem>(OrdersItem.class), oid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	public void changeStatus(String oid, Integer uid, int status) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "update orders set status=? where id=? and uid=?";
			qr.update(sql, status, oid, uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}
}
