package com.hengda.linhai.m.ui;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hengda.frame.hdplayer.BasePlayerActivity;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.fragment.ArFragment;
import com.hengda.linhai.m.fragment.HomeFragment;
import com.hengda.linhai.m.fragment.MapFragment;
import com.hengda.linhai.m.fragment.MineFragment;
import com.hengda.linhai.m.fragment.VrFragment;
import com.hengda.linhai.m.tools.FragmentUtil;
import com.hengda.linhai.m.tools.RxBus;
import com.hengda.linhai.m.tools.StatusBarCompat;
import com.hengda.linhai.m.tools.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends BasePlayerActivity {
    @Bind(R.id.container_frame)
    FrameLayout containerFrame;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_map)
    RadioButton rbMap;
    @Bind(R.id.rb_ar)
    RadioButton rbAr;
    @Bind(R.id.rb_360)
    RadioButton rb360;
    @Bind(R.id.rb_mine)
    RadioButton rbMine;
    @Bind(R.id.main_group)
    RadioGroup mainGroup;
    private HomeFragment homeFragment;
    private MapFragment mapFragment;
    private ArFragment arFragment;
    private VrFragment vrFragment;
    private MineFragment mineFragment;

    private Exhibition exhibition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarCompat.translucentStatusBar(this);
        exhibition = (Exhibition) getIntent().getSerializableExtra("exhibition");
        mainGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        if (homeFragment == null)
                            homeFragment = new HomeFragment();
                        FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_frame,
                                homeFragment, true);
                        break;
                    case R.id.rb_map:
                        if (mapFragment == null)
                            mapFragment = new MapFragment();
                        FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_frame,
                                mapFragment, true);
                        break;
                    case R.id.rb_ar:
                        if (arFragment == null)
                            arFragment = new ArFragment();
                        FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_frame, arFragment, true);
                        break;
                    case R.id.rb_360:
                        if (vrFragment == null)
                            vrFragment = new VrFragment();
                        FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_frame, vrFragment, true);
                        break;
                    case R.id.rb_mine:
                        if (mineFragment == null)
                            mineFragment = new MineFragment();
                        FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_frame, mineFragment, true);
                        break;
                }
            }
        });
        if (exhibition != null) {
            rbMap.setChecked(true);
        } else {

            rbHome.setChecked(true);
        }

    }


}
