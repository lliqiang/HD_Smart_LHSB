package com.hengda.linhai.m.bean;

/**
 * Created by lenovo on 2017/5/3.
 */

public class TransBean {

    /**
     * status : 1
     * data : {"html_path":"http://192.168.10.158/lhbwg/resource/travel/158/CHINESE/158.html"}
     * msg : 操作成功
     */

    private int status;
    /**
     * html_path : http://192.168.10.158/lhbwg/resource/travel/158/CHINESE/158.html
     */

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
        private String html_path;

        public String getHtml_path() {
            return html_path;
        }

        public void setHtml_path(String html_path) {
            this.html_path = html_path;
        }
    }
}
