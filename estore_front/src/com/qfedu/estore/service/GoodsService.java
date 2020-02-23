package com.qfedu.estore.service;

import java.util.List;

import com.qfedu.estore.entity.Goods;

public interface GoodsService {

	List<Goods> query(Goods goods);

}
