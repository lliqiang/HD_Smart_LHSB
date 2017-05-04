package com.hengda.linhai.m.app;


import android.text.TextUtils;

import com.hengda.linhai.m.tools.FileUtils;
import com.hengda.linhai.m.tools.SDCardUtil;
import com.hengda.linhai.m.tools.SharedPrefUtil;

import java.io.File;

public class HdAppConfig {
    public static final String LANGUAGE = "LANGUAGE";//语种
    public static final String IP_PORT = "IP_PORT";
    public static final String AUTO_PLAY = "AUTO_PLAY";
    public static final String LOGIN = "LOGIN";
    public static final String NICKNAME = "NICKNAME";
    public static final String GROPNO = "GROPNO";
    public static final String PUSH_TYPE = "PUSH_TYPE";//用户类型
    private static SharedPrefUtil appConfigShare = new SharedPrefUtil(HdApplication.mContext,
            HdConstant.SHARED_PREF_NAME);

    public static void setLanguage(String language) {
        appConfigShare.setPrefString(LANGUAGE, language);
    }

    public static String getLanguage() {
        return appConfigShare.getPrefString(LANGUAGE, HdConstant.LANG_DEFAULT);
    }

    //    获取默认文件存储目录
    public static String getDefaultFileDir() {
        return SDCardUtil.getSDCardPath() + "HD_SSBWG_RES/";
    }

    //获取地图图片根路径
    public static String getMapFilePath(String language, int unitno) {
        return getDefaultFileDir() + "map/" + language + "/" + unitno;
    }

    //获取展品图片根路径
    public static String getImgPath(String exhibit_id) {
        return getDefaultFileDir() + "exhibit/" + exhibit_id + "/" + "images/";
    }

    //    获取数据库文件路径
    public static String getDbFilePath() {
        return getDefaultFileDir() + HdConstant.DB_FILE_NAME;
    }

    //获取展品根路径
    public static String getFilePath(String exhibit_id) {
        return getDefaultFileDir() + "exhibit/" + exhibit_id + "/" + getLanguage() + "/";
    }

    //设置默认IP
    public static void setDefaultIpPort(String ipPort) {
        appConfigShare.setPrefString(IP_PORT, ipPort);
    }

    public static String getDefaultIpPort() {
        return appConfigShare.getPrefString(IP_PORT, HdConstant.DEFAULT_IP_PORT);
    }

    //自动讲解
    public static void setAutoPlay(boolean flag) {
        appConfigShare.setPrefBoolean(AUTO_PLAY, flag);
    }

    public static boolean getLogin() {
        return appConfigShare.getPrefBoolean(LOGIN, false);
    }
    //登录状态
    public static void setLogin(boolean flag) {
        appConfigShare.setPrefBoolean(LOGIN, flag);
    }

    public static boolean getAutoPlay() {
        return appConfigShare.getPrefBoolean(AUTO_PLAY, false);
    }

    public static void setDeviceNo(String deviceNo) {
        FileUtils.writeStringToFile(SDCardUtil.getSDCardPath()+"/HD_LHSB/"+ "DeviceNo.txt", deviceNo, false);
    }
    public static String getDeviceNo() {
        StringBuilder deviceNo = FileUtils.readStringFromFile(SDCardUtil.getSDCardPath()+HdAppConfig.getDefaultFileDir() + "DeviceNo.txt", "UTF-8");
        return TextUtils.isEmpty(deviceNo) ? HdConstant.DEFAULT_DEVICE_NO : deviceNo.toString();
    }

    //设置用户名
    public static void setNickName(String nickName) {
        appConfigShare.setPrefString(NICKNAME, nickName);
    }

    public static String getNickname() {
        return appConfigShare.getPrefString(NICKNAME,"nickname");
    }
    //设置群组号
    public static void setGroupNo(String groupNo) {
        appConfigShare.setPrefString(GROPNO,groupNo);
    }
    public static String getGroupNo() {
        return appConfigShare.getPrefString(GROPNO);
    }


    public static void setClientId(String userType) {
        appConfigShare.setPrefString(PUSH_TYPE, userType);
    }

    public static String getClientId() {
        return appConfigShare.getPrefString(PUSH_TYPE,"CLIENT");
    }
//判断设备号是否存在
    public static boolean isDeviceNo(){
        String path=SDCardUtil.getSDCardPath()+"/HD_LHSB/"+ "DeviceNo.txt";
        File file=new File(path);
        return file.exists();
    }
}
