package com.qfedu.estore.utils;

import java.io.IOException;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	private static JedisPool pool = null;
	static {
		Properties pro = new Properties();
		try {
			// 读取配置文件
			ClassLoader loader = JedisUtils.class.getClassLoader();
			pro.load(loader.getResourceAsStream("jedis.properties"));
			// 连接池配置对象
			JedisPoolConfig config = new JedisPoolConfig();
			// 最大连接数
			int maxTotal = Integer.parseInt(pro.getProperty("maxTotal"));
			// 最大空闲连接数
			int maxIdle = Integer.parseInt(pro.getProperty("maxIdle"));
			config.setMaxTotal(maxTotal);
			config.setMaxIdle(maxIdle);

			// 服务器域名或IP
			String host = pro.getProperty("host");
			// 服务器端口
			int port = Integer.parseInt(pro.getProperty("port"));
			pool = new JedisPool(config, host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 提供一个返回池子的方法
	public static JedisPool getPool() {
		return pool;
	}

	// 获得一个jedis资源方法
	public static Jedis getJedis() {
		return pool.getResource();
	}

	// 关闭的方法
	public static void closeJedis(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	// 整个应用程序关闭时，关闭连接池
	public static void closePool() {
		if (pool != null) {
			pool.close();
		}
	}
}