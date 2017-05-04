package com.hengda.linhai.m.http;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/4/24.
 */

public class ListBean implements Serializable {


    /**
     * status : 1
     * data : [{"exihibit_id":"0001","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg","title":null},{"exihibit_id":"0003","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg","title":null},{"exihibit_id":"0006","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg","title":null},{"exihibit_id":"0009","list_img_path":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg","title":null}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * exihibit_id : 0001
     * list_img_path : http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_list.jpg
     * title : null
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
        private String exihibit_id;
        private String list_img_path;
        private Object title;

        public String getExihibit_id() {
            return exihibit_id;
        }

        public void setExihibit_id(String exihibit_id) {
            this.exihibit_id = exihibit_id;
        }

        public String getList_img_path() {
            return list_img_path;
        }

        public void setList_img_path(String list_img_path) {
            this.list_img_path = list_img_path;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }
    }
}
