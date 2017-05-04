package com.hengda.linhai.m.ui;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.app.HdConstant;
import com.hengda.linhai.m.bean.AppBean;
import com.hengda.linhai.m.bean.CheckResponse;
import com.hengda.linhai.m.bean.DeviceBean;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.bean.RxBusBaseMessage;
import com.hengda.linhai.m.bean.TcpResp;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.db.HBriteDatabase;
import com.hengda.linhai.m.http.FileApi;
import com.hengda.linhai.m.http.FileCallback;
import com.hengda.linhai.m.http.HttpResponse;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.http.RequestSubscriber;
import com.hengda.linhai.m.receiver.NetStateMonitor;
import com.hengda.linhai.m.tools.AppUtil;
import com.hengda.linhai.m.tools.CommonUtil;
import com.hengda.linhai.m.tools.DataManager;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.FileUtils;
import com.hengda.linhai.m.tools.NetUtil;
import com.hengda.linhai.m.tools.RxBus;
import com.hengda.linhai.m.tools.SDCardUtil;
import com.hengda.linhai.m.tools.StatusBarCompat;
import com.hengda.linhai.m.tools.ToastUtil;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.update.CheckCallback;
import com.hengda.linhai.m.view.DialogCenter;
import com.hengda.linhai.m.view.DialogClickListener;
import com.hengda.zwf.versioninfo.VersionUtil;
import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Permission;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadStatus;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title_language)
    ImageView titleLanguage;
    @Bind(R.id.chinese_language)
    ImageView chineseLanguage;
    @Bind(R.id.enlish_language)
    ImageView enlishLanguage;
    @Bind(R.id.japanese_language)
    ImageView japaneseLanguage;
    @Bind(R.id.korean_language)
    ImageView koreanLanguage;
    @Bind(R.id.text)
    TextView text;
    private NetStateMonitor netStateMonitor;
    TextView txtProgress;
    TextView txtUpdateLog;
    private AppBean mAppBean;
    private Subscription apkDownloader;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        StatusBarCompat.translucentStatusBar(this);
        ButterKnife.bind(this);
        chineseLanguage.setOnClickListener(this);
        enlishLanguage.setOnClickListener(this);
        japaneseLanguage.setOnClickListener(this);
        koreanLanguage.setOnClickListener(this);


        checkPermission(R.string.base_permission, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    if (NetUtil.isConnected(LanguageActivity.this)) {
                        if (!HdAppConfig.isDeviceNo()) {
                            RequestApi.getInstance().reqDeviceNo(new Subscriber<DeviceBean.DataBean>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(DeviceBean.DataBean dataBean) {
                                    HdAppConfig.setDeviceNo(dataBean.getDevice_id());
                                }
                            }, "AND");
                        }
                        Log.i("decvice", "device: ---------------------" + HdAppConfig.getDeviceNo());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String ipPort = HdAppConfig.getDefaultIpPort();
                                String deviceNo = HdAppConfig.getDeviceNo();
                                try {
                                    Socket client = new Socket(ipPort, 8282);
                                    receiveMsg(client, ipPort, deviceNo);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }
            }
        });

        text.setOnClickListener(view -> {
            VersionUtil.showVersionInfo(this, R.mipmap.img_logo, ContextCompat.getColor(this, R.color.colorPrimary), "测试版v1.0", "2017.4.18", "liqiang");
        });
        registerNetStateMonitor();
