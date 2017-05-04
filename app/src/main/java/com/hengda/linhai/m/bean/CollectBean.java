package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/27.
 */

public class CollectBean {


    /**
     * status : 1
     * data : [{"exhibit_id":"0002","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg","exhibitname":"321312","collect_time":"1970.01.01 / 08:00"},{"exhibit_id":"0009","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg","exhibitname":"321312","collect_time":"1970.01.01 / 08:00"},{"exhibit_id":"0006","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg","exhibitname":"321312","collect_time":"1970.01.01 / 08:00"}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * exhibit_id : 0002
     * list_img_path : http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg
     * exhibitname : 321312
     * collect_time : 1970.01.01 / 08:00
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
        private String list_img_path;
        private String exhibitname;
        private String collect_time;

        public String getExhibit_id() {
            return exhibit_id;
        }

        public void setExhibit_id(String exhibit_id) {
            this.exhibit_id = exhibit_id;
        }

        public String getList_img_path() {
            return list_img_path;
        }

        public void setList_img_path(String list_img_path) {
            this.list_img_path = list_img_path;
        }

        public String getExhibitname() {
            return exhibitname;
        }

        public void setExhibitname(String exhibitname) {
            this.exhibitname = exhibitname;
        }

        public String getCollect_time() {
            return collect_time;
        }

        public void setCollect_time(String collect_time) {
            this.collect_time = collect_time;
        }
    }
}
