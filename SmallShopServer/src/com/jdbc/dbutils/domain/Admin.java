package com.jdbc.dbutils.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/2/9.
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    private int m_id;
    private String m_num;
    private String m_name;
    private String m_pwd;
    private String m_remark;
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getM_num() {
		return m_num;
	}
	public void setM_num(String m_num) {
		this.m_num = m_num;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_pwd() {
		return m_pwd;
	}
	public void setM_pwd(String m_pwd) {
		this.m_pwd = m_pwd;
	}
	public String getM_remark() {
		return m_remark;
	}
	public void setM_remark(String m_remark) {
		this.m_remark = m_remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Admin(int m_id, String m_num, String m_name, String m_pwd,
			String m_remark) {
		super();
		this.m_id = m_id;
		this.m_num = m_num;
		this.m_name = m_name;
		this.m_pwd = m_pwd;
		this.m_remark = m_remark;
	}
	public Admin() {
		super();
	}
	@Override
	public String toString() {
		return "Admin [m_id=" + m_id + ", m_num=" + m_num + ", m_name="
				+ m_name + ", m_pwd=" + m_pwd + ", m_remark=" + m_remark + "]";
	}
    
	
	
}