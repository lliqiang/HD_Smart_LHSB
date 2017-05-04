package com.hengda.linhai.m.bean;

/**
 * Created by lenovo on 2017/4/25.
 */

public class ExhibitListBean {

    /**
     * exhibitroom_id : 0001
     * list_img_path : http://192.168.10.158/lhbwg/resource/exhibitroom/0001/images/0001.jpg
     * exhibitroom_name : null
     */

    private String exhibitroom_id;
    private String list_img_path;
    private Object exhibitroom_name;

    public String getExhibitroom_id() {
        return exhibitroom_id;
    }

    public void setExhibitroom_id(String exhibitroom_id) {
        this.exhibitroom_id = exhibitroom_id;
    }

    public String getList_img_path() {
        return list_img_path;
    }

    public void setList_img_path(String list_img_path) {
        this.list_img_path = list_img_path;
    }

    public Object getExhibitroom_name() {
        return exhibitroom_name;
    }

    public void setExhibitroom_name(Object exhibitroom_name) {
        this.exhibitroom_name = exhibitroom_name;
    }
}
