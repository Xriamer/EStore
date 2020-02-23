package com.qfedu.estore.entity;

public class Cart {
	private Integer uid;
	private Integer gid;
	private Integer buynum;
	
	/*
	 * 购物项和商品的关系？
	 * 购物项 ===> 商品      1:1
	 * 商品 ====> 购物项  1:n
	 * 当前是购物项对象，因此可以在该对象中添加商品对象的引用
	 */
	private Goods goods;
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getBuynum() {
		return buynum;
	}

	public void setBuynum(Integer buynum) {
		this.buynum = buynum;
	}

	@Override
	public String toString() {
		return "Cart [uid=" + uid + ", gid=" + gid + ", buynum=" + buynum
				+ ", goods=" + goods + "]";
	}

}
