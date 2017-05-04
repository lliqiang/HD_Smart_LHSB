package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/25.
 */

public class GroupBean {

    /**
     * status : 1
     * data : {"group_member":[],"group_id":null}
     * msg : 操作成功
     */

    private int status;
    /**
     * group_member : []
     * group_id : null
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
        private Object group_id;
        private List<?> group_member;

        public Object getGroup_id() {
            return group_id;
        }

        public void setGroup_id(Object group_id) {
            this.group_id = group_id;
        }

        public List<?> getGroup_member() {
            return group_member;
        }

        public void setGroup_member(List<?> group_member) {
            this.group_member = group_member;
        }
    }
}
