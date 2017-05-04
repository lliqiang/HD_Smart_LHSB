package com.hengda.linhai.m.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.DetailExhibit;
import com.hengda.linhai.m.bean.MapModel;
import com.hengda.linhai.m.bean.SecondBean;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.view.PullDoorView;
import com.stx.xhb.xbanner.XBanner;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class ExhibitActivity extends BaseActivity {

    @Bind(R.id.img_seceondExhibit)
    ImageView imgSeceondExhibit;
    @Bind(R.id.back_secondExhibit)
    ImageView backSecondExhibit;

    @Bind(R.id.img_pull)
    ImageView imgPull;
    @Bind(R.id.img_floor_second)
    ImageView imgFloorSecond;
    @Bind(R.id.pulldoorView)
    PullDoorView pulldoorView;
    @Bind(R.id.img_def)
    ImageView imgDef;
    private Intent intent;
    private String exhibitroom_id;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (pulldoorView.getVisibility() == View.GONE) {
                    intent = new Intent(ExhibitActivity.this, HistoryActivity.class);
//                    intent.putExtra("mapmodel", mapModel);
                    startActivity(intent);
                    imgDef.setVisibility(View.GONE);
                    finish();
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibit);
        ButterKnife.bind(this);
        exhibitroom_id = getIntent().getStringExtra("exhibitroom_id");
        Glide.with(this).load(R.mipmap.img_croll).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgPull);
        ViewUtil.showDownloadProgressDialog(this, "加载中...");
        RequestApi.getInstance().getListDetail(new Subscriber<SecondBean>() {
            @Override
            public void onCompleted() {
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.i("e", e.getMessage());
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onNext(SecondBean secondBean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(ExhibitActivity.this).load(secondBean.getImg()).into(imgSeceondExhibit);
                    }
                });
            }
        }, exhibitroom_id, HdAppConfig.getLanguage());
        backSecondExhibit.setOnClickListener(view -> finish());
        handler.sendEmptyMessage(1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }

    @Override
    protected void onResume() {
        super.onResume();
        pulldoorView.setVisibility(View.VISIBLE);
        pulldoorView.setBgImage(R.mipmap.img_exhibit_def);
        imgDef.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
