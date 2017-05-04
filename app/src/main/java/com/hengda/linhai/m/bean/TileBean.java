package com.hengda.linhai.m.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/5/3.
 */

public class TileBean {

    /**
     * status : 1
     * data : {"exhibitroom_info":{"exhibitroom_id":"0001","exhibitroom_name":"展厅1","svg_map":"http://192.168.10.158/lhbwg/resource/exhibitroom/0001/map","route_url":"http://192.168.10.158/lhbwg/resource/floormap/2/2.jpg","width":"1800","height":"3276"},"exhibit_info":[{"exhibit_id":"0001","floor_num":"2","axis_x":"546","axis_y":"448","blueth_id":"1","poi_img":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_poi.png","type":1,"title":"321312"}]}
     * msg : 操作成功
     */

    private int status;
    /**
     * exhibitroom_info : {"exhibitroom_id":"0001","exhibitroom_name":"展厅1","svg_map":"http://192.168.10.158/lhbwg/resource/exhibitroom/0001/map","route_url":"http://192.168.10.158/lhbwg/resource/floormap/2/2.jpg","width":"1800","height":"3276"}
     * exhibit_info : [{"exhibit_id":"0001","floor_num":"2","axis_x":"546","axis_y":"448","blueth_id":"1","poi_img":"http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_poi.png","type":1,"title":"321312"}]
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
        /**
         * exhibitroom_id : 0001
         * exhibitroom_name : 展厅1
         * svg_map : http://192.168.10.158/lhbwg/resource/exhibitroom/0001/map
         * route_url : http://192.168.10.158/lhbwg/resource/floormap/2/2.jpg
         * width : 1800
         * height : 3276
         */

        private ExhibitroomInfoBean exhibitroom_info;
        /**
         * exhibit_id : 0001
         * floor_num : 2
         * axis_x : 546
         * axis_y : 448
         * blueth_id : 1
         * poi_img : http://192.168.10.158/lhbwg/resource/exhibit/0001/images/0001_poi.png
         * type : 1
         * title : 321312
         */

        private List<ExhibitInfoBean> exhibit_info;

        public ExhibitroomInfoBean getExhibitroom_info() {
            return exhibitroom_info;
        }

        public void setExhibitroom_info(ExhibitroomInfoBean exhibitroom_info) {
            this.exhibitroom_info = exhibitroom_info;
        }

        public List<ExhibitInfoBean> getExhibit_info() {
            return exhibit_info;
        }

        public void setExhibit_info(List<ExhibitInfoBean> exhibit_info) {
            this.exhibit_info = exhibit_info;
        }

        public static class ExhibitroomInfoBean {
            private String exhibitroom_id;
            private String exhibitroom_name;
            private String svg_map;
            private String route_url;
            private String width;
            private String height;

            public String getExhibitroom_id() {
                return exhibitroom_id;
            }

            public void setExhibitroom_id(String exhibitroom_id) {
                this.exhibitroom_id = exhibitroom_id;
            }

            public String getExhibitroom_name() {
                return exhibitroom_name;
            }

            public void setExhibitroom_name(String exhibitroom_name) {
                this.exhibitroom_name = exhibitroom_name;
            }

            public String getSvg_map() {
                return svg_map;
            }

            public void setSvg_map(String svg_map) {
                this.svg_map = svg_map;
            }

            public String getRoute_url() {
                return route_url;
            }

            public void setRoute_url(String route_url) {
                this.route_url = route_url;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }

        public static class ExhibitInfoBean {
            private String exhibit_id;
            private String floor_num;
            private String axis_x;
            private String axis_y;
            private String blueth_id;
            private String poi_img;
            private int type;
            private String title;

            public String getExhibit_id() {
                return exhibit_id;
            }

            public void setExhibit_id(String exhibit_id) {
                this.exhibit_id = exhibit_id;
            }

            public String getFloor_num() {
                return floor_num;
            }

            public void setFloor_num(String floor_num) {
                this.floor_num = floor_num;
            }

            public String getAxis_x() {
                return axis_x;
            }

            public void setAxis_x(String axis_x) {
                this.axis_x = axis_x;
            }

            public String getAxis_y() {
                return axis_y;
            }

            public void setAxis_y(String axis_y) {
                this.axis_y = axis_y;
            }

            public String getBlueth_id() {
                return blueth_id;
            }

            public void setBlueth_id(String blueth_id) {
                this.blueth_id = blueth_id;
            }

            public String getPoi_img() {
                return poi_img;
            }

            public void setPoi_img(String poi_img) {
                this.poi_img = poi_img;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
