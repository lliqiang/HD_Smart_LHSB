package com.hengda.linhai.m.http;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/6/11 16:47
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class Response<T> {
    private String status;
    private String msg;
    private T data;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
