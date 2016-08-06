package com.jdbc.dbutils.domain;

import java.io.Serializable;


public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;
	private int a_id;
	private String a_num;
	private String a_name;
	private String a_pwd;
	private String a_introduce;
	private String a_level;
	private String a_rebate;
	
	public int getA_id() {
		return a_id;
	}
	public void setA_id(int a_id) {
		this.a_id = a_id;
	}
	public String getA_num() {
		return a_num;
	}
	public void setA_num(String a_num) {
		this.a_num = a_num;
	}
	public String getA_name() {
		return a_name;
	}
	public void setA_name(String a_name) {
		this.a_name = a_name;
	}
	public String getA_pwd() {
		return a_pwd;
	}
	public void setA_pwd(String a_pwd) {
		this.a_pwd = a_pwd;
	}
	public String getA_introduce() {
		return a_introduce;
	}
	public void setA_introduce(String a_introduce) {
		this.a_introduce = a_introduce;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getA_level() {
		return a_level;
	}
	public void setA_level(String a_level) {
		this.a_level = a_level;
	}
	public String getA_rebate() {
		return a_rebate;
	}
	public void setA_rebate(String a_rebate) {
		this.a_rebate = a_rebate;
	}
	@Override
	public String toString() {
		return "Agent [a_id=" + a_id + ", a_num=" + a_num + ", a_name="
				+ a_name + ", a_pwd=" + a_pwd + ", a_introduce=" + a_introduce
				+ ", a_level=" + a_level + ", a_rebate=" + a_rebate + "]";
	}
	public Agent(int a_id, String a_num, String a_name, String a_pwd,
			String a_introduce, String a_level, String a_rebate) {
		super();
		this.a_id = a_id;
		this.a_num = a_num;
		this.a_name = a_name;
		this.a_pwd = a_pwd;
		this.a_introduce = a_introduce;
		this.a_level = a_level;
		this.a_rebate = a_rebate;
	}
	public Agent() {
		super();
	}
	
	

	
}
