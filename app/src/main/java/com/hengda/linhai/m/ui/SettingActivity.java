package com.hengda.linhai.m.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.LoginState;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.tools.AppUtil;
import com.hengda.linhai.m.tools.DataManager;
import com.hengda.linhai.m.tools.RxBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.back_setting)
    ImageView backSetting;
    @Bind(R.id.sw)
    Switch sw;
    @Bind(R.id.rl_autoplay)
    RelativeLayout rlAutoplay;
    @Bind(R.id.rl_language_select)
    RelativeLayout rlLanguageSelect;
    @Bind(R.id.rl_clean_res)
    RelativeLayout rlCleanRes;
    @Bind(R.id.rl_edit)
    RelativeLayout rlEdit;
    @Bind(R.id.activity_setting)
    LinearLayout activitySetting;
    @Bind(R.id.tiplanguage)
    TextView tiplanguage;
    @Bind(R.id.cacheSize)
    TextView cacheSize;
    @Bind(R.id.editInfo_Set)
    TextView editInfoSet;
    @Bind(R.id.btn_login)
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        try {
            cacheSize.setText(DataManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        editInfoSet.setText(AppUtil.getVersionName(this) + "");
        backSetting.setOnClickListener(view -> finish());
        rlLanguageSelect.setOnClickListener(view -> {
            openActivity(this, LanguageActivity.class);
        });
        if (HdAppConfig.getLogin()) {
            btnLogin.setVisibility(View.VISIBLE);
            btnLogin.setOnClickListener(view -> {
                HdAppConfig.setLogin(false);
                RxBus.getDefault().post(new LoginState(1));
                finish();
            });
        }else {
            btnLogin.setVisibility(View.GONE);
        }
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    HdAppConfig.setAutoPlay(true);
                } else {
                    HdAppConfig.setAutoPlay(false);
                }
            }
        });
        if (HdAppConfig.getLanguage().equals("CHINESE")) {
            tiplanguage.setText(R.string.chinese);
        } else {
            tiplanguage.setText(HdAppConfig.getLanguage());
        }
    }
}
