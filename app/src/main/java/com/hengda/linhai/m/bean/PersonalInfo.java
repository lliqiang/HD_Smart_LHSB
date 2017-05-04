package com.hengda.linhai.m.bean;

/**
 * Created by lenovo on 2017/4/27.
 */

public class PersonalInfo {

    /**
     * status : 1
     * data : {"nick_name":"nickname","avatar":"","sex":"0","birthday":null,"province":"未知"}
     * msg : 操作成功
     */

    private int status;
    /**
     * nick_name : nickname
     * avatar :
     * sex : 0
     * birthday : null
     * province : 未知
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
        private String nick_name;
        private String avatar;
        private String sex;
        private Object birthday;
        private String province;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "nick_name='" + nick_name + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", sex='" + sex + '\'' +
                    ", birthday=" + birthday +
                    ", province='" + province + '\'' +
                    '}';
        }
    }
}
