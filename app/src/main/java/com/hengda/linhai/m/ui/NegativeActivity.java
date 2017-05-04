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

public class NegativeActivity extends Activity {

    @Bind(R.id.back_nagative)
    ImageView backNagative;
    @Bind(R.id.wv_nagative)
    WebView wvNagative;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negative);
        ButterKnife.bind(this);
        backNagative.setOnClickListener(view -> finish());
        wvNagative.getSettings().setUseWideViewPort(true);
        wvNagative.setBackgroundColor(0);

        wvNagative.setWebViewClient(new WebViewClient() {
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
        path="http://" + HdAppConfig.getDefaultIpPort() + "/lhbwg/" + "index.php?g=mapi&m=Interactive&a=liuyan&language=" + HdAppConfig.getLanguage();
        wvNagative.loadUrl(path);

    }
}
