package com.hengda.linhai.m.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.TabAdapter;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.fragment.MessageFragment;
import com.hengda.linhai.m.fragment.NotifyFragment;
import com.hengda.linhai.m.tools.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageActicity extends BaseActivity {


    @Bind(R.id.back_message)
    ImageView backMessage;
    @Bind(R.id.tabLayout_message)
    TabLayout tabLayoutMessage;
    @Bind(R.id.viewpager_message)
    ViewPager viewpagerMessage;
    @Bind(R.id.activity_message_acticity)
    LinearLayout activityMessageActicity;
    private List<String> tabList;
    private List<Fragment> fragmentList;
    private TabAdapter tabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_acticity);
        ButterKnife.bind(this);
        StatusBarCompat.translucentStatusBar(this);
        backMessage.setOnClickListener(view -> finish());
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        backMessage.setOnClickListener(view -> finish());
        tabList.add(getString(R.string.message_exhibit));
        tabList.add(getString(R.string.notice_activity));
        fragmentList.add(new MessageFragment());
        fragmentList.add(new NotifyFragment());
        tabAdapter = new TabAdapter(getSupportFragmentManager(), fragmentList, tabList);
        viewpagerMessage.setAdapter(tabAdapter);
        tabAdapter.notifyDataSetChanged();
        tabLayoutMessage.setupWithViewPager(viewpagerMessage);

    }
}