//        RxBus.getDefault().toObservable(RxBusBaseMessage.class).subscribe(new Action1<RxBusBaseMessage>() {
//            @Override
//            public void call(RxBusBaseMessage rxBusBaseMessage) {
//                if (rxBusBaseMessage.getCode() == 1) {
//                    int autoNum = ((Exhibition) rxBusBaseMessage.getObject()).getAutonum();
//                    StringBuilder sb = new StringBuilder();
//                    String table = HdAppConfig.getLanguage();
//                    sb.append("SELECT * FROM ").append(table)
//                            .append(" WHERE autonum =")
//                            .append(autoNum + 1);
//                    HBriteDatabase.getInstance()
//                            .getDb()
//                            .createQuery(table, sb.toString(), new String[]{})
//                            .mapToList(cursor -> {
//                                return Exhibition.CursorToModel(cursor);
//                            })
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .flatMap(exhibitions -> Observable.from(exhibitions))
//                            .filter(new Func1<Exhibition, Boolean>() {
//                                @Override
//                                public Boolean call(Exhibition exhibition) {
//                                    return exhibition != null;
//                                }
//                            })
//                            .subscribe(new Action1<Exhibition>() {
//                                @Override
//                                public void call(Exhibition exhibition) {
//                                    Intent intent = new Intent(LanguageActivity.this, PlayActivity.class);
//                                    intent.putExtra("exhibition", exhibition);
//                                    startActivity(intent);
//                                }
//                            });
//
//                }
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                ToastUtil.showToast(throwable.getMessage());
//            }
//        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chinese_language:
                HdAppConfig.setLanguage("CHINESE");
                CommonUtil.configLanguage(LanguageActivity.this, "CHINESE");
                openActivity(LanguageActivity.this, MainActivity.class);
                break;
            case R.id.enlish_language:
                HdAppConfig.setLanguage("ENGLISH");
                CommonUtil.configLanguage(LanguageActivity.this, "ENGLISH");
                openActivity(LanguageActivity.this, MainActivity.class);
                break;
            case R.id.japanese_language:
                HdAppConfig.setLanguage("JAPANESE");
                CommonUtil.configLanguage(LanguageActivity.this, "JAPANESE");
                openActivity(LanguageActivity.this, MainActivity.class);
                break;
            case R.id.korean_language:
                HdAppConfig.setLanguage("KOREAN");
                CommonUtil.configLanguage(LanguageActivity.this, "KOREAN");
                openActivity(LanguageActivity.this, MainActivity.class);
                break;

        }
    }

    /**
     * 注册网络状态监听器
     */
    private void registerNetStateMonitor() {
        netStateMonitor = new NetStateMonitor() {
            @Override
            public void onConnected() {
                checkNewVersion(new CheckCallback() {
                    @Override
                    public void hasNewVersion(CheckResponse checkResponse) {
                        showHasNewVersionDialog(checkResponse);

                    }

                    @Override
                    public void isAlreadyLatestVersion() {
                        super.isAlreadyLatestVersion();
                    }
                });
            }

            @Override
            public void onDisconnected() {
                Toasty.warning(LanguageActivity.this, "网络未连接", Toast.LENGTH_SHORT, true).show();
            }
        };
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netStateMonitor, mFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netStateMonitor);
    }


    /**
     * 检查新版本
     */
    public void checkNewVersion(final CheckCallback callback) {
        if (NetUtil.isConnected(LanguageActivity.this)) {
            RequestApi.getInstance(HdConstant.APP_UPDATE_URL)
                    .checkVersion(new RequestSubscriber<CheckResponse>() {
                        @Override
                        public void succeed(CheckResponse checkResponse) {
                            super.succeed(checkResponse);
                            switch (checkResponse.getStatus()) {
                                case "2001":
                                    callback.isAlreadyLatestVersion();
                                    break;
                                case "2002":
                                    callback.hasNewVersion(checkResponse);
                                    break;
                                case "4041":
                                    break;
                            }
                        }
                    });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (HdAppConfig.getLanguage()) {
            case "CHINESE":
                chineseLanguage.setSelected(true);
                enlishLanguage.setSelected(false);
                japaneseLanguage.setSelected(false);
                koreanLanguage.setSelected(false);
                break;
            case "ENGLISH":
                chineseLanguage.setSelected(false);
                enlishLanguage.setSelected(true);
                japaneseLanguage.setSelected(false);
                koreanLanguage.setSelected(false);
                break;
            case "JAPANESE":
                chineseLanguage.setSelected(false);
                enlishLanguage.setSelected(false);
                japaneseLanguage.setSelected(true);
                koreanLanguage.setSelected(false);
                break;
            case "KOREAN":
                chineseLanguage.setSelected(false);
                enlishLanguage.setSelected(false);
                japaneseLanguage.setSelected(false);
                koreanLanguage.setSelected(true);
                break;
        }

    }

    /**
     * 有新版本时显示Dialog
     *
     * @param checkResponse
     */
    public void showHasNewVersionDialog(final CheckResponse checkResponse) {
        ScrollView scrollView = (ScrollView) View.inflate(LanguageActivity.this, R.layout
                .dialog_custom_view_scroll_txt, null);
        txtUpdateLog = ViewUtil.getView(scrollView, R.id.tvUpdateLog);
        txtUpdateLog.setText(getString(R.string.check_version) + checkResponse.getVersionInfo().getVersionName() + getString(R.string.update_log)
                + checkResponse.getVersionInfo().getVersionLog());

        DialogCenter.showDialog(LanguageActivity.this, scrollView, new DialogClickListener() {
            @Override
            public void p() {
                super.p();
                showDownloadingDialog();
                loadAndInstall(checkResponse);
            }

            /**
             * 下载并安装新版Apk
             *
             * @param checkResponse
             */
            private void loadAndInstall(CheckResponse checkResponse) {
                String apkUrl = checkResponse.getVersionInfo().getVersionUrl();
                String baseUrl = apkUrl.substring(0, apkUrl.lastIndexOf("/") + 1);
                String fileName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);
                String fileStoreDir = HdConstant.getDefaultFileDir();
                FileApi.getInstance(baseUrl).loadFileByName(fileName,
                        new FileCallback(fileStoreDir, fileName) {
                            @Override
                            public void progress(long progress,
                                                 long total) {
                                txtProgress.setText(String.format(getString(R.string.loading),
                                        DataManager.getFormatSize(progress),
                                        DataManager.getFormatSize(total)));
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call,
                                                  Throwable t) {
                                DialogCenter.hideDialog();
                            }

                            @Override
                            public void onSuccess(File file) {
                                DialogCenter.hideDialog();
                                AppUtil.installApk(LanguageActivity.this, file.getAbsolutePath());
                            }
                        });
            }

            private void loadAndInstall() {
                String apkUrl = mAppBean.getVersionInfo().getVersionUrl();
                String baseUrl = apkUrl.substring(0, apkUrl.lastIndexOf("/") + 1);
                String fileName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);
                String fileStoreDir = HdAppConfig.getDefaultFileDir();

                apkDownloader = RxDownload.getInstance().download(apkUrl, fileName, fileStoreDir)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<DownloadStatus>() {
                            @Override
                            public void call(DownloadStatus downloadStatus) {
                                txtProgress.setText(String.format(getString(R.string.loading)+"(%s/%s)",
                                        DataManager.getFormatSize(downloadStatus.getDownloadSize()),
                                        DataManager.getFormatSize(downloadStatus.getTotalSize())));
                            }
                        }, throwable -> {
                            Logger.e(throwable.getMessage());
                            Toast.makeText(LanguageActivity.this, R.string.load_fail, Toast.LENGTH_SHORT).show();
                            DialogCenter.hideDialog();
                        }, () -> {
                            Logger.e(getString(R.string.load_success));
                            DialogCenter.hideDialog();
                            AppUtil.installApk(LanguageActivity.this, fileStoreDir + fileName);
                        });

            }

            @Override
            public void n() {
                super.n();
                DialogCenter.hideDialog();
            }
        }, new String[]{getString(R.string.edit_update), getString(R.string.update), getString(R.string.cancel)});

    }

    /**
     * 显示下载Apk Dialog
     */
    private void showDownloadingDialog() {
        txtProgress = (TextView) View.inflate(LanguageActivity.this, R.layout
                .dialog_custom_view_txt, null);

        txtProgress.setText(R.string.load_app);


        DialogCenter.showDialog(LanguageActivity.this, txtProgress, new DialogClickListener() {
            @Override
            public void p() {
                DialogCenter.hideDialog();
                FileApi.cancelLoading();
            }

            @Override
            public void n() {
            }
        }, new String[]{getString(R.string.loading), getString(R.string.cancel)});
    }

    /**
     * 接收推送
     *
     * @param client
     * @param ipPort
     * @param deviceNo
     * @throws IOException
     */
    private void receiveMsg(Socket client, String ipPort, String deviceNo) {
        while (TextUtils.equals(ipPort, HdAppConfig.getDefaultIpPort()) && TextUtils.equals(deviceNo, HdAppConfig.getDeviceNo())) {
            try {
                DataInputStream input = new DataInputStream(client.getInputStream());
                byte[] b = new byte[10000];
                int length = input.read(b);
                if (length>0){

                    msg = new String(b, 0, length, "gb2312");
                }
                //noinspection deprecation
                if (msg!=null){

                    msg = Html.fromHtml(msg).toString();
                }
                for (TcpResp tcpResp : formatMsg(msg)) {
                    dealTcpResp(client, tcpResp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消息格式化
     *
     * @author 祝文飞（Tailyou）
     * @time 2017/3/8 14:57
     */
    private List<TcpResp> formatMsg(String msg) {
        msg = msg.replace("}", "},");
        msg = msg.substring(0, msg.length() - 1);
        msg = "[" + msg + "]";
        List<TcpResp> tcpRespList = new Gson().fromJson(msg,
                new TypeToken<List<TcpResp>>() {
                }.getType());
        Logger.json(new Gson().toJson(tcpRespList));
        return tcpRespList;
    }

    private void dealTcpResp(Socket client, TcpResp tcpResp) {
        switch (tcpResp.getType()) {
            case "bind":
                bindDevice(tcpResp);
                break;
            case "heart":
                sendMsg(client, "heart_beat");
                break;
            case "send_msg":
                FileUtils.writeStringToFile(SDCardUtil.getSDCardPath() + "PushLog.txt", new Gson().toJson(tcpResp), true);

                break;
        }
    }

    /**
     * 绑定设备
     *
     * @param tcpResp
     */
    private void bindDevice(TcpResp tcpResp) {
        FileUtils.writeStringToFile(SDCardUtil.getSDCardPath() + "PushLog.txt", new Gson().toJson(tcpResp), true);
        HdAppConfig.setClientId(tcpResp.getClient_id());
        RequestApi.getInstance().bindDevice(new Subscriber<HttpResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e("bind device error," + e.getMessage());
            }

            @Override
            public void onNext(HttpResponse httpResponse) {
                Logger.e("bind device success");
            }
        }, HdAppConfig.getDeviceNo(), tcpResp.getClient_id());
    }

    /**
     * socket 发送数据
     *
     * @author 祝文飞（Tailyou）
     * @time 2017/2/4 14:30
     */
    private void sendMsg(Socket client, String heart_beat) {
        try {
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            PrintWriter out = new PrintWriter(wr, true);
            out.println(heart_beat);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
