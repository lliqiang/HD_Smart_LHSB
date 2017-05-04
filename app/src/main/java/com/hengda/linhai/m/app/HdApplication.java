package com.hengda.linhai.m.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;

import com.hengda.linhai.m.common.map.LocationService;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by lenovo on 2017/4/11.
 */

public class HdApplication extends Application {
    public static Context mContext;
    public static int widthPixels = 1080;// 屏幕宽度
    public static int heightPixels =1920;// 屏幕高度
    public static Typeface typeface;
    public LocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        locationService = new LocationService(getApplicationContext());
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(config);     //UniversalImageLoader初始化
        CrashReport.initCrashReport(getApplicationContext(), "5295ad0071", true);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/fa.TTF");
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
