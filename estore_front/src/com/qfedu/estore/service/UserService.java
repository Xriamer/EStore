package com.qfedu.estore.service;

import com.qfedu.estore.entity.User;

public interface UserService {

	boolean checkExists(String tel);

	void register(User user);

	User login(String telephone, String password);

}
