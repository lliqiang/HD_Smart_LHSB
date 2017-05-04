package com.hengda.linhai.m.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/4/28.
 */

public class SearchContent implements Serializable{

    /**
     * status : 1
     * data : [{"title":"321312","exhibit_id":"31231","list_img_path":""},{"title":"321312","exhibit_id":"0002","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0003","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0004","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0005","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0006","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0007","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0008","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0009","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0010","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0001","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0001","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0002","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0002","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"},{"title":"321312","exhibit_id":"0002","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg"}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * title : 321312
     * exhibit_id : 31231
     * list_img_path :
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

    public static class DataBean implements Serializable{
        private String title;
        private String exhibit_id;
        private String list_img_path;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

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
    }
}
