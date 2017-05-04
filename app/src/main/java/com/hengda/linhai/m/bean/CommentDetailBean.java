package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/5/3.
 */

public class CommentDetailBean {

    /**
     * status : 1
     * data : [{"content":"明明","time":"2017.05.03 11:46:52","nick_name":"你猜","head_img":"http://192.168.10.158/lhbwg/data/upload/avatar/20170503/59094817dba80.jpg"},{"content":"","time":"2017.05.03 11:42:11","nick_name":"你猜","head_img":"http://192.168.10.158/lhbwg/data/upload/avatar/20170503/59094817dba80.jpg"},{"content":"Eveghejdjjdjd","time":"2017.05.03 11:39:09","nick_name":"你猜","head_img":"http://192.168.10.158/lhbwg/data/upload/avatar/20170503/59094817dba80.jpg"},{"content":"Bihari hdhdjhdjd ","time":"2017.05.03 11:38:45","nick_name":"你猜","head_img":"http://192.168.10.158/lhbwg/data/upload/avatar/20170503/59094817dba80.jpg"},{"content":"Fghhjj\n","time":"2017.05.03 10:59:36","nick_name":"你猜","head_img":"http://192.168.10.158/lhbwg/data/upload/avatar/20170503/59094817dba80.jpg"},{"content":"Fghhjj","time":"2017.05.03 10:59:33","nick_name":"你猜","head_img":"http://192.168.10.158/lhbwg/data/upload/avatar/20170503/59094817dba80.jpg"},{"content":"你好","time":"2017.04.28 10:35:10","nick_name":"你猜","head_img":"http://192.168.10.158/lhbwg/data/upload/avatar/20170503/59094817dba80.jpg"},{"content":"好","time":"2017.04.27 19:21:39","nick_name":"昵称：李系","head_img":""},{"content":"good","time":"2017.04.27 15:55:29","nick_name":"哈哈","head_img":""},{"content":"还可以","time":"2017.04.27 15:30:55","nick_name":"昵称：李系","head_img":""},{"content":"","time":"2017.04.27 15:24:14","nick_name":"昵称：李系","head_img":""},{"content":"fdfdfd","time":"2017.04.27 14:56:33","nick_name":"昵称：李系","head_img":""},{"content":"很好","time":"2017.04.27 11:48:56","nick_name":"昵称：李系","head_img":""},{"content":"eqweqweqwe","time":"2017.04.26 17:44:20","nick_name":"昵称：李系","head_img":""}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * content : 明明
     * time : 2017.05.03 11:46:52
     * nick_name : 你猜
     * head_img : http://192.168.10.158/lhbwg/data/upload/avatar/20170503/59094817dba80.jpg
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
        private String content;
        private String time;
        private String nick_name;
        private String head_img;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }
    }
}
