package com.hengda.linhai.m.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hengda.linhai.m.app.HdAppConfig;


/**
 * 作者：Tailyou
 * 时间：2016/1/12 15:12
 * 邮箱：tailyou@163.com
 * 描述：资源数据库工具类-单例模式
 */
public class HResDdUtil {

    private SQLiteDatabase db = null;

    /**
     * 单例模式
     */
    private static volatile HResDdUtil instance = null;

    /**
     * 获取实例
     *
     * @return
     */
    public static HResDdUtil getInstance() {
        if (instance == null) {
            synchronized (HResDdUtil.class) {
                if (instance == null) {
                    instance = new HResDdUtil();
                }
            }
        }
        return instance;
    }


    /**
     * 私有构造方法
     */
    private HResDdUtil() {
    }


    /**
     * 打开指定文件路径对应的数据库
     *
     * @param dbFilePath
     */
    public void openDB(String dbFilePath) {
        closeDB();
        try {
            db = SQLiteDatabase.openDatabase(dbFilePath, null, SQLiteDatabase
                    .NO_LOCALIZED_COLLATORS);
        } catch (Exception e) {
            e.printStackTrace();
            closeDB();
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {
        if (db != null)
            db = null;
    }
    /*
       * 查询Map表
       *
       * */
    public Cursor QueryMapInfo(int langugage) {
        String sql = String.format("SELECT * FROM %s WHERE language = %d",
                "mapmodel", langugage);
        return queryBase(sql);
    }
    /**
     * 基础查询方法
     *
     * @param sql 输入SQL语句
     * @return 返回Cursor
     */
    private Cursor queryBase(String sql) {
        Cursor cursor = null;
        try {
            if (db == null)
                openDB(HdAppConfig.getDbFilePath());
            cursor = db.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }
        return cursor;
    }

    /**
     * 根据自动编号加载资源
     *
     * @param autoNo
     * @return
     */
    public Cursor loadExhibitByAutoNo(int autoNo) {
        String sql = String.format("SELECT * FROM %s WHERE AutoNo = %d",
                HdAppConfig.getLanguage(), autoNo);
        return queryBase(sql);
    }

    /**
     * 加载地图信息（长、宽、缩放级别）
     *
     * @return
     */
    public Cursor loadMapInfo() {
        String sql = String.format("SELECT * FROM MAP");
        return queryBase(sql);
    }
    /**
     * 根据language加载资源
     *
     * @param Language
     * @return
     */
    public Cursor loadExhibitByLanguage(String Language) {
        String sql = "SELECT * FROM " + Language;
        return queryBase(sql);
    }
    /*
      * 查询展品编号查询展品
      *
      * */
    public Cursor QueryExhibit(String fileNO) {
        String sql = String.format("SELECT * FROM %s WHERE exhibit_id = '%s'",
                HdAppConfig.getLanguage(), fileNO);
        return queryBase(sql);
    }

}
