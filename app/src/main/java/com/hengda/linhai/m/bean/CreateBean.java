package com.hengda.linhai.m.bean;

/**
 * Created by lenovo on 2017/4/26.
 */

public class CreateBean {

    /**
     * status : 1
     * data : {"group_id":"316"}
     * msg : 注册成功
     */

    private int status;
    /**
     * group_id : 316
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
        private String group_id;

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }
    }
}
