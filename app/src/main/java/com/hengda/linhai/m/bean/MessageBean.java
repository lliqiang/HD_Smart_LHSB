package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/25.
 */

public class MessageBean {


    /**
     * status : 1
     * data : [{"imgs_path":"http://192.168.10.158/lhbwg/resource/exhibit_zixun/146/images/146.png","zixun_id":"146","title":"234234","desc":" 4234234","time":"2017-04-21"},{"imgs_path":"http://192.168.10.158/lhbwg/resource/exhibit_zixun/147/images/147.png","zixun_id":"147","title":"4234234","desc":" 23423423423","time":"2017-04-21"}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * imgs_path : http://192.168.10.158/lhbwg/resource/exhibit_zixun/146/images/146.png
     * zixun_id : 146
     * title : 234234
     * desc :  4234234
     * time : 2017-04-21
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
        private String imgs_path;
        private String zixun_id;
        private String title;
        private String desc;
        private String time;

        public String getImgs_path() {
            return imgs_path;
        }

        public void setImgs_path(String imgs_path) {
            this.imgs_path = imgs_path;
        }

        public String getZixun_id() {
            return zixun_id;
        }

        public void setZixun_id(String zixun_id) {
            this.zixun_id = zixun_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
