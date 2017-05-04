package com.hengda.linhai.m.ui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.MessageDetailBean;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.http.RequestApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class NotifyDetailActivity extends Activity {

    @Bind(R.id.back_notifyDetail)
    ImageView backNotifyDetail;
    @Bind(R.id.web_notify_detail)
    WebView webNotifyDetail;
    private String travel_id;
    private MessageDetailBean messageBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_detail);
        ButterKnife.bind(this);
        travel_id = getIntent().getStringExtra("travel_id");
        if (travel_id != null) {
            RequestApi.getInstance().getReadInfo(new Subscriber<RegisterBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(RegisterBean registerBean) {

                }
            }, HdAppConfig.getDeviceNo(), "ZIXUN", travel_id);
        }

        webNotifyDetail.setBackgroundColor(0);
        webNotifyDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        RequestApi.getInstance().getDetailNotify(new Subscriber<MessageDetailBean>() {
            @Override
            public void onCompleted() {
                if (messageBean != null && messageBean.getData().getUrl() != null) {
                    webNotifyDetail.loadUrl(webNotifyDetail.getUrl());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MessageDetailBean messageDetailBean) {
                messageBean = messageDetailBean;
            }
        }, HdAppConfig.getLanguage(), travel_id);
    }
}
