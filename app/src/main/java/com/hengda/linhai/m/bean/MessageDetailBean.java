package com.hengda.linhai.m.bean;

/**
 * Created by lenovo on 2017/5/3.
 */

public class MessageDetailBean {

    /**
     * status : 1
     * data : {"url":"http://192.168.10.158/lhbwg/resource/exhibit_zixun/157/CHINESE/157.html"}
     * msg : 操作成功
     */

    private int status;
    /**
     * url : http://192.168.10.158/lhbwg/resource/exhibit_zixun/157/CHINESE/157.html
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
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
