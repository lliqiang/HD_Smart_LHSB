package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/5/3.
 */

public class NotifyBean {

    /**
     * status : 1
     * data : [{"imgs_path":"http://192.168.10.158/lhbwg/resource/travel/158/images/158.jpg","travel_id":"158","title":"313123","desc":" 123123123123  ","time":"2017-04-26","is_read":1}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * imgs_path : http://192.168.10.158/lhbwg/resource/travel/158/images/158.jpg
     * travel_id : 158
     * title : 313123
     * desc :  123123123123
     * time : 2017-04-26
     * is_read : 1
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
        private String travel_id;
        private String title;
        private String desc;
        private String time;
        private int is_read;

        public String getImgs_path() {
            return imgs_path;
        }

        public void setImgs_path(String imgs_path) {
            this.imgs_path = imgs_path;
        }

        public String getTravel_id() {
            return travel_id;
        }

        public void setTravel_id(String travel_id) {
            this.travel_id = travel_id;
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

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }
    }
}
