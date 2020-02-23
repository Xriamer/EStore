package com.qfedu.estore.service.impl;

import com.qfedu.estore.dao.UserDAO;
import com.qfedu.estore.entity.User;
import com.qfedu.estore.service.UserService;
import com.qfedu.estore.utils.BeanFactory;
import com.qfedu.estore.utils.MD5Utils;

public class UserServiceImpl implements UserService {
	UserDAO dao = BeanFactory.getBean(UserDAO.class);
	public boolean checkExists(String tel) {
		return dao.checkExists(tel);
	}
	
	public void register(User user) {
		boolean exists = dao.checkExists(user.getTelephone());
		if ( !exists ) {
			// MD5是一种不可逆的加密算法：只能从明文得到密文，但是不能从密文解密成明文
			String pwd = MD5Utils.str2MD5(user.getPassword());
			user.setPassword(pwd);
			dao.register(user);
		}
	}

	public User login(String telephone, String password) {
		password = MD5Utils.str2MD5(password);
		return dao.login(telephone, password);
	}

}
