package com.hengda.linhai.m.app;

import android.os.Environment;

import java.io.File;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/5/27 17:19
 * 邮箱：tailyou@163.com
 * 描述：全局常量
 */
public class HdConstant {

    //    App更新相关常量
    public static String APP_KEY = "0100eb8d832b60315e49c927956ac87a";
    public static String APP_SECRET = "ac1ffe3576bfcde2060e60d0f2488182";
    public static String APP_UPDATE_URL = "http://101.200.234.14/APPCloud/";

    public static String getDefaultFileDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/HD_SSBWG_RES/";
    }

    public static final String LANG_DEFAULT = "CHINESE";
    //    SharedPrf配置文件名称
    public static final String SHARED_PREF_NAME = "HD_GXT_PREF";
    //    数据库文件名称
    public static final String DB_FILE_NAME = "Filemanage.s3db";
    public static final String DEFAULT_IP_PORT = "192.168.10.158";

    public static final int LOADING_DB = 11;
    public static final int LOADING_RES = 12;
    //    请求成功状态码
    public static final String HTTP_STATUS_SUCCEED = "000";
    //请求状态
    public static final String HTTP_STATE = "1";
    //    默认设备号
    public static final String DEFAULT_DEVICE_NO = "AND10000000000";
}
