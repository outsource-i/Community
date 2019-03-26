package com.onion.community.bean;

import java.io.Serializable;


public class HttpWrapper<T> implements Serializable {

    /**
     * code 响应码
     */
    public int code;

    /**
     * info 对于的响应信息
     */
    public String info;

    /**
     * 返回的数据类型 不确定
     */
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
