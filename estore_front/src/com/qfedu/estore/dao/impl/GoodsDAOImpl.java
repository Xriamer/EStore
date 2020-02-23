package com.qfedu.estore.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;

import com.qfedu.estore.dao.GoodsDAO;
import com.qfedu.estore.entity.Goods;
import com.qfedu.estore.utils.JDBCUtils;

public class GoodsDAOImpl implements GoodsDAO {
	// 通用查询操作
	public List<Goods> query(Goods goods) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from goods where 1=1";
			
			List<Object> list = new ArrayList<>();
			if ( goods.getId() != null ) {
				sql += " and id=? ";
				list.add(goods.getId());
			}
			
			if ( goods.getCid() != null ) {
				sql += " and cid=? ";
				list.add(goods.getCid());
			}
			
			//if ( goods.getName() != null && !"".equals(goods.getName()) )
			if ( StringUtils.isNotBlank(goods.getName()) ) {
				sql += " and name like ? ";
				list.add("%" + goods.getName() + "%");
			}
			
			//...
			Object[] params = list.toArray();
			return qr.query(sql, new BeanListHandler<Goods>(Goods.class), params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

}
