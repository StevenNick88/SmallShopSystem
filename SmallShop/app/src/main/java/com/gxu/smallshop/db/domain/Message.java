package com.gxu.smallshop.db.domain;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String ms_num;
	private String ms_question;
	private String ms_answer;
	private String buyer_num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMs_num() {
		return ms_num;
	}
	public void setMs_num(String ms_num) {
		this.ms_num = ms_num;
	}
	public String getMs_question() {
		return ms_question;
	}
	public void setMs_question(String ms_question) {
		this.ms_question = ms_question;
	}
	public String getMs_answer() {
		return ms_answer;
	}
	public void setMs_answer(String ms_answer) {
		this.ms_answer = ms_answer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBuyer_num() {
		return buyer_num;
	}
	public void setBuyer_num(String buyer_num) {
		this.buyer_num = buyer_num;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", ms_num=" + ms_num + ", ms_question="
				+ ms_question + ", ms_answer=" + ms_answer + ", buyer_num="
				+ buyer_num + "]";
	}
	public Message(int id, String ms_num, String ms_question, String ms_answer,
				   String buyer_num) {
		super();
		this.id = id;
		this.ms_num = ms_num;
		this.ms_question = ms_question;
		this.ms_answer = ms_answer;
		this.buyer_num = buyer_num;
	}
	public Message() {
		super();
	}

}
