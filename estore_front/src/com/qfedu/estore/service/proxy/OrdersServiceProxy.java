package com.qfedu.estore.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import com.qfedu.estore.service.OrdersService;
import com.qfedu.estore.utils.BeanFactory;
import com.qfedu.estore.utils.JDBCUtils;

public class OrdersServiceProxy {
	private static OrdersService service = BeanFactory.getBean(OrdersService.class);
	
	public static OrdersService createOrdersServiceProxy() {
		return (OrdersService)Proxy.newProxyInstance(
				service.getClass().getClassLoader(), 
				service.getClass().getInterfaces(), 
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						String methodName = method.getName();
						Object returnValue = null;
						if ( methodName.startsWith("select")
								|| methodName.startsWith("query")
								|| methodName.startsWith("find")
								|| methodName.startsWith("get")) {
							// 性能统计相关的业务
							
							returnValue = method.invoke(service, args);
							
						}
						// 进行统一的事务管理
						else {
							/*
							 * 事务处理的细节：
							 * 1、手动开启事务
							 * 2、必须使用同一个连接对象来完成，因为提交及回滚事务都是通过连接对象来完成
							 * 3、在catch中回滚事务，并且尽可能保证catch顶级异常Exception
							 * 4、在finally中提交事务
							 */
							Connection conn = null;
							try {
								conn = JDBCUtils.getConnection();
								System.out.println(conn);
								conn.setAutoCommit(false);
								
								// 核心业务调用
								returnValue = method.invoke(service, args);
								
							} catch (Exception e) {
								e.printStackTrace();
								try {
									conn.rollback();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							} finally {
								try {
									conn.commit();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								/*try {
									// 关闭连接：实际上连接并没有真正关闭，而是还回连接池中。
									conn.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}*/
							}
						}
						
						return returnValue;
					}
				});
	}
}
