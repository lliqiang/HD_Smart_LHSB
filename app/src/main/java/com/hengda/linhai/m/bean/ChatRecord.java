package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/28.
 */

public class ChatRecord {

    /**
     * status : 1
     * data : {"self_img":"","opposite_img":"","chat_content":[{"from":1,"content":"fdfdf","time":"17:42:26"}]}
     * msg : 操作成功
     */

    private int status;
    /**
     * self_img :
     * opposite_img :
     * chat_content : [{"from":1,"content":"fdfdf","time":"17:42:26"}]
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
        private String self_img;
        private String opposite_img;
        /**
         * from : 1
         * content : fdfdf
         * time : 17:42:26
         */

        private List<ChatContentBean> chat_content;

        public String getSelf_img() {
            return self_img;
        }

        public void setSelf_img(String self_img) {
            this.self_img = self_img;
        }

        public String getOpposite_img() {
            return opposite_img;
        }

        public void setOpposite_img(String opposite_img) {
            this.opposite_img = opposite_img;
        }

        public List<ChatContentBean> getChat_content() {
            return chat_content;
        }

        public void setChat_content(List<ChatContentBean> chat_content) {
            this.chat_content = chat_content;
        }

        public static class ChatContentBean {
            private int from;
            private String content;
            private String time;

            public int getFrom() {
                return from;
            }

            public void setFrom(int from) {
                this.from = from;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
