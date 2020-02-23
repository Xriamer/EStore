package com.qfedu.estore.dao;

import java.util.List;

import com.qfedu.estore.entity.Goods;

public interface GoodsDAO {

	List<Goods> query(Goods goods);

}
