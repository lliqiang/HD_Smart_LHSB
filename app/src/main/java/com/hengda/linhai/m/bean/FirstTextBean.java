package com.hengda.linhai.m.bean;

/**
 * Created by free46000 on 2017/3/19.
 */

public class FirstTextBean {
    private String text;

    public FirstTextBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
