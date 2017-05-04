package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/26.
 */

public class DetailBean {

    /**
     * status : 1
     * data : {"exhibit_id":"0001","title":"321312","mp3_path":"","sub_title":"测试展品1","img":["http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001.jpg","http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_1.jpg"],"like_num":"425","comment_num":"0","little_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_little.png","html_path":"","is_like":1}
     * msg : 操作成功
     */

    private int status;
    /**
     * exhibit_id : 0001
     * title : 321312
     * mp3_path :
     * sub_title : 测试展品1
     * img : ["http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001.jpg","http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_1.jpg"]
     * like_num : 425
     * comment_num : 0
     * little_img_path : http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_little.png
     * html_path :
     * is_like : 1
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
        private String exhibit_id;
        private String title;
        private String mp3_path;
        private String sub_title;
        private String like_num;
        private String comment_num;
        private String little_img_path;
        private String html_path;
        private int is_like;
        private List<String> img;

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

        public String getMp3_path() {
            return mp3_path;
        }

        public void setMp3_path(String mp3_path) {
            this.mp3_path = mp3_path;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getLittle_img_path() {
            return little_img_path;
        }

        public void setLittle_img_path(String little_img_path) {
            this.little_img_path = little_img_path;
        }

        public String getHtml_path() {
            return html_path;
        }

        public void setHtml_path(String html_path) {
            this.html_path = html_path;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }

    @Override
    public String toString() {
        return "DetailBean{" +
                "status=" + status +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
