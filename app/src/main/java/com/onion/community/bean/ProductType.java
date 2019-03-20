package com.onion.community.bean;

import java.io.Serializable;

/**
 * created by zhangqi on 2018/12/24
 */
public class ProductType implements Serializable {

    private int src;
    private String name;

    private Class clazz;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
