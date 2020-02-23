package com.qfedu.estore.entity;

import java.util.Date;
import java.util.List;

public class Orders {
	private String id; // 订单编号
	private Integer uid;
	private Double totalprice;
	private String address;
	private Integer status; // 1:待付款 2：已付款 3：已过期
	private Date createtime;

	// 订单 -- 订单明细 1：n 因此可以在订单对象中添加订单明细的集合
	private List<OrdersItem> list;

	public List<OrdersItem> getList() {
		return list;
	}

	public void setList(List<OrdersItem> list) {
		this.list = list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", uid=" + uid + ", totalprice=" + totalprice + ", address=" + address + ", status="
				+ status + ", createtime=" + createtime + ", list=" + list + "]";
	}

	public Orders() {
		super();
	}

	public Orders(String id, Integer uid, Double totalprice, String address, Integer status, Date createtime) {
		super();
		this.id = id;
		this.uid = uid;
		this.totalprice = totalprice;
		this.address = address;
		this.status = status;
		this.createtime = createtime;
	}

}
