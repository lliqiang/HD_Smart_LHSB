package com.hengda.linhai.m.ui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdviceActicity extends Activity {

    @Bind(R.id.back_advice)
    ImageView backAdvice;
    @Bind(R.id.wv_advice)
    WebView wvAdvice;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asvice_acticity);
        ButterKnife.bind(this);
        backAdvice.setOnClickListener(view -> finish());

        wvAdvice.getSettings().setUseWideViewPort(true);
        wvAdvice.setBackgroundColor(0);

        wvAdvice.setWebViewClient(new WebViewClient() {
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
        path = "http://" + HdAppConfig.getDefaultIpPort() + "/lhbwg/" + "index.php?g=mapi&m=Interactive&a=papers&language=" + HdAppConfig.getLanguage();
        wvAdvice.loadUrl(path);
    }
}
