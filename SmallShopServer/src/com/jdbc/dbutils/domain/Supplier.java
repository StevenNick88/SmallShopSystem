package com.jdbc.dbutils.domain;

import java.io.Serializable;

public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    private int s_id;
    private String s_num;
	private String s_name;
	private String s_pwd;
	private String s_introduce;
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public String getS_num() {
		return s_num;
	}
	public void setS_num(String s_num) {
		this.s_num = s_num;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_pwd() {
		return s_pwd;
	}
	public void setS_pwd(String s_pwd) {
		this.s_pwd = s_pwd;
	}
	public String getS_introduce() {
		return s_introduce;
	}
	public void setS_introduce(String s_introduce) {
		this.s_introduce = s_introduce;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Supplier(int s_id, String s_num, String s_name, String s_pwd,
			String s_introduce) {
		super();
		this.s_id = s_id;
		this.s_num = s_num;
		this.s_name = s_name;
		this.s_pwd = s_pwd;
		this.s_introduce = s_introduce;
	}
	public Supplier() {
		super();
	}
	@Override
	public String toString() {
		return "Supplier [s_id=" + s_id + ", s_num=" + s_num + ", s_name="
				+ s_name + ", s_pwd=" + s_pwd + ", s_introduce=" + s_introduce
				+ "]";
	}
	
	
	
}
