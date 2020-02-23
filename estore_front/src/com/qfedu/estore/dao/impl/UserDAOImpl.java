package com.qfedu.estore.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.qfedu.estore.dao.UserDAO;
import com.qfedu.estore.entity.User;
import com.qfedu.estore.utils.JDBCUtils;

public class UserDAOImpl implements UserDAO {
	public boolean checkExists(String tel) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select count(*) from user where telephone=?";
			return qr.query(sql, new ScalarHandler<Long>(), tel) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}    

	public void register(User user) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			
			String sql = "insert into user values(null,?,?,?)";
			qr.update(sql, user.getPassword(), user.getNickname(), user.getTelephone());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}
	// DBUtils
	@Override
	public User login(String telephone, String password) {
		try {
			QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from user where telephone=? and password=?";
			return qr.query(sql, new BeanHandler<User>(User.class), telephone, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("...");
		}
	}

}
