package com.gxu.smallshop.db.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private int u_id;
	private String u_num;
	private String u_name;
	private String u_pwd;
	private String u_email;
	private String u_phone;
	private String u_rebate;
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getU_num() {
		return u_num;
	}
	public void setU_num(String u_num) {
		this.u_num = u_num;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_pwd() {
		return u_pwd;
	}
	public void setU_pwd(String u_pwd) {
		this.u_pwd = u_pwd;
	}
	public String getU_email() {
		return u_email;
	}
	public void setU_email(String u_email) {
		this.u_email = u_email;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getU_rebate() {
		return u_rebate;
	}
	public void setU_rebate(String u_rebate) {
		this.u_rebate = u_rebate;
	}
	@Override
	public String toString() {
		return "User [u_id=" + u_id + ", u_num=" + u_num + ", u_name=" + u_name
				+ ", u_pwd=" + u_pwd + ", u_email=" + u_email + ", u_phone="
				+ u_phone + ", u_rebate=" + u_rebate + "]";
	}
	public User(int u_id, String u_num, String u_name, String u_pwd,
				String u_email, String u_phone, String u_rebate) {
		super();
		this.u_id = u_id;
		this.u_num = u_num;
		this.u_name = u_name;
		this.u_pwd = u_pwd;
		this.u_email = u_email;
		this.u_phone = u_phone;
		this.u_rebate = u_rebate;
	}
	public User() {
		super();
	}




}
