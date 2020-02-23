package com.qfedu.estore.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.qfedu.estore.entity.Goods;
import com.qfedu.estore.service.GoodsService;
import com.qfedu.estore.utils.BeanFactory;

@SuppressWarnings("all")
@WebServlet("/goods")
public class GoodsServlet extends BaseServlet {
	GoodsService service = BeanFactory.getBean(GoodsService.class);
	
	public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Goods goods = new Goods();
		
		try {
			BeanUtils.populate(goods, req.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Goods> gList = service.query(goods);
		// 将数据保存到request中，在页面中要进行数据的获取
		req.setAttribute("gList", gList);
		// 只有点击商品详情才会传递target参数
		String target = req.getParameter("target");
		if ( "detail".equals(target) ) {
			req.getRequestDispatcher("/goods_detail.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/goods.jsp").forward(req, resp);
		}
	}
}
