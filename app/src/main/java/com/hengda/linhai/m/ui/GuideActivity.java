package com.hengda.linhai.m.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.TabAdapter;
import com.hengda.linhai.m.fragment.IntroFragment;
import com.hengda.linhai.m.fragment.TranficFragment;
import com.hengda.linhai.m.tools.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity {

    @Bind(R.id.back_guide)
    ImageView backGuide;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager_guide)
    ViewPager viewpagerGuide;
    private List<String> stringList;
    private List<Fragment> fragmentList;
    private TabAdapter tabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        ButterKnife.bind(this);
        StatusBarCompat.translucentStatusBar(this);
        backGuide.setOnClickListener(view -> finish());
        stringList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        fragmentList.add(new IntroFragment());
        fragmentList.add(new TranficFragment());
        stringList.add("场馆简介");
        stringList.add("票务及交通信息");
        tabAdapter=new TabAdapter(getSupportFragmentManager(),fragmentList,stringList);
        viewpagerGuide.setAdapter(tabAdapter);
        tabAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewpagerGuide);

    }
}
