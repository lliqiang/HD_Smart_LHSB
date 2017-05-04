package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/28.
 */

public class SearchBean {

    /**
     * status : 1
     * data : {"history_content":["1","0001"],"hot_search":["dasdasdasdasd","4234","11","0001","12"]}
     * msg : 操作成功
     */

    private int status;
    private DataBean data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<String> history_content;
        private List<String> hot_search;

        public List<String> getHistory_content() {
            return history_content;
        }

        public void setHistory_content(List<String> history_content) {
            this.history_content = history_content;
        }

        public List<String> getHot_search() {
            return hot_search;
        }

        public void setHot_search(List<String> hot_search) {
            this.hot_search = hot_search;
        }
    }
}
