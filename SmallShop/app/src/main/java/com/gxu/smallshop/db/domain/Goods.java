package com.gxu.smallshop.db.domain;

import java.io.Serializable;

public class Goods implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String g_num;
    private String g_name;
    private String g_img;
    private String g_price;
    private String g_introduce;
    private String g_type;
    private String g_count;
    private String g_time;


    public String getG_count() {
        return g_count;
    }
    public void setG_count(String g_count) {
        this.g_count = g_count;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getG_num() {
        return g_num;
    }
    public void setG_num(String g_num) {
        this.g_num = g_num;
    }
    public String getG_name() {
        return g_name;
    }
    public void setG_name(String g_name) {
        this.g_name = g_name;
    }
    public String getG_img() {
        return g_img;
    }
    public void setG_img(String g_img) {
        this.g_img = g_img;
    }
    public String getG_price() {
        return g_price;
    }
    public void setG_price(String g_price) {
        this.g_price = g_price;
    }
    public String getG_introduce() {
        return g_introduce;
    }
    public void setG_introduce(String g_introduce) {
        this.g_introduce = g_introduce;
    }
    public String getG_type() {
        return g_type;
    }
    public void setG_type(String g_type) {
        this.g_type = g_type;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getG_time() {
        return g_time;
    }
    public void setG_time(String g_time) {
        this.g_time = g_time;
    }
    @Override
    public String toString() {
        return "Goods [id=" + id + ", g_num=" + g_num + ", g_name=" + g_name
                + ", g_img=" + g_img + ", g_price=" + g_price
                + ", g_introduce=" + g_introduce + ", g_type=" + g_type
                + ", g_count=" + g_count + ", g_time=" + g_time + "]";
    }
    public Goods(int id, String g_num, String g_name, String g_img,
                 String g_price, String g_introduce, String g_type, String g_count,
                 String g_time) {
        super();
        this.id = id;
        this.g_num = g_num;
        this.g_name = g_name;
        this.g_img = g_img;
        this.g_price = g_price;
        this.g_introduce = g_introduce;
        this.g_type = g_type;
        this.g_count = g_count;
        this.g_time = g_time;
    }
    public Goods() {
        super();
    }
}
