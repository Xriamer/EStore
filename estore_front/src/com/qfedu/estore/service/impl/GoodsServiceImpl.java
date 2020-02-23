package com.qfedu.estore.service.impl;

import java.util.List;

import com.qfedu.estore.dao.GoodsDAO;
import com.qfedu.estore.entity.Goods;
import com.qfedu.estore.service.GoodsService;
import com.qfedu.estore.utils.BeanFactory;

public class GoodsServiceImpl implements GoodsService {

	GoodsDAO dao = BeanFactory.getBean(GoodsDAO.class);
	
	public List<Goods> query(Goods goods) {
		return dao.query(goods);
	}

}
