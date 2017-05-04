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

public class MessageDetailActivity extends Activity {

    @Bind(R.id.back_messge_detail)
    ImageView backMessgeDetail;
    @Bind(R.id.web_messge_detail)
    WebView webMessgeDetail;
private String Zixun_id;
    private MessageDetailBean messgeBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        backMessgeDetail.setOnClickListener(view -> finish());
        webMessgeDetail.setBackgroundColor(0);
        Zixun_id=getIntent().getStringExtra("Zixun_id");
        if (Zixun_id!=null){
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
            },HdAppConfig.getDeviceNo(),"NOTICE",Zixun_id);
        }

        webMessgeDetail.setWebViewClient(new WebViewClient() {
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
RequestApi.getInstance().getDetailMessage(new Subscriber<MessageDetailBean>() {
    @Override
    public void onCompleted() {
        if (messgeBean.getData().getUrl()!=null){
//            webMessgeDetail.loadUrl(messgeBean.getData().getUrl());
            webMessgeDetail.loadUrl("https://www.baidu.com/?tn=62095104_oem_dg");
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(MessageDetailBean messageDetailBean) {
        messgeBean=messageDetailBean;
    }
}, HdAppConfig.getLanguage(),Zixun_id);


    }
}
