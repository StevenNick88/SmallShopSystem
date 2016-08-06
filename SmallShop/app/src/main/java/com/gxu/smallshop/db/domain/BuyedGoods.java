package com.gxu.smallshop.db.domain;

import java.io.Serializable;

public class BuyedGoods implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String buyer_num;
	private String goods_num;
	private String goods_name;
	private String buy_time;
	private String goods_img;
	//折扣率
	private String goods_rebate;
	private String buy_price;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBuyer_num() {
		return buyer_num;
	}
	public void setBuyer_num(String buyer_num) {
		this.buyer_num = buyer_num;
	}
	public String getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getBuy_time() {
		return buy_time;
	}
	public void setBuy_time(String buy_time) {
		this.buy_time = buy_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getGoods_img() {
		return goods_img;
	}
	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}
	public String getGoods_rebate() {
		return goods_rebate;
	}
	public void setGoods_rebate(String goods_rebate) {
		this.goods_rebate = goods_rebate;
	}
	public String getBuy_price() {
		return buy_price;
	}
	public void setBuy_price(String buy_price) {
		this.buy_price = buy_price;
	}
	public BuyedGoods(int id, String buyer_num, String goods_num,
					  String goods_name, String buy_time, String goods_img,
					  String goods_rebate, String buy_price) {
		super();
		this.id = id;
		this.buyer_num = buyer_num;
		this.goods_num = goods_num;
		this.goods_name = goods_name;
		this.buy_time = buy_time;
		this.goods_img = goods_img;
		this.goods_rebate = goods_rebate;
		this.buy_price = buy_price;
	}
	@Override
	public String toString() {
		return "BuyedGoods [id=" + id + ", buyer_num=" + buyer_num
				+ ", goods_num=" + goods_num + ", goods_name=" + goods_name
				+ ", buy_time=" + buy_time + ", goods_img=" + goods_img
				+ ", goods_rebate=" + goods_rebate + ", buy_price=" + buy_price
				+ "]";
	}
	public BuyedGoods() {
		super();
	}



}
