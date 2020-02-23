package com.qfedu.estore.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	// DATA_SOURCE是存放数据库连接的容器
	private static final ComboPooledDataSource DATA_SOURCE = new ComboPooledDataSource();
	// ThreadLocal可以将其中的对象和当前线程相关，只要是同一个线程，获取的对象始终是同一个
	private static final ThreadLocal<Connection> TL = new ThreadLocal<Connection>();
	
	// 获取数据源
	public static DataSource getDataSource() {
		return DATA_SOURCE;
	}

	// 获取连接
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = TL.get();
			if (conn == null) {
				conn = DATA_SOURCE.getConnection();
				TL.set(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(conn);
		return conn;
	}
}
