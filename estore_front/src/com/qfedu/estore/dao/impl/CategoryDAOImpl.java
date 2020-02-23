package com.qfedu.estore.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.qfedu.estore.dao.CategoryDAO;
import com.qfedu.estore.entity.Category;
import com.qfedu.estore.utils.JDBCUtils;

public class CategoryDAOImpl implements CategoryDAO {

	public List<Category> query() {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from category ";
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

}
