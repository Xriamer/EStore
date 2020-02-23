package com.qfedu.estore.utils;

import java.util.ResourceBundle;

@SuppressWarnings("all")
public class BeanFactory {
	// 读取接口的实现类配置文件
	private static final ResourceBundle rb = ResourceBundle.getBundle("beans");

	public static <T> T getBean(Class<T> clazz) {
		try {
			// 获取接口名称
			String interfaceName = clazz.getSimpleName();
			// 在配置文件中，key对应接口名称，值对应接口的实现类全名称(包名+类名)
			String classPath = rb.getString(interfaceName);
			// 创建实现类的实例
			T t = (T) Class.forName(classPath).newInstance();
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
