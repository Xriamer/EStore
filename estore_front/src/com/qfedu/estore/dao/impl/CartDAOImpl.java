package com.qfedu.estore.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.qfedu.estore.dao.CartDAO;
import com.qfedu.estore.entity.Cart;
import com.qfedu.estore.entity.Goods;
import com.qfedu.estore.utils.JDBCUtils;

public class CartDAOImpl implements CartDAO {

	public List<Cart> query(Cart cart) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from cart where 1=1";
			
			List<Object> list = new ArrayList<>();
			if ( cart.getUid() != null ) {
				sql += " and uid=? ";
				list.add(cart.getUid());
			}
			
			if ( cart.getGid() != null ) {
				sql += " and gid=? ";
				list.add(cart.getGid());
			}
			
			return qr.query(sql, new BeanListHandler<Cart>(Cart.class), list.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	public void add(Cart cart) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "insert into cart values(?,?,?)";
			qr.update(sql, cart.getGid(), cart.getUid(), cart.getBuynum());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	public void update(Cart cart) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "update cart set buynum=? where uid=? and gid=?";
			qr.update(sql, cart.getBuynum(), cart.getUid(), cart.getGid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
		
	}

	@Override
	public int goodsNum(String uid) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select sum(buynum) from cart where uid=?";
			BigDecimal bd = qr.query(sql, new ScalarHandler<BigDecimal>(), uid);
			return bd == null ? 0 : bd.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	public void changeNum(Cart cart) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "update cart set buynum=? where uid=? and gid=?";
			qr.update(sql, cart.getBuynum(), cart.getUid(), cart.getGid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	public void del(Integer uid, int gid) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "delete from cart where uid=? and gid=?";
			qr.update(sql, uid, gid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

	@Override
	public List<Cart> queryWithGoods(Cart cart) {
		// 查询不包含商品信息的购物车数据
		List<Cart> cList = this.query(cart);
		// 查询商品信息
		for (Cart c : cList) {
			try {
				QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
				String sql = "select * from goods where id=?";
				Goods goods = qr.query(sql, new BeanHandler<Goods>(Goods.class), c.getGid());
				c.setGoods(goods);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("...");
			}
		}
		return cList;
	}

	public void clear(Integer uid) {
		try {
			QueryRunner qr = new QueryRunner();
			String sql = "delete from cart where uid=?";
			qr.update(JDBCUtils.getConnection(), sql, uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}
}
