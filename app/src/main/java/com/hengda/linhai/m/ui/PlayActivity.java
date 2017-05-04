package com.hengda.linhai.m.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hengda.frame.hdplayer.HDExoPlayer;
import com.hengda.frame.hdplayer.MusicService;
import com.hengda.frame.hdplayer.model.MusicTrack;
import com.hengda.frame.hdplayer.util.ControllerUtil;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.CommentDetailBean;
import com.hengda.linhai.m.bean.DetailBean;
import com.hengda.linhai.m.bean.DetailExhibit;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.http.ListBean;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.NetUtil;
import com.hengda.linhai.m.tools.RxBus;
import com.hengda.linhai.m.view.BottomDialog;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;

public class PlayActivity extends BaseActivity {

    @Bind(R.id.xbanner)
    XBanner xbanner;
    @Bind(R.id.wv_play)
    WebView wvPlay;
    @Bind(R.id.heart_play)
    TextView heartPlay;
    @Bind(R.id.message_play)
    TextView messagePlay;
    @Bind(R.id.grid_play)
    GridView gridPlay;
    @Bind(R.id.last_play)
    ImageView lastPlay;
    @Bind(R.id.next_play)
    ImageView nextPlay;
    @Bind(R.id.img_playorpause)
    ImageView imgPlayorpause;
    @Bind(R.id.seekbar)
    SeekBar seekbar;
    @Bind(R.id.erea_name)
    TextView ereaName;
    @Bind(R.id.activity_play)
    RelativeLayout activityPlay;
    @Bind(R.id.share_img_play)
    ImageView shareImgPlay;
    @Bind(R.id.location_play)
    ImageView locationPlay;
    @Bind(R.id.back_play)
    ImageView backPlay;
    private boolean dragging;
    private PlaybackStatus mPlaybackStatus;

    String exhibit_id;
    private List<String> images;
    private List<String> titles;
    private String path;
    private Drawable drawable;
    private Drawable drawable1;
    private boolean flag;
    private CommentDetailBean detailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        exhibit_id = getIntent().getStringExtra("exhibit_id");
        images = new ArrayList<>();
        titles = new ArrayList<>();
        drawable = getResources().getDrawable(R.mipmap.img_heart);
        drawable1 = getResources().getDrawable(R.mipmap.not_heart);
// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawable1.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        initControl();
        initPlaybackStatus();
        RequestApi.getInstance().getCommentDetail(new Subscriber<CommentDetailBean>() {
            @Override
            public void onCompleted() {
                messagePlay.setText("评论 " + detailBean.getData().size() + "");
            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e", e.getMessage());
            }

            @Override
            public void onNext(CommentDetailBean commentDetailBean) {
                detailBean = commentDetailBean;
            }
        }, exhibit_id);
        RequestApi.getInstance().getDetailInfo(new Subscriber<DetailBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e", e.getMessage());
            }

            @Override
            public void onNext(DetailBean detailBean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        heartPlay.setText("喜欢 " + detailBean.getData().getLike_num());
                        path = detailBean.getData().getMp3_path();
                        locationPlay.setOnClickListener(view -> {

                            Intent intent = new Intent(PlayActivity.this, LocationActivity.class);
                            intent.putExtra("exhibit_id", exhibit_id);
                            startActivity(intent);
                        });
                        if (HDExoPlayer.isPlaying()) {//playing
                            imgPlayorpause.setImageResource(R.mipmap.pause);
                        } else if (!HDExoPlayer.isPlaying() && HDExoPlayer.getPosition() == 0) { // begin play
                            HDExoPlayer.prepare(new MusicTrack(0, detailBean.getData().getTitle(), path, R.drawable.def_placeholder, R.drawable.def_placeholder), true);
                            imgPlayorpause.setImageResource(R.mipmap.pause);
                        } else if (!HDExoPlayer.isPlaying() && HDExoPlayer.getPosition() != 0) { //pausing
                            imgPlayorpause.setImageResource(R.mipmap.play);
                        }
                        if (detailBean.getData().getIs_like() == 0) {
                            heartPlay.setCompoundDrawables(drawable1, null, null, null);
                            flag = false;
                        } else {
                            heartPlay.setCompoundDrawables(drawable, null, null, null);
                            flag = true;
                        }
                        heartPlay.setText(detailBean.getData().getLike_num());
                        messagePlay.setText(detailBean.getData().getComment_num());
                        wvPlay.setBackgroundColor(0);
                        wvPlay.setWebViewClient(new WebViewClient() {
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
                        wvPlay.loadUrl(detailBean.getData().getHtml_path());
                        xbanner.setData(detailBean.getData().getImg());
                        xbanner.setmAdapter(new XBanner.XBannerAdapter() {
                            @Override
                            public void loadBanner(XBanner banner, View view, int position) {
                                Glide.with(PlayActivity.this).load(detailBean.getData().getImg().get(position)).into((ImageView) view);
                            }
                        });
                    }
                });

            }
        }, HdAppConfig.getLanguage(), exhibit_id, HdAppConfig.getDeviceNo());


        backPlay.setOnClickListener(view -> finish());
