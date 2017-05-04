package com.hengda.linhai.m.bean;

/**
 * Created by lenovo on 2017/4/25.
 */

public class RegisterBean {

    /**
     * status : 1
     * data : {"isSuccess":1}
     * msg :
     */

    private int status;
    /**
     * isSuccess : 1
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
        private int isSuccess;

        public int getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(int isSuccess) {
            this.isSuccess = isSuccess;
        }
    }
}
