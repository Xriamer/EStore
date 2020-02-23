package com.qfedu.estore.entity;

import java.util.Date;

public class Goods {
	private Integer id;
	private Integer cid;
	private String name;
	private Double marketprice;
	private Double estoreprice;
	private Integer num;
	private String imgurl;
	private Integer ishot;
	private Integer status;
	private Date createtime;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(Double marketprice) {
		this.marketprice = marketprice;
	}

	public Double getEstoreprice() {
		return estoreprice;
	}

	public void setEstoreprice(Double estoreprice) {
		this.estoreprice = estoreprice;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Integer getIshot() {
		return ishot;
	}

	public void setIshot(Integer ishot) {
		this.ishot = ishot;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", cid=" + cid + ", name=" + name
				+ ", marketprice=" + marketprice + ", estoreprice="
				+ estoreprice + ", num=" + num + ", imgurl=" + imgurl
				+ ", ishot=" + ishot + ", status=" + status + ", createtime="
				+ createtime + ", description=" + description + "]";
	}

	public Goods() {
		super();
	}

	public Goods(Integer id, Integer cid, String name, Double marketprice,
			Double estoreprice, Integer num, String imgurl, Integer ishot,
			Integer status, Date createtime, String description) {
		super();
		this.id = id;
		this.cid = cid;
		this.name = name;
		this.marketprice = marketprice;
		this.estoreprice = estoreprice;
		this.num = num;
		this.imgurl = imgurl;
		this.ishot = ishot;
		this.status = status;
		this.createtime = createtime;
		this.description = description;
	}

}
