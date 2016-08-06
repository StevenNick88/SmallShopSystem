package com.gxu.smallshop.db.domain;

import java.io.Serializable;

public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String log_num;
    private String log_content;
    private String user_num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLog_num() {
		return log_num;
	}
	public void setLog_num(String log_num) {
		this.log_num = log_num;
	}
	public String getLog_content() {
		return log_content;
	}
	public void setLog_content(String log_content) {
		this.log_content = log_content;
	}
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", log_num=" + log_num + ", log_content="
				+ log_content + ", user_num=" + user_num + "]";
	}
	public Log(int id, String log_num, String log_content, String user_num) {
		super();
		this.id = id;
		this.log_num = log_num;
		this.log_content = log_content;
		this.user_num = user_num;
	}
	public Log() {
		super();
	}
    
    
}
