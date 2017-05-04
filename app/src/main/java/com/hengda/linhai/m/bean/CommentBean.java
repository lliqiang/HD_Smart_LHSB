package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/27.
 */

public class CommentBean {


    /**
     * status : 1
     * data : [{"exhibit_id":"0001","title":"321312","comment_content":"很好","time":"2017.04.27 / 11:48"},{"exhibit_id":"0001","title":"321312","comment_content":"eqweqweqwe","time":"2017.04.26 / 17:44"}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * exhibit_id : 0001
     * title : 321312
     * comment_content : 很好
     * time : 2017.04.27 / 11:48
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String exhibit_id;
        private String title;
        private String comment_content;
        private String time;

        public String getExhibit_id() {
            return exhibit_id;
        }

        public void setExhibit_id(String exhibit_id) {
            this.exhibit_id = exhibit_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getComment_content() {
            return comment_content;
        }

        public void setComment_content(String comment_content) {
            this.comment_content = comment_content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
