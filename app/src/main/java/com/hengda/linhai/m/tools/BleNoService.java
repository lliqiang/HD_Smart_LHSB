package com.hengda.linhai.m.tools;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.AutoNum;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.http.RequestSubscriber;
import com.hengda.zwf.autonolibrary.BleNumService;
import com.hengda.zwf.autonolibrary.beacon.Beacon;
import com.hengda.zwf.autonolibrary.beacon.BeaconHelper;
import com.hengda.zwf.autonolibrary.util.NoUtil;
import com.orhanobut.logger.Logger;

import java.util.List;


/**
 * 作者：祝文飞（Tailyou）
 * 邮箱：tailyou@163.com
 * 时间：2016/11/9 14:54
 * 描述：蓝牙收号服务，思路：
 * 1、启动蓝牙收号后，将后台扫描得到的 BluetoothDevice device, int rssi, byte[] scanRecord 解析成Beacon；
 * 2、通过接口回调，在收号服务中处理解析得到的Beacon；
 * 3、根据一定条件过滤，将符合条件的Beacon加入List；
 * 4、每个一定时间间隔从List中取AutoNo，取的原则是：按平均rssi排序，较大为优
 */
public class BleNoService extends BleNumService {

    private static final long TIME_INTERVAL = 2000L; //取号时间间隔
    private static final int RSSI_THRESHOLD = -94;   //有效Rssi阈值
    private NoUtil noUtil;
    private Handler handler;
    private Runnable runnable;
    private int lastAutoNo;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        noUtil = NoUtil.getInstance();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                List<Integer> bestBeaconNums = noUtil.getBestBleNums();

                if (bestBeaconNums.size() > 0) {
                    uploadLocation(bestBeaconNums.get(0));
                    RxBus.getDefault().post(new AutoNum(bestBeaconNums.get(0)));
                }
                handler.postDelayed(this, TIME_INTERVAL);
            }
        };
        handler.postDelayed(runnable, TIME_INTERVAL);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        handler = null;
        BeaconHelper.getBluetoothAdapter(this).disable();
    }

    /**
     * 在此处进行过滤，将符合条件的Beacon加到List
     *
     * @author 祝文飞（Tailyou）
     * @time 2017/1/23 8:49
     */
    @Override
    public void onBeaconReceive(Beacon beacon) {
        if (beacon.getRssi() > RSSI_THRESHOLD) {
            Logger.e("ble no:" + beacon.getMajor());
            noUtil.addBeacon(beacon);
        }
    }

    /**
     * 启动WiFi收号服务
     *
     * @author 祝文飞（Tailyou）
     * @time 2017/1/23 8:45
     */
    @Override
    public void startWifiNoService() {

    }

    /**
     * 停止WiFi收号服务
     *
     * @author 祝文飞（Tailyou）
     * @time 2017/1/23 8:45
     */
    @Override
    public void stopWifiNoService() {

    }

    /**
     * 上传位置
     *
     * @param autoNum
     */
    private void uploadLocation(int autoNum) {
        if (NetUtil.isConnected(BleNoService.this) && lastAutoNo != autoNum) {
            RequestApi.getInstance().uploadLocation(new RequestSubscriber<String>() {
                @Override
                public void succeed(String s) {
                    Logger.e("上传位置成功");
                    lastAutoNo = autoNum;
                }

                @Override
                public void failed(Throwable e) {
                    Logger.e("上传位置失败" + e.getMessage());
                }
            }, String.valueOf(autoNum));
        }
    }

}
