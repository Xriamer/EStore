package com.qfedu.estore.dao;

import com.qfedu.estore.entity.User;

public interface UserDAO {

	boolean checkExists(String tel);

	void register(User user);

	User login(String telephone, String password);

}
