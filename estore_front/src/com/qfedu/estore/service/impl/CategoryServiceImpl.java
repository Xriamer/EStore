package com.qfedu.estore.service.impl;

import java.util.List;

import com.qfedu.estore.dao.CategoryDAO;
import com.qfedu.estore.entity.Category;
import com.qfedu.estore.service.CategoryService;
import com.qfedu.estore.utils.BeanFactory;

public class CategoryServiceImpl implements CategoryService {
	CategoryDAO dao = BeanFactory.getBean(CategoryDAO.class);
	public List<Category> query() {
		return dao.query();
	}
}
