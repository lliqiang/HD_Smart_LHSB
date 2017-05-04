package com.hengda.linhai.m.bean;

/**
 * Created by lenovo on 2017/4/24.
 */

public class DeviceBean {

    /**
     * status : 1
     * data : {"device_id":"AND0000000028"}
     * msg : 操作成功
     */

    private int status;
    /**
     * device_id : AND0000000028
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
        private String device_id;

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }
    }
}
