package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/27.
 */

public class RecordBean {

    /**
     * status : 1
     * data : [{"exhibit_id":"0001","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg","exhibitname":null,"visit_time":"1970.01.01 / 08:00"}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * exhibit_id : 0001
     * list_img_path : http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg
     * exhibitname : null
     * visit_time : 1970.01.01 / 08:00
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
        private Object exhibitname;
        private String visit_time;

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

        public Object getExhibitname() {
            return exhibitname;
        }

        public void setExhibitname(Object exhibitname) {
            this.exhibitname = exhibitname;
        }

        public String getVisit_time() {
            return visit_time;
        }

        public void setVisit_time(String visit_time) {
            this.visit_time = visit_time;
        }
    }
}