//        nextPlay.setOnClickListener(view -> {
//            // TODO: 2017/4/17 推出返回上一页同时传递
//            RxBus.getDefault().post(1, exhibition);
//            finish();
//
//        });
        messagePlay.setOnClickListener(view -> {
            Intent intent = new Intent(PlayActivity.this, CommentActivity.class);
            intent.putExtra("exhibit_id", exhibit_id);
            startActivity(intent);
        });
        shareImgPlay.setOnClickListener(view -> showShare());
        heartPlay.setOnClickListener(view -> {
            heartState();
        });

    }

    private void heartState() {
        RequestApi.getInstance().clickOk(new Subscriber<RegisterBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e", e.getMessage());
            }

            @Override
            public void onNext(RegisterBean registerBean) {
                if (flag) {
                    heartPlay.setCompoundDrawables(drawable1, null, null, null);
                    Toasty.warning(PlayActivity.this, getString(R.string.click_ok), Toast.LENGTH_SHORT).show();
                } else {
                    heartPlay.setCompoundDrawables(drawable, null, null, null);
                    Toasty.warning(PlayActivity.this, getString(R.string.cancel_click), Toast.LENGTH_SHORT).show();
                }
                flag = !flag;
            }
        }, exhibit_id, HdAppConfig.getDeviceNo());
    }

    private void initControl() {
        seekbar.setMax(ControllerUtil.PROGRESS_BAR_MAX);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                dragging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dragging = false;
                HDExoPlayer.seekTo(seekBar.getProgress() * HDExoPlayer.getDuration() / ControllerUtil.PROGRESS_BAR_MAX);
            }
        });
        imgPlayorpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!HDExoPlayer.isPlaying()) {
                    imgPlayorpause.setImageResource(R.mipmap.pause);
                    HDExoPlayer.play();
                } else {
                    imgPlayorpause.setImageResource(R.mipmap.play);
                    HDExoPlayer.pause();
                }
            }
        });
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    private void initPlaybackStatus() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(MusicService.POSITION_CHANGED);
        filter.addAction(MusicService.STATE_BUFFERING);
        filter.addAction(MusicService.STATE_ERROR);
        filter.addAction(MusicService.STATE_COMPLETED);
        filter.addAction(MusicService.TOGGLEPAUSE_ACTION);
        mPlaybackStatus = new PlaybackStatus();
        registerReceiver(mPlaybackStatus, filter);
    }

    private final class PlaybackStatus extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            if (action.equals(MusicService.POSITION_CHANGED)) {
                long duration = intent.getExtras().getLong("duration");
                long position = intent.getExtras().getLong("position");
                long bufferedPosition = intent.getExtras().getLong("bufferedPosition");
                if (!dragging) {
                    seekbar.setProgress(ControllerUtil.progressBarValue(duration, position));
                    seekbar.setSecondaryProgress(ControllerUtil.progressBarValue(duration, bufferedPosition));
                }
            } else if (action.equals(MusicService.STATE_BUFFERING)) {

            } else if (action.equals(MusicService.STATE_ERROR)) {

            } else if (action.equals(MusicService.STATE_COMPLETED)) {

                imgPlayorpause.setImageResource(R.mipmap.play);
            } else if (action.equals(MusicService.TOGGLEPAUSE_ACTION)) {
                if (!HDExoPlayer.isPlaying()) {
                    imgPlayorpause.setImageResource(R.mipmap.play);
                } else {
                    imgPlayorpause.setImageResource(R.mipmap.pause);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mPlaybackStatus);
    }
}
