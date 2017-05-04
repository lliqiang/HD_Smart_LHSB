package com.hengda.linhai.m.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/4/25.
 */

public class JoinGroup implements Serializable{

    /**
     * status : 1
     * data : {"is_success":1,"info":{"group_member":[{"device_id":"AND0000000126","nick_name":"","head_img":"","exhibitroom_name":"位置未知","floor_num":"0"}],"group_id":"262"}}
     * msg : 操作成功
     */

    private int status;
    /**
     * is_success : 1
     * info : {"group_member":[{"device_id":"AND0000000126","nick_name":"","head_img":"","exhibitroom_name":"位置未知","floor_num":"0"}],"group_id":"262"}
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

    public static class DataBean implements Serializable{
        private int is_success;
        /**
         * group_member : [{"device_id":"AND0000000126","nick_name":"","head_img":"","exhibitroom_name":"位置未知","floor_num":"0"}]
         * group_id : 262
         */

        private InfoBean info;

        public int getIs_success() {
            return is_success;
        }

        public void setIs_success(int is_success) {
            this.is_success = is_success;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean implements Serializable{
            private String group_id;
            /**
             * device_id : AND0000000126
             * nick_name :
             * head_img :
             * exhibitroom_name : 位置未知
             * floor_num : 0
             */

            private List<GroupMemberBean> group_member;

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public List<GroupMemberBean> getGroup_member() {
                return group_member;
            }

            public void setGroup_member(List<GroupMemberBean> group_member) {
                this.group_member = group_member;
            }

            public static class GroupMemberBean implements Serializable{
                private String device_id;
                private String nick_name;
                private String head_img;
                private String exhibitroom_name;
                private String floor_num;

                public String getDevice_id() {
                    return device_id;
                }

                public void setDevice_id(String device_id) {
                    this.device_id = device_id;
                }

                public String getNick_name() {
                    return nick_name;
                }

                public void setNick_name(String nick_name) {
                    this.nick_name = nick_name;
                }

                public String getHead_img() {
                    return head_img;
                }

                public void setHead_img(String head_img) {
                    this.head_img = head_img;
                }

                public String getExhibitroom_name() {
                    return exhibitroom_name;
                }

                public void setExhibitroom_name(String exhibitroom_name) {
                    this.exhibitroom_name = exhibitroom_name;
                }

                public String getFloor_num() {
                    return floor_num;
                }

                public void setFloor_num(String floor_num) {
                    this.floor_num = floor_num;
                }
            }
        }
    }
}
