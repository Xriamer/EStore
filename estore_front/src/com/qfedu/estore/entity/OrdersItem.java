package com.qfedu.estore.entity;

public class OrdersItem {
	private String oid;
	private Integer gid;
	private Integer buynum;
	// 订单明细和商品之间是 1:1关系，因此可以在订单明细对象中添加商品对象
	private Goods goods;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public OrdersItem() {
		super();
	}

	public OrdersItem(String oid, Integer gid, Integer buynum) {
		super();
		this.oid = oid;
		this.gid = gid;
		this.buynum = buynum;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
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
		return "OrdersItem [oid=" + oid + ", gid=" + gid + ", buynum=" + buynum
				+ ", goods=" + goods + "]";
	}
}
